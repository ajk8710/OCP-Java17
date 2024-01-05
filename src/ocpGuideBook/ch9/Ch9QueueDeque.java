package ocpGuideBook.ch9;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

public class Ch9QueueDeque {
    
    public static void main(String[] args) {
        
        // Common methods of Queue interface:
        // add(E), offer(E) : adds to last.
        // peeK(), element() : reads first.
        // remove(), poll() : removes first.
        
        // offer, peek, poll return null instead of exception.
        
        Queue<String> que = new PriorityQueue<>();
        System.out.println(que.peek());  // null
        
        
        // Deque is sub-interface of Queue
        // LinkedList implements both the List and Deque, and has both functionalities.
        // But it's not efficient as "pure" deque. Use ArrayDeque when only doing stack/queue operations and not needing list functionalities.
        
        // Common methods of Deque interface:
        
        // add(E), addLast(E), offer(E), offerLast(E) : adds to last.
        // addFirst(E), offerFirst(E), push(E)  // this is confusing part of Java because push normally means push last.
        
        // peeK(), peekFirst(), getFirst(), element() : reads first.
        // peekLast(), getLast() : reads last.
        
        // remove(), poll(), pollFirst(), pop() : removes first. this is confusing part of Java because pop normally means pop last.
        // removeLast(), pollLast() : removes last.
        
        // offer, peek, poll related methods return null instead of exception.
        // Note again push & pop happens in front in Java.
        
        Deque<String> deq = new ArrayDeque<>();
        System.out.println(deq.poll());  // null
    }
}
