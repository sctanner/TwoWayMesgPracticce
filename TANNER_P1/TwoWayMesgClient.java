/*
 * Implementation of the two-way message client in java
 * By Srihari Nelakuditi and Steven Tanner for CSCE 416
 */

// Package for I/O related stuff
import java.io.*;

// Package for socket related stuff
import java.net.*;

/*
 * This class does all the client's job
 * It connects to the server at the given address
 * and sends messages typed by the user to the server
 */
public class TwoWayMesgClient {
	/*
	 * The client program starts from here
	 */
	public static void main(String args[])
	{
		// Client needs server's contact information
		if (args.length != 2) {
			System.out.println("usage: java OneWayMesgClient <server name> <server port>");
			System.exit(1);
		}

		// Get server's whereabouts
		String serverName = args[0];
		int serverPort = Integer.parseInt(args[1]);

		// Be prepared to catch socket related exceptions
		try {
			// Connect to the server at the given host and port
			Socket sock = new Socket(serverName, serverPort);
			System.out.println(
					"Connected to server at ('" + serverName + "', '" + serverPort + "')");

			/**************************** 
			WE ARE CONNECTED************
			****************************/
			
			// Prepare to read from keyboard
			BufferedReader fromUserReader = new BufferedReader(new InputStreamReader(System.in));

			// Prepare to write to server with auto flush on
			PrintWriter toServerWriter = new PrintWriter(sock.getOutputStream(), true);

			

			// Keep doing till we get EOF from user
			while (true) {
				// Read a line from the keyboard
				String line = fromUserReader.readLine();

				// If we get null, it means user is done, exit the loop which will close socket
				if (line == null) {
					System.out.println("Closing connection");
					break;
				}
				// Send the line to the server
				toServerWriter.println(line);
				
				//MESSAGE AS BEEN SENT, NOW WE WILL GET A RESPONSE
				BufferedReader fromServerReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	
				// Read a message from the client
				String message = fromServerReader.readLine();

				// If we get null, it means client sent EOF
				if (message == null) {
					System.out.println("Client closed connection");
					sock.close();
					fromServerReader.close();
					break;
				}

				// Display the message from server
				System.out.println("Server: " + message);
			

			}
			// close the socket and exit
			
			fromUserReader.close();	
			toServerWriter.close();
			sock.close();
		
	}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}