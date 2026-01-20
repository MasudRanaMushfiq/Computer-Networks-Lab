/*
 * Program Name : TCP Client Program (String-based Communication)
 * Description  : This program implements a TCP client using Java sockets.
 *                The client connects to a server and sends text-based
 *                commands such as Hi, Hello, Date, Time, and Exit.
 *                The server response is received and displayed on the client.
 * Port Number  : 5000
 * Protocol     : TCP
 * Author       : Masud Rana Mushfiq
 */

import java.io.*; // Import classes for input/output streams 
import java.net.*; // Used for networking classes (ServerSocket, Socket)
import java.util.Scanner; // Used to take input from the keyboard.

public class Client {
    public static void main(String[] args) { // Program execution starts from here 
        try {
            // Creates a server socket "localhost" means the server is running on the same machine 
            Socket s = new Socket("localhost", 5000); 

            DataInputStream dis = new DataInputStream(s.getInputStream());
            // Used to send data to the server 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            Scanner sc = new Scanner(System.in); // Reads user input from the keyboard 

            boolean running = true;

            while (running) {
                // Displays available commands to the user 
                System.out.println("\n--- MENU ---");
                System.out.println("Type: Hi");
                System.out.println("Type: Hello");
                System.out.println("Type: Time");
                System.out.println("Type: Date");
                System.out.println("Type: Exit");
                System.out.print("Enter command: ");

                String query = sc.nextLine();   // Reads the entire line entered by the user 
                // Sends the user command to the server. flush() ensures data is sent immediately 
                dos.writeUTF(query); 
                dos.flush();

                String response = dis.readUTF(); // Reads server reply as a String 
                System.out.println("Server response: " + response); // Prints server message on client console 

                if (query.equalsIgnoreCase("exit")) {
                    running = false; 
                }
            }

            sc.close();
            dis.close();
            dos.close();
            s.close();
            // // Closes: Scanner, Input stream, Output stream, Socket connection

        } catch (Exception e) {
            e.printStackTrace();
            // Prints full error details for debugging 
        }
    }
}



