/**-----------------------------------------------------------------------------
  * Author:          Sumit Gouthaman
  * Written:         15 July 2014
  * Last updated:    15 July 2014
  * 
  * Compilation:     javac-algs4 KdTree.java
  * 
  * Stores a set of points in a unit 2D space.
  *---------------------------------------------------------------------------*/

public class KdTree {
    private Node root;
    private int size;
    private final boolean VERTICAL = true;
    private final boolean HORIZOTAL = false;
    
    public KdTree() {
        root = null;
        size = 0;
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public int size() {
        return size;
    }
//    public KdTree()                                 // construct an empty set of points
//    public boolean isEmpty()                        // is the set empty?
//    public int size()                               // number of points in the set
//    public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
//    public boolean contains(Point2D p)              // does the set contain the point p?
//    public void draw()                              // draw all of the points to standard draw
//    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
//    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }
}