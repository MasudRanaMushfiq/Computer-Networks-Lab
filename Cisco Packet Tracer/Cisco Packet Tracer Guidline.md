

### How Use Cisco Packet Tracer(For Ubuntu Only):

**1. Install Cisco Packet Tracer**  (Installation process mentioned here is Only for Ubuntu)
Download the `.deb` file from the official Cisco Networking Academy website and install it using:

```bash
cd ~/Downloads  
sudo dpkg -i CiscoPacketTracer_amd64.deb  
sudo apt-get install -f   # only if dependencies are broken
```

**2. Open the Software and Log In**  
After installation, open Packet Tracer. To use it, you **must log in**:

- If you want easy login, first log in to the Cisco Networking Academy website **before opening the app**.
- If you log in before opening Packet Tracer, you can simply click “Login via browser” and it will log in easily.
- If you face problems logging in or cannot log in, there is a trick: **turn off all network connections** (airplane/flight mode). 
- In offline mode, Packet Tracer will not ask for login and can be used without internet.


**Tutorial Link**: [Cisco Packet Tracer Bangla Tutorial](https://youtube.com/playlist?list=PL-fqJwobrXB9fRVmsloEOR2QdI32kY2mD&si=VkA8vx68yiWSaqtJ)  

### Cable Description:

**1. ⚡Automatic (Auto-Connect) Cable**  
Used to automatically connect devices without manually selecting a cable type.
**Why it works:** Packet Tracer detects the type of devices and their ports, then automatically chooses the correct cable (straight-through, cross-over, or serial) based on networking rules.

**2. Copper Straight-Through Cable**  
Used to connect different types of devices such as PC to Switch or Switch to Router.  
**Why it works:** Different devices have opposite TX (Transmit) and RX (Receive) pin configuration, so signals go directly without crossing.

**3. ▬ ▬ Copper Cross-Over Cable**  
Used to connect same types of devices such as Switch to Switch or Router to Router.  
**Why it works:** Same-type devices have the same TX and RX pins, so the cable crosses the wires to match transmit to receive.

**4. Serial Cable (DCE/DTE)**  
Used to connect Router to Router for WAN communication for extreme fast connection.  
**Why it works:** The DCE side provides the clock signal required for data transmission, and the DTE side uses that clock to synchronize communication.

### Which Device Model Need to Choose:
- **PC / Laptop** – No specific model needed; use any.
- **Hub** – Use **PT Hub**.
- **Router** – Use **2911**.
- **Switch** – Use **2960**.

- To place the **same device or cable multiple times** in the workspace, select the device while holding the `Ctrl` button and now just click on the workspace; by clicking, you can add multiple devices.

---

#### Note:
- The **default gateway** of a PC is the IP address that devices in a network use to send traffic to **other networks**.
- On a PC or host, you set the default gateway to the **IP address of the router port** that is **connected to the same network/subnet** as the PC.

#### Question 01: 
If two computers are connected to a switch and the switch is connected to a router, does the switch need any configuration?

**Answer:**  **NO**
Here’s why:
1. **Switch works at Layer 2 (Data Link Layer)** – it just forwards frames based on MAC addresses.
2. **PCs connected to the switch** can communicate with each other directly without any switch configuration.
3. **Switch doesn’t need an IP address** unless you want to manage it (like logging into the switch for settings).
4. **Router handles Layer 3 (IP routing)** – the default gateway on the PCs points to the router port so they can reach other networks.

#### Question 02: 
Why do the networks `192.168.10.0/24` (subnet mask `255.255.255.0`) and `192.168.13.0/16` (subnet mask `255.255.0.0`) overlap, even though the last two octets and their subnet mask range are different?

**Why they overlap**
- `/16` network (`192.168.0.0/16`) **includes** all addresses from `192.168.0.0` to `192.168.255.255`.
- That **range includes `192.168.10.0`**, which is the `/24` network of Network A.
- Therefore, from the router’s perspective, Network A is **inside Network B**, causing **overlap and routing confusion**.

**Key point**
- Two networks **only do not overlap** if their **network ranges (defined by IP + subnet mask)** do not intersect.
- Changing the subnet mask changes how “big” the network is. A `/16` network is huge, covering many smaller `/24` networks.

#### Question 03: 
If I have two networks connected to two separate routers, and the routers are connected to each other, with Network 1 as `192.168.10.0/24`, Network 2 as `192.168.11.0/24`, and the cable between the routers as `192.168.12.0/24` and their subnet is `255.255.255.0`, is there any conflict since the first three octets are all different?

**Answer:**  There is **no conflict**.

#### Note: 
When you configure a static route on a router, you tell it:
1. **Which network you want to reach** (the final destination network).
2. **The subnet mask of that network**.
3. **The next-hop IP address** (the router IP to forward the packet to reach that final network).
4. **Next-hop IP** in static routing is always the **neighbor router’s IP on the directly connected link** not its own port ip.

#### Note on RIP 
**RIP (Routing Information Protocol)** is a dynamic routing protocol that automatically shares routing information between routers using **hop count** as a metric.

**When configuring RIP, you must add:**
- The **network address** of every network directly connected to that router by port.
- Not the interface IP address.
- Not remote networks.

---

### Inter-VLAN Routing

**Inter-VLAN Routing** is a method that allows devices in **different VLANs** (different networks) to communicate with each other using a Layer 3 device (Router).

Since each VLAN belongs to a different IP subnet, communication between them requires routing.

**More Inter Vlan Routing Info:** [Inter Vlan Routing Artcile By Cisco Press](https://www.ciscopress.com/articles/article.asp?p=3089357&seqNum=4)   
**Youtube Tutorial:** [Inter VLan Routing Bangla Tutorial](https://youtu.be/LfbC4KWWml4?si=6X2PNx2o7OpLBdL1)    

**Why Inter-VLAN Routing is Needed**
- VLAN 10 → 192.168.10.0/24
- VLAN 20 → 192.168.20.0/24

These are different networks.  
Devices in different networks cannot communicate directly without a router.

### Router-on-a-Stick Inter-VLAN Routing

**How It Works**
1. Each VLAN has its own subnet.
2. Each subnet has its own default gateway.
3. The default gateway is configured on the router (using subinterfaces).
4. When a device wants to communicate with another VLAN:
    - It sends the packet to its default gateway.
    - The router receives the packet.
    - The router checks the destination network.
    - The router forwards the packet to the correct VLAN.

> [!NOTE]
> Inter-VLAN Routing is the process of enabling communication between different VLAN networks using a router that acts as the default gateway for each VLAN.

**Configure Sub-interface**
1. Enable physical interface:
```c
interface g0/0  
no shutdown           // Activate interface
```

2. Create **subinterfaces** for each VLAN:
```c
interface g0/0              // Enter into port physical interface
no shutdown                 // Enable the physical interface

interface g0/0.10           // Create subinterface 10 (virtual interface) in 0/0
encapsulation dot1Q 10      // Bind this subinterface to VLAN 10
ip address 192.168.10.1 255.255.255.0  // Set gateway for VLAN 10

interface g0/0.20           // Create subinterface 20 (virtual interface) in 0/0
encapsulation dot1Q 20      // Bind this subinterface to VLAN 20
ip address 192.168.20.1 255.255.255.0  // Set gateway for VLAN 20
```

**Explanation:**

| Command                  | Purpose                                                    |
| ------------------------ | ---------------------------------------------------------- |
| `g0/0.10`                | Creates subinterface 10 on physical interface g0/0         |
| `encapsulation dot1Q 10` | Tells router to handle VLAN 10 frames on this subinterface |
| `ip address ...`         | Sets default gateway for VLAN 10                           |



**Question:**  
What does `dot1Q` mean in router subinterfaces?

**Answer:**
`dot1Q` is a standard **VLAN tagging protocol** (IEEE 802.1Q).
- It tells the router that the subinterface will handle traffic **tagged with a specific VLAN**.
- Without `dot1Q`, the router cannot distinguish which VLAN a frame belongs to when multiple VLANs share the same physical interface.

---







