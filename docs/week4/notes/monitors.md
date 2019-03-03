# Monitors

## Semaphore Recap:

A semaphore has a non-negative integer value and supports the following two operations:

- An acquire operation **acquire()**: an atomic operation that waits
  for semaphore to become positive, then decrements it.
  
- A release operation **release()**: an atomic operation that increments
  the semaphore, waking up a waiting process if any.
  
Using Semaphores we can achieve **mutual exclusion** and **scheduling constraint**.


### Positive Aspects of Semaphores

- Powerful enough to program all kinds of synchronisation and resource management requirements.
- Relatively easy to implement efficiently.
- More general semaphores can be simulated by binary ones.

### Negative Aspects of Semaphores

- Low level facility and still error-prone, especially when more than one semaphore
is in use at a time.
    - Missing **release()** may lead to deadlocks
    - Missing **acquire()** can lead to violation of mutual exclusion.


## Monitor

There are two parts to a **monitor:**
- **lock** for enforcing mutual exclusion.
- **guards** for controlling access to resources.

In OOP a **Monitor** is a special class with:
- **Attributes** (data) to be shared by processes.
- **Synchronised methods** to access the data.
- **Variables** for condition controls.

Monitor Data should **ONLY** be accessed via its method. Thus entry to a 
Monitory by one process **excludes** entry by any other processes.


### Synchronization

Java has a fundamental mechanism (based on **monitors**): to make methods **synchronized**.

A class providing a shared resource can have one or more methods accessing
the data declared as *synchronized*.

This ensures that at any given moment of time only **one** thread can be executing a *synchronized* method.

Java associates a **lock** with every object which:
- transparently inserts code to **acquire** the lock before executing a synchronised method,
-  code to **release** the lock when the method returns.

Concurrent threads are **blocked** until the lock is released.

Only **one thread at a time** may hold the lock and thus may be **executing**
 the *synchronised* method.
 
Executing synchronised methods of **different objects** can be **concurrent**.

#### Condition Synchronisation

**Condition Synchronisation** is the property that one process wishing to access shared
data must be **delayed** until some condition holds **regardless** of whether any other
thread is trying to use the resource.

Java provides a **thread wait queue** for each monitor to provide conditional synchronisation.

```java
    // wake up a single thread that is waiting on this object
    public final void notify();

    // wakes up all threads that waiting on this object
    public final void notifyAll();
    
    // wait to be notified by another thread. Implicitly releases the lock
    public final void wait() throws InterruptedException
```

An Example of Condition Synchronisation in Java:
```java
    public synchronized void act() throws InterruptedException(){
    while(!condition) 
        wait();
    
    // modify some critical data.
    notifyAll();
    }
```

Java Semaphore Class updated:

```java
    class Semaphore {
        private int value;
        
        public Semaphore(int i){
            this.value = i;
        }
        
        public synchronized void acquire() throws InterruptedException{
            while(value == 0)
                this.wait();
            --value;
        }
        
        public synchronized void release() throws InterruptedException {
           // objects will only be waiting if the value is 0 thus notify them all
            if(value == 0) 
                this.notifyAll();
            ++value;
        }
    }
```
   
## Summary

- A Semaphore concept has some limitations.
- Monitor is a better choice for condition synchronisation
- A monitor has a **set of data** to be shared among threads and **synchronised methods** to be used by threads.   