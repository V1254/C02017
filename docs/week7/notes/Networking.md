# Networking.

## Overview & Introduction

- Introduction to Computer networks
- Basic concepts involving: Classification, Layered Structure, Protocols and Services.

A computer network is a collection of **interconnected autonomous** computers. Two computers are said to be interconnected if they can exchange information.

Computer Network vs **Distributed System (DS)**
- A Distributed System is coherent and transparent; eg: WWW
- A Distributed System is a software built on top of a network.

## Types of Networks


##### Network Classification

There are two important criteria to consider when selecting a network; **transmission technology** and scale.

According to the transmission technology:
- Broadcast networks have a single communication channel shared by all machines.
- Point-to-point networks consist of many connections between pairs of machines.

According to the scale:
- Personal Area Networks - **PAN**s.
- Local Area Networks - **LAN**s.
- Metropolitan Area Networks - **MAN**s
- Wide Area Networks - **WAN**s.
- **Internetworks**.

Network Classification by Scale:

![](https://i.imgur.com/LX693OT.png)

### Local Area Networks

- Privately Owned by an **organisation**.
- Fairly small, up to a **few kilometres** in size.
- Speeds of **100MBps - 10Gbps** and few errors.
- Mix of broadcasts (**wireless, bug or ring** topologies.)

![](https://i.imgur.com/1AQ6Zk0.png)

### Metropolitan Area Networks

Covers a city or other large area and can be public or privately owned. eg; cable based broadband.

![](https://i.imgur.com/91iTiFg.png)

### Wide Area Networks

- Covers a country or continent
- Computers/**hosts** or smaller networks are connected by a **communication subnet**.
- Subnet consists of **transmission lines** and **switching elements** called **routers**.

![](https://i.imgur.com/M986x2d.png)

### Internetworks

- Collection of interconnected networks.
- Connected by **gateways** which translate data if necessary.
- Example : **INTERNET**.

**Subnet** = routers + communication lines
**Network** = subnet + hosts
**Internetwork** = different networks + gateways. 

## Layered Protocols

To exchange information, the communicating machines need to agree on **how to communicate**.

A **protocol** is a collection of rules describing how communication is to proceed. This can be things
like the order of messages and how the info is presented within a message.

Due to the complexity of computer communications, protocols are **arranged in layers** with each layer handling
different aspects of the communication.

![](https://i.imgur.com/PPMwZio.png)

Each layer **provides services** to the next layer up.

Each layer carries out a 'conversation' with the same layer on another host.The rules used 
form that layer's **protocol**.

**Peers**: two hosts/processes carrying out a 'conversation'.

The **interface** between two layers defines which primitive operations and services the 
lower layer makes available to its upper layer.

![](https://i.imgur.com/JV3qu7N.png)

### Services

A **service** is a specified set of **primitives** or **operations** available to a user or other activity.

**Connection-oriented** services:
- Use for a sequence of messages.
- Analogous to the telephone system.
- Used for file transfer/remote login.

**Connectionless** services: 
- Use for single messages which are **self-contained**.
- Analogous to the postal system.
- Might be unreliable, reliable, or request-reply services.
- Eg: database queries.

### Services and Protocols

A service is a **set of primitives** that a layer provides to its upper layer.
It defines what a layer does,but not how to implement said operations.

A **protocol** is a set of rules governing the format and meaning of the info exchanged 
by peer entities of the same layer.

Entities use protocols to implement their service definitions

![](https://i.imgur.com/gjEQJNj.png)
### Virtual Communication Channel

**No real data is passed directly between peers (except at the lowest layer)**.

The layer *n* sender entity passes the info to layer *n-1*. The layer *n* receiver
gets info from layer *n-1*.

Each layer may add a **header** and/or **trailer** to the info it sends, which is stripped off by its peer.

![](https://i.imgur.com/ScmTIUI.png)

### The ISO/OSI Reference Model

The International Standards Organization (ISO) Open Systems Interconnection (OSI) reference model defines seven layers

![](https://i.imgur.com/9b4j4CH.png)

Physical Layer: responsible for transmitting raw **bits** over a communication medium

Data link layer: transforms the raw transmissions media into a **reliable line** for
the network layer; transmit **data frames** while **detecting** and **correcting** errors.

Network Layer: **routing** between non-adjacent nodes within the **subnet**; transmits **packets** of data.

Transport layer: accepts data from the session layer, **splitting it** and ensures all the parts
arrive correctly at the other end.

Session layer: establish **sessions** between different machines.

Presentation layer: **encodes** data in a standard way, and **translates** between different representations.

Application layer: contains common **application oriented protocols** eG: file transfer protocol (ftp) and email.

### TCP/IP Model

The model has four layer and utilises the **Internet Protocol**(IP) and **Transmission Control Protocol**(TCP)

![](https://i.imgur.com/u3Knb03.png)

Host-to-network-layer: (**link**) logically sits below the internet layer and combines data link and physical layers from the OSI model.

Internet layer: responsible for transporting packets over the network; it use the **Internet Protocol**(IP).

Transport layer: allows peer entities to carry on an established communication channel. 
Utilises two protocols TCP (**reliable**) and UDP(**unreliable**).

Application layer: contains **application-oriented** protocols.

Layers and protocols:

![](https://i.imgur.com/PvKbQYr.png)

### Hybrid Model

Physical Layer: Includes the transmission hardware:

Data link Layer: Includes software for effective transmission of bits between computers.

Network Layer: communicating computers are not **directly** connected but this layer establishes a route.

Transport Layer: High level communication protocols between hosts.

Application Layer: The software used with the internet; Web browsers, email, search engines, etc..;

## Summary

- Definition of computer networks
- Classification of computer networks.
- Network software protocols.
- The OSI & TCP/IP reference models.