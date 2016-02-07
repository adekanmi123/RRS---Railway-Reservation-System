import java.sql.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class ErrorCheck
{ int i;
  ErrorCheck()
  { }
  public boolean nullCheck(String getF[],JLabel err)
   { boolean chk=false;
     int i;
	 for(i=0;i<11;i++)
	 { if(i==3 && getF[i].equals("Choose your security question "))
	   { break; }
	   else if(i==6 && getF[i].equals("DD"))
	   { break; }
	   else if(i==7 && getF[i].equals("MM"))
	   { break; }
	   else if(i==8 && getF[i].equals("YYYY"))
	   { break; }
	   else if(getF[i].equals(""))
	   { break; }
	 }
	 if(i!=11)
	 { chk=false; }
	 switch(i)
     { case 0 : err.setText("*Specify Username"); break;	 
	   case 1 : err.setText("*Specify Password"); break;
	   case 2 : err.setText("*Retype Password"); break;
	   case 3 : err.setText("*Specify security question"); break;
	   case 4 : err.setText("*Answer your security question"); break;
	   case 5 : err.setText("*Specify name"); break;
	   case 6 :
	   case 7 :
	   case 8 : err.setText("*Specify Birth-date"); break;
	   case 9 : err.setText("*Specify Email ID"); break;
	   case 10 : err.setText("*Specify Mobile No"); break;
	   case 11 : chk=true;
	 }
	 return chk;
   }
   public boolean passwordCheck(JPasswordField p1,JPasswordField p2,JLabel err)
	{ String pa1,pa2;
	  char ps1[]=new char[20];
	  char ps2[]=new char[20];
	  ps1=p1.getPassword();
	  ps2=p2.getPassword();
	  pa1=new String(ps1);
	  pa2=new String(ps2);
	  if(pa1.equals(pa2))
	  { err.setText(""); 
	    return true; }
	  else
	  { err.setText("*Passwords didn't match"); 
	    return false;
	  }
	}
	public boolean elseCheck(JTextField jf1,JTextField jf2,JLabel err)
	{ boolean a,b; 
	 long no;
	 String s1,s2;
	 s1=jf1.getText();
	 s2=jf2.getText();
     if(s1.indexOf('@')==-1 || s1.indexOf('.')==-1)
	 { err.setText("*Enter valid email-ID");
	   a=false;
	 }
	 else
	 { a=true;}
	 if(s2.length()!=10)
	 { err.setText("*Enter valid Mobile No"); 
	   b=false;
	 }
	 else
	 {  try
	    { no=Long.parseLong(s2); }
		catch(NumberFormatException nfe)
		{ err.setText("*Enter valid Mobile No"); 
		  b=false;
		  return a&b;
		}
		b=true;
	 }
	 return a&b;
    }	
	public boolean validateUser(String name,JLabel err)
	{ 
	  boolean ck=false,f=true;
	  try
	  { DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	    Connection connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
		Statement st=connection.createStatement();
		ResultSet rs=st.executeQuery("select username from rrs_user");
	    String usr="";
		i=0;
		while(rs.next())
		{ usr=rs.getString("username");
		  if(usr.equalsIgnoreCase(name))
		  { f=false;
		    break; }
		  i++;
		}
		rs.close();
		st.close();
		connection.close();
		if(f)
		{ ck=true; }
		else
		{ err.setText("*Username already taken"); }
	 }
	 catch(Exception e)
	 { e.printStackTrace(); }
	 return ck;
	}
}