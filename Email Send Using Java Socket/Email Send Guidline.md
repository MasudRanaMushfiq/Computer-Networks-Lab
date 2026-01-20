
## Java Email Sending via SMTP (SSL)

### Topic:
- Java Socket Programming
- Secure Network Programming (SSL/TLS)
- SMTP Protocol (Email sending)
### What the Program Does:

1. **Connects to Gmail SMTP server securely**
   - Uses `SSLSocket` to connect to `smtp.gmail.com` on port `465`.

2. **Authenticates the user**
   - Converts email and password to Base64.
   - Sends `AUTH LOGIN` command followed by credentials.

3. **Sends an email**
   - Specifies sender: `MAIL FROM`.
   - Specifies recipient: `RCPT TO`.
   - Sends email content using `DATA` command:
     - Includes headers: `FROM`, `TO`, `Subject`.
     - Email body.
     - Ends with a single `.` to signal end of message.

4. **Closes connection**
   - Sends `QUIT` command to terminate the SMTP session.

### Steps:
1. At first write code in Java language. 
2. Give `Gmail Address` and `App Password`. 
	- To get App Password Go to `Manage Your Gmail`
	- Go to Security and ensure `2-step verification` is ON.
	- Search in the search bar "App Password"
	- Give any name for app and get "App Password"
3. Replace `Sender` and `Receiver` Gmail Address. 
4. Replace `From` and `To` Address. 
5. Write `Email Subject` and `Email Body`. 
6. `Run` Java code file. 

This will now take some seconds and automatically sent email to the receiver. 

