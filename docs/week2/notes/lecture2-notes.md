# Processes and Multi-tasking

## Simple Fetch-Execute Cycle

 CPU operates in a **Fetch-Execute Cycle** - it just does one thing at a time
 
 1. Fetch Next instruction from Memory
 2. Execute the instruction
 
 If an I/O operation is required, the CPU will wait until I/O is complete.
 
 ![Fetch-Execute Cycle Image](https://i.imgur.com/yGT1rQu.png)
 
 #### Benefits of Having more than one process run at a time.
 
 - **Convenience** to the user
 - Need to monitor and control all kinds of **background tasks**.
 - Better CPU **utilisation**
 
 
 #### Implementing Multi-tasking
  Simultaneous execution of several process is achieved by the CPU perfoming **a little bit** of each process then suspending and moving on to the next.<br>
  The CPU uses the **interrupt** mechanism to determine which particular process can be suspended to allow the next one to take over.
  
  ##### The Interrupted CPU Cycle:
  ![](https://i.imgur.com/dBTTtG7.png)
  
  
  **Interrupts** are events that cause the CPU to stop the current process and switch to the **special process** for handling the interrupt.
  <br>
  The **Dispatcher** perfoms the operation required by the interrupt and then **decides which process is next to run**
  
  
  #### Types of Interrupts
  **I/O**: to signal completion, error or other state change
  <br>
  **Timer**: caused by a clock within the CPU; used to alert the Operating System at pre-set intervals for time critical activities.
  <br>
  **Hardware Error**: caused by hardware faults.
  <br>
  **Program**: caused by error conditions within user programs.
  
  #### Programs and Processes
  A **program** is code ready to executed on a system.
  <br>
  A **process** is a program **in execution**, i.e, a **particular instance** of a program **together with it's data**.
  <br>
  There may be several **copies** of the same program running simultaneously as **different processes**.
  <br>
  The OS stores information about each process in a **process control block** (PCB).
  <br>
  **Multi-tasking:** is the simultaneous running of two or more processes.
  <br>
  
  Shared code must not be changed during execution, and each process requires its own data area.
  <br>
  
  #### Process Creation
  
  Created by a user via  Command:
  
    When a User invokes a program, OS creates a PCB to represent the execution of that process. 
    OS also allocates resources for the process.
    Thus a process consists of machine code in memory and a PCB.
  
  Created by another process which is known as **spawning** a process.
  
    The process that creates a new process is called the parent while the created process is the child.
    The child can also spawn further processes hence forming a tree of processes.
  
  
  ### Kernel Mode vs User Mode
  
   OS needs to **protect** processes from each other for security.

   **Supervisor** mode can only invoked by the OS.

   **Kernel mode** reserved for the lowest-level most trusted functions of the operation System. Crashes in kernel mode are 
   catastrophic as they will halt the entire pc.

   Applications, Windows API and User-mode drivers run in **User Mode**

### The Dispatcher

 After an interrupt has been handled, control is then passed to the **Dispatcher** or **Low level scheduler**
 
 
### States of processes.

The currently running process is in the **RUNNING** state; only one process can be in this state.

#### Clock interrupt:
 Initiated by a clock within the cpu.
 
 Gives the dispatches an opportunity to context switch.
 
 A process stopped by a clock interrupt goes to the **READY** state.
 
#### I/O interrupt:
 Initiated by the CPU when an I/O instruction occurs in the running process or by an I/O device signalling completion.
 
 When a process starts an I/O process it goes to the **BLOCKED** state.
 
 When an I/O operation completes for a process, the process goes to the **READY** state.
 
 The **DISPATCHER** can only **SELECT** a process to run only if it is in the **READY** sate.
 
  ##### Diagram of process states
  ![](https://i.imgur.com/2KH8yyV.png)
  
  
  ## Summary:
  
  - A process can be **RUNNING,READY** or **BLOCKED**
  - **Interrupts** are used to stop the currently **RUNNING** process.
  - A **Clock Interrupt** causes the current process to become **READY**.
  - An **I/O Interrupt** causes the current process to become **BLOCKED** and upon completion becomes **READY**
  - The **Dispatcher** chooses the next process to run from a **QUEUE** of **Ready** processes.
  - **Scheduling:** is the policy for choosing among the **READY** processes.

