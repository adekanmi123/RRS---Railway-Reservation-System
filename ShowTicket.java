import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.sql.*;

public class ShowTicket implements ActionListener
{	JPanel tdone,tcan;
	JLabel tr,pd,head,canhead,ctr,cpd,err;
	JLabel tickinfo[]=new JLabel[12];
	JLabel ticketinfo[]=new JLabel[12];
	JLabel ctickinfo[]=new JLabel[12];
	JLabel cticketinfo[]=new JLabel[12];
	JLabel sno[]=new JLabel[6];
	JLabel pname[]=new JLabel[6];
	JLabel csno[]=new JLabel[6];
	JLabel cpname[]=new JLabel[6];
	JLabel agelab[]=new JLabel[6];
	JLabel sexlab[]=new JLabel[6];
	JLabel state[]=new JLabel[6];
	JLabel coach[]=new JLabel[6];
	JLabel seat[]=new JLabel[6];
	JLabel cstate[]=new JLabel[6];
	JLabel ccoach[]=new JLabel[6];
	JLabel cseat[]=new JLabel[6];
	Checkbox cancb[]=new Checkbox[6];
	JButton cancelTicket;
	String str[]={" TransactionID   :"," PNR No                :"," Train No.             :"," Train Name        :"," Date                     :"," Class                   :"," From                    :"," To                         :"," Scheduled Departure : "," Quota                  : "," Distance             			:"," Total Fare 	          :"};
	String shead="        SNO.   Name                                          Age           Sex            Status                 Coach             Seat/Birth   ";
	String chead="        SNO.   Name                                                Status                 Coach                Seat/Berth                         Select";
	Color c1,c2;
	LineBorder lb;
	int r,g,b,totpass;
	Font f1,f2;
	int a=0,i,j,c;
	boolean showtickflag=false,canflag=false;
	Ticket tdet=new Ticket();
	Vector<Passenger> vpass,vcan,vcancel;
 ShowTicket()
 { tdone=new JPanel();
   tdone.setLayout(null); 
   //tdone.setBounds(225,130,600,400);
   tdone.setBackground(Color.white);
   
   tcan=new JPanel();
   tcan.setLayout(null); 
   tcan.setBounds(225,130,600,400);
   tcan.setBackground(Color.white);
	
   r=0;g=124;b=200;
   c2=new Color(r,g,b);
   r=228; g=234; b=255;
   c1=new Color(r,g,b);
  
   f1=new Font("Verdana",Font.BOLD,12);
   f2=new Font("TimesNewRoman",Font.PLAIN,12);
   lb=new LineBorder(Color.gray);
     
   cancelTicket=new JButton("Cancel Ticket");
   cancelTicket.setBounds(450,350,120,30);
   cancelTicket.setForeground(Color.white);
   cancelTicket.setBackground(c2);
   cancelTicket.addActionListener(this);
  
   tcan.add(cancelTicket);
   tickDetailsPanel();
   cancelPanel();
 }
 
 public void tickDetailsPanel()
 {  tr=new JLabel(" Ticket Reservation ");
    pd=new JLabel(" Details of Passengers "); 
    head=new JLabel(shead);
	pd.setBounds(10,175,200,20);
    pd.setForeground(c2);
    pd.setFont(f1);
    head.setBounds(0,200,590,20);
    head.setOpaque(true);
    head.setBackground(c1);
	tr.setBounds(10,20,150,20);
	tr.setForeground(c2);
	tr.setFont(f1);
	
	a=0;
	for(j=0,r=50;j<12;j+=2,r+=19)
    {
		for(i=0,c=25;i<2;i++,c+=269)
		{ 	tickinfo[j+i]=new JLabel(""+str[(j+i)]);
			tickinfo[j+i].setBorder(lb);
			tickinfo[j+i].setBounds(c,r,270,20);
			tdone.add(tickinfo[j+i]);
	
			ticketinfo[j+i]=new JLabel();
			if((j+i)==8)
			{ ticketinfo[j+i].setBounds(c+135,r+1,170,19); }
			else
			{ ticketinfo[j+i].setBounds(c+100,r+1,170,19); }
			ticketinfo[j+i].setFont(f2);
			tdone.add(ticketinfo[j+i]);
		}
    }
	for(i=0,r=225;i<6;i++,r+=20)
	{  sno[i]=new JLabel();
	   sno[i].setBounds(35,r,20,20);
	   sno[i].setFont(f2);
	   pname[i]=new JLabel();
	   pname[i].setBounds(60,r,160,20);
	   pname[i].setFont(f2);
	   agelab[i]=new JLabel();
	   agelab[i].setBounds(230,r,20,20);
	   agelab[i].setFont(f2);
	   sexlab[i]=new JLabel();
	   sexlab[i].setBounds(285,r,40,20);
	   sexlab[i].setFont(f2);
	   state[i]=new JLabel();
	   state[i].setBounds(340,r,70,20);
	   state[i].setFont(f2);
	   coach[i]=new JLabel();
	   coach[i].setBounds(430,r,40,20);
	   coach[i].setFont(f2);
	   seat[i]=new JLabel();
	   seat[i].setBounds(500,r,50,20);
	   seat[i].setFont(f2);
	   tdone.add(sno[i]); tdone.add(pname[i]); tdone.add(agelab[i]); tdone.add(sexlab[i]); tdone.add(state[i]); 
	   tdone.add(coach[i]); tdone.add(seat[i]);
	}
	tdone.add(tr); tdone.add(pd); tdone.add(head);
  }
  public void cancelPanel()
  { cpd=new JLabel(" Details of Passengers "); 
	ctr=new JLabel(" Cancel Ticket");
	canhead=new JLabel(chead);
	cpd.setBounds(10,175,200,20);
    cpd.setForeground(c2);
    cpd.setFont(f1);
 
	canhead.setBounds(0,200,590,20);
    canhead.setOpaque(true);
    canhead.setBackground(c1);

	ctr.setBounds(10,20,150,20);
	ctr.setForeground(c2);
	ctr.setFont(f1);
	
	err=new JLabel();
	err.setBounds(20,350,400,20);
	err.setForeground(Color.red);
	err.setFont(f2);
	
	tcan.add(cpd); tcan.add(ctr); tcan.add(canhead);	tcan.add(err);
    for(j=0,r=50;j<12;j+=2,r+=19)
    {
		for(i=0,c=25;i<2;i++,c+=269)
		{ 	ctickinfo[j+i]=new JLabel(""+str[(j+i)]);
			ctickinfo[j+i].setBorder(lb);
			ctickinfo[j+i].setBounds(c,r,270,20);
			tcan.add(ctickinfo[j+i]);
	
			cticketinfo[j+i]=new JLabel();
			if((j+i)==8)
			{ cticketinfo[j+i].setBounds(c+135,r+1,170,19); }
			else
			{ cticketinfo[j+i].setBounds(c+100,r+1,170,19); }
			cticketinfo[j+i].setFont(f2);
			tcan.add(cticketinfo[j+i]);
		}
    }
	for(i=0,r=225;i<6;i++,r+=20)
	{  csno[i]=new JLabel();
	   csno[i].setBounds(35,r,20,20);
	   csno[i].setFont(f2);
	   cpname[i]=new JLabel();
	   cpname[i].setBounds(60,r,160,20);
	   cpname[i].setFont(f2);
	   cstate[i]=new JLabel();
	   cstate[i].setBounds(230,r,70,20);
	   cstate[i].setFont(f2);
	   ccoach[i]=new JLabel();
	   ccoach[i].setBounds(335,r,40,20);
	   ccoach[i].setFont(f2);
	   cseat[i]=new JLabel();
	   cseat[i].setBounds(410,r,60,20);
	   cseat[i].setFont(f2);
	   cancb[i]=new Checkbox();
	   cancb[i].setBounds(550,r,20,20);
	   cancb[i].setVisible(false);
	   tcan.add(csno[i]); tcan.add(cpname[i]);
	   tcan.add(cstate[i]); tcan.add(cancb[i]);
	   tcan.add(ccoach[i]); tcan.add(cseat[i]);
	}
  }
  
  public void showTicketInfo(String pnr)
  { showtickflag=true; 
	String ticketInfo[]=tdet.getTicketInfo(pnr);
    vpass=tdet.getPassengerInfo(pnr);

	for(i=0;i<12;i++)
	{ ticketinfo[i].setText(ticketInfo[i]); 
	}
	
    for(i=0;i<vpass.size();i++)
	{  Passenger p=(Passenger)vpass.elementAt(i);
	   sno[i].setText(""+(i+1));
       pname[i].setText(p.pna);
	   agelab[i].setText(""+p.Age);
	   sexlab[i].setText(p.Sex);
	   state[i].setText(p.status);
	   coach[i].setText(p.coachno);
	   String s="0"+p.seatno;
	   if(s.length()<4)
	   { s="0"+s; }
	   s=s+"/"+p.berth;
	   seat[i].setText(s);
	}
  }
  
  
  public void cancelTicketInfo(String pnr)
  { canflag=true; 
	String ticketInfo[]=tdet.getTicketInfo(pnr);
    vcan=tdet.getPassengerInfo(pnr);

	for(i=0;i<12;i++)
	{ cticketinfo[i].setText(ticketInfo[i]); }
	
    for(i=0;i<vcan.size();i++)
	{  Passenger p=(Passenger)vcan.elementAt(i);
	   csno[i].setText(""+(i+1));
       cpname[i].setText(p.pna);
	   cstate[i].setText(p.status);
	   ccoach[i].setText(p.coachno);
	   String s="0"+p.seatno;
	   if(s.length()<4)
	   { s="0"+s; }
	   s=s+"/"+p.berth;
	   cseat[i].setText(s);
	   cancb[i].setVisible(true);
	}
  }
  public void clearInfo()
  { if(showtickflag)
    { for(i=0;i<12;i++)
      { ticketinfo[i].setText("");   }
	  for(i=0;i<6;i++)
      { sno[i].setText("");
	    pname[i].setText("");
	    agelab[i].setText("");
	    sexlab[i].setText("");
	    state[i].setText("");
	    coach[i].setText("");
	    seat[i].setText("");
   	  }
	  showtickflag=false;
	  vpass.removeAllElements();
	}
	if(canflag)
    { for(i=0;i<12;i++)
      { ticketinfo[i].setText("");   }
	  for(i=0;i<6;i++)
      { csno[i].setText("");
	    cpname[i].setText("");
	    cstate[i].setText("");
	    ccoach[i].setText("");
	    cseat[i].setText("");
		cancb[i].setState(false);
		cancb[i].setVisible(false);
   	  }
	  canflag=false;
	  vcan.removeAllElements();
	}
  }
  
  public void cancellation(Vector<Passenger> vcancel)
  { try
    { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	  Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	  for(i=0;i<vcancel.size();i++)
	  {   Passenger p=(Passenger)vcancel.elementAt(i);
	      CallableStatement clst=conn.prepareCall("{call cancellation(?,?)}");
		  clst.setInt(1,p.pid);
		  clst.setString(2,p.pnr);
		  clst.executeUpdate();
		  clst.close();
	  }
	  conn.close();	  
	}
	catch(Exception e)
	{ e.printStackTrace(); }
  }
  public void actionPerformed(ActionEvent ae)
  { String ac=ae.getActionCommand();    
	if(ac.equals("Cancel Ticket"))
	{ System.out.println(vcan.size()); 
	  vcancel=new Vector<Passenger>(vcan.size());
	  for(i=0;i<vcan.size();i++)
	  { Passenger p=(Passenger)vcan.elementAt(i);
	    if(cancb[i].getState())
		{ vcancel.add(p); }
	  }
	  if(vcancel.size()==0)
	  { err.setText("No passenger selected"); }
	  else
	  { err.setText(""); 
	    cancellation(vcancel);
		cancelTicket.setEnabled(false);
		err.setText("Ticket cancelled, refund amount will be credited to your account");
	  }
	}
  }
}