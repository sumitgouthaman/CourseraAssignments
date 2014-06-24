/**-----------------------------------------------------------------------------
 * Author:           Sumit Gouthaman
 * Written:          23 June 2014
 * Last updated:     23 June 2014
 * 
 * Compilation:      javac-algs4 Deque.java
 * Execution:        <No main method. Cannot be executed directly>
 * 
 * Class that implments the Deque data structure.
 * 
 *--------------------------------------------------------------------------- */

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization of a
 * stack and a queue that supports inserting and removing items from either the
 * front or the back of the data structure.
 * 
 * @author Sumit Gouthaman
 */
public class Deque<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int size;

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }
    
    public boolean isEmpty() {
        return (size == 0);
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        size++;
        if (size == 1) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
    }
    
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (oldLast != null) {
            oldLast.next = last;
        } else {
            first = last;
        }
        size++;
    }
    
    public Item removeFirst() {
        if (size < 1) throw new NoSuchElementException();
        Node removed = first;
        first = first.next;
        size--;
        if (size == 0) {
            last = null;
        } else {
            first.prev = null;
        }
        return removed.item;
    }

    public Item removeLast() {
        if (size < 1) throw new NoSuchElementException();
        Node removed = last;
        last = last.prev;
        size--;
        if (size != 0) {
            last.next = null;
        }
        return removed.item;
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        int n1, n2;
        
        deque.addFirst(10);
        n1 = deque.removeFirst();
        if (n1 == 10 && deque.size() == 0) {
            StdOut.println("Test 01: Passed");
        } else StdOut.println("Test 01: Failed");
        
        deque.addFirst(20);
        deque.addFirst(30);
        n1 = deque.removeFirst();
        if (n1 == 30 && deque.size() == 1) {
            StdOut.println("Test 02: Passed");
        } else StdOut.println("Test 02: Failed");
        
        n2 = deque.removeFirst();
        if (n1 == 30 && n2 == 20 && deque.size() == 0) {
            StdOut.println("Test 03: Passed");
        } else StdOut.println("Test 03: Failed");
        
        deque.addLast(40);
        deque.addLast(50);
        if (deque.size() == 2) {
            StdOut.println("Test 04: Passed");
        } else StdOut.println("Test 04: Failed");
        
        n1 = deque.removeLast();
        if (n1 == 50 && deque.size() == 1) {
            StdOut.println("Test 05: Passed");
        } else StdOut.println("Test 05: Failed");
        
        n2 = deque.removeLast();
        if (n1 == 50 && n2 == 40 && deque.size() == 0) {
            StdOut.println("Test 06: Passed");
        } else StdOut.println("Test 06: Failed");
        
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addLast(4);
        
        n1 = deque.removeFirst();
        n2 = deque.removeLast();
        if (n1 == 3 && n2 == 4 && deque.size() == 2) {
            StdOut.println("Test 07: Passed");
        } else StdOut.println("Test 07: Failed");
        
        n1 = deque.removeFirst();
        n2 = deque.removeLast();
        if (n1 == 1 && n2 == 2 && deque.size() == 0) {
            StdOut.println("Test 08: Passed");
        } else StdOut.println("Test 08: Failed");
    }
    
    private class Node {
        private Item item;
        private Node next;
        private  Node prev;
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node f;
        
        public DequeIterator() {
            f = first;
        }
        
        public boolean hasNext() {
            return !(f == null);
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (hasNext()) {
                Item result = f.item;
                f = f.next;
                return result;
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}