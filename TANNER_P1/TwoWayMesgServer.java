/*
 * Implementation of a two-way message server in java
 * By Srihari Nelakuditi and Steven Tanner for CSCE 416
 */

// Package for I/O related stuff
import java.io.*;

// Package for socket related stuff
import java.net.*;

/*
 * This class does all the server's job
 * It receives the connection from client
 * and prints messages sent from the client
 */
public class TwoWayMesgServer {
	/*
	 * The server program starts from here
	 */
	public static void main(String args[]) {
		// Server needs the port number to listen on
		if (args.length != 1) {
			System.out.println("usage: java OneWayMesgServer <port>");
			System.exit(1);
		}

		// Get the port on which server should listen */
		int serverPort = Integer.parseInt(args[0]);

		// Be prepared to catch socket related exceptions
		try {
			// Create a server socket with the given port
			ServerSocket serverSock = new ServerSocket(serverPort);
			System.out.println("Waiting for a client ...");

			// Wait to receive a connection request
			Socket clientSock = serverSock.accept();
			System.out.println("Connected to a client at ('" +
												((InetSocketAddress) clientSock.getRemoteSocketAddress()).getAddress().getHostAddress()
												+ "', '" +
												((InetSocketAddress) clientSock.getRemoteSocketAddress()).getPort()
												+ "')"
												);

			// No other clients, close the server socket
			serverSock.close();

			/**************************** 
			WE ARE CONNECTED************
			****************************/

			// Prepare to read from keyboard
			BufferedReader fromUserReader = new BufferedReader(new InputStreamReader(System.in));

			BufferedReader fromClientReader = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));

			// Keep serving the client
			while (true) {
				// Read a message from the client
				String message = fromClientReader.readLine();

				// If we get null, it means client sent EOF
				if (message == null) {
					System.out.println("Client closed connection");
					clientSock.close();
					break;
				}

				// Display the message
				System.out.println("Client: " + message);



				//MESSAGE HAS BEEN RECEIVED, NOW WE WILL SEND ONE BACK
				PrintWriter toServerWriter = new PrintWriter(clientSock.getOutputStream(), true);
				// Read a line from the keyboard
				String line = fromUserReader.readLine();

				// If we get null, it means user is done
				if (line == null) {
					System.out.println("Closing connection");
					break;
				}
				// Send the line to the server
				toServerWriter.println(line);

				
			}
		



		}
		catch(Exception e) {
			// Print the exception message
			System.out.println(e);
		}
	}
}