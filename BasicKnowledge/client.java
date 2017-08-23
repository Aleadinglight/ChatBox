import java.net.Socket;
import java.io.*;

public class client{
	public static void main(String[] args) throws Exception {
		// Define the port connecting and the host ip.
		int port = 8000;
		String host = "127.0.0.1";
		// try to connect to the server
		try{
			Socket socket = new Socket(host, port);
			// Inform that the connection is successful
			System.out.println("Just connected to: "+socket.getRemoteSocketAddress());
			// Send hello to server
			OutputStream outToServer = socket.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF("Hello from "+ socket.getLocalSocketAddress());
			// Receive message from server
			InputStream inFromServer = socket.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			System.out.println("Received: "+in.readUTF());

			socket.close();
		} catch (IOException e){
			// The server refused to communicate, throws an error.
			//e.printStackTrace();
			System.out.println("Connection refused!");
		}	

	}
}
