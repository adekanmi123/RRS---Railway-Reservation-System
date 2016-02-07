import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.text.*;

public class UserModify implements ActionListener
{
    JPanel umodify, chpasswd;
    JLabel luname,lsecq,lseca,ldob,lemail,lmob,uname,dob,msg,oldpwd,newpwd,repwd,msg2,chpwdlab,updlab;
    JPasswordField oldpwdf,newpwdf,repwdf;
    JButton savech,cancch,savepwd,cancpwd;
    JTextField seca,email,mob;
    JComboBox secq;
    String unst;
    String seq[]={"Choose your security question ","What is your favourite car?","What was the name of your first school?","What was your childhood hero?","What is your favourite pass time?","What is all time favourite sports-team?","What is your father's middle name?","Who was your favourite teacher?","Where did you meet your first spouse?"};
	Color c2;
	int r,g,b;
	Font f1,f2;
    UserModify(String u)
    {   unst = u;
	    r=0; g=124; b=200;
	    c2=new Color(r,g,b);
		f1=new Font("Verdana",Font.BOLD,15);
		f2=new Font("TimesNewRoman",Font.PLAIN,12);
		
        umodify = new JPanel();
        umodify.setLayout(null);
        umodify.setVisible(false);
		
		updlab=new JLabel("Update Profile");
		updlab.setBounds(10,10,150,30);
		updlab.setFont(f1);
		updlab.setForeground(c2);
		umodify.add(updlab);
		
        chpasswd = new JPanel();
        chpasswd.setLayout(null);
        chpasswd.setVisible(false);
		
		chpwdlab=new JLabel("Change Password");
		chpwdlab.setBounds(10,10,150,30);
		chpwdlab.setFont(f1);
		chpwdlab.setForeground(c2);
		chpasswd.add(chpwdlab);
		
        luname = new JLabel("User Name                          :");
        lsecq = new JLabel("Security Question             :");
        lseca = new JLabel("Answer                                :");
        ldob = new JLabel("Date of Birth                       :");
        lemail = new JLabel("E-Mail Id                               :");
        lmob = new JLabel("Mobile No.                           :");
        savech = new JButton("Save Changes");
        savech.setActionCommand("Save Changes");
        uname = new JLabel("X");
        dob = new JLabel("00-00-0000");
        seca = new JTextField();
        email = new JTextField();
        mob = new JTextField();
        secq = new JComboBox();
        msg = new JLabel("");
        msg2 = new JLabel("");
        oldpwd = new JLabel("Old Password                      :");
        newpwd = new JLabel("New Password                    :");
        repwd = new JLabel("Retype New Password      :");
        oldpwdf = new JPasswordField();
        newpwdf = new JPasswordField();
        repwdf = new JPasswordField();
        savepwd = new JButton("Save Password");
        savepwd.setActionCommand("Save Password");
		
		savepwd.addActionListener(this);
	    savech.addActionListener(this);

        umodify.add(luname); umodify.add(lseca);
        umodify.add(lsecq); umodify.add(lemail);
        umodify.add(lmob); umodify.add(savech);
        umodify.add(uname);
        umodify.add(ldob); umodify.add(dob); umodify.add(msg);
        umodify.add(seca); umodify.add(email); umodify.add(mob); umodify.add(secq);
		
        chpasswd.add(oldpwd); chpasswd.add(newpwd); chpasswd.add(repwd);
        chpasswd.add(oldpwdf); chpasswd.add(newpwdf); chpasswd.add(repwdf);
        chpasswd.add(savepwd); chpasswd.add(msg2);
    }
    
    public void userModify()
    {   umodify.setBounds(215,130,600,400);
        umodify.setBackground(Color.white);
        luname.setBounds(50,40,150,20);
        lsecq.setBounds(50,80,150,20);
        lseca.setBounds(50,120,150,20);
        ldob.setBounds(50,160,150,20);
        lemail.setBounds(50,200,150,20);
        lmob.setBounds(50,240,150,20);
        uname.setBounds(220,40,200,20);
        secq.setBounds(220,80,200,20);
        seca.setBounds(220,120,200,20);
        dob.setBounds(220,160,200,20);
        email.setBounds(220,200,200,20);
        mob.setBounds(220,240,200,20);
        savech.setBounds(150,300,115,30);
		savech.setForeground(Color.white);
		savech.setBackground(c2);
        msg.setBounds(100,340,200,20);
		msg.setForeground(c2);
        for(int i=0;i<9;i++)
     	{ secq.addItem(seq[i]); }
        try
	    {   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	        Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
	        Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery("select * from rrs_user where lower(username)=lower('"+unst+"')");
            rs.next();
            SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
            uname.setText(unst);
            secq.setSelectedItem(rs.getString("securityq"));
            seca.setText(rs.getString("securitya"));
            dob.setText(sf.format(rs.getDate("bdate")));
            email.setText(rs.getString("email"));
            mob.setText(rs.getString("mobile"));
            rs.close();
            st.close();
            conn.close();	
	  }
	  catch(Exception e)
	  { System.out.println(e); }
    }
    
    public void saveChanges()
    {
        boolean ch = true;
        try
		{   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		    Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
			Statement st=conn.createStatement();
            String str[] = new String[4];
            str[0] =(String) secq.getSelectedItem();
            str[1] = seca.getText();
            str[2] = email.getText();
            str[3] = mob.getText();
            if(str[0].equals("Choose your security question "))
            { msg.setText("*Choose a security question"); ch=false;   }
            else if(str[1].equals(""))
            { msg.setText("*No Blank Answer Allowed"); ch=false; }
            else if(str[2].indexOf('@')==-1 || str[2].indexOf('.')==-1)
			{ msg.setText("*Enter valid email-ID"); ch=false;  }
			else if(str[3].length()!=10)
			{ msg.setText("*Enter valid Mobile No"); ch=false; }
			else
			{  try
			   { long no = Long.parseLong(str[3]); }
			   catch(NumberFormatException nfe)
			   { msg.setText("*Enter valid Mobile No"); ch=false; }
			}
			if(ch)
			{
				st.executeUpdate("update rrs_user set securityq = '"+str[0]+"', securitya = '"+str[1]+"', email = '"+str[2]+"', mobile = '"+str[3]+"' where lower(username)=lower('"+unst+"')");
				msg.setText("Changes Saved!");
			}
            st.close();
            conn.close();	
	  }
	  catch(Exception e)
	  { System.out.println(e); }
    }
    
    public void changePassword()
    {
        chpasswd.setBounds(215,130,600,400);
        chpasswd.setBackground(Color.white);
        oldpwd.setBounds(50,40,150,20);
        newpwd.setBounds(50,80,150,20);
        repwd.setBounds(50,120,150,20);
        oldpwdf.setBounds(220,40,200,20);
        newpwdf.setBounds(220,80,200,20);
        repwdf.setBounds(220,120,200,20);
        savepwd.setBounds(150,180,150,30);
		savepwd.setForeground(Color.white);
		savepwd.setBackground(c2);
        msg2.setBounds(100,230,200,20);
		msg2.setForeground(c2);
    }
    
    public void savePassword()
    {   String s1,s2,s3;
        boolean ch1,ch2,ch3;
        ch1 = ch2 = ch3 = true;
        s1 = new String(oldpwdf.getPassword());
        s2 = new String(newpwdf.getPassword());
        s3 = new String(repwdf.getPassword());
        try
	    {   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		    Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
		    Statement st=conn.createStatement();
			ResultSet rs = st.executeQuery("select password from rrs_user where lower(username)=lower('"+unst+"')");
            rs.next();
            if(s1.equals("")||s2.equals("")||s3.equals(""))
            {  msg2.setText("No Blanks Allowed");
               ch3 = false;
            }
            if(!s1.matches(rs.getString("password")))
            {  msg2.setText("Reenter Old Password");
               ch1 = false;    
            }
			if(!s2.matches(s3))
			{  msg2.setText("Passwords Don't Match");
               ch2 = false;
            }
			if(ch1&ch2&ch3)
			{  st.executeUpdate("update rrs_user set password='"+s2+"' where lower(username)=lower('"+unst+"')");
			   msg2.setText("Changes Saved!");
            }
			rs.close();
			st.close();
			conn.close();	
		} 
	    catch(Exception e)
		{ System.out.println(e); }
  }
  
  public void actionPerformed(ActionEvent ae)
  {  String ac=ae.getActionCommand();
     if(ac.equals("Save Changes"))
     {	saveChanges();
	    savech.setEnabled(false);
     }
     else if(ac.equals("Save Password"))
     {	savePassword();
	    savepwd.setEnabled(false);
     }
  }
}