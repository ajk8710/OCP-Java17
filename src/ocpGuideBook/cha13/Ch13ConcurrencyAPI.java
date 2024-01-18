package ocpGuideBook.cha13;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Ch13ConcurrencyAPI {

    public static void main(String[] args) {
        /*
        Thread: Smallest unit of execution that can be scheduled by OS.
        Process: Group of threads, has shared environment (same memory space for variables, communicate directly).
        Task: Single unit of work performed by thread, commonly implemented by lambda expression.
        
        Order of thread execution is not often guaranteed.
        Runnable is functional interface with abstract void run() method
        */
        // Constructor with Runnable target as null. run() calls target.run(). start() starts thread and runs.
        Runnable myThread = new Thread();
        
        // Thread(Runnable target) constructor. start() starts thread & runs target (task).
        new Thread(() -> System.out.println("Which finishes first")).start();
        System.out.println("Not guaranteed");  // runs in main thread
        
        // Implementing Runnable
        Runnable usingAnonymous = new Runnable() {
            @Override
            public void run() {
                System.out.println("Printing usingAnonymousClass");
            }
        };
        
        Runnable usingLambda1 = () -> System.out.println("Printing usingLambda1");
        Runnable usingLambda2 = () -> {
            for (int i = 0; i < 3; i++) System.out.println("Printing usingLambda2: " + i);  // order within a thread is synchronous, 0, 1, 2.
        };
        
        // Creating Thread with a task (Runnable), then start(). start() runs thread. Execution order of threads is not guaranteed. (It asks to run at some point in future. CPU may be busy.)
        // These are asynchronous task, does not wait results of each.
        new Thread(usingAnonymous).start();
        new Thread(usingLambda1).start();
        new Thread(usingLambda2).start();
        
        new Thread(usingAnonymous).run();  // run() runs it but does not start thread, does not run in new thread. (Runs in main thread.)
        
        // More generally, we can create a Thread and its associated task by:
        // Provide a Runnable object or lambda expression to the Thread constructor. (Using lambda is common. Java knows lambda should implement the only one abstract method.)
        // Create a class that extends Thread and overrides the run() method. (Uncommon)
        
        // Daemon thread in Java is a low-priority thread that performs background operations such as garbage collection.
        // Java program terminates when only threads that are running are daemon threads. Non-daemon threads prevents program termination until they're finished.
        // Threads created by developers are not daemon threads. They can be set to daemon by threadName.setDaemon(true).
        
        
        // Calling interrupt() on a thread in TIMED_WAITING or WAITING state causes the thread to become RUNNABLE again, by causing InterruptedException.
        // It does not throw exception on thread that is NEW or RUNNABLE state.
        final var mainThread = Thread.currentThread();
        
        new Thread(() -> {
            System.out.println("I'm new thread");
            mainThread.interrupt();  // interrupt() notifies thread, i.e. throws 
        }).start();
        
        try {
            Thread.sleep(1_000);  // 1 second (1000 milliseconds)
        }
        catch (InterruptedException e) {
            System.out.println("Wake up!");  // stop waiting and get up
        }
        
        
        // Concurrency API: java.util.concurrent package.
        // In practice, it is often better to use Concurrency API than working with Thread objects directly.
        // It includes ExecutorService interface, and Executors factory class creates instances of ExecutorService.
        
        ExecutorService service = Executors.newSingleThreadExecutor();  // newSingleThreadExecutor creates a single thread. It's like submitting sequential tasks to a single thread.
        try {
            System.out.println("begin");  // in a main thread
            service.execute(usingAnonymous);  // in a service thread. Thread created on first execute. Tasks within this service is sequential (newSingleThreadExecutor)
            service.execute(usingLambda1);  // in a service thread
            service.execute(usingLambda2);  // in a service thread
            System.out.println("end");  // in a main thread
        } finally {  // using try-finally is not required but is good practice.
            service.shutdown();  // need to call shutdown unlike Thread object. Rejects any new tasks (isShutdown true), continues previously submitted tasks (isTerminated becomes true when all done).
            System.out.println(service.isShutdown());  // true
            System.out.println(service.isTerminated());  // false yet
        }
        // shutdownNow() attempts to stop all running tasks, but is not guaranteed to succeed.
        
        try {  // awaitTermination blocks and waits upto specified time, or until termination.
            service.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        if (service.isTerminated()) {  // true
            System.out.println("Terminated");
        }
        
        // execute(Runnable) is "fire and forget". It returns void, calling thread does not know the result.
        // submit(Runnable) returns a Future<?> instance (represents status of task) that can be used to determine whether the task is completed.
        // submit(Callable<T>), submit(Collection<Callable<T>>) returns Future<T> or list of Future<T>.
        // invokeAny(Collection<Callable<T>>) waits for at least one to complete. Returns T.
        
        // submit(Runnable)'s future.get() returns null. submit(Callable)'s future.get() return generic.
        // Runnable is lambda returning void. Callable is lambda returning non-void.
        service = Executors.newSingleThreadExecutor();
        try {
            Future<?> futureReuslt = service.submit(() -> System.out.println("Hello"));  // submit(Runnable)
            System.out.println("Is done: " + futureReuslt.isDone());  // false yet
            System.out.println("Is cancelled: " + futureReuslt.isCancelled());  // false
            futureReuslt.get(3, TimeUnit.SECONDS);  // returns null for Runnable, since Runnable's run() method is void.
        } catch (Exception e) {  // need to catch exceptions from get().
            System.out.println("Time is out");
        } finally {  // using try-finally is not required but is good practice.
            service.shutdown();
        }
        // get() gets result of task, waiting endlessly if it is not yet available.
        // get(long timeout, TimeUnit unit) gets result of task, waiting specified amount of time. If timeout, throws TimeoutException.
        
        
        /* Unlike Runnable's run() returns void, Callable's call() returns matching type value & throws Exception.
           Note that submit(Callable) method does not throw Exception though.
        @FunctionalInterface public interface Callable<V> {
            V call() throws Exception;
        } */
        try {
            Future<Integer> result = service.submit(() -> 30 + 11);  // Lambda has return type, so Java says it must be callable.
            System.out.println(result.get());  // 41
        } catch (Exception e) {  // need to catch exceptions from get().
            System.out.println("Catch Exception");
        } finally {
            service.shutdown();
        }
        
        
        // ScheduledExecutorService is sub-interface of ExecutorService. It's for future task or repeating task.
        // Following method returns ScheduledFuture, which has getDelay() method that returns the remaining delay.
        // schedule(callable, delay, timeUnit), schedule(runnable, delay, timeUnit)
        // scheduleAtFixedRate(runnable, initialDelay, period, timeUnit) execute task after initial delay then new task every period. (regardless of termination of previous task)
        // scheduleAtFixedDelay(runnable, initialDelay, delay, timeUnit) execute task after initial delay then new task, with delay after termination of previous task.
        ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
        
        Runnable task1 = () -> System.out.println("I'm Runnable");  // lambda returns void: Runnable
        Callable<String> task2 = () ->  "I'm Callable";  // lambda returns String: Callable
        ScheduledFuture<?> sf1 = scheduledService.schedule(task1, 1, TimeUnit.SECONDS);  // having this in try-finally is better practice.
        ScheduledFuture<?> sf2 = scheduledService.schedule(task2, 2, TimeUnit.SECONDS);  // having this in try-finally is better practice.
        try {
            System.out.println("r1: " + sf1.get());  // sf1: null
            System.out.println("r2: " + sf2.get());  // sf2: I'm Callable
        } catch(Exception e) {  // need to catch exceptions from get().
            System.out.println("Exception");
        }
        scheduledService.shutdown();  // having this in try-finally is better practice.
        
        
        // Thread pool is group of pre-instantiated reusable threads.
        // While a single-thread executor will wait for the thread to become available before running the next task,
        // a pooled-thread executor can execute the next task concurrently
        
        ExecutorService service1 = Executors.newCachedThreadPool();  // Creates new threads as needed but reuses previously constructed threads if available.
        ExecutorService service2 = Executors.newFixedThreadPool(3);  // int parameter for fixed number of threads in pool
        ScheduledExecutorService service3 = Executors.newScheduledThreadPool(3);
        
        Runnable task3 = () -> System.out.println("Run after 2 sec");
        Runnable task4 = () -> System.out.println("Run after 1 sec");
        try {
            sf1 = service3.schedule(task3, 2, TimeUnit.SECONDS);
            sf2 = service3.schedule(task4, 1, TimeUnit.SECONDS);  // finishes first
        } catch(Exception e) {
            System.out.println("Exception");
        } finally {
            service3.shutdown();
        }
        
        scheduledService = Executors.newSingleThreadScheduledExecutor();
        task3 = () -> System.out.println("Run after 4 sec");
        task4 = () -> System.out.println("Run after 3 sec");
        try {
            sf1 = scheduledService.schedule(task3, 4, TimeUnit.SECONDS);
            sf2 = scheduledService.schedule(task4, 3, TimeUnit.SECONDS);  // still finishes first
        } catch(Exception e) {
            System.out.println("Exception");
        } finally {
            scheduledService.shutdown();
        }
        
    }
    
}
