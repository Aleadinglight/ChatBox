import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class server{
	public static void main(String[] args) throws Exception {
		int port=8000;
		ServerSocket serverSocket  = new ServerSocket(port);

		System.err.println("Started server on port "+port);
		while (true){
			// Waits for an incoming client, blocks until there is connection or server timeout
			Socket clientSocket = serverSocket.accept();
			System.err.println("Accept connection from client.");
			
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			System.out.println(in.readUTF());

			OutputStream outToClient = clientSocket.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToClient);
			out.writeUTF("You just connected to "+ clientSocket.getLocalSocketAddress());

			clientSocket.close();
		}
	}
}
