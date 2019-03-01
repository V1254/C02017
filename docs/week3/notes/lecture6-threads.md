# Threads

## Overview:
 - Java's Support for Concurrency.
 - Java Thread Class and Runnable Interface
 - Java Executors and ThreadPools
 
## Concurrency
Java Virtual Machine (JVM) allows an application to have **multiple threads** running concurrently.

There are two way to create a new thread:
- **Extending** the Thread class.
- **Implementing** the Runnable interface. 


### Extending Thread 

Declare a subclass of Thread and override its run() method if needed.

```java
    public class ExThread extends Thread{
    int someField;
    
    ExThread(int f ){
        this.someField = f;
    }
    
    public void run(){
        System.out.println("what the thread should do");
    }
    }
```
Then simply instantiate a new instance and start the thread.

```java
    ExThread t =  new ExThread(10);
    t,start();
```

#### Thread Class Methods

- **t.start()**: causes thread t to begin execution concurrently(JVM calls the run() method).
- **t.run()**: Calls the run method of thread t directly without concurrency. 
- **Thread.sleep(ms)**: Pause the execution of the current thread for ms milliseconds.
- **t.join()**: causes the calling thread to wait(block) until thread t has completed.


## Implementing the Runnable Interface

This is the preferred way to implement concurrency as java does **not permit multiple inheritance**.
The **Thread ** class also implements the Runnable Interface

The runnable interface has one method **run()**.

```java
    public interface Runnable{
     void run();
    }
```

Example Use:

```java
    public class MyRunnable implements Runnable{
        int field;
        
        MyRunnable(int f){this.field = f};
        
        public void run(){
            System.out.println("Im a Thread ..");
        }
    }
```        
 Simple then Instantiate and use as before. 
```java
    MyRunnable r = new MyRunnable(10);
    Thread t = new Thread(r);
    t.start();
```

The outcome of using these two methods are usually **non-deterministic**, and can differ with each run of the program.

## ThreadPoolExecutor

ThreadPoolExecutor separates the thread creation and its execution. You only have to implement the 
runnable interface and send them to the Executor.

The ThreadPoolExecutor is responsible for thread execution, instantiation and running with necessary threads.

Creating a new ThreadPool:

```java
        // creates a new Threadpool of fixed size.
        // Threads will wait in a queue until a thread is available.
       ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
```

Adding a new Object to the Executor is done as follows:
```java
    executor.execute(runnableObject);
```
Getting the number of active threads:
```java
    executor.getActiveCount();
```

# Summary

Threads in java can be created via:
- **Extending Thread** class
- **Implementing Runnable** interface

The result of running programs with multiple threads may be **non-deterministic**.

The Java **ThreadPoolExecutor** class can be used to collectively manage the behaviour of threads.



