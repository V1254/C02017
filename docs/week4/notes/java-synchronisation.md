# Synchronisation in Java

## Overview:

- **Semaphores** in java
- **Mutual exclusion** with semaphores
- **Producer/consumer** example
- **Implement** producer/consumer example **using semaphores**

## Semaphores:

Simple Example of a Semaphore:
```java
    class Semaphore {
        int field;
        Semaphore(int i){
            this.field = i;
        }
        
        void acquire(){
            // do some shit;
        }
        
        void release(){
            // do some shit;
        }
    }
```
- **acquire()** will decrease the value of the field by 1.
- **acquire()** will **block** if the field value is 0.
- **release** will **free** the resource and increase the value of field by 1.


#### Achieving mutual exclusion with Semaphore

```java
        class SomeThread extends Thread {
        Semaphore mutex;
        
        SomeThread (Semaphore s){mutex = s;}
        
        public void run(){
            // Do some non -critical work here      
            mutex.acquire();
            // Do some critical work here.     
            mutex.release();
        }
       }
       
       class Controller {
            public static void main(String[] args){
              Semaphore s = new Semaphore(1);
              SomeThread_1 t1 = new SomeThread(s);
              ... 
              SomeThread_n tn = new SomeThread(s);
              t1.start();
              ...;
              tn.start();
            }
       }
```

Now consider the Three Process t1, t2 and t3.

![](https://i.imgur.com/q7ckg6i.png)

Once one process enters its critical sections, the others must wait hence achieving mutual exclusion.

## Semaphore to Solve the Consumer/Producer problem.

### The Producer/Consumer problem

Problem description:
- A **producer** repeatedly produces items and places them into a buffer;
- meanwhile a **consumer** consumes the items one-by-one by taking from the buffer.

Requirements:
- Assume an implementation of a **bounded FIFO buffer** with operations place() and take(). 
- The producer may produce a new item only when the buffer is not full.
- THe consumer may consume an item only when the buffer is not empty.
= All items produced are eventually consumed.

### Behaviour of the FIFO Buffer

- Implemented as an array.
- We need *NextIn* and *NextOut* to locate where to *place* an item *into* and *take* and item from the buffer
- *NextIn* and *NextOut* are incremented one by one. When they reach the end of the buffer, need to roll-over to 0.

The Buffer class:

```java
    class Buffer { 
        static final int BUFFERSIZE = 5;
        int[] _b = new int[BUFFERSIZE];
        int _NextIn, _NextOut;
        
        Buffer(){
            _NextIn = 0;
            _NextOut = 0;
        }
        
        public void place(int item){
            _b[_NextIn] = item;
            _NextIn = (_NextIn + 1) % BUFFERSIZE;
        }
        
        public int take(){
            int v = _b[_NextOut];
            _NextOut = (_NextOut+1)%BUFFERSIZE;
            return v;
        }
    }
```

#### Using Semaphores 1:

- The buffer is **critical resource**
- Need to achieve **mutual exclusion** of the *take* and *place* methods that modify the critical resource.
- Use a **semaphore** MutEx to enforce this mutual exclusion.

#### Using Semaphores 2:

Need to prevent *take* method from operating on the empty buffer.

- Use a **semaphore *ItemsReady*** to record the number of items in the buffer.
- The consumer must be blocked when the semaphores *ItemReady* is 0.
- The initial value of *ItemsReady* will be 0, since the queue is empty,
- After the producer *places* an item, it will *release* the semaphore *ItemsReady*.

#### Using Semaphores 3:

Need to prevent the *place* method operating on a full buffer.

- Use a **semaphore *SpacesLeft*** to record free spaces in the buffer.
- The producer should be blocked when the **SpacesLeft** is 0.
- After the consumer *takes* an item, it will release the semaphore *SpacesLeft*.

### The *Producer* class

```java
    class Producer extends Thread {
        Buffer _b;
        Semaphore _mx, _ready, _spaces;
        
        Producer(Buffer b, Semaphore mutEx, Semaphore itemsReady, Semaphore spacesLeft){
            _b = b;
            _mx = mutEx;
            _ready = itemsReady;
            _spaces = spacesLeft;
        }
        
        @Override
        public void run(){
            for(int i =1; i <= 100;i++){
                // wait for spaces
                _spaces.acquire();
                // wait for mutual exclusion
                _mx.acquire();
                // add an item tgo the buffer.
                _b.place(i);
                // release mutual exclusion
                _mx.release();
                // release the consumer
                _ready.release();
            }
        }
    }
    
    class Consumer extends Thread {
        Buffer _b;
        Semaphore _mx, _ready,_spaces;
        
        Consumer(Buffer b, Semaphore mutEx, Semaphore itemsReady, Semaphore spacesLeft){
            _b = b;
            _mx = mutEx;
            _ready = itemsReady;
            _spaces = spacesLeft;
        }
        
        @Override
        public void run(){
            int someValue;
            for(int i =1; i <=100; i++){
                // wait for items to ready
                _ready.acquire();
                // wait for mutual exclusion
                _mx.acquire();
                // take some item
                someValue = _b.take();
                // release mutual exclusion
                _mx.release();
                // release the producer
                _spaces.release();
                // print out the value taken
                System.out.println(someValue);
            }
        }
    }
```

**Deadlock**: a process is said to be deadlocked if it is waiting for an event which will never happen.

## Summary

- A Semaphore is a primitive used to synchronise threads.
- A semaphore has acquire() and release() operations to guarantee mutual exclusions of threads relevant to one shared object.
- Should know how to solve the Producer/Consumer problems using semaphores.