# Memory Management (2)

## Overview

- Direct Memory Management.
- Swapping in Direct Memory model.
- **Virtual Memory** vs direct memory swapping
- **Page table implementation.**
- **Page replacement algorithms.**

#### Memory Management Aims

- To make the addressing of memory **transparent**.
- To allow **multiple processes** to **share** physical system memory, but keep each process's **logical** 
address space **separate,** with acceptable level of **performance.**

#### Limitations of direct addressing & Swapping

Modern interactive applications can use **100s of Mb or more**, thus swapping **whole process's memory**
in/out of disk would take unacceptable amount of time.

Individual process might require **more memory than there is physically available**.

Modern **Virtual Memory (VM)** and hardware support for **paging** address the above issue.

## Virtual Memory

The memory **"seen"** by user space is not **real physical memory**.

The OS provides a **virtual address space** for **each process**.

A **virtual address* is translated to the **real address* by the **Memory Management Unit**(MMU).

The **translation*** of addresses has to be very fast, therefore the **MMU** is implemented in hardware.

### Paging

Virtual memory is **partitioned** into chunks of equal size called **pages**.

**Page size** is much **smaller** than the **total address space** needed for a process.

Not all pages of a processes are needed at the same time.

Virtual memory pages that are in use are stored in RAM(called page frames); the rest of the pages
are stored in disc.

When a process tries to access a page that is not in physical memory a page fault occurs.

Page fault: an old page frame in RAM needs to be swapped with the new paged being accessed.

###### Relationship between virtual frames and physical memory

![](https://i.imgur.com/9I9H3V6.png)

#### Paging-related implementation issues.

- **Data structure(s)** to support page table
- Choosing the **page size**.
- Choosing the **page replacement algorithm**.

##### Page table structure:

- Structure is **implemented mostly in hardware**.

Attributes of a page table entry:
- Present/absent: true if and only if this page is **currently in physical memory**.
- Page Frame number: The **physical page frame** this page is stored in (if any).
- Modified: Has the data been **modified since it was read from disk**.
- Referenced: has the data been **referenced** (recently).
- Protection: is the data **read/write/executable**.


###### Optimisations

Modern systems have **huge amounts of RAM**: the page table is itself a large data structure that can be **slow to manipulate**.

Modern hardware uses ** translation lookaside buffers** to cache the most **commonly used page table entries**.

With large amounts of RAM, may need to utilise **multi-level tables**.

**RISC** processors typically have **simpler MMUs**.

If the **Page Size** is :
- **Too Large** - page swapping will take a very long time
- **Too small** - page swapping will be repeated often.

Common pages sizes are 4-8k.

## Page Replacement Algorithm

- Page **replacement** happens when a **page fault** occurs.
- MMU is asked for a page where the **present bit** is false.
- Need to choose **which** page to **swap out** to disk.
- Ideally want to choose the page that will **not be needed for the longest period of time.**
- If there are several, look for **clean (un-modified) pages** that will not need to be written to disk.
- Poor page replacement can result in **thrashing** - the machine spends more time dealing with pages then real work.

## Clairvoyant page replacement algorithm

Input needed: for each page, the **next** instruction that will reference this frame.

Find the page that will next be referenced the **furthest in the future** and swap it to disk.

**Impossible** to implement in a real operating system due to the inherent impossibility of predicting future behaviour.

##### The optimal algorithm is nevertheless useful.

Support you have a real page replacement algorithm and want to **test its performance**.

This is done by **comparing** it against the optimal algorithm.

Run the same process **twice** (or more).

On the first run, **collect the timings** of all the page references so that they can be sued when analysing the perfomance
of the replacement algorithm.

###### Page replacement algorithms
- Not recently used.
- FIFO page replacement.
- CLock page replacement.
- Least recently used (LRU).
- Working set replacement.
- WSClock page replacement.


### Not Recently Used algorithm 

Each page frame has a **referenced** bit:
- *false* if the frame **has not yet** been referenced
- *true* if the frame **has been referenced**.

The page to be swapped is randomly selected among those that have **not been reference (0 bit)**.

Periodically **all bits are reset** to 0.

Obvious disadvantages: **crude selection** and the **need** to **reset the bits**.

### FIFO page replacement.

Maintain a **FIFO queue** as pages are added to physical memory.

When a page fault occurs, simple **swap out** the page at the **head of queue.**

Simple and efficient to implement, but **likely** to swap out pages that are **actively being used.**

Can check if the head page **has been referenced;** if so, give it a **second change** (**""FIFO with 2nd chance"**).

### Clock page replacement.

Maintain a **circular list** of all page frames (the **clock**).

Maintain a **pointer** to one of the pages in the clock (the **hand** of the clock).

On a page fault, start at the hand and **look for a page to evict**:
1. if **referenced is false: evict** this page
2. if **referenced is true: reset referenced** and **advance the clock hand** and try again.
3. **Advance and clock hand**.

Functionally identical to FIFO with 2nd chance; but more efficient to implement.

### Least Recently Used (LRU) algorithm

The OS records the time when each page is referenced. The "oldest" frame is evicted on a page fault.

The system maintains a counter that is **incremented with each CPU instruction**.

Each page table entry **records the counter value** when that page is referenced.

The frame associated with the **smallest** value of the register is the one that is **swapped**.

Could be implemented in hardware.

### Working set replacement

Observe that most processes exhibit **locality of reference** - concentrate on just a few pages.

The set currently required is the **working set&& of pages - try to keep the working set in memory.

For active pages record **time of last use**.

When a **page fault occurs**, consider all the page frames and choose the one based on **referenced, dirty** and
**time of last use**.

**Working set** is not implemented directly, but is a concept that informs other more practical techniques.

#### WSClock replacement.

Maintain **circular list** of page frames as they are loaded, but include the **time of last use**.

Define **Tau(τ)** as a limit beyond which pages are **too old** to count as **in the working set**.

Instead of searching whole page table, just **search the clock** for pages that are older than τ.

Can be **efficiently implemented** in practice.

#### Virtual memory in practice.

Real modern systems combine **VM management** and **page replacement** in a general purpose **memory allocator** that is used by the file system.

Real systems use **LRU/clock hybrid** approaches, which aim to **dynamically capture** and **working-set** and **keep it in memory**.

**Pre-loading** and **anticipatory paging** allows pages to be loaded **before a page fault occurs**.

## Summary

- Virtual Memory.
- Paging.
- Page table implementation.
- Page replacement algorithms.








