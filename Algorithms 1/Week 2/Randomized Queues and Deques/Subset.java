/**-----------------------------------------------------------------------------
 * Author:           Sumit Gouthaman
 * Written:          26 June 2014
 * Last updated:     26 June 2014
 * 
 * Compilation:      javac-algs4 Subset.java
 * Execution:        echo A B C D E F G H I | java Subset 3
 *                   Format: echo [set of strings] | java-agls4 Subset [number]
 * 
 * Class that takes a set of strings and prints out a random subset.
 * 
 *--------------------------------------------------------------------------- */
/**
 * Class Subset that reads in a set of strings and then prints out a random
 * subset of k integers (where k is passed as a command line argument).
 * 
 * @author Sumit Gouthaman
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String[] strings = StdIn.readAllStrings();
        for (String s: strings) {
            rq.enqueue(s);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}