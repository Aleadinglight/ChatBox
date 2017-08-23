package pack;
import send.client;
import javax.swing.*; 
import java.awt.event.*;
import java.io.*;

public class GUI implements ActionListener{ 
	JLabel l1;
	JTextArea area;
	JButton b;

	public void actionPerformed(ActionEvent e){
		String text = area.getText();
		client user = new client();
		user.send_data(text);
		user=null;
	}
	
	public GUI(){
		// Create a container
		JFrame f = new JFrame();
		// Create textarea, label, button
		area = new JTextArea();
		b = new JButton("send");
		l1=new JLabel();  
		
		//put them in the place you want on the container
    	area.setBounds(20, 75, 250, 200);
		b.setBounds(100, 300, 120, 30);
		b.addActionListener(this);
		l1.setBounds(50,25,100,30);  

		// Add the textarea, button, label to the frame
		f.add(b);
		f.add(area);
		f.add(l1);
		// Set some features for the container
		f.setSize(400,500);
		f.setLayout(null);
		f.setVisible(true);
	}
}
