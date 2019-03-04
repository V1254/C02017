# Memory Management

## Overview

1. Memory Management
2. Swapping

## Memory Management

### Aims of Memory management

- To provide **memory for processes** running concurrently.
- To provide satisfactory **performance**.
- To **protect** processes' memory from each other but to allow **sharing** of memory when desired.
- To make addressing of memory **transparent**.

### Memory Hierarchy

![](https://i.imgur.com/14viNSd.png)

- **Registers** (in CPU) (10s of bytes)(about 1 CPU cycle to access)
- **Cache** levels 1,2,3 (near processor)(a few Mb)(many 10s or 100s of GB/s)
- 'Main' (**volatile**) **RAM** and (non-volatile) **ROM** (outside processor) (multiple Gb) (about 10Gb/s)
- **Non-volatile** storage - disk, SSD (100s or 1000s of Gb) (10s or 100s of Mb/s)
- **Off-line** storage - tape, DVD, Bluray , cloud storage (1Mb/s to 100s Mb/s in ideal conditions).


### Memory Contents:

- **System ROMs** (bios, device drivers, OS)
- System **code** in RAM (OS)
- System **state** in RAM (OS)
- Process memory:
    - Process **code**
    - Process **data**
    - Process **stack**

These are organized across physical memory in **distinct sections**.

### Single Process Systems

- Only **one process** running and in memory
- The running process can address the **entire memory space**.
- Code is written with **absolute** addresses.
- When a process completes, it is **replaced in memory** with a **new process**.
    - This is invoked via a direct user command or batch control language etc.
    
![](https://i.imgur.com/WJfGZGY.png)

### Multi-Process Systems

- Most modern systems have enough memory for **more than one process's code and data** to be stored.
- Each process is loaded into **consecutive areas**>

Code with **absolute addresses will fail**
- Use **static relocation** to **rewrite code** as it is loaded or
- Use abstract **address space** for each process with a fixed **base address** set when process is loaded.

![](https://i.imgur.com/IuaF5fy.png)

#### Memory addressability

Memory space is limited by the **width** of the **address bus**.
- A 32-bit bus can reference 2<sup>32</sup> = 4.3Gbytes
- A 64-bit bus can reference 2<sup>64</sup> = 16Exabytes

Machine instructions **cannot directly access the whole memory space.**

Registers are used to hold a **base address*.

The address part of machine instructions is used as an **offset** from the base address

Eg:
   <pre>
           code base address: 1,000;
                 instruction: JMP 123;
 effective address jumped to: 1,000 + 123 =1,123
 </pre>
 
 #### Memory allocation
 
 Each process is allocated the memory space it needs
 
 Processes are loading into **contiguous memory** until is it full.
 
 When a process terminates, it space is **freed**
 
 This introduces 'holes' - **external fragmentation**, Thus with luck, adjacent fragment can be **coalesced**.
 
 ![](https://i.imgur.com/Bck5Sl8.png)
 
 ## Swapping
 
 With a scheduler, a **multi-processing** can be achieved on a system with direct memory access.
 This is fine until we need to run **more processes than will fit in memory** at once.
 
 Swapping is strategy for handling **over-allocation** of memory.
  - Memory **full** and want to run another process?
  - Pick a process currently in-memory and **copy it out to disk**.
  - Use the newly free space to **load another process**.
  
  #### Strategies:
  
  First fit: Look for the **first un-allocated space** that is big enough to fit the process.
  
  Best fit: Look through **all** un-allocated space and choose the one that is the **best fit**.
  
  Worst fit: Look through **all** un-allocated space and choose the one that is the **largest.**
  
  
 ## Summary:
 
 - Machine memory needs to be managed.
 - Simplest approach is **direct memory** addressing by using a **base address** and **offset** can load multiple
 processes at the same time.
 - **Swapping** is a strategy used for handling **over-allocation** of memory.