import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class TrainEnquiryList extends Logo
{
    TrainEnquiry ote;
    JPanel listpanel,list;
    JScrollPane jsplist;
    JLabel lhead,tnum[],tname[],tfreq[],arr[],dep[],classes[];
    JButton back;
    String src,dest;
    TELObj temp;
    Vector<TELObj> vlst;
    Font f2;
    int i;
    TrainEnquiryList(String s,TrainEnquiry ote,String frostn,String tostn)
    {   super(s);
        src = frostn; dest = tostn;
        this.ote = ote;
        setLayout(null);
		setLocation(100,100);
		setBackground(Color.white);
        setBounds(0,0,840,600);
        listpanel = new JPanel();
        listpanel.setLayout(null);
		listpanel.setBackground(Color.white);
        list = new JPanel();
        list.setLayout(null);
		list.setBackground(Color.white);
        jsplist = new JScrollPane(list);
        lhead = new JLabel("Train No.      Train Name                                          Frequency                                       Departure            Arrival             AvailableClasses");
		lhead.setBounds(0,20,840,20);
		lhead.setOpaque(true);
        lhead.setBackground(c2);
		lhead.setForeground(Color.white);
		
        con.add(listpanel);
        listpanel.add(jsplist);  
		list.add(lhead);
        f2=new Font("TimesNewRoman",Font.PLAIN,12);        
		listpanel.setBounds(10,120,805,430);
        listpanel.setBackground(Color.white);
        jsplist.setBounds(10,40,785,300);
        list.setBounds(0,0,805,700);
        showlist();
    }
    
    public void showlist()
    {
        setVisible(true);
        
        vlst = new Vector<TELObj>();
        String weekDay[]={"SUN","MON","TUE","WED","THU","FRI","SAT"};
		
        try {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	    Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","RailwayAdmin","rrsbe3ss");
        Statement st=conn.createStatement();
        Statement st2=conn.createStatement();
		Statement st3=conn.createStatement();
		Statement st4=conn.createStatement();
        st.executeQuery("select * from trainmaster where trainno in (select a.trainno \"trainno\" from (select * from trainjourney where stncode='"+src+"') a, (select * from trainjourney where stncode='"+dest+"') b where a.trainno=b.trainno and a.distance<b.distance)");
        ResultSet rs = st.getResultSet();
        ResultSet rs2=null,rs3=null,rs4=null;
        while(rs.next())
        {   temp=new TELObj();
		    String str = "";
            for(String s:weekDay)
            {
                if(rs.getString(s).equals("Y"))
                { str = str+s+" "; }
            }
            if(str.equals("SUN MON TUE WED THU FRI SAT "))
             { str = "DAILY";  }
            st2.executeQuery("select dep from trainjourney where stncode='"+src+"' and trainno="+rs.getString("trainno"));
            rs2 = st2.getResultSet();
            rs2.next();
			temp.tdep=rs2.getString("dep");
			st3.executeQuery("select arr from trainjourney where stncode='"+dest+"' and trainno="+rs.getString("trainno"));
            rs3 = st3.getResultSet();
            rs3.next();	
			temp.tarr=rs3.getString("arr");
			
			st4.executeQuery("select * from trainclass where trainno="+rs.getString("trainno"));
            rs4 = st4.getResultSet();
            rs4.next();	
			int cl[]=new int[7];
			String cls[]={"AC1","AC2","AC3","CC","FC","SL","SS"};
			cl[0]=rs4.getInt("AC1");
			cl[1]=rs4.getInt("AC2");
			cl[2]=rs4.getInt("AC3");
			cl[3]=rs4.getInt("CC");
			cl[4]=rs4.getInt("FC");
			cl[5]=rs4.getInt("SL");
			cl[6]=rs4.getInt("SS");
			temp.tclasses=" ";
			for(i=0;i<7;i++)
			{ if(cl[i]>0)
			  { temp.tclasses=temp.tclasses+" "+cls[i]; }
			}
            temp.trainno=rs.getString("trainno");
			temp.tname=rs.getString("tname");
			temp.freq=str;
            vlst.addElement(temp);
        }
		rs2.close(); rs3.close(); rs4.close();
		
		rs.close();
		st2.close();	st3.close();	st4.close();
		st.close();
        conn.close();
        
        }
        catch(Exception e)
        { e.printStackTrace(); }
        
        tnum = new JLabel[vlst.size()];
        tname = new JLabel[vlst.size()];
        tfreq = new JLabel[vlst.size()];
        arr = new JLabel[vlst.size()];
		dep = new JLabel[vlst.size()];
		classes = new JLabel[vlst.size()];
      
        for(int i=0,row=40;i<vlst.size();i++,row+=25)
        {   temp = vlst.elementAt(i);
            tnum[i] = new JLabel(temp.trainno);
            tnum[i].setBounds(15,row,50,20);
            tnum[i].setFont(f2);
            tname[i] = new JLabel(temp.tname);
            tname[i].setBounds(70,row,200,20);
            tname[i].setFont(f2);
            tfreq[i] = new JLabel(temp.freq);
            tfreq[i].setBounds(260,row,200,20);
            tfreq[i].setFont(f2);
			dep[i] = new JLabel(temp.tdep);
            dep[i].setBounds(445,row,50,20);
            dep[i].setFont(f2);
            arr[i] = new JLabel(temp.tarr);
            arr[i].setBounds(530,row,50,20);
            arr[i].setFont(f2);
			classes[i] = new JLabel(temp.tclasses);
            classes[i].setBounds(600,row,150,20);
            classes[i].setFont(f2);
            list.add(tnum[i]); list.add(tname[i]); list.add(tfreq[i]); list.add(arr[i]); list.add(dep[i]); list.add(classes[i]);
        }
    }
}