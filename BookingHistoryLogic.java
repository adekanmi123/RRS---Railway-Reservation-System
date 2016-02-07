import java.util.*;
import java.sql.*;

public class BookingHistoryLogic implements Runnable
{ Vector<BookingHistory> vbook=new Vector<BookingHistory>(10);
  Vector<CancelledHistory> vcan=new Vector<CancelledHistory>(10);
  Thread bthread;
  int i;
  String userid;
  BookingHistoryLogic(String una)
  { userid=una;
    bthread=new Thread(this);
	bthread.start();
  }
  public void run()
  { prepareList(); }
  public void prepareList()
  { try
	{ 
	  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	  Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	  Statement st=conn.createStatement();
	  ResultSet rs=st.executeQuery(" select * from booking_history where lower(username)=lower('"+userid+"') order by doj desc,transactionid desc");
	  while(rs.next())
	  { BookingHistory bkh=new BookingHistory();
	    bkh.trid=rs.getInt("TransactionID");
		bkh.pnr=rs.getString("PNR");
		bkh.src=rs.getString("Source");
		bkh.des=rs.getString("Destination");
		String t=rs.getString("DOJ");
		bkh.doj=dateFormat(t);
		t=rs.getString("DOB");
		bkh.dob=dateFormat(t);
		bkh.res_stat=rs.getString("RES_STATUS");
	    vbook.add(bkh); 
	  } 
	 rs.close();
	 st.close();
	 Statement sc=conn.createStatement();
	 ResultSet rc=sc.executeQuery(" select * from cancelhistory where lower(userid)=lower('"+userid+"') order by doc desc,transid desc");
	 while(rc.next())
	 { CancelledHistory cn=new CancelledHistory();
	    cn.trid=rc.getInt("TransID");
		cn.pnr=rc.getString("PNR");
		String t=rc.getString("DOC");
		cn.doc=dateFormat(t);
		cn.reason=rc.getString("REASON");
		cn.ref=rc.getInt("REFUND");
	    vcan.add(cn); 
	  }
	  rc.close();
	  sc.close();
	  
	  Statement sl=conn.createStatement();
	  sl.executeUpdate(" insert into logaudit values('"+userid+"',sysdate,to_char(sysdate,'hh:mi:ss'),'LOGIN')");
	  sl.close();
	  
	  CallableStatement cls=conn.prepareCall("{ call updatetables() }");
	  cls.executeUpdate();
	  cls.close();
	  conn.close();
	}
	catch(Exception e)
	{ e.printStackTrace();	}
  }	
   
  public Vector<BookingHistory> getBookingList()
  { return vbook; }
  
  public Vector<CancelledHistory> getCancelledHist()
  { return vcan; }
  
  public String dateFormat(String s)
  { String sdoj=s.substring(0,s.indexOf(" "));
	StringTokenizer sd=new StringTokenizer(sdoj,"-");
	i=0;
	String dt[]=new String[sd.countTokens()];
    while(sd.hasMoreTokens())
	{ dt[i]=sd.nextToken();
	  i++;
	}
	sdoj=dt[2]+"-"+dt[1]+"-"+dt[0];
	return sdoj;
  }
}