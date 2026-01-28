/*
 * Program Name : Secure Email Sending using SMTP over SSL
 * Description  : This program sends an email using the SMTP protocol
 *                over a secure SSL connection. It connects to Gmail's
 *                SMTP server, authenticates using Base64-encoded
 *                credentials, and sends an email using standard
 *                SMTP commands.
 *
 * SMTP Server  : smtp.gmail.com 
 * Port Number  : 465 
 * Protocol     : SMTP over SSL 
 *
 * Author       : Masud Rana Mushfiq 
 */ 

import java.io.*; // Import classes for input/output streams
                  // * means import all public classes and interfaces inside the java.io package.
import javax.net.ssl.*; // Import SSL socket classes for secure communication
import java.util.*; // Import utility classes fro Base64 encoder

class Email{

  // Declare static stream references(variable), the actual stream objects are initialized later at runtime.
  private static DataOutputStream dos;   // Used to send data to the SMTP server
  public static BufferedReader br;   // Used to read responses from the SMTP server

  // Main method: program execution starts here

  // "throws Exception" means checked exceptions are not handled here;
  // they are passed to the JVM, which stops the program if an error occurs.
  public static void main(String argv[]) throws Exception {

    // Store Sender email address in variable 
    String user = "s2211176104@ru.ac.bd";

    // Store Gmail App Password (used for SMTP authentication) in variable
    String pass = "ecef cvqm ziho opqt"; //replaced

    // The given string is converted to bytes, then encoded into Base64 ASCII characters 
    // because SMTP is text-based so rather than binary data ASCII cherecter is safe.
    String username = new String(Base64.getEncoder().encode(user.getBytes()));

    // Encode password in Base64
    String password = new String(Base64.getEncoder().encode(pass.getBytes()));

    // Create a secure SSL socket connection to Gmail SMTP server on port 465 
    SSLSocket s = (SSLSocket) SSLSocketFactory
                    .getDefault()
                    .createSocket("smtp.gmail.com", 465);

    // Gets the raw byte output stream of the SSL socket and Wrap it with DataOutputstream
    // Output stream to send commands to the server 
    dos = new DataOutputStream(s.getOutputStream());

    // Gets raw bytes coming from the server, Converts bytes into characters, read line by line efficiently
    // BufferedReader() reads one line of text from the input stream (until it finds a newline \n) and returns it as a String.

    // Input stream to read responses from the server
    br = new BufferedReader(new InputStreamReader(s.getInputStream()));

    // Reads a single line from the server using BufferedReader.readLine(), returns it as a String, and prints it with the prefix "SERVER: ".
    // Read and print the First SMTP server greeting
    System.out.println("SERVER: " + br.readLine());

    // "EHLO smtp.gmail.com\r\n" sends the EHLO command to setup communication as client and here \r\n marks the end of the line.
    send("EHLO smtp.gmail.com\r\n");

    // Read multi-line(9 times) server response for EHLO
    // "250" is the final response line for EHLO which means END
        send("EHLO smtp.gmail.com\r\n");
              System.out.println("SERVER: "+ br.readLine());
              System.out.println("SERVER: "+ br.readLine());
              System.out.println("SERVER: "+ br.readLine());
              System.out.println("SERVER: "+ br.readLine());
              System.out.println("SERVER: "+ br.readLine());
              System.out.println("SERVER: "+ br.readLine());
              System.out.println("SERVER: "+ br.readLine());
              System.out.println("SERVER: "+ br.readLine());
              System.out.println("SERVER: "+ br.readLine());

    // Start SMTP authentication process
    // SMTP commands must end with \r\n becauseserver only detects the end command when receives CRLF
    send("AUTH LOGIN\r\n");
    System.out.println("SERVER: " + br.readLine());

    // Send Base64 encoded username
    send(username + "\r\n");
    System.out.println("SERVER: " + br.readLine());

    // Send Base64 encoded password
    send(password + "\r\n");
    System.out.println("SERVER: " + br.readLine());

    // Specify sender email address
    send("MAIL FROM:<s2211176104@ru.ac.bd>\r\n");
    System.out.println("SERVER: " + br.readLine());

    // Specify recipient email address
    send("RCPT TO:<masudranaorg71@gmail.com>\r\n");
    System.out.println("SERVER: " + br.readLine());

    // Tell server that email content will follow
    // After this command, the server switches into message input mode 
    send("DATA\r\n");
    System.out.println("SERVER: " + br.readLine());

    // Email header: FROM
    send("FROM: s2211176104@ru.ac.bd\r\n");

    // Email header: TO
    send("TO: masudranaorg71@gmail.com\r\n");

    // Email header: Subject
    send("Subject: Email test\r\n");

    // Blank line separating headers and body (mandatory)
    send("\r\n");

    // Email body content
    send("THIS IS A TEST EMAIL. THANK YOU\r\n");

    // Single dot indicates end of email data to SMTP server as command 
    send(".\r\n");
    System.out.println("SERVER: " + br.readLine());

    // Close SMTP session
    send("QUIT\r\n"); 
    System.out.println("SERVER: " + br.readLine());
  }


  // Method to send data to SMTP server
  private static void send(String s) throws Exception {

    // Write command to the server
    dos.writeBytes(s);

    // Force data to be sent immediately
    dos.flush();

    // Delay to allow server to process the command(Optional)
    Thread.sleep(1000);

    // Print what the client sends
    System.out.println("CLIENT: " + s);
  }
}


