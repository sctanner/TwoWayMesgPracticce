// Package for I/O related stuff
import java.io.*;
import java.util.*;
// Package for socket related stuff
import java.net.*;

public class asMethods {


    
    public static String sendMessage(PrintWriter messageWriter,BufferedReader fromUserReader){
        try{
        //accept a String from the user
        String line = fromUserReader.readLine();

				// If we get null, it means user is done, exit the loop which will close socket
				if(line == null) {
					System.out.println("Closing connection");
				}
				// Send the line to the server
				messageWriter.println(line);
                return line;
    }
    catch(Exception e) {
        System.out.println(e);
    }//close catch
    return "RIP!!!!";
    }//close sendMessage()


    public static String receiveMessage(BufferedReader newMessageReader){
        try{
        // Read a message from the client
				String message = newMessageReader.readLine();

				// If we get null, it means client sent EOF
				if (message == null) {
					System.out.println("Client closed connection");
				}
                return message;

            }
            catch(Exception e) {
                System.out.println(e);
            }//close catch
            return "RIP!!!!!!!!!!!";
    }
    


    public static void main(String args[])
	{
    if(args.length > 2 || args.length < 1){
        System.out.println("Please restart with port number (also name if client). Exiting...\n");
        System.exit(0);
    }



    //WE WILL BE A SERVER***********************************
    if(args.length == 1){
        
        
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
            String message = "Start message";
            BufferedReader messageReceiver = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
            PrintWriter messageWriter = new PrintWriter(clientSock.getOutputStream(), true);
            BufferedReader fromUserReader = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                
                message = receiveMessage(messageReceiver);
                if(message.equals(null)){
                    break;
                }
                System.out.println("Client: " + message + "\n");
                message = sendMessage(messageWriter, fromUserReader);
                if(message.equals(null)){
                    break;
                }
                    //System.out.println("Client: " + message + "\n");
            }
            //sock.close();
            messageReceiver.close();
            messageWriter.close();
            fromUserReader.close();
            clientSock.close();



    }
    catch(Exception e) {
        System.out.println(e);
    }

    }//WE WILL BE A CLIENT***********************
    
    else{
        
		int serverPort = Integer.parseInt(args[1]);
        //System.out.println("Server name?\n");
        String serverName = args[0];
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
			PrintWriter messageWriter = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader messageReceiver = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            String message = "Start message!";
            while(true){
                
                message = sendMessage(messageWriter, fromUserReader);
                if(message.equals(null)){ 
                    break;
                }
                //System.out.println("Server: " + message + "\n");
                message = receiveMessage(messageReceiver);
                if(message.equals(null)){
                break;
                }
                System.out.println("Server: " + message + "\n");
            }
            sock.close();
            messageReceiver.close();
            messageWriter.close();
            fromUserReader.close();
        
        }
        catch(Exception e) {
			// Print the exception message
			System.out.println(e);
		}
    }
    System.out.println("\nConnection ended... \nGoodbye!\n");
    System.exit(0);
    }//close main

    }//close class
