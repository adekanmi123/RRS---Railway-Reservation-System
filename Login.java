import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.io.*;


public class Login extends Logo implements ActionListener
{ JPanel lg,links,frgtpwd;
  JLabel lin,un,ps,enq;
  TextField tf1,tf2;
  JButton log,sup,fop,trenq,rsenq,pnrenq,closefrgt,generate;
  JLabel err,fpwdlbl,fsecq,fseca,anserr;
  String userid,pswd,usernamest;
  JTextField safield;
  int i;  
  boolean enqflag=false,fgtflag=false;
  TrainEnquiry trq=null;
  Login(String s)
  { super(s);
    setLayout(null);
	con=getContentPane();
	
	lg=new JPanel();
	links=new JPanel();
	loginPanel();
	linksPanel();
	userid="";
    log.addActionListener(this);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  public void loginPanel()
  {	lg.setLayout(null);
    lg.setBounds(10,120,400,200);
	lin=new JLabel("  Login");
    tf1=new TextField(20);
	tf2=new TextField(20);
	err=new JLabel();
	un=new JLabel("Username :");
	ps=new JLabel("Password :");
	log=new JButton(" Login");
	log.setBackground(c2);
	log.setForeground(Color.white);
	sup=new JButton(" Sign-up");
	sup.setBackground(c2);
	sup.setForeground(Color.white);
	fop=new JButton("Forgot Password");
	fop.setBorderPainted(false);
	fop.setOpaque(false);
	fop.setBackground(Color.white);
	fop.setForeground(c2);
	tf2.setEchoChar('*');
	lin.setBounds(0,0,400,30);
	tf1.setBounds(150,40,150,25);
	tf2.setBounds(150,80,150,25);
	un.setBounds(20,40,80,30);
	ps.setBounds(20,80,80,30);
	log.setBounds(50,130,100,30);
	sup.setBounds(200,130,100,30);
	fop.setBounds(100,160,150,30);
	err.setForeground(c2);
	err.setBounds(30,103,300,20);
	lg.setBackground(Color.white);
	lin.setOpaque(true);
	lin.setBackground(c2);
	lin.setForeground(Color.white);
	con.add(lg);
	lg.add(lin); lg.add(tf1); lg.add(tf2); lg.add(un); lg.add(ps); lg.add(log); lg.add(sup); lg.add(fop); lg.add(err);
	sup.addActionListener(this);
	fop.addActionListener(this);
  }	
  public void linksPanel()
  { 
    links.setBounds(415,120,400,200);
	links.setLayout(null);
	links.setBackground(Color.white);
    enq=new JLabel(" Enquiries");
	enq.setBounds(0,0,400,30);
	enq.setOpaque(true);
	enq.setBackground(c2);
	enq.setForeground(Color.white);
	
	trenq=new JButton("Train Enquiries");
	trenq.setBounds(10,40,200,30);
	trenq.setHorizontalAlignment(SwingConstants.LEFT);
	trenq.setBorderPainted(false);
	trenq.setOpaque(false);
	trenq.setBackground(Color.white);
	trenq.setForeground(c2);
	trenq.addActionListener(this);
	
	pnrenq=new JButton(" PNR Enquiries");
    pnrenq.setHorizontalAlignment(SwingConstants.LEFT);
	pnrenq.setBounds(10,80,200,30);
	pnrenq.setBorderPainted(false);
	pnrenq.setOpaque(false);
	pnrenq.setBackground(Color.white);
	pnrenq.setForeground(c2);
	pnrenq.addActionListener(this);
	con.add(links); links.add(enq); links.add(trenq); links.add(pnrenq);
  }
  public void forgotPanel()
  {	  frgtpwd = new JPanel();
      frgtpwd.setLayout(null);
      frgtpwd.setBackground(Color.white);
      frgtpwd.setBounds(10,340,800,200);
      frgtpwd.setVisible(false);
      closefrgt = new JButton("X");
      closefrgt.setBackground(c2);
      closefrgt.setForeground(c1);
      closefrgt.setBounds(758,0,42,20);
      closefrgt.addActionListener(this);
      fpwdlbl = new JLabel("Security Question");
      fpwdlbl.setForeground(c2);
      fpwdlbl.setBounds(50,20,200,30);
      fpwdlbl.setFont(new Font("Arial",Font.PLAIN,20));
      fsecq = new JLabel("");
      fsecq.setBounds(50,60,300,20);
      fseca = new JLabel("Answer:");
      fseca.setBounds(50,90,60,20);
      safield = new JTextField();
      safield.setBounds(110,90,200,20);
      generate = new JButton("Generate Password");
      generate.setBounds(50,120,200,20);
	  generate.setBackground(c2);
	  generate.setForeground(Color.white);
      generate.addActionListener(this);
	  anserr=new JLabel("");
	  anserr.setForeground(c2);
	  anserr.setBounds(50,150,450,20);
      con.add(frgtpwd);
	  frgtpwd.add(closefrgt);	frgtpwd.add(fpwdlbl);	frgtpwd.add(fsecq);		frgtpwd.add(fseca);
      frgtpwd.add(safield);		frgtpwd.add(generate);	frgtpwd.add(anserr);
  }
  public void actionPerformed(ActionEvent ae)
  { String ac=ae.getActionCommand();
    if(ac.equals(" Sign-up"))
    { this.setVisible(false);
      SignUp l1=new SignUp("Sign-up");
      l1.setVisible(true);
   	  l1.setSize(840,600);
   }
   else if(ac.equals(" Login"))
   { userid=tf1.getText();
     pswd=tf2.getText();
     boolean vl=validateUser();
	 if(vl)
	 { this.dispose();
	   SidePanelAction lp=new SidePanelAction(userid,usernamest);
	   lp.setSize(840,600);
	   lp.setVisible(true);
	 } 
	 else
	 { err.setText("Username and password didn't match"); }
   }
   else if(ac.equals("Forgot Password"))
   {   String uname = tf1.getText();
       if(enqflag)
	   { trq.tenq.setVisible(false); 
	     enqflag=false;
	   }
       if(uname.equals(""))
       {  err.setText("Enter Username");
	      this.setSize(840,370);
       }
       else
       { forgotPanel();
         err.setText("");
         try
	     { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	       Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	       Statement st=conn.createStatement();
           ResultSet rs = st.executeQuery("select securityq from rrs_user where lower(username)=lower('"+uname+"')");
           rs.next();
           fsecq.setText(rs.getString("securityq"));   
           frgtpwd.setVisible(true);
           rs.close();
           st.close();
           conn.close();	
		   this.setSize(840,600);
		   fgtflag=true;
	   }
	   catch(Exception e)
	   { err.setText("Invalid Username"); }
	   
     }
   }
   else if(ac.equals("Generate Password"))
   {   String uname = tf1.getText();
       if(uname.equals(""))
       { err.setText("Enter Username");  }
       else
       {anserr.setText("");
        try
	    { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	     Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	     Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery("select securitya from rrs_user where lower(username)=('"+uname+"')");
            rs.next();
            String answer1 = rs.getString("securitya");
            String answer2 = safield.getText();
            if(answer1.equalsIgnoreCase(answer2))
            {  anserr.setText("");
                String rnd = getRandomString();
                System.out.println(rnd);
                st.executeUpdate("update rrs_user set password='"+rnd+"' where lower(username)=('"+uname+"')");
                FileWriter fw = new FileWriter("password.txt");
                fw.write(""+rnd);
                fw.close();
				generate.setEnabled(false);
				anserr.setText("Password changed, Saved in the file  D:\\BE\\BE3 SS\\Project\\Password.txt");
            }
            else
            { anserr.setText("Wrong Answer"); }
            rs.close();
            st.close();
            conn.close();
	  }
	  catch(Exception e)
	  { System.out.println(e); err.setText("Invalid Username"); }
     }  
   }
   else if(ac.equals("X"))
   {  frgtpwd.setVisible(false);
      fgtflag=false;
	  enqflag=false;
      this.setSize(840,370);
   }
   else if(ac.equals("Train Enquiries"))
   { this.setSize(840,600);
     if(fgtflag)
	 { frgtpwd.setVisible(false); fgtflag=false;}
     trq=new TrainEnquiry(this);
     trq.tenq.setVisible(true);
	 enqflag=true;
   }
   else if(ac.equals(" PNR Enquiries"))
   { PNREnquiry peq=new PNREnquiry("PNR ENQUIRY");
     peq.setVisible(true);
   }   
 }  
  public String getRandomString()
  {
      String s = "";
      char cset[]=new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                             'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                             '0','1','2','3','4','5','6','7','8','9'};
      Random ra = new Random();
      for(int i=0;i<8;i++)
      {
          s+=cset[ra.nextInt(62)];
      }
      return s;
   }
  public boolean validateUser()
  {  boolean ck=false;
     try
	  { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	    Connection connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
		Statement st=connection.createStatement();
		ResultSet rs=st.executeQuery("select * from rrs_user");
	    String usr="";
		i=0;
		while(rs.next())
		{ usr=rs.getString("username");
		  if(usr.equalsIgnoreCase(userid))
		  { String pass=rs.getString("password");
		    ck=pass.equals(pswd);
			if(ck)
			{ userid=usr;
			  usernamest=rs.getString("name"); }
			break;
		  }
		  i++;
		}
		rs.close();
		st.close();
		connection.close();
	 }
	 catch(Exception e)
	 { }
	 return ck;
  }
}
