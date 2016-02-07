import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.sql.*;

public class HistoryGUI implements ActionListener
{ JPanel bookhist,refhist;
  JLabel bookhistlab,lstick,head,err1,err2,canhead,cslist,refhistlab,info;
  JButton shtick,cantick;
  int r,g,b,i,j;
  Font f1,f2;
  Color c1,c2;
  String bookhead=" Select  TransactionID  PNR Number    From        To        Date Of Journey       PNR Status        Date Of Booking";
  String chead="  SNO         TransactionID             PNR Number        Date Of Cancellation          Reason              RefundAmount";
  boolean bkflag=false,canflag=false;
  JRadioButton select[]=new JRadioButton[10];
  BookingHistoryLogic bkl;
  ButtonGroup bgp=new ButtonGroup();
  
  JLabel source[]=new JLabel[10];
  JLabel dest[]=new JLabel[10];
  JLabel pnrno[]=new JLabel[10];
  JLabel trid[]=new JLabel[10];
  JLabel bhistlab[]=new JLabel[10];
  JLabel canhistlab[]=new JLabel[10];
  
  Vector<BookingHistory> vbkh;
  Vector<CancelledHistory> vcan;
  BookingHistory abk=null;
  CardLayout clo;
  JPanel focusPane;
  ShowTicket sht;
  HistoryGUI(BookingHistoryLogic bkl,CardLayout cl,JPanel jp,ShowTicket sh)
  { clo=cl;
    focusPane=jp;
	sht=sh;
    r=228; g=234; b=255;
	c1=new Color(r,g,b);
	r=0; g=124; b=200;
    c2=new Color(r,g,b);
	this.bkl=bkl;
    bookingHistoryPanel();
	cancelPanel();
  }
  public void bookingHistoryPanel()
  { bookhist=new JPanel();
    bookhist.setLayout(null);
	bookhist.setBackground(Color.white);
	bookhist.setBounds(215,130,600,400);
	
	f1=new Font("Verdana",Font.BOLD,15);
	bookhistlab=new JLabel("Booking History");
	bookhistlab.setBounds(10,10,150,30);
	bookhistlab.setFont(f1);
	bookhistlab.setForeground(c2);
	
	f1=new Font("Verdana",Font.BOLD,13);
	lstick=new JLabel("List of Tickets");
	lstick.setBounds(10,50,150,20);
	lstick.setFont(f1);
	lstick.setForeground(c2);
	
	f2=new Font("TimesNewRoman",Font.PLAIN,12);
	head=new JLabel(bookhead);
	head.setFont(f2);
	head.setBounds(0,80,600,20);
	head.setOpaque(true);
	head.setBackground(c1);
	head.setFont(f2);
	
	f2=new Font("TimesNewRoman",Font.PLAIN,12);
	shtick=new JButton("Get PNR Status");
	shtick.setBounds(100,340,150,25);
	shtick.setBackground(c2);
	shtick.setForeground(Color.white);
	shtick.addActionListener(this);
	
	err1=new JLabel("");
	err1.setFont(f2);
	err1.setForeground(Color.red);
	err1.setBounds(20,310,200,20);
	
	info=new JLabel("Any change will be reflected when you next time login");
	info.setForeground(Color.gray);
	info.setBounds(10,370,400,20);
	
	cantick=new JButton("Cancel Ticket");
	cantick.setBounds(300,340,150,25);
	cantick.setBackground(c2);
	cantick.setForeground(Color.white);
	cantick.addActionListener(this);
	
	bookhist.add(bookhistlab);
	bookhist.add(lstick);
	bookhist.add(head);
	bookhist.add(shtick);
	bookhist.add(cantick);
	bookhist.add(err1);
	bookhist.add(info);
	
	for(i=0,r=100;i<10;i++,r+=20)
	{ 	select[i]=new JRadioButton();
		bgp.add(select[i]);
		select[i].setBackground(Color.white);
		select[i].setFont(f2);
		select[i].setBounds(8,r,40,20);
		select[i].setVisible(false);
		select[i].addActionListener(this);
	
		trid[i]=new JLabel();
		trid[i].setBounds(50,r,100,20);
		trid[i].setFont(f2);	
		
		pnrno[i]=new JLabel();
		pnrno[i].setBounds(125,r,100,20);
		pnrno[i].setFont(f2);
		
		source[i]=new JLabel();
		source[i].setBounds(210,r,50,20);
		source[i].setFont(f2);
		
		dest[i]=new JLabel();
		dest[i].setBounds(260,r,50,20);
		dest[i].setFont(f2);
		
		bhistlab[i]=new JLabel();
		bhistlab[i].setBounds(290,r,300,20);
		bhistlab[i].setFont(f2);
		
		bookhist.add(select[i]);
		bookhist.add(trid[i]);
		bookhist.add(pnrno[i]);
		bookhist.add(source[i]);
		bookhist.add(dest[i]);
		bookhist.add(bhistlab[i]);
      }
  }
  public void cancelPanel()
  { refhist=new JPanel();
    refhist.setLayout(null);
	refhist.setBackground(Color.white);
	refhist.setBounds(215,130,600,400);
	
	f1=new Font("Verdana",Font.BOLD,15);
	refhistlab=new JLabel("Cancelled History");
	refhistlab.setBounds(10,10,150,30);
	refhistlab.setFont(f1);
	refhistlab.setForeground(c2);
	
	f1=new Font("Verdana",Font.BOLD,13);
	cslist=new JLabel("List of Cancelled Tickets");
	cslist.setBounds(10,50,200,20);
	cslist.setFont(f1);
	cslist.setForeground(c2);
	
	f2=new Font("TimesNewRoman",Font.PLAIN,12);
	canhead=new JLabel(chead);
	canhead.setFont(f2);
	canhead.setBounds(0,80,600,20);
	canhead.setOpaque(true);
	canhead.setBackground(c1);
	canhead.setFont(f2);
	
	err2=new JLabel("");
	err2.setFont(f2);
	err2.setForeground(Color.red);
	err2.setBounds(20,310,200,20);
	
	info=new JLabel("Any change will be reflected when you next time login");
	info.setForeground(Color.gray);
	info.setBounds(10,370,400,20);
	
	refhist.add(refhistlab);
	refhist.add(cslist);
	refhist.add(canhead);
	refhist.add(err2);
	refhist.add(info);
	
	for(i=0,r=100;i<10;i++,r+=25)
	{
	  canhistlab[i]=new JLabel();
	  canhistlab[i].setBounds(10,r,590,20);
	  canhistlab[i].setFont(f2);
	  refhist.add(canhistlab[i]);
	}
  }  
  public void showBookingHistory()
  { vbkh=bkl.getBookingList();
    BookingHistory bk;
	f2=new Font("TimesNewRoman",Font.PLAIN,12);
    if(vbkh.size()==0)
	{ err1.setText("No history found"); }
	else
	{ err1.setText("");
	  bkflag=true;
	  for(i=0,r=100;i<vbkh.size();i++,r+=20)
	  { if(i==10)
	    { break; }
	    bk=vbkh.elementAt(i);
	    select[i].setText(""+(i+1));
		select[i].setVisible(true);
	    if(i==10)
	    { break; }
		String t=bk.getTransID();
		int x=10-t.length();
		for(j=0;j<x;j++)
		{ t="  "+t; }
		trid[i].setText(t);
		pnrno[i].setText(bk.pnr);
		select[i].setActionCommand(""+i);
		source[i].setText(bk.src);
		dest[i].setText(bk.des);
		t=bk.bookInfo();
		bhistlab[i].setText(t);
      }
	}  
  }
  public void showCancelledHistory()
  { vcan=bkl.getCancelledHist();
    CancelledHistory bk;
	if(vcan.size()==0)
	{ err2.setText("No history found"); }
	else
	{ err2.setText("");
	  canflag=true;
	  for(i=0;i<vcan.size();i++)
	  { bk=vcan.elementAt(i);
	    if(i==10)
		{ break; }
		String t=bk.getTransID();
		int x=10-t.length();
		for(j=0;j<x;j++)
		{ t="  "+t; }
		t=""+(i+1)+"                "+t+"     "+bk.canInfo();
		canhistlab[i].setText("  "+t);
		
	  }
	}
  }
  
  public void clearInfo()
  { if(bkflag)
    { for(i=0;i<10;i++)
	  { select[i].setVisible(false);
	    bhistlab[i].setText("");
		pnrno[i].setText("");
		source[i].setText("");
		dest[i].setText("");
	  }
	  err1.setText("");
	  bkflag=false;
	}
	if(canflag)
	{ for(i=0;i<10;i++)
	  { canhistlab[i].setText("");
	  }
	  err2.setText("");
	 canflag=false;
	}
  }
  public void actionPerformed(ActionEvent ae)
  { String ac=ae.getActionCommand();
	if(ac.equals("Get PNR Status"))
	{ if(abk==null)
	 { err1.setText("Please select one ticket"); }
	 else if(abk.res_stat.equals("CAN"))
	 { err1.setText("Ticket already cancelled"); }
	 else
	 { sht.showTicketInfo(abk.pnr);
	   clo.show(focusPane,"ShowTicket");
	   err1.setText("");
	 }
	}
	else if(ac.equals("Cancel Ticket"))
	{ if(abk==null)
	 { err1.setText("Please select one ticket"); }
	 else
	 { if(abk.res_stat.equals("CAN"))
	   { err1.setText("Ticket already cancelled"); }
	   else if(abk.res_stat.equals("J_OVER"))
	   { err1.setText("Journey over, No modifications are allowed"); }
	   else
	   { sht.cancelTicketInfo(abk.pnr); 
	     clo.show(focusPane,"CancelTicket");
		 err1.setText("");
	   }
	 }
	}
	else
	{   try
		{ int x=Integer.parseInt(ac);
		  abk=(BookingHistory)vbkh.elementAt(x);
		}
		catch(NumberFormatException e)
		{ e.printStackTrace();  }
	}
  }
}