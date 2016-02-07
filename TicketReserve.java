import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.sql.*;

public class TicketReserve implements ActionListener
{ JPanel ticr;
  String ticklab[]={" Train Number    :"," Train Name        :"," Date                     :"," Class                   :"," From                    :"," To                         :"," Reserved Upto  : "," Quota                  : "};
  String infolab[]=new String[8];
  JTextField pname[]=new JTextField[6];
  JTextField age[]=new JTextField[6];
  JComboBox sex[]=new JComboBox[6];
  Checkbox srctzn[]=new Checkbox[6];
  JLabel info[]=new JLabel[8];
  JLabel ticketinfo[]=new JLabel[8];
  LineBorder lb;
  JButton proceed,reset;
  Font f1,f2,f;
  Color c1,c2;
  int r,g,b,a,i,totpass=0,totfare;
  boolean erflag=false,trflag=false;
  String doj,pnr,userid,selection=null;
  TrainListObj tobj;
  TrainList tlist;
  JDialog conftick;
  JOptionPane jop;
  JButton confirm;
  JLabel totfarelab,cflab,err;
  Vector<Passenger> pinfo=new Vector<Passenger>(6);
  Ticket passtick;
  ShowTicket showtick;
  CardLayout clo;
  JPanel focusPane;

  TicketReserve(String u,CardLayout cl,JPanel jp,ShowTicket sht)
  {  userid=u;
     clo=cl;
	 focusPane=jp;
	 showtick=sht;
     r=228; g=234; b=255;
	 c1=new Color(r,g,b);
	 r=0; g=124; b=200;
     c2=new Color(r,g,b);
	 lb=new LineBorder(Color.gray);
     f1=new Font("Verdana",Font.BOLD,12);
     f2=new Font("TimesNewRoman",Font.PLAIN,12);

	 ticr=new JPanel();
     ticr.setLayout(null);
	 
	 err=new JLabel();
	 err.setBounds(30,340,300,20);
	 err.setFont(f2);
	 err.setForeground(Color.red);
	 ticr.add(err);
	 
	 Object[] options = { "Make Payment", "Cancel" };
	 jop=new JOptionPane(null,JOptionPane.INFORMATION_MESSAGE,JOptionPane.YES_NO_OPTION,null,options,options[0]);
	 jop.setInitialValue(options[0]);
	 conftick=jop.createDialog(null,"Confirm Payment");
	 
	 ticrPanel();
  }
  
  public void setTrainInfo(TrainList tl,TrainListObj ot)
  { tlist=tl; 
    tobj=ot; 
    infolab[0]=""+tobj.trno;
	infolab[1]=tobj.trna;
    infolab[2]=tlist.dj;
	infolab[3]=tlist.cls;
	infolab[4]=tlist.sna1+" ("+tlist.s1+")";
	infolab[5]=tlist.sna2+" ("+tlist.s2+")";
	infolab[6]=tlist.s2;
	infolab[7]=tlist.quota;
	ticrInfo();
	doj=tlist.getSqlDate();
  }
  
  public void ticrInfo()
  { a=0;
     for(int j=0,r=50;j<8;j+=2,r+=19)
     {
		for(int i=0,c=25;i<2;i++,c+=269,a++)
		{ 	info[j+i]=new JLabel(""+ticklab[a]);
			info[j+i].setBorder(lb);
			info[j+i].setBounds(c,r,270,20);
			ticr.add(info[j+i]);
			ticketinfo[j+i]=new JLabel(""+infolab[a]);
			ticketinfo[j+i].setBounds(c+100,r+1,170,19);
			ticketinfo[j+i].setFont(f2);
			ticr.add(ticketinfo[j+i]);
		}
     }
	 
  }  
  public void ticrPanel()
  {
     ticr.setBackground(Color.white);
     ticr.setBounds(225,130,600,400); 
     proceed=new JButton(" Go ");
     proceed.setBackground(c2);
     proceed.setForeground(Color.white);
     proceed.setBounds(300,350,100,25);
	 proceed.addActionListener(this);
	 
     reset=new JButton(" Reset ");
     reset.setBackground(c2);
     reset.setForeground(Color.white);
     reset.setBounds(450,350,100,25);
	 reset.addActionListener(this);
	 
     JLabel tr=new JLabel("Ticket Reservation");
     tr.setForeground(c2);
     tr.setFont(f1);
     tr.setBounds(25,20,130,20);
     JLabel jl1=new JLabel(" Passenger Details ");
     jl1.setForeground(c2);
     jl1.setFont(f1);
     jl1.setBounds(25,130,130,20); 
     
     JLabel sno[]=new JLabel[6];
	 JLabel header[]=new JLabel[5];
	 String hd[]={"   SNO","Name","Age","Sex","Senior Citizen"};
	 for(int i=0;i<5;i++)
	 { header[i]=new JLabel(hd[i]);
	   ticr.add(header[i]);
	 }
	 header[0].setBounds(10,160,90,20);		header[0].setOpaque(true);	header[0].setBackground(c1);	header[0].setFont(f2);
	 header[1].setBounds(100,160,200,20);	header[1].setOpaque(true);	header[1].setBackground(c1);	header[1].setFont(f2);
	 header[2].setBounds(300,160,120,20);	header[2].setOpaque(true);	header[2].setBackground(c1);	header[2].setFont(f2);
	 header[3].setBounds(420,160,80,20);	header[3].setOpaque(true);	header[3].setBackground(c1);	header[3].setFont(f2);
	 header[4].setBounds(500,160,100,20);	header[4].setOpaque(true);	header[4].setBackground(c1);	header[4].setFont(f2);
	 
      for (int i=0,r=190;i<6;i++,r+=25)
      {   sno[i]=new JLabel();
          pname[i]=new JTextField();
          age[i]=new JTextField();
          sex[i]=new JComboBox();
          sex[i].addItem("M");
          sex[i].addItem("F");
          srctzn[i]=new Checkbox("");
          sno[i].setText(""+(i+1));
          sno[i].setFont(f2);
		  sno[i].setBounds(40,r-3,20,10);
          pname[i].setBounds(100,r-3,150,18);
          age[i].setBounds(300,r-3,60,18);
          sex[i].setBounds(420,r-3,60,18);
          srctzn[i].setBounds(530,r,20,18);
          
          ticr.add(sno[i]); ticr.add(pname[i]); ticr.add(age[i]);
          ticr.add(sex[i]); ticr.add(srctzn[i]);
      }
      ticr.add(proceed); ticr.add(reset);
      ticr.add(tr);
      ticr.add(jl1);
  }
  
  public void setInfo()
  { String tna;
    for(i=0;i<7;i++)
    { if(i==6)
	  { totpass=i; break; }
	  tna=pname[i].getText();
	  if(tna.equals(""))
	  { totpass=i; 
	    break;
	  }
	}
	for(i=0;i<totpass;i++)
	{ Passenger p=new Passenger();
	  p.pid=i+1;
	  p.pna=pname[i].getText();
	  try
	  { p.Age=Integer.parseInt(age[i].getText()); 
	    if(p.Age>150)
		{ erflag=true; 
		  err.setText("Age value looks suspicious");  
		  break;
		}
	    erflag=false;
	  }
	  catch(NumberFormatException nfe)
	  { err.setText("Can't have non-numeric characters in AGE field"); erflag=true; break;}
	  p.Sex=(String)sex[i].getSelectedItem();
	  p.srzn=srctzn[i].getState();
	  if((p.Sex.equals("M") && p.Age<60 && p.srzn) || (p.Sex.equals("F") && p.Age<58 && p.srzn))
	  {  err.setText("Not eligible for senior citizen concession"); erflag=true; break; }
	  pinfo.add(p);
	}
  }
  
  public void clearInfo()
  { if(trflag)
    { for(int j=0;j<8;j+=1)
      { infolab[j]="";
	    ticketinfo[j].setText("");
      }
	  for (i=0;i<6;i++)
      { pname[i].setText("");
        age[i].setText("");
        sex[i].setSelectedItem("M");
        srctzn[i].setState(false);
      }
	  trflag=false;
	}
  }
  
  public void actionPerformed(ActionEvent ae)
  { String ac=ae.getActionCommand();
    String er=err.getText();
    if(ac.equals(" Go "))
	{ setInfo(); 
	  if(totpass!=0 && !erflag)
	  { err.setText("");
	    passtick=new Ticket();
	    passtick.setTicket(tlist,tobj,pinfo,totpass);
	    totfare=passtick.FindTotFare();
		jop.setMessage("Total Fare : "+totfare);
		conftick.setVisible(true);
		selection=(String)jop.getValue();
		
		if(selection!=null)
		{ //System.out.println(selection); 
			if(selection.equals("Make Payment"))
			{ passtick.generateTicket(userid);
			  conftick.setVisible(false);
			  showtick.clearInfo();
			  showtick.showTicketInfo(passtick.pnr);
			  clo.show(focusPane,"ShowTicket");
			  clearInfo();
			  trflag=true;
			}
		}
		erflag=false;
	  }
	  else if(totpass==0)
	  { err.setText("Insert Passenger Details"); 
	    erflag=true;
	  }
	}
	else if(ac.equals(" Reset "))
	{ for (i=0;i<6;i++)
      { pname[i].setText("");
        age[i].setText("");
        sex[i].setSelectedItem("M");
        srctzn[i].setState(false);
      }
	  err.setText("");
	}
  }  
}
