# Sharing Resources among concurrent processes.

## What you should know:

 - Abstract notion of processes and Threads
 - Potential to take advantage of concurrent programming by:
    - **Fully utilising CPU** while some processes are blocked.
    - Fully utilising **multi-CPU** systems.
    - Some problems become **easier to solve** with parallel reasoning.
    - Some problems are **inherently non-deterministic.**
    
## Overview of topics

1. **Inter-process communication**
2. **Critical regions** and **mutual exclusion.**
3. Possible solutions:
    1. **Disabling interrupts**.
    2. **Access in turns; Peterson's algorithm**.
    3. **Semaphores**.
    
## Shared resources:

### InterProcess Communication

 Processes need to communicate, but with concurrent operations, there are issues with:
 - **passing** messages **between** processes.
 - safe **sharing** of resources
 - correct **sequencing** in the presence of **dependencies**.
 
 Programs often used shared resources to:
  - Send/receive data to/from the **same device**.
  - Use the same **file** or a **block of RAM** to store data.
  
  Thus the Role of the Operating System is to **avoid incorrect operation** of programs
  due to shared resource **clashes**.
  
#### Printer spooler

`This shared resources mechanism ensures that files are printed one after the other,
rather than one page from one file and another from a different file.`

Printer spool: a place on a disk organised into a number of **consecutive slots.**

Each process that wants to print a file:
 - detects the **first** unoccupied slot.
 - stores the file **name** in the slot.
 
The printer manager periodically examines the slots and sends the files to be printed.
 
##### Scenario of a class with the printer spooler.

1. Processes P1 and P2 are simultaneously running and both want to print their files.
2. Process P1 detects the first unoccupied slot in the spooler (say: 6) and then a context switch occurs.
3. Process P2 resumes and detects that slot 6 is free, writes its file name there and then a context switch occurs.
4. Process P1 resumes again: it writes its file name to slot 6 erasing the record of process P2.
5. Hence Process P2's file will never be printed.

##### Race Conditions.

**Race** conditions are situations when two or more processes are reading/writing some shared data
and the **result depends on the other of execution**.

The above is an example of **race condition because of the following two circumstances:**
- Process P1 was **interrupt** during the use of the shared resources.
- Process P2 **used** the shared resource while P1 was interrupted.
 
##### Avoiding race conditions

A **critical region** is part of a program where **shared memory** is accessed.
Successful synchronisation of these resources requires:

- **Mutual Exclusion:** no two processes should be in their critical region at the same time.
- **Progress:** no process running outside the critical region may block other processes.
- **Bounded wait:** each processes should enter its critical region eventually.
 
 
 ## First approaches to synchronisation
 
 ### Disabling interrupts: the method
 
 At the hardware level, the processors have instructions that effectively
 **disable all interrupts**.
 
 A process accessing a shared resource can do the following:
 1. **Disable all interrupts**.
 2. **Access the shared resource.**
 3. **Re-enable the interrupts.**
 
 The absence of interrupts ensures that the process is not interrupted 
 while it accesses the shared resource.
 
 #### Overview of disabling interrupts:
 
 Disabling interrupts **achieves mutual exclusion.**
 
 However:
  - Disabling interrupts **prevents any other processes running 
  even if it does not require the shared resource.**
  - The strategy is **non-preemptive**.
  - So neither **progress** nor **bounded wait** is assured. 
 
 
 ##### Access in Turns: Simple algorithm
 
 | Algorithm for Process 0 (P0) |  Algorithm for Process 1 (P1) |
 |:----------------------------:|:-----------------------------:|
 | `while (turn == 1) {};`      | `while (turn == 0) {};`  
 | ` Access the resource;`      | ` Access the resource;`
 | ` turn = 1;`                 | ` turn = 0;`
 
 The above algorithm has a very huge drawback; namely that the **CPU wastes
 time** as process are *stuck in an empty loop* until it is their turn.
 
 Another drawback is if its not a processes turn, then it cannot start using the resource even if the other 
 process is not interacting with the resource.
 
 ## Peterson's algorithm overview
 
 - Keep track of which processes are **interested** in accessing the critical region.
 - each process uses a Boolean flag(processId) to **express interest** in entering
 the critical section.
 - All flags are **initialised to false**.
 - To avoid a **deadlock**, the process that starts the entry protocol
 **last** is allowed to proceed to the critical section **first**.
 
 Peterson's algorithm for two Processes P0 & P1
 
 flag[0] = false; flag[1] = false;
 
  | Algorithm for Process 0 (P0) |  Algorithm for Process 1 (P1) |
  |:----------------------------:|:-----------------------------:|
  | `flag[0] = true;`      | `flag[1] = true;`    
  | `turn = 1;`      |  `turn = 0;`
  | `while(flag[1] && turn ==1){..}`      | `while(flag[0] && turn ==0){..}`  
  | `// access resource`      | `// access resource`
  | `flag[0] = false;`                 | `flag[1] = false;`  
  
  The loop in Peterson;s algorithm performs **busy waiting**.
  
  **Busy waiting**: the program actively checks the process to free whilst performing other tasks.
  
  In peterson's algorithm waiting is performed **only if the other process 
  is interest in accessing the shared resource.**
  
  
  #### Informal Justification of Peterson's Algorithm
  
  1. If only P0 wants to access the resource. Then P0 can proceed to the critical section
   because flag[1] would be false, *vice verse for the P1 process.*
  2. If both processes want to access the resource then both flag[0] & flag[1] are true.
  3. Suppose that P1 is the last to modify turn i.e turn == 0;
  4. P1 would therefore perform a **busy wait** and P0 will enter the critical region.
  5. vice versa if P0 was the last to modify turn.
  
  #### Problems with Peterson's Algorithm 
  
  - The **enter/exit protocols** are directly programmed using **busy-waiting.**
  - Busy-waiting protocols are difficult to **design, understand, generalise and analayse**.
  - No **separation** between variables used for **synchronisation** and "normal" variables.
  - Thus they are often error-prone and inefficient.
  - Modern compilers and processors make it very difficult to make naive assumptions
  about the **order statements are executed.**
  
  ## Semaphore Motivation
  
  It is much simpler to write **entry/exit protocols** using single statements:
  
  <pre>
  <b>GETSEMAPHORE;</b>
  critical section;
  <b>RELEASESEMAPHORE;</b>
  non-critical section;
  </pre>
 
 A **semaphore** is an integer variable that takes only non-negative values representing the
 **quantity** of a resource available.
 
 ##  Implementing Semaphores
 
  - Acquire() and Release() need to be implemented as **synchronized.**
  - Acquire() should wait until the semaphore becomes > 0;
  - Release() should release other processes waiting on the semaphore.
  - Acquire() and Release() use block-waiting rather than busy waiting 
 
 # Summary
 
 - **Block-Waiting** : A process that is blocked is suspended by the operating system and will be automatically notified when the data that it is waiting on becomes available.
 - **Busy-waiting**: A process that is busy waiting is essentially continuously running, asking "Are we there yet"???????.
 - Processes need to communicate/share resources.
 - Concurrent process can result in **race conditions** in access to **critical region**.
 - Attempt to achieve the following in concurrent programs:
    - mutual exclusion
    - progress
    - bounded wait.
    
  At this point should have knowledge on:
  
  - [ ] Disable Interrupts.
  - [ ] Access in turns.
  - [ ] Peterson's Algorithm.
  - [ ] Semaphores.
    

  