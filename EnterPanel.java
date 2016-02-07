import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class EnterPanel 
{  JPanel enter;
   JLabel wall_e;
   JButton bhist,prnt,cancel,refund;
   EnterPanel()
   { enter=new JPanel();
     enter.setLayout(null);
     enterPanel();
   }
   void enterPanel()
   {
    enter.setBounds(215,130,600,400);
    enter.setBackground(Color.white);
    
    wall_e=new JLabel(new ImageIcon("WallPaper.png"));
    wall_e.setBounds(0,0,600,250);
    enter.add(wall_e); 
  }
}