import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.border.*;

class LoginPage extends Logo implements ActionListener
{   JPanel enter,side,links;
    JButton signout,bh,can,up,cp,ftr,res,dsel,home;
    JLabel plan,viewDate,mname,err,quota;
	JComboBox from,to,cls;
	JDialog jd;
    Font f1,f2;
	int a;
   JPanel CalPanel,MonthPane,focusPane;
   JButton prev,next;
   Calendar cal;
   String Monthlist[]={"January","February","March","April","May","June","July","August","September","October","Novemeber","December"};
   JButton[] button = new JButton[49];
   int day,totdays,year,curmon,finalmon,today,thismon;
   String selection,username,userid;
   LoginPage(String uid,String una)
   { super(uid);
     setLayout(null);
	 userid=uid;
	 username=una;
     side=new JPanel();
     links=new JPanel();  
     side.setLayout(null);
	 links.setLayout(null);
     f1=new Font("Verdana",Font.BOLD,12);
     f2=new Font("TimesNewRoman",Font.PLAIN,12);
     initCal();
     sidePanel();
	 linksPanel();
     setDefaultCloseOperation(EXIT_ON_CLOSE);  
   }
   void linksPanel()
   { links.setBackground(Color.orange);
     links.setBounds(10,110,805,20);
	 signout=new JButton("SignOut");
     signout.setHorizontalAlignment(SwingConstants.RIGHT);
     signout.setBorderPainted(false);
     signout.setOpaque(false);
	 signout.setFont(f1);
     signout.setBackground(Color.orange);
	 signout.setForeground(Color.white);
     signout.setBounds(700,0,100,20);
     signout.addActionListener(this);
	 
	 home=new JButton("Home");
     home.setBorderPainted(false);
     home.setOpaque(false);
	 home.setFont(f1);
     home.setBackground(Color.orange);
	 home.setForeground(Color.white);
     home.setBounds(600,0,100,20);
     home.addActionListener(this);
	 home.setVisible(false);
	 
	 con.add(links); links.add(signout); links.add(home);
  }
  void sidePanel()
  {  String pln[]={"From","To","Date","Class"};
     Label lab[]=new Label[5];
	 int i,r;
     side.setBounds(10,130,200,400);
     side.setBackground(Color.white);
     con.add(side);     
	 
     plan=new JLabel(" Plan My Travel");
     plan.setBounds(0,0,200,20);
     plan.setOpaque(true);
     plan.setBackground(c2);
     plan.setForeground(Color.white); 
     side.add(plan);
	 
	 err=new JLabel();
	 err.setBounds(10,370,100,20);
	 err.setForeground(Color.red);
	 err.setFont(f2);
	 side.add(err);
	 
	 for(i=0,r=30;i<4;i++,r+=30)
	 { lab[i]=new Label(pln[i]);
	   lab[i].setBounds(10,r,50,15);
	   side.add(lab[i]);
	 }
     setSelection();    
	 viewDate=new JLabel(getSelection());
	 viewDate.setBounds(70,90,90,20);
	 viewDate.setBorder(new LineBorder(Color.gray));
	 viewDate.setFont(f2);
	 side.add(viewDate);
	 
	 dsel=new JButton("..");
	 dsel.setBounds(170,90,22,20);
	 side.add(dsel);
	 dsel.addActionListener(this);
     from=new JComboBox();
     from.setBounds(70,27,120,20);
	 from.setFont(f2);
	 from.setBackground(Color.white);
	 to=new JComboBox();
     to.setBounds(70,57,120,20);
	 to.setFont(f2);
	 to.setBackground(Color.white);
	 from.addItem("Station 1");
	 to.addItem("Station 2");
	 String cd,nm;
	 try
	 {  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	    Connection connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
		Statement st=connection.createStatement();
		ResultSet rs=st.executeQuery("select * from station order by stncode");
		while(rs.next())
		{ cd=rs.getString("stncode");
		  nm=rs.getString("stname");
		  from.addItem(cd+" "+nm);
		  to.addItem(cd+" "+nm);
		}
		rs.close();
		st.close();
		connection.close();
	 }
	 catch(Exception e)
	 { System.out.println(e); }
     cls=new JComboBox();
	 cls.setFont(f2);
     cls.setBounds(70,117,120,20);
	 cls.setBackground(Color.white);
	 cls.addItem("SS"); cls.addItem("SL"); cls.addItem("CC"); cls.addItem("FC"); cls.addItem("AC1"); cls.addItem("AC2"); 
	 cls.addItem("AC3");

     side.add(from);      side.add(to);
     side.add(cls);      
	 JLabel serv=new JLabel(" Services");
     serv.setBounds(0,205,200,20);
     serv.setOpaque(true);
     serv.setBackground(c2);
     serv.setForeground(Color.white);
	 side.add(serv);
	 
	 JLabel usp=new JLabel(" User Profile");
     usp.setBounds(0,295,200,20);
     usp.setOpaque(true);
     usp.setBackground(c2);
     usp.setForeground(Color.white);
	 side.add(usp);
	 
	 bh=new JButton(" Booked History");
	 can=new JButton(" Cancelled History");
	 up=new JButton(" Update Profile");
	 cp=new JButton(" Change Password");	 
	 bh.setHorizontalAlignment(SwingConstants.LEFT);
	 bh.setBackground(c1);
	 bh.setBounds(0,225,200,20);
	 can.setHorizontalAlignment(SwingConstants.LEFT);
	 can.setBackground(c1);
	 can.setBounds(0,245,200,20);
	 up.setHorizontalAlignment(SwingConstants.LEFT);
	 up.setBackground(c1);
	 up.setBounds(0,315,200,20);
	 cp.setHorizontalAlignment(SwingConstants.LEFT);
	 cp.setBackground(c1);
	 cp.setBounds(0,335,200,20);
	 ftr=new JButton("Find Trains");
	 res=new JButton("Reset");
	 ftr.setBackground(c2);
	 res.setBackground(c2);
	 ftr.setForeground(Color.white);
	 res.setForeground(Color.white);
	 ftr.setBounds(10,150,100,20);
	 res.setBounds(120,150,70,20);
	 side.add(ftr); side.add(res);
	 side.add(bh); side.add(can);
	 side.add(up); side.add(cp);
	 bh.addActionListener(this);
	 can.addActionListener(this);
	 up.addActionListener(this);
	 cp.addActionListener(this);
 }
  void initCal()
  { cal=Calendar.getInstance();
	thismon=cal.get(Calendar.MONTH);
	curmon=thismon;
	finalmon=curmon+2;
	year=cal.get(Calendar.YEAR);
	today=cal.get(Calendar.DATE);
	jd=new JDialog();
	jd.setLayout(null);
	jd.setModal(true);
	setSelection();
	monthPanel();
	calendarPanel();
	jd.add(MonthPane);
	jd.add(CalPanel);
	jd.pack();
    jd.setLocation(200,200);
	jd.setSize(400,270);
  }
  void monthPanel()
  { MonthPane=new JPanel();
    MonthPane.setLayout(null);
    MonthPane.setBounds(0,0,400,30);
	MonthPane.setBackground(Color.white);
	jd.add(MonthPane);
	
	prev=new JButton("<");
	prev.setBackground(c2);
	prev.setForeground(Color.white);
	prev.setBounds(0,0,50,30);
	MonthPane.add(prev);	
	
	mname=new JLabel(Monthlist[thismon]+" "+year);
	mname.setBounds(50,0,300,30);
	mname.setHorizontalAlignment(SwingConstants.CENTER);
	MonthPane.add(mname);
    
    next=new JButton(">");
	next.setBounds(335,0,50,30);
	next.setBackground(c2);
	next.setForeground(Color.white);
	prev.addActionListener(this);
	next.addActionListener(this);
	MonthPane.add(next);
  } 
  void calendarPanel()
  {
    CalPanel=new JPanel(new GridLayout(7,7));
	CalPanel.setBounds(0,30,400,200);
	CalPanel.setBackground(Color.white);
	jd.add(CalPanel);
	String[] header = { "S", "M", "T", "W", "T", "F", "S"};
	for (int x = 0; x < button.length; x++) 
	{ button[x] = new JButton();
      button[x].setFocusPainted(false);
	  button[x].setBorderPainted(false);
      button[x].setOpaque(false); 
      if (x < 7) 
	  {button[x].setBorderPainted(true);
	   button[x].setOpaque(true);
       button[x].setText(header[x]);
       button[x].setBackground(c2);
       button[x].setForeground(Color.white);
      }
	  else
	  { button[x].setBackground(Color.white); }
       CalPanel.add(button[x]);	
	  if(x>6)
	  { button[x].addActionListener(this);
	  }									   
    }
  }
  void displayCal()
  { for (int x = 7; x < button.length; x++) 
	{ button[x].setText("");
    }
	cal.set(year,curmon,1);
	day=cal.get(Calendar.DAY_OF_WEEK);
	totdays=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	int dy,i;
	if(curmon<=thismon)
	{ prev.setEnabled(false); }
	else
	{ prev.setEnabled(true); }
	if(curmon>=finalmon)
	{ next.setEnabled(false); }
	else
    { next.setEnabled(true); }
	mname.setText(Monthlist[curmon]+" "+year);
	for(i=6+day,dy=1;dy<=totdays;i++,dy++)
    { button[i].setText(""+dy);
	  button[i].setForeground(c2);
	  if(curmon==thismon)
	  { if(dy<today)
	    { button[i].setEnabled(false); }
	    else
	    { button[i].setEnabled(true); }
	  }
	  else if(curmon==finalmon)
	  { if(dy>today)
	    { button[i].setEnabled(false); }
	    else
	    { button[i].setEnabled(true); }
	  }
      else
	  { button[i].setEnabled(true); }
	}
 }
 
 public void actionPerformed(ActionEvent ae)
 { String ac=ae.getActionCommand();  }
 
  String getSelection()
  { return selection; }
  void setSelection()
  { selection=""+today+"/"+(curmon+1)+"/"+year;}
}

