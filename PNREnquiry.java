import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PNREnquiry extends JFrame implements ActionListener
{  JPanel pnrenq;
   JLabel enter=new JLabel("Enter PNR");
   JLabel err=new JLabel();
   JTextField enterpnr=new JTextField();
   JButton go=new JButton("Get Status");
   Color c2;
   int r,g,b;
   ShowTicket sht=new ShowTicket();
   PNREnquiry(String a)
   { super(a);
     
     r=0; g=124; b=200;
 	 c2=new Color(r,g,b);
	
     setLayout(null);
     this.setSize(600,80);
	 this.setLocation(100,100);
	 
	 sht.tdone.setBounds(0,50,600,400);
	 sht.tdone.setVisible(false);
	 this.add(sht.tdone);
	 
     pnrenq=new JPanel();
     pnrenq.setLayout(null);
	 pnrenq.setBackground(Color.white);
	 pnrenq.setBounds(0,0,600,500);
	 this.add(pnrenq);
	 
	 enter.setBounds(10,10,100,20);
	 pnrenq.add(enter);
	 
	 enterpnr.setBounds(80,10,100,20);
	 pnrenq.add(enterpnr);
	 
	 go.setBounds(200,10,150,20);
	 pnrenq.add(go);
	 go.setBackground(c2);
	 go.setForeground(Color.white);
	 go.addActionListener(this);
	 
	 err.setBounds(400,10,100,20);
	 err.setForeground(Color.red);
	 pnrenq.add(err);
   }
   public void actionPerformed(ActionEvent ae)
   { String pnrno=enterpnr.getText();
     if(!pnrno.equals(""))
     { int c=Ticket.validatePNR(pnrno);
	   err.setText("");
	   if(c==0)
	   { err.setText("Invalid PNR"); }
	   else
	   { this.setSize(600,500);
	     err.setText(""); 
		 sht.tdone.setVisible(true);
		 sht.showTicketInfo(pnrno);
	   }
	 }
	 else
	 { err.setText("Enter PNR value"); }
   } 
}