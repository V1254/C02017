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
  
  
