import java.sql.*;
import java.util.*;

public class TrainList
{ Vector<TrainListObj> vtrain=new Vector<TrainListObj>(5,5);
  Vector<TrainListObj> vtrain2=new Vector<TrainListObj>(5,5);
  TrainListObj ot1,ot2;
  StringTokenizer st;
  String dj,avlb;
  float a1,a2;
  String s1,s2,cls,sna1,sna2,sqlDate,quota,avl;
  int i,j,t,doj,x,fare,dist;
  String weekDay[]={"SUN","MON","TUE","WED","THU","FRI","SAT"};
  String sqlmon[]={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
  int dd,mm,yy;
  Calendar cal;
  TrainList(String a,String b,String cl,String d)
  { cls=cl;
	x=a.indexOf(" ");
	s1=a.substring(0,x);
	sna1=a.substring((x+1),a.length());
	x=b.indexOf(" ");
	s2=b.substring(0,x);
	sna2=b.substring((x+1),b.length());
    dj=d; quota="General";
	makeDate();
  }
  public void makeDate()
  { st=new StringTokenizer(dj,"/");
    String dt[]=new String[st.countTokens()];
	i=0;
    while(st.hasMoreTokens())
	{ dt[i]=st.nextToken();
	  i++;
	}
	dd=Integer.parseInt(dt[0]);
	mm=Integer.parseInt(dt[1]);
	yy=Integer.parseInt(dt[2]);
	cal=Calendar.getInstance();
	cal.set(yy,(mm-1),dd);
	doj=cal.get(Calendar.DAY_OF_WEEK);
	doj=doj-1;
	sqlDate=""+dd+"-"+sqlmon[mm-1]+"-"+yy;
	prepareList();
  }
  public void prepareList()
  { int dflag=0;
    try
    {  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	   Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
       Statement st=conn.createStatement();
	   ResultSet rs=st.executeQuery("select * from TrainJourney order by trainno,distance");     //ORDER
	   String sna;
	   int t1=0,trno,dy=0;
	   i=0;
	   String arr="",dep="",s="";
	   String c="";
	   
	   while(rs.next())
	   { sna=rs.getString("stncode");
	     trno=rs.getInt("trainno");
	     if(sna.equals(s1))
		 { t1=trno; 
		   arr=rs.getString("Dep");					//GET DEP TIME
		   dy=rs.getInt("day");
		 }
		 else if(sna.equals(s2) && trno==t1)
		 {  Statement st3=conn.createStatement();
	        ResultSet rs3=st3.executeQuery("select * from trainclass where trainno="+t1);
		    rs3.next();
			if(dflag==0)
			{	Statement stdist=conn.createStatement();
				ResultSet rsdist=stdist.executeQuery("select distance from trainjourney where stncode='"+s1+"' and trainno="+trno);
				rsdist.next();
				int d1=rsdist.getInt("distance");
				rsdist.close();	stdist.close();
				Statement stdist1=conn.createStatement();
				ResultSet rsdist1=stdist1.executeQuery("select distance from trainjourney where stncode='"+s2+"' and trainno="+trno);
				rsdist1.next();
				int d2=rsdist1.getInt("distance");
				rsdist1.close();	stdist1.close();
				dist=(int)Math.abs(d2-d1);
				dflag=1;
		   }
			if(rs3.getInt(cls)!=0)
			{   dep=rs.getString("Arr");				// GET ARR TIME
				Statement st2=conn.createStatement();
				t=doj-dy+1;
				if(t<0)
				{ t=t+7;}
				t=t%7;
				s=weekDay[t];
				ResultSet rs2=st2.executeQuery("select tname,"+s+" from trainmaster where trainno="+t1);
				rs2.next();
				c=rs2.getString(s);
				
				// GET AVAILIBILITY
				avlb=getAvailibility(trno,conn);
				// GET FARE
				int fre=getFare(trno,dist,30,"M",conn);
				if(c.equals("Y"))
				{ TrainListObj ot=new TrainListObj(i,trno,rs2.getString("tname"),arr,dep,avlb,dist);	
				  ot.setFare(fre);
				  vtrain.add(ot);
				  i++;
				}
				rs2.close();
				st2.close();
			}
		    t1=0;
			rs3.close();
			st3.close();
		 }
	   }
	   rs.close();
	   st.close();
       conn.close();
	}
	catch(Exception e)
	{ System.out.println(e); }
	sortList();
  }
  
  public String getAvailibility(int tr,Connection conn)
  {	try
    {	CallableStatement clst=conn.prepareCall("{call getavailibility(?,?,?,?)}");
		clst.setString(1,sqlDate);
		clst.setInt(2,tr);
		clst.setString(3,cls);
		clst.registerOutParameter(4,java.sql.Types.VARCHAR);
		clst.executeUpdate();
		avl=clst.getString(4);
		clst.close();
	}
	catch(Exception e)
	{ System.out.println(e); }
	return avl;
  }
  
  public int getFare(int tr,int d,int a,String s,Connection conn)
  {	int fr=0;
    try
    {	CallableStatement frc=conn.prepareCall("{call farecalc(?,?,?,?,?,?)}");
		frc.setInt(1,tr);
		frc.setString(2,cls);
		frc.setInt(3,d);
		frc.setInt(4,a);
		frc.setString(5,s);
		frc.registerOutParameter(6,java.sql.Types.NUMERIC);
		frc.executeUpdate();
		fr=frc.getInt(6);
		frc.close();
	}
	catch(Exception e)
	{ System.out.println(e); }
	return fr;
  }
  
  public void sortList()
  { int a[]=new int[vtrain.size()];
	for(i=0;i<vtrain.size();i++)
	{ ot1=(TrainListObj)vtrain.elementAt(i);
	  a1=ot1.getArrival();
	  for(j=i+1;j<vtrain.size();j++)
	  { ot2=(TrainListObj)vtrain.elementAt(j);
	    a2=ot2.getArrival();
		if(a1>a2)
		{ t=ot1.index;
		  ot1.index=ot2.index;
		  ot2.index=t;
		}
	  }
	  a[i]=ot1.index;
	}
	vtrain2.addAll(vtrain);
	for(i=0;i<vtrain.size();i++)
	{ ot1=(TrainListObj)vtrain.elementAt(i);
	  vtrain2.set(a[i],ot1);
	}
  }
  
  public int getRecords()
  { return (int)vtrain2.size(); }
  
  public Vector<TrainListObj> getVector()
  { return vtrain2; }
  
  public TrainListObj getTrainInfo(int tno)
  { TrainListObj ot=null;
    for(i=0;i<vtrain2.size();i++)
    { ot=(TrainListObj)vtrain2.elementAt(i);
	  if(ot.trno==tno)
	  { break; }
	}
	return ot;
  }
  
  public String getSqlDate()
  { return sqlDate; }
}           
