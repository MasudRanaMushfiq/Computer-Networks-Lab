


### Network Topology
```
PC1 ----|                          |---- PC3
        |-- Switch1 ---- Switch2 --|
PC2 ----|                          |---- PC4
```

### VLAN Plan

|VLAN ID|Name|PCs|
|---|---|---|
|10|CSE|PC1, PC3|
|20|CSE|PC2, PC4|

### IP Address Plan

#### VLAN 10

- PC1: 192.168.10.1 /24
- PC3: 192.168.10.3 /24

#### VLAN 20

- PC2: 192.168.20.2 /24
- PC4: 192.168.20.4 /24

(No gateway needed because no routing)

### Step 1: Place Devices in Packet Tracer

- 2 × Switch (2960)
- 4 × PC
- Connect:
    - PC ↔ Switch using **Copper Straight-Through**
    - Switch ↔ Switch using **Copper Cross-Over**

### Step 2: Configure PCs

#### PC1
```
IP: 192.168.10.1
Mask: 255.255.255.0
```

#### PC2
```
IP: 192.168.20.2
Mask: 255.255.255.0
```

#### PC3
```
IP: 192.168.10.3
Mask: 255.255.255.0
```

#### PC4
```
IP: 192.168.20.4
Mask: 255.255.255.0
```

### Step 3: Configure Switch 1 (S1)

Enter CLI:
```
enable
configure terminal
```

### Create VLANs
```
vlan 10
name CSE
exit

vlan 20
name CSE
exit
```

### Assign PC Ports (example ports)
```
interface fastEthernet0/1
switchport mode access
switchport access vlan 10
exit

interface fastEthernet0/2
switchport mode access
switchport access vlan 20
exit
```

### Configure Trunk Port (to Switch 2)
```
interface fastEthernet0/24
switchport mode trunk
exit
```

### Step 4: Configure Switch 2 (S2)

```
enable
configure terminal
```

### Create VLANs (must match)

```bash
vlan 10
name CSE
exit

vlan 20
name CSE
exit

no vlan 10 # This deletes VLAN 10 from the VLAN database.
```
- What happens to ports when a VLAN is deleted?
- Any **access port** that was assigned to VLAN 10 becomes **inactive**
- To make it active do `switchport access vlan 1`, it take the port to default vlan. 
- By default, **VLAN 1** is the native VLAN 

### Assign PC Ports
```
interface fastEthernet0/1
switchport mode access
switchport access vlan 10
exit

interface fastEthernet0/2
switchport mode access
switchport access vlan 20
exit
```

### Trunk Port (to Switch 1)
```
interface fastEthernet0/24
switchport mode trunk
exit
```

### Step 5: Verify Configuration

### Check VLANs
```
show vlan brief
```

### Check Trunk
```
show interfaces trunk
```



### Step 6: Test Connectivity

#### From PC1
```
ping 192.168.10.3
```

- Or you can transfer data using UI from Cisco Packet Tracer. 


### What is a trunk port (in simple words)?

A **trunk port** is a switch port that:
- Can carry **traffic from multiple VLANs at the same time using single wire**
- Adds a **VLAN tag** to each Ethernet frame
- Uses **IEEE 802.1Q tagging**

Without trunk → switch has **no VLAN information** across switches.

### Why access port is not enough?

An **access port**:
- Belongs to **only ONE VLAN**
- Does **not** tag frames
- Used for **end devices** (PC, printer)

### Why `192.168.1.0` cannot be assigned to a PC
In any IPv4 subnet:
- **First address** → Network address
- **Last address** → Broadcast address
- **Middle addresses** → Usable host IPs

- `192.168.1.0` represents the **entire network itself**, not a device.

---








