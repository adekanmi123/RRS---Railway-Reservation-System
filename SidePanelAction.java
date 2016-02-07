import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.border.*;

public class SidePanelAction extends LoginPage implements ActionListener
{ int i;
  UserModify umfy;
  TrainListPane tlp;
  TicketReserve trs;
  EnterPanel epl;
  CardLayout clo;
  ShowTicket sht;
  HistoryGUI bkh;
  SidePanelAction(String a,String b)
  { super(a,b); 
    BookingHistoryLogic bkl=new BookingHistoryLogic(a);
    clo = new CardLayout();
    focusPane = new JPanel(clo);
    focusPane.setBounds(215,130,600,400);
    con.add(focusPane);
	
	epl=new EnterPanel();
	focusPane.add(epl.enter,"EnterPanel");
	
    ftr.addActionListener(this);
	res.addActionListener(this);
	
	sht=new ShowTicket();
	focusPane.add(sht.tdone,"ShowTicket");
	focusPane.add(sht.tcan,"CancelTicket");
	
	trs=new TicketReserve(userid,clo,focusPane,sht);
	focusPane.add(trs.ticr,"TicketReserve");
	
	tlp=new TrainListPane(trs,clo,focusPane);
	focusPane.add(tlp.tlist,"TrainListPane");
	
	umfy=new UserModify(userid);
	focusPane.add(umfy.umodify,"UserModify");
    focusPane.add(umfy.chpasswd,"ChangePassword");
	
	try
	{ bkl.bthread.join(); }
	catch(Exception e)
    { System.out.println(); }
	
	bkh=new HistoryGUI(bkl,clo,focusPane,sht);
	focusPane.add(bkh.bookhist,"BookingHistory");
	focusPane.add(bkh.refhist,"CancelledHistory");
	
	clo.show(focusPane,"EnterPanel");
  }
  
  public void actionPerformed(ActionEvent ae)
  { String ac=ae.getActionCommand();   
   if(ac.equals("SignOut"))
   { this.dispose(); 
     trs.clearInfo(); 
	 tlp.clear(); 
	 bkh.clearInfo();
	 sht.clearInfo();
	 try
	 { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); 
	   Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	   Statement st=conn.createStatement();
	   st.executeUpdate("insert into logaudit values('"+userid+"',sysdate,to_char(sysdate,'hh:mi:ss'),'LOGOUT')");
	   st.close();
	   conn.close();
	 }
	 catch(Exception e)
	 { e.printStackTrace(); }
	 Login l1=new Login("Login");
	 l1.setVisible(true);
	 l1.setSize(840,370);
   }
   else if(ac.equals("Home"))
   { clo.show(focusPane,"EnterPanel");
     trs.clearInfo(); 
	 tlp.clear(); 
	 bkh.clearInfo();
	 sht.clearInfo();
     home.setVisible(false);
   }
   else if(ac.equals("Find Trains"))
   { String seldate=getSelection(); 
	 String st1,st2,cl;
     st1=(String)from.getSelectedItem();
     st2=(String)to.getSelectedItem();
     cl=(String)cls.getSelectedItem();
	 if(st1.equals("Station 1") || st2.equals("Station 2") || st1.equals(st2))
	 { err.setText("Invalid selection..."); }
     else
	 { err.setText(" ");
	   TrainList tl=new TrainList(st1,st2,cl,seldate);
	   Vector<TrainListObj> v1=tl.getVector(); 
	   home.setVisible(true); 
	   tlp.clear();
	   tlp.setTrainList(tl);
	   tlp.showList(v1);
	   clo.show(focusPane,"TrainListPane");
	 }
  }
  
  else if(ac.equals(" Booked History"))
  { bkh.clearInfo();
    bkh.showBookingHistory();
	clo.show(focusPane,"BookingHistory");
	home.setVisible(true);
  }
  else if(ac.equals(" Cancelled History"))
  { bkh.clearInfo();
    bkh.showCancelledHistory();
    clo.show(focusPane,"CancelledHistory");
	home.setVisible(true);
  }
  else if(ac.equals("Reset"))
  { from.setSelectedIndex(0);
    to.setSelectedIndex(0);
	cls.setSelectedIndex(0);
  }

  else if(ac.equals(" Update Profile"))
  { home.setVisible(true);
    clo.show(focusPane,"UserModify");
    umfy.userModify();
    umfy.msg.setText("");
  }
  else if(ac.equals(" Change Password"))
  { home.setVisible(true);
    clo.show(focusPane,"ChangePassword");
    umfy.changePassword();
    umfy.msg2.setText("");
    umfy.oldpwdf.setText("");
    umfy.newpwdf.setText("");
    umfy.repwdf.setText("");
  }
  else if(ac.equals(">"))
  { curmon++;
    if(curmon==12)
	{ curmon=0;
	  year++;
	}
	displayCal();
  }
  else if(ac.equals("<"))
  { curmon--;
    if(curmon<=0)
    { curmon=11;
	  year--;
	}
	 displayCal();
  } 
  else if(ac.equals(""))
  { selection="";
  } 
  else if(ac.equals(".."))
  { displayCal();
	jd.setVisible(true);
  }
  else
  { try
	{ day=Integer.parseInt(ac); 
	  selection=""+day+"/"+(curmon+1)+"/"+year; 
	  viewDate.setText(selection);
	  jd.setVisible(false);
     }
	 catch(Exception e)
     { }
  }
 }
}
