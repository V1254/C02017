## Scheduling Policy Notes.

Scheduling poliy determines which **Ready** process to run next, and **how long** a process is given to use the CPU

There are two types of Scheduling Policies: **Non-preemptive scheduling** & **Preemptive scheduling**

#### Non-preemptive scheduling:

A process releases the CPU **only** if it **Blocks on I/O** or it completes.

The process **cannot** be stopped during regular operation.

#### Preemptive scheduling:

A process can be stopped **at any point by a clock interrupt**

## Efficiency of Scheduling Policies.

There are 3 measure of a good scheduling policy; **Turnaround time** , **CPU USage** & **Response time**.

### Definitions

- **Turnaround time:** the difference between the time a process is submitted for execution and the time the execution is complete.

- **CPU usage:** given some amount of time, the **percentage** of time during which the CPU is **busy**.

- **Response time:** the **average turnaround time** of user requests.

A good scheduling policy aims to **minimise turnaround ** and **response time,** and **maximise CPU usage.**

## Operating Systems:

### **Batch systems:**

Run a series of programs **without human intervention**

**Turnaround time** and **CPU Usage** are more important measures than **Response time**.

Examples of Batch Systems: Sciencific computers and salary calculations in big companies.


### **Interactive Systems:**

Designed to continuously communicate with the user.

**Response time** is a more important measure than general average **Turnaround time** or **CPU Usage**.


## First Come First Served (FCFS) policy:

- This policy is **non-preemptive.**
- The **first Ready** process is the queue is started.
- The process releases the CPU when it **finishes** or it **BLOCKS** on I/O.
- When a **Blocked** process returns to the **Ready** state, it goes to the **back of the ready queue.**

### Advantages:
 - Easy to implement.
 - No indefinite waiting (each process will get a turn to get executed).
 
### Disadavatages:
 - very un-responsive as **turnaround time** of short processes can be much longer than their **actual execution time**.
 
## Shorted Job First (SJF) policy:

 - This policy is **non-preemptive.**
 - Required knowledge about the runtime of processes **before they actually execute.**
 - From the queue of **Ready** processes, the one with the smallest runtime is executed.
 - rest is same as **FCFS**
 
 ### Advantage:
  Favourable to process with short runtimes.
  
 ### Disadvantages:
 - Requires advance knowledge about runtimes.
 - indefinite waiting is possible, if multiple new short processes continously arrive.

## Shorted Remainig Time First (SRTF) policy.
 - This is a **preemptive** policy.
 - For every process, requires knowledge of the **remaining runtime**.
 - Arrival of a process in the **ready queue leads to an interrupt**
 - **process with the shortest remaining time** is executed.
 - A process is said to be **preempted** if a process with a **shorter remamining time** than the current one is executed.
 
 ## Advantages:
  - even more sensitive to the short processes than **SJF**
  
 ## Disadvantages:
  - requires advanced knowledge of runtimes.
  - indefinite waiting is still possible.
  
## Round-Robin scheduling policy.
  - This is a **preemptive** policy used in **interactive** systems.
  - Each process in turn get the CPU for a **Fixed time quantum (typically 10-100ms)**.
  - When the quantum finishes: a **timeout interrupt** occurs and the process goes to the **back of the Ready queue.**
  - New processes join the **back of the ready queue.**
  
  **Context switch:** is the procedure of replacing the currently **running** process by another one. This takes a **non-zero amount of time.**
  
  If the quantum is too short, the CPU spends a large proportion of its time **context switching**.
  
  If the quantum is too long then the **response time** may become unacceptable (Behaves like **FCFS**).
  
  ## Multi-level schedulers
  
  Most real systems use multiple levels of job queue.
  
  For example 3 queues; High, Medium and Low priority Ready queues.
  
  Each queue can operate its own policy.
  
  Process that are **preempted** move between the different levels depending on various criteria, eg: age of the process.
  
  Higher level queues are given more priority by the scheduler.
  


