# Devices, Input/Output and Buffering.

## Overview

- Objectives of I/O management system
- Structure of I/O system
- Buffering techniques.

## I/O System
I/O devices vary in many ways:

![](https://i.imgur.com/nRSCuyX.png)

I/O devices are **much slower** than the processor and is it a desire to use I/O devices **efficiently** and 
**avoid blocking** CPU.

I/O devices are quite **complex and very diverse** and the OS user should not have to **know the details**  of I/O devices,
and I/O system should allow the OS user to treat devices in a **uniform way**.

The OS user should deal with **virtual devices** rather than actual devices.

#### Structure of I/O system

The I/O system consists of several **layers**.

**Application** programs are written in **high level languages** and translated to **system calls**.

I/O **Control System** deals with I/O related system calls.

The I/O systems comprises of the **Device Driver** and a **Device Controller**.

**Device Driver:**
- **software** module in the OS that **communicates** with a particular I/O **device**
- Converts **logical I/O** request into **specific commands** to the device.

**Device Controller**
- **Hardware unit** attached to the I/O bus
- Provides a hardware **interface** between **computer** and **I/O device**.

![](https://i.imgur.com/1JrNSPw.png)

The are two main kinds of **I/O Device**.

Block Device: transfers data in **groups of characters**.
Character Device: transfer data **one character a time**.

The **Device Descriptor** contains the information that will be used by the I/O controller

Some of these are:

- Device **Identifier**.
- Instructions that operate the device
- Character **translation** tables
- Current **status**
- Current user process.

#### Repeated Data I/O

A process that does I/O will be **repeatedly waiting** for the I/O to complete.

1. Process issues system calls to read the first chunk of data into **Working RAM**.
2. The process **uses** the data.

![](https://i.imgur.com/24n38Ye.png)

A simple **read-processing cycle** :

![](https://i.imgur.com/4frjq87.png)

The above read processing cycle has several problems:
- CPU is **ide most of the time**, waiting for data transfers to finish.
- **Total** time is the **sum** of P**s** and T**s**.
- The disk unit is **not running continuously** between accesses.

## Buffering:

Efficiency can be improved by using **buffering**.

**Buffer** is an **interim** memory area - holds data in **transit** between process's working RAM and the device.

The are two type of buffering:
    - **Input** Buffering.
    - **Output** Buffering.
    
Buffers **smooth out peaks** in I/O demand.

### Output Buffering

**Output data** is transferred to an **output buffer**.

The **OS empties** the buffer when it is full.

The process is **blocked only if** it tries to output when the **buffer is full** and the OS has **not finished emptying it**.

### Input Buffering

**Input data** data is store in an **input buffer**.

The **process extracts data** rom this buffer.

The **OS** refills the buffer **when it is empty**.

The process is **blocked only if** trying to get data **from an empty buffer** waiting for the OS to re-fill it.

### Single Buffering.

Blocks of data are read into the buffer and then moved to the process's working memory.

Once a block has moved to working memory, its processing can be **in parallel** with reading the next block.

![](https://i.imgur.com/hCS16Tj.png)

Timings:

![](https://i.imgur.com/ZEpo1gc.png)

Time spent processing **(P<sub>i</sub>)**
Time moving buffer to working area **(M<sub>i</sub>)**
Time to transfer block to buffer **(T<sub>i</sub>)**

This is slightly better than the previous cycle but the **process is still blocked** some of the time. Furthermore the **device
is still idle** some of the time as well.

### Double Buffering.

Using **two buffers**: the process can write (or read) to one, while the Os empties (or fills) the other from the device.

![](https://i.imgur.com/5ZmKIMv.png)

Buffer A can be **emptied** into working memory while Buffer B is being **filled** by I/O from the device.

Timing to process, move and transfer blocks:

![](https://i.imgur.com/3UyxmV9.png)

If the processing time is less than transfer time, data I/O to the device **can be continuous**.

## Summary

- I/O system separates users from the complexity of I/O devices.
- I/O system consists of: application program, I/O control system, device driver, device controller and I/O devices.
- Input and Output buffering can be used to improve efficiency.
- Can use double or multiple buffering technique to reduce CPU idleness.





