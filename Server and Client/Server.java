/*
 * Program Name : TCP Server Program (String-based Communication)
 * Description  : This program implements a TCP server using Java sockets.
 *                The server accepts client connections and responds to
 *                text-based commands such as Hi, Hello, Date, Time, and Exit.
 *                Communication is done using DataInputStream and
 *                DataOutputStream.
 * Port Number  : 5000
 * Protocol     : TCP
 * Author       : Masud Rana Mushfiq
 */

import java.io.*; // Import classes for input/output streams 
import java.net.*; // Used for networking classes (ServerSocket, Socket)
import java.util.Date; // Used to get current date and time
import java.text.SimpleDateFormat; // Used to format date and time into readable form 

public class Server {
    public static void main(String[] args) { // Execution starts from here.
        try {
            ServerSocket ss = new ServerSocket(5000); // Creates a server socket 
    // Port 5000 is not fixed. Any free port number can be used, but the server and client must use the same port.
            System.out.println("Server started. Waiting for client..."); // Prints server status on console
            
            // Server waits here until a client connects.
            // Once connected, a Socket object is created.
            Socket s = ss.accept();
            System.out.println("Client connected.");

            // Used to receive and sent data from client
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            boolean running = true;

            while (running) {
                String query = dis.readUTF();   // Reads a String command sent by client. 
                String response;

                // show client query
                System.out.println("Client query: " + query);
                
                // equalsIgnoreCase() ignores case sensitivity 
                if (query.equalsIgnoreCase("hi") || query.equalsIgnoreCase("hello")) {
                    response = "Hello from server!";

                } else if (query.equalsIgnoreCase("date")) {

                    // Gets current date and Formats it as day/month/year.
                    response = "Date: " +
                            new SimpleDateFormat("dd/MM/yyyy")
                            .format(new Date());

                } else if (query.equalsIgnoreCase("time")) {

                    response = "Time: " +
                            new SimpleDateFormat("HH:mm:ss")
                            .format(new Date());

                } else if (query.equalsIgnoreCase("exit")) {

                    response = "Server closing connection...";
                    dos.writeUTF(response);
                    dos.flush(); // flush() ensures data is sent immediately 
                    running = false;
                    continue; // continue skips remaining loop and as rummig is false so overall loop is terminated 

                } else {
                    response = "Unknown command!";
                }

                dos.writeUTF(response);
                dos.flush();
                System.out.println("Response sent.");
            }

            dis.close(); // Closes input/output streams, Closes client socket, Closes server socket 
            dos.close();
            s.close();
            ss.close();

            System.out.println("Server closed.");

        } catch (Exception e) {
            e.printStackTrace(); // Prints error details everything 
        }
    }
}


