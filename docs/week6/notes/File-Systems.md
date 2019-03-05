# File Systems

## Overview

- Objectives of file management system
- Concepts of file,directory
- General disk layout
- File allocation methods
- Directories

### Objectives of File System

- Allow **creation** and **deletion** of files.
- Allow access to file by **symbolic** names.
- Allow the **sharing** of files where required and **protect** files against
unauthorised access.
- Perform **automatic management** of **secondary storage** allocating files to **physical** blocks.
- Protect files against **system failure.**

### Concept of a File

- A **uniform logical view of information storage.**
- A **named** collection of related information.
- Recorded on **secondary storage - persistent**.
- Allow storage of **large volumes** of data.
- Allow multiple processes to access the data **concurrently.**

#### File Naming

A FileName has two parts separated by a full stop
- The part before the full stop is the **basename**
- THe part after the full stop is the **extension.**


File extensions are used to determine the contents of a file but these are easy to trick.
The **file** command on *NIX/MacOSX* looks into a file and tries to identify what type it is.


Each Field has a set of attributes associated with it :

![](https://i.imgur.com/HIz23wA.png)

#### File Structure and Access

A file can be stored as :
- A **sequence of bytes** and it is up to the program that accesses the file to interpret the byte sequence.
- A sequence of **fixed-length** structured records.
- A tree of **variable length** records, where each has a **key** field to be used for record retrieval.

There are two types of File Access:
- Sequential Access: - Good for magnetic tapes.
- Random Access: - Good for disks.

## Directories

- Most file systems allow files to be grouped together into **directories**,giving a more logical organisation.
- Allow operations to be performed on a group of files which have something in common.
- Allow different files to have the same filename as long as they are indifferent directories
- Each directory is managed via a special **directory file**, which contains a **file descriptor** for each constituent file.

### Directory Structure

- One-level system: one **root directory** contains **all** files.
- Two-level system: each user a private directory.
- Hierarchical System: a tree of directories.

![](https://i.imgur.com/KYQYlSx.png)

#### File and Directory Operations

Interactive facilities for on-line users:

- **Create** a file or directory
- **Delete** a file or an empty directory
- **Copy** a file or directory
- **Rename** a file or directory
- **Display** a file or **list** the content of a directory

Services for programmers via high level languages:

- **Open**: make a file ready for processing.
- **Close**:make a file unavailable for processing.
- **Read**: input data from a file.
- **Write**: output data to a file.
- **Seek**: select a position in a file for data transfer.

## File System Layout (MBR)

- A disk is divided into **partitions/volumes**, each holds an independent file system.
- Sector 0 is the **Master Boot Record(MBR)**.
- Each partition has a **boot** and a **super** block, containing info about that file system.

![](https://i.imgur.com/0Hk80wo.png)

### Contiguous Allocation


Disks (partitions) are split into **blocks** of a fixed size,e.g, 1KB, 4K. Files are stored in blocks in a manner dependent on the file-system type.

**File Descriptor** is a data structure for finding the **first** block of a file

**Contiguous allocation** assigns contiguous blocks to a file

![](https://i.imgur.com/4ttfikv.png)

Advantages of Contiguous Allocation:

- **Simple to implement:** just need to store the first block address and the length.
- The **performance** of such an implementation is **good** as the read/write heads have to move very little.
- **Resilient to disk faults**: damage to a single block results in only localised loss of data.
- Allows **easy random access.**

Disadvantages of Contiguous Allocation:

- Need to "guess" the **size** of the file when it is **first created.**
- **Fragmentation:** as files are deleted, holes appear.


### Linked List allocation

- Store a file as a **linked list** of blocks.
- Need only to store the **first block** address in the file descriptor.
- Each block contains **some data** and a **pointer to the next block**, and the final block contains a null pointer.

![](https://i.imgur.com/RdS0iYq.png)

Advantages:
- **Every** block can be used.
- **No space is lost** due to disk **fragmentation**, except for internal fragmentation in the last block.
- The file size **does not have to be known beforehand**.

Disadvantages:
- Random access is **very slow**.
- More **overhead:** as space is used in each block by the pointer.
- Reliability could be a problem.

### File Allocation Table

Store the pointers in a **file allocation table** in memory.

The pointer in a file descriptor points to the location in the FAT representing its first block.

![](https://i.imgur.com/RqxosdK.png)

Advantages:
- Does **not waste space** in each block.
- **Random access is much easier** as FAT is in memory.

Disadvantages:
- FAT **may be too large** to hold in main memory, so has to be held on secondary storage.
- May require **several disk accesses** to read all relevant FAT entries.
- Damage to FAT can cause **serious data loss.**

### index-nodes (inodes)

Each file has an **inode** (index?=node) listing all the attributes for the file.

The inode also contains a number of **direct pointers** to disk blocks and there are typically 12 direct pointers.

There are also three **indirect pointers**, and these pointers point to further data structures which eventually lead to disk block address.
The first of the pointers is a single level of indirection, the next pointer is a double indirect pointer and the third is a triple indirect pointer.

![](https://i.imgur.com/lYFtsqm.png)


#### Implementing Directories

The **path name** is used to locate a directory entry.

A **directory entry** contains all the information needed:
- For contiguous and linked list allocations, it contains the **first disk block address**.
- For inode implementation it contains the **inode number**.

The directory entry also provides a **mapping** from a **filename** to the **disk blocks** that contain the data.


## Management and Resilience

### Journalling file system

- A "single" file operation might involve **multiple actual writes** to the disk.
- Potential to leave files and directories in **invalid state after a failure** and thus time consuming repairs are needed.
- A **journaling* file systems uses a special disk area to make record of all changes just before they are made.
- The event of failure causes a **replay of the journal** to bring it back into a consistent state.

### Battery Back Up

- More expensive disk systems come with **BBU*.
- small(ish) amount of dedicated cache RAM powered by battery.
- Disk writes go to cache first.
- Battery Unit has enough power to either finish the write or keep the data **live** until power is restored.

### Logical Volumes

In windows multiple drives appear with distinct **drive letters**. Attaching a new drive, results in generation of a new letter.

Unix style systems present a single **uniform file system** with a single **root*.

We can add another layer of abstraction called the **Logical Volume Manager**. Can create arbitrary mapping between
physical drives and the logical drives seen by the OS. Can resize, extend, replace physical drives without altering the 
logical view.

Such Examples are **lvm,zfs,btrfs**.

### RAID

**Redundant Array of Inexpensive Discs** (RAID)

- Spread file  system across multiple physical discs.
- Several **levels** of RAID with different perfomance characteristis.

RAID0: Striping of data over multiple drives, to improve performance with no space overhead. (size = s X n).

![](https://i.imgur.com/Zn6QPxR.png)

RAID1: Mirroring of data over multiple drives to improve resilience and maybe read speed; (total size is s).

![](https://i.imgur.com/joi5sS0.png)

RAID5: Block-level striping with distributed parity that can survive failure of any single drive. (size = (n-1) * s)

![](https://i.imgur.com/6KqWbhf.png)

## Summary

- File is logical representation of data on 2nd-ary storage.
- Has various attributes
- Directory is a special file that contains other files
- Implementation needs to be efficient with space usage and speed of retrieval.
- Need to manage complex failure prone systems.





