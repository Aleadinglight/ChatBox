package test;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class thu implements ActionListener, Runnable{
	private static final int port=8000;
	private static final String host = "127.0.0.1";
	ServerSocket serverSocket;
	JFrame f;
	JLabel l1;
	// This class describes the conversation
	JTextArea chatScreen;
	// This is the box where user put in messages
	JTextArea chatBox;
	JButton sendButton;
	private Thread thread;
	private Kind kind;
	//private Scanner in;
	//private volatile PrintWriter out;
	private String data;
	private DataInputStream in;
	private volatile DataOutputStream out;

	public static enum Kind{
		Client(100, "Trying"), Server(500, "Awaiting");
		private int offset;
		private String activity;

		private Kind(int offset, String activity){
			this.offset = offset;
			this.activity = activity;
		}
	}

	public thu(Kind kind){
		
		JFrame f = new JFrame();
		// Create textarea, label, button
		chatScreen = new JTextArea();
		chatBox = new JTextArea();
		sendButton = new JButton("send");
		l1=new JLabel();  
		this.kind = kind;
		// Put them in the place you want on the container
    		// Block the chat screen so it only be used to display texts
    		chatScreen.setBounds(20, 30, 400, 250);
		chatScreen.setEditable(false);
		chatBox.setBounds(20,300, 400, 55);
		sendButton.setBounds(430, 300, 70, 50);
		sendButton.addActionListener(this);
		l1.setBounds(20,281,400,15);  
		l1.setText("Type in your message: ");

		// Add the chatBox, chatScreen, sendButton, label to the frame
		f.setTitle("Echo "+kind);
		f.add(chatScreen);
		f.add(chatBox);
		f.add(sendButton);
		f.add(l1);
		// Set some features for the container
		f.setLocation(kind.offset, 300);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(540, 400);
		f.setLayout(null);
		f.setVisible(true);
		display(kind.activity+" "+host+" on port "+port);
		thread = new Thread(this, kind.toString());
	}

	public void start(){
		thread.start();
	}

	public void actionPerformed(ActionEvent e){
		String text = chatBox.getText();
		try {
			if (out != null) {
            	out.writeUTF(text);
        }}
        catch (Exception ae){
        	ae.printStackTrace();
        }
		chatBox.setText(null);
		display(text);
	}

	public void run(){
		try{
			Socket socket;
			if (kind == Kind.Client){
				socket = new Socket(host, port);
			}
			else{
				ServerSocket serverSocket = new ServerSocket(port);
				socket = serverSocket.accept();
			}
			//in = new Scanner(socket.getInputStream());
            //out = new PrintWriter(socket.getOutputStream(), true);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			display("Connected!");
			while (true){
				//display(in.nextLine());
				display(in.readUTF());
			}

		} catch (Exception e){
			display("fail here!");
			display(e.getMessage());
			e.printStackTrace(System.err);
		}
	}
	private void display(final String s){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				chatScreen.append(s+"\u23CE\n");
			}
		});
	}
	
	

	public static void main(String[]  args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new thu(Kind.Server).start();
				new thu(Kind.Client).start();
			}
		});
	}
}
