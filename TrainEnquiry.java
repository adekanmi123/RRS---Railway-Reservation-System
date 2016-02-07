import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class TrainEnquiry implements ActionListener
{   JPanel tenq;
    JButton closebutton,go;
    JLabel jlfrom,jlto,err;
    JComboBox jcbfrom,jcbto;
    Login lg;
    TrainEnquiryList tel;
    TrainEnquiry(Login lg)
    {
        this.lg = lg;
        tenq = new JPanel();
        closebutton = new JButton("X");
        jlfrom = new JLabel("From");
        jlto= new JLabel("To");
        jcbfrom = new JComboBox();
        jcbto = new JComboBox();
        go = new JButton("GO");
        err = new JLabel("");
        trainenq();
        lg.con.add(tenq);
        tenq.add(closebutton); tenq.add(jlfrom); tenq.add(jlto); tenq.add(jcbfrom); tenq.add(jcbto);
        tenq.add(go); tenq.add(err);
    }
    
    public void trainenq()
    {
        tenq.setLayout(null);
        tenq.setBackground(Color.white);
        tenq.setBounds(10,340,800,200);
        tenq.setVisible(false);
        closebutton.setBackground(lg.c2);
        closebutton.setForeground(lg.c1);
        closebutton.setBounds(758,0,42,20);
        closebutton.addActionListener(this);
        jlfrom.setBounds(20,40,50,20);
        jcbfrom.setBounds(70,40,150,20);
        jlto.setBounds(230,40,20,20);
        jcbto.setBounds(260,40,150,20);
        go.setBounds(70,100,60,30);
        go.setForeground(lg.c1);
        go.setBackground(lg.c2);
        go.addActionListener(this);
        err.setBounds(150,100,200,20);
        jcbfrom.addItem("Station 1");
	 jcbto.addItem("Station 2");
	 String cd,nm;
	 try
	 {  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	    Connection connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
		Statement st=connection.createStatement();
		ResultSet rs=st.executeQuery("select * from station order by stncode");
		while(rs.next())
		{ cd=rs.getString("stncode");
		  nm=rs.getString("stname");
		  jcbfrom.addItem(cd+" "+nm);
		  jcbto.addItem(cd+" "+nm);
		}
		rs.close();
		st.close();
		connection.close();
	 }
	 catch(Exception e)
	 { System.out.println(e); }
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==closebutton)
        {
            tenq.setVisible(false);
            lg.setSize(840,370);
        }
        else if(ae.getSource()==go)
        {
            if(jcbfrom.getSelectedItem()=="Station 1"||jcbto.getSelectedItem()=="Station 2")
            { err.setText("Select Both Stations"); }
            else if(jcbfrom.getSelectedItem().equals(jcbto.getSelectedItem()))
            { err.setText("Select Different Stations"); }
            else
            { String src = (String) jcbfrom.getSelectedItem();
              src = src.substring(0,src.indexOf(" "));
              String dest = (String) jcbto.getSelectedItem();
              dest = dest.substring(0,dest.indexOf(" "));
              tel = new TrainEnquiryList("Train List",this,src,dest);  
              err.setText("");
		    }
        }
    }
}