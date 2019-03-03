# Deadlocks

## Overview

- Definition & Example of **DeadLocks**.
- **Handling** Deadlocks
- Processes and **resources**>
- Single resource deadlock **detection**.
- **Banker's Algorithm**.
- **Prevention** of deadlocks.
- **Livelocks** and **starvation**.


### Definition of deadlock

**Deadlock**: a situation when one or more processes get stuck.
The simplest example is:
```java
    while(true){;}
```

In multi-process systems concern is with deadlocks involving **two
or more processes** where each process **is waiting for an event
caused by another process**.

A thread running **alone** does **not** deadlock, but when two programs **run together** they **can** deadlock.

The are four ways to handle deadlocks.

- Ignore.
- Detect and Rectify.
- Avoid.
- Prevent.

## Handling Deadlocks

A potential problem has to be **explicitly dealt with** only if the **cost of letting it happen** significantly
outweighs the **cost of rectifying it**.

Deadlocks mostly **do little harm to normal users** of single user and small multi-user systems, so consumer systems
**ignore most potential deadlocks**.

For **real-time** or **safety critical** such as medical equipments and avionics **deadlocks would be devastating**.

#### DeadLock detection

The operating system runs a special **deadlock detection** process.

When a deadlock has been **detected**, **action** is taken to **rectify** the problem.

#### Rectify detected deadlocks.

Preemption: 
- pick a process that is holding a resourcing resulting in deadlock and give that resource to a different process
- Drawback: some resources are non-preemptable.

Rollback:
- **Check-point** all processes periodically.
- When deadlock is detected **roll back** to a previous check point and process with **different allocation** of resources.

Kill processes:
- Linux OOM killer.
- might be done manually as the system cannot decide which processes to terminate.

#### Deadlock avoidance

Use a scheduling policy that **avoids** deadlocks.

Scheduler considers the resources that a process requires, and does not allow it to start unless **all resources will be available**.

Requires **knowledge about future** operations of processes which is hard to achieve.

#### Deadlock preventions.

Design systems so that deadlocks **cannot** in principle **occur**.

This will involve imposing **strict requirement** on programs running on the system.
    eg: Processes cannot start unless all possible resources are known to be available.
    
Under **deadlock avoidance** deadlocks are **possible in principle** but the system **tries to foresee** and avoid them.


## Process and Resources.

Deadlocks involve two main notions: **Process** and **Resource.**

Resources are very broadly understood to be things like :
- block of memory
- printer
- scanner
- file on disk
- etc..

Processes can **hold** and **release** resources as execution proceeds.

Deadlocks occur if there is a **set of process** where **each wants to use** a resource currently being **held** by another
process.

### Detecting deadlock with single resource of each type.

**Single** resource of each type: eg: **One** printer, **One** disc, **One** scanner etc..

Consider state of the process at **any given moment** and determine if there is a deadlock.

This will require:
 - The list of processes
 - The list of resources
 - the relationship between the given processes and given resources.
 
 Represent the state of processes and resources with a **graph**:
 - **Nodes** correspond to processes and resources
 - **Edges:**
    - **From** resource R **to** process P, if P **Holds** R
    - **From** process P **to** resource R, if P **requests** R

The system **is in deadlock** if and only if the graph has a **directed cycle**. 

Single resource deadlock example: 

![](https://i.imgur.com/89iVr4b.png)
 
For **Multiple** resource situations, we can use matrices:
 - Matrix of resources that **exists** in the system
 - Matrix of resources **in use**
 - Matrix of **available** resources
 - Matrix of processes **holding** resources
 - Matrix of processes **requesting** resources.
 
 Example of resource Matrices:
 
 ![](https://i.imgur.com/YZdELOg.png)
 
 if the process request matrix has **no row** that is **smaller than or equal** to the **available resource** matrix then
 the processes are in **deadlock**: none of them will ever get a full allocation.
 
 Otherwise there is no deadlock **now** - but could be in the future.
 
 ## Banker's Algorithm
 
 1. Initially all the processes are **unmarked.**
 2. If **all** the processes are **marked**, return "NO DEADLOCK"
 3. if there is **no unmarked** process whose row of **requested requested resources is <= the array of **available resources**
 then return "DEADLOCK"
 4. let P be an unmarked process whose **requested** resources are <= **available resources**.
 5. **Add** P's row of **held** resources to the row of **available resources**.
 6.  **Mark** P
 7. Goto Step2.
 
 **For each** resource request, the system **analyses** the safeness of the state occurring if the requests were granted.   
 **If** the resulting state is **not safe** then the request is either **denied** or **delayed**.
 
 #### Four Conditions for deadlock to occur
 - Mutual exclusion:
    - Each resource can be held by **at most one** process.
 - Hold and wait:
    - processes currently holding resources may request **more resources**.
 - No preemption:
    - Resources granted **cannot be forcibly taken away** before the process has released them.
 - Circular wait:
    - There is a **circular** chain of processes waiting for a resource held by the **next**
    member of the chain.
    
 Consider prevention of **Hold and Wait** and **Circular Chain** in order to prevent deadlocks.
 
 ##### Elimination of the Hold & Wait Condition
 
 Each program asks for **all** the resources it will require at the **beginning** of its run.
 This is usually only possible in **batch oriented systems** as user requests are unpredictable in **interactive systems**.
 
  ##### Elimination of the Circular Chain Condition:
  
  **All** the resources are **enumerated** monotonically.
  
  A process can **only** ask for a resource if the resource number is **greater** than the numbers of **all**  the resources already
  held by other processes.
  
  
  ## Livelocks and Starvation
  
  **LiveLock** is an effective deadlock where a group of process perform infinitely the same sequence of actions
  in response to one another.
  
  **Starvation** is a situation where a process never gets an opportunity to run because the resources it needs
  are repeatedly allocated to other process. This is often due to poor scheduling policy.
  
  ## Summary
  
  - **DeadLock** is a state where no processes can proceed because all the requires resources are already in use by other processes.
  - Deadlock is very undesirable.
  - Can try to Either **Detect** and **Rectify** or **Avoid** or **Prevent.**
  - **Banker's** algorithm can be used for **avoidance**.
  - **Prevention** requires eliminating one or more of the conditions for deadlock.

 
 




