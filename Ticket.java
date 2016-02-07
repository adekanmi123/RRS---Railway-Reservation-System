import java.util.*;
import java.sql.*;

public class Ticket
{ TrainList tl;
  TrainListObj tob;
  String pnr,fare,userid,dob;
  int totfare,i,totpass;
  Vector<Passenger> vpass;
  String ticketInfo[]=new String[12];
  String sqlmon[]={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
  Ticket()
  { }
  public void setTicket(TrainList t,TrainListObj otb,Vector<Passenger> vp,int tp)
  { tl=t; tob=otb;  vpass=vp;  totpass=tp; }
  public void generatePNR()
  { Calendar cl=Calendar.getInstance();
    int d,m,y,h,mi;
	String sd,sm,sh,smi;
	d=cl.get(Calendar.DATE);
	sd=""+d;
	if(sd.length()<2)
	{ sd="0"+sd; }
	m=cl.get(Calendar.MONTH)+1;
	sm=""+m;
	if(sm.length()<2)
	{ sm="0"+sm; }
	y=cl.get(Calendar.YEAR);
	dob=""+d+"-"+sqlmon[m-1]+"-"+y;
	y-=2000;
	h=cl.get(Calendar.HOUR);
	sh=""+h;
	if(sh.length()<2)
	{ sh="0"+sh; }
	mi=cl.get(Calendar.MINUTE);
	smi=""+mi;
	if(smi.length()<2)
	{ smi="0"+smi; }
	pnr=""+sd+sm+y+sh+smi;
  }
  public void generateTicket(String usr)
  { userid=usr;
    generatePNR();
    try
    {  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	   Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	   // TICKET
	   Statement stick=conn.createStatement();
	   stick.executeUpdate("insert into ticket values('"+pnr+"','"+tl.sqlDate+"',"+tob.trno+",'"+tob.trna+"','"+tl.s1+"','"+tob.art+"','"+tl.s2+"','"+tob.dpt+"',"+tl.dist+","+totpass+",null,"+totfare+",'"+tl.cls+"')");
	   stick.close();
	  // BOOKING HISTORY 
	   Statement sbook=conn.createStatement();
	   sbook.executeUpdate("insert into booking_history values(null,'"+userid+"','"+pnr+"','"+tl.s1+"','"+tl.s2+"','"+tl.sqlDate+"','"+dob+"',null)");
	   sbook.close();
	  // PASSENGERS
	  Statement spass=conn.createStatement();
	  for(i=0;i<totpass;i++)
      { Passenger p=(Passenger)vpass.elementAt(i);
		spass.executeUpdate("insert into passenger values("+(i+1)+",'"+pnr+"','"+p.pna+"',"+p.Age+",'"+p.Sex+"',null,null,null,null)");
	  }
	  spass.close(); conn.close();
	}
	catch(Exception e)
	{ System.out.println(e); e.printStackTrace(); }	
  }	  
  public int FindTotFare()
  { totfare=0;
    int f;
    try
    { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	  Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	  for(i=0;i<totpass;i++)
      { Passenger p=(Passenger)vpass.elementAt(i);
	    if(!p.srzn)
	    { f=tl.getFare(tob.trno,tl.dist,30,p.Sex,conn); }           // No senior citizen checked
		else
		{ f=tl.getFare(tob.trno,tl.dist,p.Age,p.Sex,conn); }
		totfare+=f;
	  }
	  conn.close();
	}
	catch(Exception e)
	{ e.printStackTrace();}
	return totfare;
  }

  String[] getTicketInfo(String pnr)
  {String ticketInfo[]=new String[12]; 
   try
   { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	 Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	 Statement sbkhy=conn.createStatement();
	 ResultSet rbkhy=sbkhy.executeQuery(" select * from booking_history where pnr="+pnr);
	 rbkhy.next();
	 ticketInfo[0]=""+rbkhy.getInt("TransactionID");
	 ticketInfo[1]=pnr;
	 rbkhy.close();	sbkhy.close();
	 Statement stick=conn.createStatement();
	 ResultSet rtick=stick.executeQuery(" select * from ticket where pnr="+pnr);
	 rtick.next();
	 ticketInfo[2]=""+rtick.getInt("Trainno");
	 ticketInfo[3]=""+rtick.getString("trna");
	 String sdoj=rtick.getString("doj");
	 sdoj=sdoj.substring(0,sdoj.indexOf(" "));
	 StringTokenizer sd=new StringTokenizer(sdoj,"-");
	 i=0;
	 String dt[]=new String[sd.countTokens()];
     while(sd.hasMoreTokens())
	 { dt[i]=sd.nextToken();
	  i++;
	 }
	 sdoj=dt[2]+"-"+dt[1]+"-"+dt[0];
	 ticketInfo[4]=sdoj;
	 ticketInfo[5]=""+rtick.getString("classtype");
	 ticketInfo[8]=""+rtick.getString("dep");
	 ticketInfo[10]=""+rtick.getInt("dist")+" KM";
	 ticketInfo[11]=" Rs. "+rtick.getInt("fare");
	 totpass=rtick.getInt("totpass");
	 Statement sstn=conn.createStatement();
	 String s1=rtick.getString("source");
	 String s2=rtick.getString("destination");
	 ResultSet rstn=stick.executeQuery(" select * from station where stncode='"+s1+"' or stncode='"+s2+"'");
	 rstn.next();
	 ticketInfo[6]=""+rstn.getString("stname")+" ("+s1+")";
	 rstn.next();
	 ticketInfo[7]=""+rstn.getString("stname")+" ("+s2+")";
	 ticketInfo[9]="General";
	 rstn.close(); sstn.close();
	 rtick.close(); stick.close();
	 conn.close();
   }
   catch(Exception e)
   { System.out.println(e); 
     e.printStackTrace();
   }
   return ticketInfo;
 }   
 
 Vector<Passenger> getPassengerInfo(String pnr)
 { Vector<Passenger> pinfo=new Vector<Passenger>(6);
   try
   { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	 Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	 Statement spass=conn.createStatement();
	 ResultSet rpass=spass.executeQuery(" select * from passenger where pnr="+pnr+" order by passid");
	 while(rpass.next())
	 { Passenger p=new Passenger();
	   p.pnr=pnr;
	   p.pid=rpass.getInt("passid");
	   p.pna=rpass.getString("pna");
	   p.Age=rpass.getInt("age");
	   p.Sex=rpass.getString("sex");
	   p.status=rpass.getString("status");
	   p.coachno=rpass.getString("coachno");
	   if(p.coachno==null)
	   { p.coachno=""; }
	   p.seatno=rpass.getInt("seatno");
	   p.berth=rpass.getString("berth");
	   if(p.berth==null)
	   { p.berth=""; }
	   pinfo.add(p);
	 }
	 rpass.close();
	 spass.close();
	 conn.close();
    }
    catch(Exception e)
    { System.out.println(e); 
	  e.printStackTrace();
	}
	return pinfo;
  }
  public static int validatePNR(String pnrno)
  { int c=0;
    try
    { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	 Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	 Statement spass=conn.createStatement();
	 ResultSet rpass=spass.executeQuery(" select * from ticket where pnr="+pnrno);
	 while(rpass.next())
	 { c++; }
	 rpass.close();
	 spass.close();
	 conn.close();
	}
	catch(Exception e)
	{ e.printStackTrace(); }
	return c;
  }
}