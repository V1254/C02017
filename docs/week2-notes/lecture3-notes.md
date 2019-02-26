# Linux Operating System and the Linux User Interface

## User interface is built on top of an OS

The user interface is the user's gateway into the computer.

Enables the user to interact with the computer via the capabilities provided the OS.

## System calls and API

**System calls** are a layer of services/procedures provided by the OS, just on top of the hardware to allow user programs to access
some (sensitive or priviliged) actions.

Different Oss have similiar (but not identical) set of system calls.

An OS only has a limited set of system calls, but also provides a stable Application Programming Interface (API) of functions and 
libraries between the programmer and the system call interface.

Invoking a system call is similiar to calling any other function, but the implementation of a system call is part of the operating system,
not part of the application layer code.

## Example Systems calls of UNIX OS

System V UNIX provides about 64 systems calls, concering file, I/O and processes.

![](https://i.imgur.com/L9PciBf.png)

## Kernel vs User Mode:

![](https://i.imgur.com/vdboNYH.png)

## Shells

A **shell** is a program that acts as the primary interface between a user and the operatin system's basic functionality:
- Control/monitor other processes.
- System admin tasks(File management, device configuration etc..)

Basic divide between the **command line shells** and **GUI shells.**

## Command line

Allows users to **interact** directly with an OS via a **terminal emulator.**

User enters **commands**, and responses are shown immediately (in text form).

A series of shell commands can be assembled in a text file to produce a **shell program** or **script**.

## Graphical user Interface (GUI)

**GUI**s are a common, significant feature in modern computing

Features of GUI systems:
- On-screen overlapping **windows**
- **Pointing device**, usually a mouse.
- Various standard graphical features: buttons, icons scrolls bars etc..
- Standardised higher level componenets: menus, selection lists etc.

A GUI system is a **desktop metaphor:** the screen display represents a desk with documents the user can interact with.

## Summary

User interface enables the user computer interaction.

Systems calls are the ultimate user interface to the computer.

Different users may prefer different user interfaces:
- System calls may be directly used by programmers.
- Command line languages are efficient at getting low level access to the OS features.
- Graphical user interfaces are now very common.


