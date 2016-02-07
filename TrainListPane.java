import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TrainListPane implements ActionListener
{ JPanel tlist;
  JLabel head,trlab,err;
  JButton go;
  ButtonGroup bgp=new ButtonGroup();
  JRadioButton jrb[]=null;
  JLabel tnum[]=null;
  JLabel tname[]=null;
  JLabel ardep[]=null;
  JLabel avail[]=null;
   JLabel fare[]=null;
  int size,i,row;
  String trno="";
  Color c2;
  int r,g,b;
  Font f1,f2;
  Vector<TrainListObj> trainvector;
  TrainList tl;
  TicketReserve trs;
  CardLayout clo;
  JPanel fcpane;
  boolean trflag=false;
  TrainListPane(TicketReserve trs,CardLayout cl,JPanel base)
  { this.trs=trs;
    tlist=new JPanel();
    tlist.setLayout(null);
	fcpane=base;
	clo=cl;
	r=0; g=124; b=200;
    c2=new Color(r,g,b);
    f1=new Font("Verdana",Font.BOLD,15);
    f2=new Font("TimesNewRoman",Font.PLAIN,12);
    tlist.setBounds(215,130,600,400);
    tlist.setBackground(Color.white);
	
    trlab=new JLabel(" List of Trains ");
	trlab.setBounds(15,15,150,20);
	trlab.setForeground(c2);
	trlab.setFont(f1);
	tlist.add(trlab);
	
	err=new JLabel("");
	err.setBounds(20,300,200,20);
	err.setFont(f2);
	err.setForeground(Color.red);
	
	go=new JButton(" Go ");
	go.setBounds(200,350,100,25);
	go.setForeground(Color.white);
	go.setBackground(c2);
	go.addActionListener(this);
	
	head=new JLabel("    Train No   Train Name                                                        Departs      Arrives      Availibility       Fare       Book");
    head.setBounds(0,50,600,20);
	head.setOpaque(true);
	head.setBackground(c2);
	head.setForeground(Color.white);
    tlist.add(head);
	
	tlist.add(go);
	tlist.add(err);
  }
  
  public void setTrainList(TrainList tls)
  { tl=tls; }
  
  public void showList(Vector<TrainListObj> v1)
  {	trflag=true;
    trainvector=v1;
	size=trainvector.size();
    if(size==0)
	{ err.setText(" No trains found"); 
	}
	else
    { err.setText("");
    }
   jrb=new JRadioButton[size];
   tnum=new JLabel[size];
   tname=new JLabel[size];
   ardep=new JLabel[size];
   avail=new JLabel[size];
   fare=new JLabel[size];
   for(i=0,row=70;i<size;i++,row+=25)
   { TrainListObj ot=(TrainListObj)trainvector.elementAt(i);
     tnum[i]=new JLabel(""+ot.trno);
	 tname[i]=new JLabel(ot.trna);
	 ardep[i]=new JLabel(""+ot.art+"          "+ot.dpt);
	 avail[i]=new JLabel(""+ot.avl);
	 fare[i]=new JLabel(""+ot.fr);
	 jrb[i]=new JRadioButton();
     bgp.add(jrb[i]);	
	 tnum[i].setBounds(10,row,50,20);
	 tname[i].setBounds(70,row,230,20);
	 ardep[i].setBounds(305,row,100,20);
	 avail[i].setBounds(433,row,50,20);
	 fare[i].setBounds(485,row,40,20);
	 fare[i].setHorizontalAlignment(SwingConstants.RIGHT);
	 jrb[i].setBounds(555,row,25,20);
	 jrb[i].setBackground(Color.white);
	 jrb[i].setActionCommand(""+ot.trno);
     tnum[i].setFont(f2); tname[i].setFont(f2); ardep[i].setFont(f2); avail[i].setFont(f2); fare[i].setFont(f2);
	 jrb[i].addActionListener(this);
	 tlist.add(tnum[i]);  tlist.add(tname[i]); tlist.add(ardep[i]); tlist.add(avail[i]); tlist.add(fare[i]); 
	 tlist.add(jrb[i]);
   }
  }
  
  public void clear()
  { if(trflag)
    { if(size!=0)
      { err.setText(" ");
        for(i=0;i<size;i++)
        { tnum[i].setText("");
		  ardep[i].setText("");
		  avail[i].setText("");
		  fare[i].setText("");
	      jrb[i].setVisible(false);
	      tnum[i].setVisible(false);
	      tname[i].setVisible(false);
	      ardep[i].setVisible(false);
		  avail[i].setVisible(false);
		  fare[i].setVisible(false);
        }
	  }
	  else
	  { err.setText(" "); }
	  trno="";
	  trflag=false;
	}
  }   
  
  public void actionPerformed(ActionEvent ae)
  { String t=ae.getActionCommand();
	if(t.equals(" Go "))
	{ if(trno.equals(""))
      { err.setText("Please select one train"); }
      else
      { err.setText("");
	    int tno=Integer.parseInt(trno);
	    TrainListObj train=tl.getTrainInfo(tno);
		if(train.avl.equals("REGT"))
		{ err.setText("Regret, No more tickets can be booked in this train"); }
		else
		{ trs.clearInfo();
		  trs.setTrainInfo(tl,train);
		  clo.show(fcpane,"TicketReserve");
		  clear();		
		}
	  }
	}
	else
	{ trno=t; }
  }
}