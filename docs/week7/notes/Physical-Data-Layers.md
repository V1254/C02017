# Physical & Data Layers.

## Overview

1. Network Layers
2. The Lower Layers
3. Frames and Delimiters.
4. Error correcting and detecting encoding.

## Physical Layer

Aim is to transmit **raw bits** reliably between devices.

There are two broad kinds of transmission media:
- **Guided(wired)**: coaxial cable and fibre optics.
- **Unguided(wireless)**: radio, lasers, micro-waves and infra-red..

Transmission media differ in bandwidth, propagation delay, cost, ease of installation
and maintenance.

##### Manchester Encoding:

Each bit period has two intervals.
- 0 bit: a low voltage in 1st interval and a high one in 2nd.
- 1 bit: a high voltage in 1st interval and a low one in 2nd.

Every bit has a transition in the middle; so no signal or loss of clock can
always be detected.

![](https://i.imgur.com/uf2cl37.png)

The above in binary is: 10000101111

##### Differential Manchester Encoding:
Each bit period has a voltage in the middle.

0 bit: a change of horizontal voltage at the start of the bit period
1 bit: a change of vertical voltage at the start of the bit period.

Polarity does not affect this type of encoding.

## Data Link Layer

The central issue is to **reliably** transmit data over a non-reliable channel.

Reliability means:
- Avoid **loss** or **corruption** of information.
- Avoid a faster sender **swamping** a slower receiver with a flood of bits.

Establishing absolute reliability is impossible.

Different approaches make particular assumption about the conditions affecting reliability.

### Services at the data-link layer

There are 3 types of Services at the Data Link Layer:

- **Unacknowledged connectionless**: a stream of bits is sent without any concern of their fate.
- **Acknowledge connectionless**: sender dives the stream into **frames** and requires **acknowledgement** that each 
frame has arrived.
- **Acknowledged connection-oriented**: a connection is established before any frames are sent. Frames
that are sent must be received in order.

### Framing

Data is sent in small pieces called **frames** and an **acknowledgement** is sent to the sender after each frame.

Applicable to **acknowledged connectionless** and **connection-oriented** services.

**Acknowledgements help with:**
- avoiding overloading of a slow receiver
- **localising** any transmissions problems to the **most recent frame**.

### Delimiters and Stuffing

The sender needs to specify the **beginning** and **end** of frames using **delimiters**.

A **delimiter** is a particular byte **preceding** and **following** each frame.

There are two protocols:

- **Stuffing byte** protocol
- **Stuffing bit** protocol

###### Stuffing Byte Protocol

Two special bytes are needed; the delimiter itself and the control byte.

If the **delimiter** byte occurs in the frame, put the control byte before it in the frame.

If the **control** byte itself occurs in the frame, add **another** control byte is before it.

Example:

Delimiter Byte = 0;
Control Byte = 1;
Need to send: 21501601
Actually send: **0**2**1**15**1**0**1**16**1**0**1**10

###### Stuffing Bit Protocol

The **Delimiter** is '01111110', inside the frame '0' is inserted after every occurs of '11111'.

Example:

Need to send: 00111111 10110001
Actually send: **01111110** 0011111**0** 110110001 **01111110**

This results in a smaller overhead than byte stuffing.

### Corruption, Parity and Redundancy

Data can be **corrupted** by the physical layer where **error**s are introduced.

Some Examples of Corruption:

Sending 'LONDON' and Receiving 'OXFORD'
Sending '0' and Receiving 1.

100% Data Corruption Guarantee is impossible to achieve.

###### Parity Encoding

In each byte, 7 bits are allocated for the content, one for **verification**.
The **verification** bit is called a **parity** bit.

If the number of '1's in the 7 bit is even then the parity that is added is '0' , otherwise '1'.

###### Parity Encoding :- Processing

The sender calculates the correct value of the parity bit and transmits it.

The receiver re-calculates parity and checks if the received parity bit is the same.

If there a disparity between the two, an error is sent instead of an **acknowledgment**.

Parity encoding is an example of a **redundant encoding**.
To avoid corruption **verification** data is added to the real content.


###### Error Detecting vs Error Correcting

Error **detecting** codes detect particular types of errors without the ability to correct them.

Error **correcting** codes **put right** particular types of errors without needing re-transmission.


###### Triple Modular Redundancy.

Each 0 is replace by '000' and each 1 is replace by '111'. The Errors can be detected and corrected as single bit errors.

Example:
Want to transmit: **'011'**
Encode and Transmit as : **'000 111 111'**
Assume received:  **'010 001 110''**
Can successfully decode as: '011'.

###### Drawbacks of triple modular encoding:

There is a **high overhead** to the encoding as the verification data is **twice the size** of the original content.Thus
Adding **additional traffic** is passed through the network, slowing down communication.

#### Hamming Code

**Corrects** all single bit errors and can detec tup to 3 bit errors.

if there are **n** bits there code adds at most **log(n) + 1** verification bits.

![](https://i.imgur.com/UjtB1cI.png)

## Sliding Window Protocol

Sender transmits frames to the receiver. Frames are **sequentially numbered**, and receiver send an acknowlegement
for the OK frames.

When receiver **notices an error** in a frame, it needs to **transmit** a **nak** back to the sender.

The **nak** indicates the frame number where the error was received.

**Sender solutions**:

The sender can simply **go back** to the frame **after the last which was acked**
and **re-start** transmitting from that point.

This is a **sliding window** of 1.

**However** the receiving side may has already received intermediate frame by the time the nak is received by the sender.

We can use a **Non-trivial** sliding window to fix the above problem.

The receiver can **buffer** up to *n* frames after sending a **nak**.

The sender **only** need sto **re-send** the frames that were nak-ed.

After re-sending, the sender can then **resume** the sequence where it left off. This is a **sliding window of size *n***.




## Summary

- Physical layer concerned with transmission of signals along wire/through the air.
- Data layer concerned with splitting data into frames and encoding it to send
through potentially lossy physical layer.
- Considered error detection/correction methods for transmitted data.