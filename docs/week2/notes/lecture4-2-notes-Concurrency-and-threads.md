# Concurrency and Threads

## Heavyweight processes

A **process** is an operating system abstraction to represent what is needed to run a program and consists of **Sequential Program Execution Stream**
plus **Protected Resources**.

The current state of a **process** is held in the **PCB (process control block)**.

To **multiplex** several process we need to allocate CPU time to processes using a suitable scheduling policy
<pre>
  <b>preemptive:</b> SRT and RR
  <b>non-preemptive:</b> FCFS & SJF
</pre>

Controlled access to <b> non-CPU resources:</b> eg. memory,I/O

## Threads

A <b> Thread is an independant sequence of execution within a process.</b>

![](https://i.imgur.com/rRNkBLs.png)

### Similiarities of a Thread and a Process
- Both have a <b> single sequential flow </b> of control with a defined start and end.
- At any time a thread has a <b> single point of execution.</b>
- A thread has its own <b> execution stack & program counter.</b>

A thread is also known as a <b> lightweight </b> process.

### Difference between Thread and Process.
- A Thread cannot exists on it's own. It can only exists <b> within a process </b>.
- A thread is usually <b> created </b> and/or <b> controlled </b> by a process.
- Threads can **share** a process's resources, including memory and open files.

## Sequantial Programming

Definition: Traditional activity of construction a program containing **one process** using a computer language.

The program is supposed to execute on a **single** processor architecture.

### Single Processor Architecture:

- A CPU is linked to Random Access Memory (RAM) and I/O devices by **buses**
- Both <b> program instructions </b> and <b> data </b> are stored in **RAM**.
- The CPU repeatedly executed the cycle of:
    - Fetching, decoding and executing the **next instruction**.
    - References by the current value of **program counter**.
- Can execute just **one instruction** at any time.

![](https://i.imgur.com/rSpq4qh.png)

### Executing a Sequential Program.

The **execution sequence** is the sequence of values of program counter and there is only one possible **sequence of execution**.

A sequential program gives the system **strict instructions** on the ** order of executing** the statements in the program;

Eg;

<pre>
The sample Program Below:
    <b>P;Q;R    </b>
tells the system to execute the statements in the order
    <b>P => Q => R   </b>
<b> P must precede Q and Q must precede R </b>   
</pre>
    
### System level Execution of P;Q;R

Each statement may be compiled into several <b> machine instructions </b>

P is treated as a **single machine instruction**

    p:(x=1) store 1 at the address of x.
    
Q (y=x+1) is broken into **3 machine instructions** as follows:

<pre>
    q1: <b>load</b> the value of x into a CPU <b> register </b>.
    q2: <b>increment</b> the value in this register by 1.
    q3: <<b>store</b> the value in this register at the address of y.
</pre>

R (x = y+2) is also broken into **3 machine instructions** as follows:

<pre>
    r1: <b>load</b> the value of y into a CPU <b> register. </b>
    r2: <b>increment</b> the value in that register by 2.
    r3: <b>store</b> the value in that register at the address of x.
</pre>


**Total Ordering:** Single threaded computation with no overlap in the execution of statements.

## Concurrency

A program is concurrent if its instructions can be perfomed simultaneously.

### Benefits of Concurrency.

- Improve efficiency in program execution using **multi-CPU** hardware.
- Improve **CPU utilisation**

### Multi-processor System

A computer with multiple-CPUs/cores is a **Parallel Computer System**

**Parallel computation** can be implemented on a paralled computer system.

If **each task** is completed by its own CPU, the computation is called **Maximum Parallel Computation**.

### Uni-Processor Multi-Tasking System

A **uni-CPU** system can support **multi-tasking/mult-thread**.

Each process/thread has its **own process counter**.

The program counter **forks** to produce many process/thread counters, which later **re-join**.

In each CPU cycle, a process is **non-deterministically** chosen and its next command is loaded and executed.

There may be **many different possible paths**.

This CPU sharing technique is called **interleaving**.

Formula for calculating the total number of possible interleaves of two process is given below:

![](https://i.imgur.com/3gP7aXQ.png)

m/n are number of instructions in two different process.


### Concurrent Programming Issues

The concurrent processes must interact with each other in order to **share resources** or **exchange data**.

**Synchronisation**: when, how and with what **language abstractions** can we synchronise computation to eliminate **unacceptable interleavings** and thus unacceptable outputs.

**Distribution:** how can we **distribute processes** among a **number of processors**, and how can a process on one processor
**interact** with another process on a different processor.






