import javax.swing.*;
import java.awt.*;

public class Logo extends JFrame
{ Container con;
  JPanel logo;
  JLabel rrslogo;
  int r,g,b;
  Color c1,c2;
  Logo(String s)
  { super(s);
    r=228; g=234; b=255;
	c1=new Color(r,g,b);
	r=0; g=124; b=200;
	c2=new Color(r,g,b);
    con=getContentPane();
    logo=new JPanel();
	logoPanel();
  }	
  public void logoPanel()
  { logo.setBounds(10,10,805,100);
	logo.setBackground(Color.white);
	con.add(logo);
	rrslogo=new JLabel(new ImageIcon("Logobar2.png"));
	rrslogo.setBounds(10,10,780,100);
	logo.add(rrslogo);
  }
}