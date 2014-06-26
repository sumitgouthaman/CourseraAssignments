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
    private Item[] items;
    private int N;
    
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        N = 0;
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        int newSize = N + 1;
        if (newSize > ((double) items.length * 0.75)) {
            doubleSize();
        }
        items[N] = item;
        int randomIndex = StdRandom.uniform(0, N + 1);
        exch(randomIndex, N);
        N++;
    }
    
    public Item dequeue() {
        if (N == 0) throw new NoSuchElementException();
        Item toBeReturned = items[--N];
        items[N] = null; //To prevent loitering
        if (N < ((double) items.length * 0.25)) {
            halveSize();
        }
        return toBeReturned;
    }
    
    public Item sample() {
        return items[StdRandom.uniform(0, N)];
    }
    
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }
    
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
    
    private void exch(int i, int j) {
        Item temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }
    
    private void doubleSize() {
        Item[] newArray = (Item[]) new Object[items.length * 2];
        System.arraycopy(items, 0, newArray, 0 , items.length);
        items = newArray;
    }
    
    private void halveSize() {
        if (items.length <= 2) return;
        Item[] newArray = (Item[]) new Object[items.length / 2];
        System.arraycopy(items, 0, newArray, 0 , newArray.length);
        items = newArray;
    }
    
    private class RandomizedIterator implements Iterator<Item> {
        private Item[] snapshot;
        
        private int currIndex;
        
        public RandomizedIterator() {
            snapshot = (Item[]) new Object[N];
            System.arraycopy(items, 0, snapshot, 0, N);
            StdRandom.shuffle(snapshot);
            currIndex = 0;
        }
        
        public boolean hasNext() {
            return (currIndex < snapshot.length);
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (hasNext()) {
                return snapshot[currIndex++];
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}