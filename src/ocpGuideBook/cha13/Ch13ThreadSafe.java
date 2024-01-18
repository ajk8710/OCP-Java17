package ocpGuideBook.cha13;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ch13ThreadSafe {
    
    private volatile int count = 0;  // valatile is rarely used in practice, but may show up on exam.
    private AtomicInteger cnt = new AtomicInteger(0);;
    
    public static void main(String[] args) {
        
        /*
        volatile ensures that only one thread is modifying a variable at one time,
        and that data read among multiple threads is consistent.
        The increment operator ++ is not thread-safe, even when volatile is used, 
        because operation is not atomic, carry out two tasks, read & write. Between read & write, another thread can interrupt.
        */
        
        // Atomic Operation.
        // java.util.concurrent.atomic package includes atomic values that can be updated atomically.
        AtomicBoolean ab;
        AtomicInteger ai;
        AtomicLong al;
        
        ai = new AtomicInteger(0);
        System.out.println(ai.getAndIncrement());  // 0
        System.out.println(ai.incrementAndGet());  // 2
        ai.set(5);
        System.out.println(ai.getAndSet(6));  // 5
        System.out.println(ai.get());  // 6
        
        Ch13ThreadSafe threadSafe = new Ch13ThreadSafe();
        ExecutorService service = Executors.newFixedThreadPool(10);
        
        try {
            for (int i = 0; i < 10; i++) {
                // service.submit(() -> threadSafe.incrementCountNotSafe());  // 1 5 1 3 7 2 6 1 8 4 (duplicate numbers)
                service.submit(() -> threadSafe.incrementCount());  // 1 4 5 6 2 8 10 3 9 7 (atomic operations)
            }
        } finally {
            service.shutdown();
        }
        
        
        // synchronized keyword:
        // A monitor or lock supports mutual exclusion, where at most one thread can enter particular segment of codes.
        // In java, any object can be used as monitor, with synchronized keyword.
        
        /* Following is not synchronized. It synchronizes creation of threads, not operation on a variable.
        service = Executors.newFixedThreadPool(10);
        synchronized (threadSafe) {
            try {
                for (int i = 0; i < 10; i++) {
                    service.submit(() -> threadSafe.incrementCount());
                }
            } finally {
                service.shutdown();
            }
        }
        */
        
        // Using synchronized block in the method
        service = Executors.newFixedThreadPool(10);
        try {  // Remove previous try-finally to see the correct output.
            for (int i = 0; i < 10; i++) {  
                service.submit(() -> threadSafe.incrementCountSynchronized());  // 1 2 3 4 5 6 7 8 9 10  (synchronized operations)
            }
        } finally {
            service.shutdown();
        }
        
        
        // Lock provides additional features, compared to synchronized keyword.
        // void lock() requests lock and blocks until lock is acquired. (same as synchronized keyword)
        // void unlock() release lock - if you call unlock() when you do not have lock, IllegalMonitorStateException is thrown (runtime exception).
        // boolean tryLock() Requests lock and returns false immediately if not available.
        //     Commonly used with if or if-else. (Without "if" it will simply return false, and continue to protected codes below without lock.)
        // boolean tryLock(timeout, timeUnit) requests lock for specified time, returns false if not acquired by timeout.
        
        Lock lock = new ReentrantLock();  // ReentrantLock has another constructor that takes boolean fairness. If true, lock to be granted in order of request.
        if (lock.tryLock()) {  // If another thread also requires lock, this may or may not acquire lock
            try {
                System.out.println("I acquired lock");
            } finally {
                lock.unlock();  // It's good practice to have unlock in try-finally.
            }
        }  // Make sure you call same number of lock() (or tryLock()) and unlock(). Otherwise, next call to lock() or tryLock() never gets the lock.
        
        
        /*
        CyclicBarrier can be used to control complex tasks with many steps.
        CyclicBarrier(limit) constructor: limit means number of threads to wait for.
        CyclicBarrier(limit, runnable): executes runnable every time limit is reached.
        public void cleanLionPen(CyclicBarrier c1, CyclicBarrier c2) {
            try {
                removeLions();  // multiple threads run these method in parallel.
                c1.await();  // awaits blocks until number of threads reaches here. Then count restarts.
                cleanPen();
                c2.await();  // awaits blocks until number of threads reaches here. Then count restarts.
                addLions();
            } catch (InterruptedException | BrokenBarrierException e) {
                // Handle checked exceptions here
            }
        }
        */
    }
    
    private void incrementCountNotSafe() {
        System.out.print(++count + " ");  // increment operator is not atomic operation. Read & write is not one single operation.
    }
    
    private void incrementCount() {
        System.out.print(cnt.incrementAndGet() + " ");  // incrementAndGet is atomic operation.
    }
    
    private void incrementCountSynchronized() {
        synchronized (this) {  // this can be any object
            System.out.print(++count + " ");  // only one thread can enter synchronized block at the time.
        }
    }
    
    // Can put synchronized keyword on instance method to synchronize on object itself.
    private synchronized void incrementCountSynchronizedMethod() {
        System.out.print(++count + " ");  // only one thread can enter synchronized block at the time.
    }
    // Can also put synchronized keyword on static method. Or create block with synchronized(Ch13ThreadSafe.class).
    // Static synchronization is to order thread access across threads of all instances rather than single instance.
    
}
