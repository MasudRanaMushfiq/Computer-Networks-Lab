# Computer Networking Lab — Java Socket & Cisco Packet Tracer

Hands-on networking lab covering **TCP Socket Programming**, **Secure Email via SMTP over SSL**, and **Cisco Packet Tracer simulations**. Built using raw Java socket APIs and Cisco from scratch.

---

## 🎯 Objectives

- Implement **TCP Server–Client** communication using Java sockets
- Send real email using **SMTP over SSL** (port 465) with Base64-encoded credentials
- Design and simulate network topologies in **Cisco Packet Tracer**: Two Router, Static Routing, RIP, Inter-VLAN Routing

---

## ⚙️ Requirements

- **Java JDK 8+** — IntelliJ IDEA, Eclipse, or VS Code
- **Cisco Packet Tracer 8.x**
- Gmail account with **App Password** (2-Step Verification enabled)

---

## 📂 Project Structure

```
Networking-Lab/
├── Server and Client/
│   ├── Server.java
│   └── Client.java
├── Email Send/
│   └── Email.java
└── Cisco Packet Tracer/
    ├── Two Router Communication.pkt
    ├── Static Routing.pkt
    ├── RIP Routing.pkt
    └── Inter Vlan Routing.pkt
```

---

## 🚀 How It Works

### 1. TCP Server–Client (`Server and Client/`)

#### Supported Commands

| Command   | Server Response                  |
|-----------|----------------------------------|
| `Hi`/`Hello` | `Hello from server!`          |
| `IP`      | `Server IP: <local IP address>`  |
| `Date`    | `Date: dd/MM/yyyy`               |
| `Time`    | `Time: HH:mm:ss`                 |
| `Exit`    | Closes connection                |

#### How to Run

1. Run `Server.java` first — it waits for a connection.
2. Run `Client.java` — enter commands from the menu.
3. Type `Exit` to terminate both ends.

---

### 2. Secure Email via SMTP over SSL (`Email Send/`)

Implements the full **SMTP handshake** over `SSLSocket` — no external library used.

#### Key Code Pattern

```java
SSLSocket s = (SSLSocket) SSLSocketFactory.getDefault().createSocket("smtp.gmail.com", 465);

String username = new String(Base64.getEncoder().encode(user.getBytes()));
String password = new String(Base64.getEncoder().encode(pass.getBytes()));

send("AUTH LOGIN\r\n");
send(username + "\r\n");
send(password + "\r\n");
send("MAIL FROM:<sender@gmail.com>\r\n");
send("RCPT TO:<recipient@gmail.com>\r\n");
send("DATA\r\n");
send("Subject: Your Subject\r\n\r\n");
send("Email body here.\r\n");
send(".\r\n");   // Single dot = end of message
send("QUIT\r\n");
```

#### How to Run

1. Enable 2-Step Verification → generate an **App Password** from Google Account → Security.
2. Replace credentials in `Email.java`:
   ```java
   String user = "your_email@gmail.com";
   String pass = "xxxx xxxx xxxx xxxx";
   ```
3. Compile and run: `javac Email.java && java Email`

> ⚠️ **Never use your regular Gmail password** — App Passwords are mandatory.

---

### 3. Cisco Packet Tracer Simulations

| Topology | Description |
|----------|-------------|
| **Two Router Communication** | Basic inter-router IP addressing |
| **Static Routing** | Manually configured routing tables |
| **RIP Routing** | RIP v2 dynamic routing with auto-discovery |
| **Inter-VLAN Routing** | VLANs on a switch with router-on-a-stick |

**Static vs RIP comparison:**

| Feature       | Static Routing | RIP Routing           |
|---------------|----------------|-----------------------|
| Configuration | Manual         | Automatic             |
| Scalability   | Low            | Medium                |
| Convergence   | Instant        | Slow (hop-count based)|

> Open `.pkt` files via `File → Open` in [Cisco Packet Tracer](https://www.netacad.com/).

---

## 💡 Key Concepts

- **TCP** is connection-oriented — `ServerSocket.accept()` blocks until a client connects.
- **`DataInputStream/DataOutputStream`** transmit strings with a 2-byte length prefix — both sides must use the same type.
- **SMTP is text-based** — every command ends with `\r\n`; a single dot `.` signals end of message body.
- **Base64 encoding** is required for SMTP `AUTH LOGIN` credentials.
- **`SSLSocket`** wraps TCP with TLS — Gmail port 465 requires SSL from the first byte.
- **VLANs** segment a physical switch logically; inter-VLAN traffic requires a Layer 3 device.

---

## ⚠️ Important Notes

- Always start **Server before Client** — client throws `ConnectException` otherwise.
- **Never hardcode credentials** in shared/public repos — use environment variables in production.
- Cisco `.pkt` files are **version-sensitive** — update Packet Tracer if a file fails to open.

---

## 📖 References

- [RFC 5321 — SMTP Specification](https://www.rfc-editor.org/rfc/rfc5321)
- [Cisco NetAcad — Packet Tracer](https://www.netacad.com/)
- [Google App Passwords](https://support.google.com/accounts/answer/185833)

---

## 👨‍💻 Author

**Masud Rana Mushfiq** — Dept. of Computer Science and Engineering, University of Rajshahi
