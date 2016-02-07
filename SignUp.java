import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class SignUp extends Logo implements ActionListener
{
  JPanel form,links,confirm;
  String months[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
  String lab[]={"Username : ","Password : ","Confirm Password :","Security Question : ","Your Answer :","Name : ","Date of Birth : ","Email-ID : ","Mobile : "};
  String seq[]={"Choose your security question ","What is your favourite car?","What was the name of your first school?","What was your childhood hero?","What is your favourite pass time?","What is all time favourite sports-team?","What is your father's middle name?","Who was your favourite teacher?","Where did you meet your first spouse?"};
  String getF[]=new String[11];
  JLabel tag[]=new JLabel[9];
  JLabel err,rrslogo;
  JTextField jf[]=new JTextField[5];
  JPasswordField p1,p2;
  JComboBox sq,dd,mm,yy;
  JButton res,sub,home,go,back;
  Font f;
  int i,r1,r;
  SignUp(String s)
  { super(s);
    setLayout(null);
	initialize();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  public void initialize()
  { sq=new JComboBox();
	dd=new JComboBox();
	mm=new JComboBox();
	yy=new JComboBox();
	
	
	res=new JButton("Reset");
	sub=new JButton("Submit");
    res.setForeground(Color.white);
	res.setBackground(c2);
	sub.setForeground(Color.white);
	sub.setBackground(c2);
	form=new JPanel();
	form.setLayout(null);
	form.setBounds(10,132,805,420);
	form.setBackground(Color.white);
	con.add(form);
	sq.setBounds(200,130,250,20);
	dd.setBounds(200,250,50,20);
	mm.setBounds(260,250,60,20);
	yy.setBounds(330,250,70,20);
	for(i=0;i<9;i++)
	{ sq.addItem(seq[i]); }
	dd.addItem("DD");
	mm.addItem("MM");
	yy.addItem("YYYY");
	for(i=0;i<12;i++)
	{ mm.addItem(months[i]); }
    for(i=1;i<32;i++)
	{ dd.addItem(""+i); }
	Calendar cl=Calendar.getInstance();
	int y=cl.get(Calendar.YEAR);
	y=y-18;
	for(i=y;i>1950;i--)
    { yy.addItem(""+i);   }	
	for(i=0,r=10,r1=10;i<9;i++,r+=40,r1+=40)
    { tag[i]=new JLabel(lab[i]);
	  tag[i].setBounds(20,r,150,20);
	  form.add(tag[i]);
	}
	for(i=0,r=10,r1=10;i<5;i++,r+=40,r1+=40)
    { jf[i]=new JTextField(20);
	      if(i==1)
		  { r1+=120; }
		  if(i==3)
		  { r1+=40; }
		 jf[i].setBounds(200,r1,150,20);
	     form.add(jf[i]);
	}
	JLabel mob=new JLabel("+91");
	form.add(mob);
	f=new Font("Rockwell",Font.PLAIN,15);
	mob.setFont(f);
	mob.setBounds(165,328,40,20);
	p1=new JPasswordField(20);
	p2=new JPasswordField(20);
	p1.setEchoChar('*');
	p2.setEchoChar('*');
	p1.setBounds(200,50,150,20);
	p2.setBounds(200,90,150,20);
	err=new JLabel();
	err.setBounds(600,10,200,20);
	err.setForeground(Color.red);
	form.add(err);
	form.add(p1);
	form.add(p2);
	res.setBounds(400,370,100,30);   sub.setBounds(200,370,100,30);
	form.add(sq);
	form.add(dd); form.add(mm); form.add(yy); 
    form.add(res); form.add(sub);
	
	res.addActionListener(this);
	sub.addActionListener(this);
	
	links=new JPanel();
	links.setLayout(null);
	linksPanel();
	con.add(links); 
	links.add(home);
   }
   public void linksPanel()
   {  links.setBackground(Color.orange);
      links.setBounds(10,110,805,20);
      home=new JButton("Home");
	  home.setBounds(700,0,100,20);
      home.setHorizontalAlignment(SwingConstants.RIGHT);
      home.setBorderPainted(false);
      home.setOpaque(false);
      home.setBackground(Color.orange);
      home.setForeground(Color.white); 
      home.addActionListener(this);
   }
   public void actionPerformed(ActionEvent ae)
   { String ac=ae.getActionCommand();
     if(ac.equals("Reset"))
	 { for(i=0;i<5;i++)
	   { jf[i].setText(""); }
	   sq.setSelectedIndex(0);
	   dd.setSelectedIndex(0);
	   mm.setSelectedIndex(0);
	   yy.setSelectedIndex(0);
	   p1.setText("");
	   p2.setText("");
	   err.setText("");
	 }
	 else if(ac.equals("Home"))
	 { this.setVisible(false); 
	   Login l1=new Login("Login");
	   l1.setVisible(true);
	   l1.setSize(840,600);
	 }
	 else if(ac.equals("Submit"))
	 { ErrorCheck ec=new ErrorCheck();
	   checkFields();
	   boolean chk=ec.nullCheck(getF,err);
	   if(chk)
	   { boolean psck=ec.passwordCheck(p1,p2,err); 
	     if(psck)
		 { boolean ex=ec.elseCheck(jf[3],jf[4],err); 
		   if(ex)
		   { boolean uname=ec.validateUser(jf[0].getText(),err); 
		     if(uname)
			 { checkFields();
			   form.setVisible(false);
			   confirmPanel();
			 }
		   }
		 }  	
	   }
	 }
	 else if(ac.equals("Go"))
	 { createUser();
	   this.dispose();
	   System.out.println(getF[0]);
	   SidePanelAction l1=new SidePanelAction(getF[0],getF[5]);
	   l1.setSize(840,600);
	   l1.setVisible(true);
	 }
	 else if(ac.equals("Back"))
	 { confirm.setVisible(false);
	   for(i=0,r=10;i<9;i++,r+=40)
      { tag[i].setBounds(20,r,150,20);
	    form.add(tag[i]);
	  }
	  form.setVisible(true);
	 }
	}
	public void confirmPanel()
    { JLabel data[]=new JLabel[8];
	  confirm=new JPanel();
	  confirm.setLayout(null);
	  confirm.setBounds(10,132,800,420);
	  confirm.setBackground(Color.white);
	  confirm.setVisible(true);
	  con.add(confirm);
	  f=new Font("Rockwell",Font.PLAIN,30);
	  JLabel conf=new JLabel("Confirm Details");
	  conf.setBounds(30,10,300,60);
	  conf.setForeground(c2);
	  conf.setFont(f);
      confirm.add(conf);
	  int j;
	  for(i=0,r=80;i<9;i++,r+=40)
      { if(i==1 || i==2)
	    { r-=40;
	     continue;
		}
	    tag[i].setBounds(20,r,150,20);
	    confirm.add(tag[i]);
	  }
	  f=new Font("BookmanOldStyle",Font.PLAIN,12);
	  for(i=0,j=0,r=80;i<7;i++,r+=40)
	  { data[i]=new JLabel();
	    if(i==1)
	    { j=3;
		  data[i].setText(getF[j]);
		  j++;
 		}
		else if(i==4)
        { j=6;
		  data[i].setText(getF[j]+"-"+getF[j+1].toLowerCase()+"-"+getF[j+2].substring(2,4));		
		  j=j+3;
		}
	    else
	    { data[i].setText(getF[j]); 
		  j++;
		}
		data[i].setFont(f);
	    data[i].setBounds(200,r,150,20);
		confirm.add(data[i]);
		go=new JButton("Go");
		back=new JButton("Back");
		go.setBounds(200,370,100,30);
		go.setBackground(c2);
		go.setForeground(Color.white);
		back.setBounds(400,370,100,30);   
		back.setBackground(c2);
		back.setForeground(Color.white);
		confirm.add(go);
		confirm.add(back);
		go.addActionListener(this);
		back.addActionListener(this);
      }
	}
   public void checkFields()
   { char ps1[]=new char[20];
	 char ps2[]=new char[20];
	 ps1=p1.getPassword();
	 ps2=p2.getPassword();
	 getF[0]=jf[0].getText();
	 getF[1]=new String(ps1);
	 getF[2]=new String(ps2);
	 getF[3]=(String)sq.getSelectedItem();
	 getF[4]=jf[1].getText();
	 getF[5]=jf[2].getText();
	 getF[6]=(String)dd.getSelectedItem();
	 getF[7]=(String)mm.getSelectedItem();
	 getF[8]=(String)yy.getSelectedItem();
	 getF[9]=jf[3].getText();
	 getF[10]=jf[4].getText();
   }
	public void createUser()
	{ 
	  String date;
	  date=getF[6]+"-"+getF[7].toLowerCase()+"-"+getF[8].substring(2,4); 
	  try
	  { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	    Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
		Statement st=conn.createStatement();
		st.executeUpdate("insert into rrs_user values('"+getF[0]+"','"+getF[1]+"','"+getF[3]+"','"+getF[4]+"','"+getF[5]+"','"+date+"','"+getF[9]+"','"+getF[10]+"')");
		st.close();
		conn.close();	
	  }
	  catch(Exception e)
	  { System.out.println(e); }
	} 
}