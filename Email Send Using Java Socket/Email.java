import java.io.*; // Import classes for input/output streams
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

    // Store Gmail app password (used for SMTP authentication) in variable
    String pass = "dbde bupl yhgn nops";

    // The given string is converted to bytes, then encoded into Base64 ASCII characters because SMTP is text-based so rather than binary data ASCII cherecter is safe.
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

    // Input stream to read responses from the server
    br = new BufferedReader(new InputStreamReader(s.getInputStream()));

    // Read and print the initial SMTP server greeting
    System.out.println("SERVER: " + br.readLine());

    // Send EHLO command to identify the client to the server
    send("EHLO smtp.gmail.com\r\n");

    // Read multi-line server response for EHLO
    String line;
    while ((line = br.readLine()) != null) {
      System.out.println("SERVER: " + line);
      if (line.startsWith("250 "))
        break; // Last line of EHLO response
    }

    // Start SMTP authentication process
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

    // Single dot indicates end of email data
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

    // Delay to allow server to process the command
    Thread.sleep(1000);

    // Print what the client sends
    System.out.println("CLIENT: " + s);
  }
}
