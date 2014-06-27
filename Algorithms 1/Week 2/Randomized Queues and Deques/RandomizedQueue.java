/**-----------------------------------------------------------------------------
 * Author:           Sumit Gouthaman
 * Written:          25 June 2014
 * Last updated:     26 June 2014
 * 
 * Compilation:      javac-algs4 RandomizedQueue.java
 * Execution:        <No main method. Cannot be executed directly>
 * 
 * Class that implments a randomized queue.
 * 
 *--------------------------------------------------------------------------- */

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class for Randomized queue.
 * This data structure is conceptually similar to a queue. However, on dequeue,
 * a random element is removed and returned instead of the last item.
 * 
 * @author Sumit Gouthaman
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    /**
     * This Queue is internally implemented as a growing and shrinking array
     */
    private Item[] items;  // Array representing the queue
    private int N;         // Index next empty position in the array
    
    /**
     * Default constructor
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[2]; // Start with 2 items, grow when needed
        N = 0;
    }
    
    /**
     * Returns if the Queue is empty
     * 
     * @returns Boolean representing if the queue is empty
     */
    public boolean isEmpty() {
        return N == 0;
    }
    
    /**
     * Returns current size of the queue
     * 
     * @returns size of the queue
     */
    public int size() {
        return N;
    }
    
    /**
     * Add element to the queue
     * 
     * @param item The item to be added
     * @throws NullPointerException if item is null
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        int newSize = N + 1;
        if (newSize > ((double) items.length * 0.75)) {
            doubleSize();
        }
        items[N] = item;
        /**
         * The queue is maintained as a random queue as it grows
         */
        int randomIndex = StdRandom.uniform(0, N + 1);
        exch(randomIndex, N);
        N++;
    }
    
    /**
     * Remove and return a random element from the queue
     * 
     * @returns the removed element
     * @throws NoSuchElementException if queue is empty
     */
    public Item dequeue() {
        /**
         * Since the array is maintainted as a shuffled queue when elements are
         * added, we just need to return the last element on dequeue
         */
        if (N == 0) throw new NoSuchElementException();
        Item toBeReturned = items[--N];
        items[N] = null; //To prevent loitering
        if (N < ((double) items.length * 0.25)) {
            halveSize();
        }
        return toBeReturned;
    }
    
    /**
     * Returns (but does not remove) a random element from the queue
     * 
     * @returns a random element from the queue
     * @throws NoSuchElementException if queue is empty
     */
    public Item sample() {
        if (N == 0) throw new NoSuchElementException();
        return items[StdRandom.uniform(0, N)];
    }
    
    /**
     * Returns a iterator to traverse the elements of the queue in random order
     * 
     * @returns a random iterator
     */
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }
    
    /**
     * Main method for unit testing
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        int n1, n2;
        
        rq.enqueue(10);
        if (rq.size() == 1) {
            StdOut.println("Test 01: Passed");
        } else StdOut.println("Test 01: Failed");
        
        n1 = rq.dequeue();
        if (n1 == 10 && rq.size() == 0) {
            StdOut.println("Test 02: Passed");
        } else StdOut.println("Test 02: Failed");
        
        rq.enqueue(20);
        rq.enqueue(30);
        
        if (rq.size() == 2) {
            StdOut.println("Test 03: Passed");
        } else StdOut.println("Test 03: Failed");
        
        n1 = rq.dequeue();
        n2 = rq.dequeue();
        if (((n1 == 20 && n2 == 30) || (n1 == 30 && n2 == 20)) && rq.size() == 0) {
            StdOut.println("Test 04: Passed");
        } else StdOut.println("Test 04: Failed");
        
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        
        StdOut.println("Iterator test:");
        for (int i: rq) {
            StdOut.println("Outer Loop: " + i);
            StdOut.print("Inner Loop: ");
            for (int j: rq) {
                StdOut.print(j + " ");
            }
            StdOut.println();
        }
    }
    
    /**
     * Function that exchanges the items in two positions of the array
     * 
     * @param i the first index
     * @param j the second index
     */
    private void exch(int i, int j) {
        Item temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }
    
    /**
     * Double the size of the array and copy elements to the new array
     */
    private void doubleSize() {
        Item[] newArray = (Item[]) new Object[items.length * 2];
        System.arraycopy(items, 0, newArray, 0 , items.length);
        items = newArray;
    }
    
    /**
     * Shrink the size of the array and copy elements to the new array
     */
    private void halveSize() {
        if (items.length <= 2) return;
        Item[] newArray = (Item[]) new Object[items.length / 2];
        System.arraycopy(items, 0, newArray, 0 , newArray.length);
        items = newArray;
    }
    
    /**
     * Iterator implementation to traverse the queue in random order.
     */
    private class RandomizedIterator implements Iterator<Item> {
        private Item[] snapshot; // A snapshot of the items
        
        private int currIndex;   // Index of next item to be returned
        
        /**
         * Default constructor
         */
        public RandomizedIterator() {
            snapshot = (Item[]) new Object[N];
            System.arraycopy(items, 0, snapshot, 0, N);
            StdRandom.shuffle(snapshot);
            currIndex = 0;
        }
        
        /**
         * Returns whether any more elements are left to be returned
         *
         * @returns Boolean representing if any more elements are left
         */
        public boolean hasNext() {
            return (currIndex < snapshot.length);
        }
        
        /**
         * Removing elements is not supported
         * 
         * @throws UnsupportedOperationException
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        /**
         * Return next element in the deck
         * 
         * @returns next element in deque
         * @throws NoSuchElementException if no more elements are left
         */
        public Item next() {
            if (hasNext()) {
                return snapshot[currIndex++];
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}