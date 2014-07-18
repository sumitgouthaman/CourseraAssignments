/**-----------------------------------------------------------------------------
  * Author:          Sumit Gouthaman
  * Written:         15 July 2014
  * Last updated:    16 July 2014
  * 
  * Compilation:     javac-algs4 KdTree.java
  * 
  * Stores a set of points in a unit 2D space.
  *---------------------------------------------------------------------------*/
/**
 * Class to implement a Kd-Tree (2d-Tree) data structure to store a set of
 * points in 2D space.
 * 
 * Provides efficient implementations of the nearest neighbor and range search
 * operations.
 * 
 * @author Sumit Gouthaman
 */
public class KdTree {
    /*
     * Static fields representing direction of partition in each level.
     */
    private static final boolean VERTICAL = true;
    private static final boolean HORIZOTAL = false;
    
    private Node root; // The root of the tree
    private int size;  // No of nodes in the tree
    
    /**
     * Public contructor
     */
    public KdTree() {
        root = null;
        size = 0;
    }
    
    /**
     * Determines if the tree is empty.
     * 
     * @returns boolean representing if the tree is empty
     */
    public boolean isEmpty() {
        return root == null;
    }
    
    /**
     * Determines size of the tree.
     * 
     * @returns no of nodes in the tree
     */
    public int size() {
        return size;
    }
    
    /**
     * Inserts a Point2D object into the tree.
     * 
     * @param p The point to be inserted
     */
    public void insert(Point2D p) {
        if (p == null) return;
        RectHV rect;
        /*
         * If root is null, it implies that the tree is empty.
         * In such a case, create a new RectHV object that represents the entire
         * possible area.
         */
        if (root == null) rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        else rect = root.rect;
        
        // Call helper insert method
        root = insert(root, p, rect, VERTICAL);
    }
    
    /**
     * Determines if the given point is present in the tree.
     * 
     * @param p The point to be checked
     * @returns boolean representing if the point is present in the tree
     */
    public boolean contains(Point2D p) {
        // Call helper contains method
        return contains(root, p, VERTICAL);
    }
    
    /**
     * Draw all points and partitioning lines using StdDraw.
     */
    public void draw() {
        // Call helper draw method
        draw(root, VERTICAL);
    }
    
    /**
     * Get a set of points that fall in given 2D range.
     * 
     * @param rect The RectHV object representing the 2D range
     * @returns An Iterable over points in the range
     */
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> points = new Stack<Point2D>();
        // Call helper method for range
        range(root, rect, points, VERTICAL);
        return points;
    }
    
    /**
     * Get point nearest to the given query point.
     * 
     * @param p The query point
     * @returns The nearest point to the query point
     */
    public Point2D nearest(Point2D p) {
        NearestPt nearestPt = new NearestPt();
        nearestPt.p = null;
        nearestPt.dist = Double.MAX_VALUE;
        nearest(root, p, nearestPt, VERTICAL);
        return nearestPt.p;
    }
    
    /*---------------------------- PRIVATE CLASSES ---------------------------*/
    
    /**
     * Private class to store values related to a single node of the Kd-Tree.
     */
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // axis-aligned rect corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }
    
    /**
     * Private class to store values related to currently found nearest point
     * in neighbor search queries.
     */
    private static class NearestPt {
        private Point2D p;
        private double dist;
    }
    
    /*---------------------------- PRIVATE METHODS ---------------------------*/
    
    /**
     * Private method to perform insert at a particular node.
     * To be used recursively and triggered from a outer wrapping method.
     * 
     * @param parent The node to tbe considered root
     * @param p The point to be inserted
     * @param rect The rect representing the potential domain of the point
     * @param direction The direction of partitioning at the given level
     * @returns The actual node in case parent is null, else the parent
     */
    private Node insert(Node parent, Point2D p, RectHV rect, boolean direction) {
        if (parent == null) {
            /*
             * This condition determines that the probeing has reached the end
             * of the path and a new Node has to be inserted here.
             */
            Node node = new Node();
            node.p = p;
            node.rect = rect;
            node.lb = null;
            node.rt = null;
            size++;
            return node;
        }
        
        // Do not insert if node is already present
        if (parent.p.equals(p)) return parent;
        
        if (direction == VERTICAL) {
            /*
             * Check whether point lies to left or right of the partitioning
             * line.
             */
            if (p.x() < parent.p.x()) {
                /*
                 * The point is on the left.
                 * Check whether this root already has a left child.
                 * If yes, follow that link, else add the node here.
                 */
                if (parent.lb != null) {
                    parent.lb = insert(parent.lb, p, parent.lb.rect, !direction);
                    return parent;
                }
                RectHV newRect = new RectHV(rect.xmin(), rect.ymin(), 
                                            parent.p.x(), rect.ymax());
                parent.lb = insert(parent.lb, p, newRect, !direction);
            } else {
                /*
                 * The point is on the right.
                 * Check whether this root already has a right child.
                 * If yes, follow that link, else add the node here.
                 */
                if (parent.rt != null) {
                    parent.rt = insert(parent.rt, p, parent.rt.rect, !direction);
                    return parent;
                }
                RectHV newRect = new RectHV(parent.p.x(), rect.ymin(), 
                                            rect.xmax(), rect.ymax());
                parent.rt = insert(parent.rt, p, newRect, !direction);
            }
        } else {
            /*
             * Check whether point lies to bottom or top of the partitioning
             * line.
             */
            if (p.y() < parent.p.y()) {
                /*
                 * The point is on the bottom.
                 * Check whether this root already has a bottom child.
                 * If yes, follow that link, else add the node here.
                 */
                if (parent.lb != null) {
                    parent.lb = insert(parent.lb, p, parent.lb.rect, !direction);
                    return parent;
                }
                RectHV newRect = new RectHV(rect.xmin(), rect.ymin(), 
                                            rect.xmax(), parent.p.y());
                parent.lb = insert(parent.lb, p, newRect, !direction);
            } else {
                /*
                 * The point is on the top.
                 * Check whether this root already has a top child.
                 * If yes, follow that link, else add the node here.
                 */
                if (parent.rt != null) {
                    parent.rt = insert(parent.rt, p, parent.rt.rect, !direction);
                    return parent;
                }
                RectHV newRect = new RectHV(rect.xmin(), parent.p.y(), 
                                            rect.xmax(), rect.ymax());
                parent.rt = insert(parent.rt, p, newRect, !direction);
            }
        }
        
        return parent;
    }
    
    /**
     * Private helper method to check whether tree contains a point below
     * certain root node.
     * 
     * @param node The root node bein considered
     * @param p The point being checked for
     * @param direction The direction of partitioning at certain level
     * @returns boolean representing if the point was found
     */
    private boolean contains(Node node, Point2D p, boolean direction) {
        // Return not found if end of path
        if (node == null) return false;
        
        // Return true if the node matches the point
        if (node.p.equals(p)) return true;
        
        /* Else determine which subtree to explore by comparing with the
         * partition depending on whether its vertical or horizontal.
         */
        if (direction == VERTICAL) {
            if (p.x() < node.p.x()) {
                if (node.lb != null) return contains(node.lb, p, !direction);
            } else {
                if (node.rt != null) return contains(node.rt, p, !direction);
            }
        } else {
            if (p.y() < node.p.y()) {
                if (node.lb != null) return contains(node.lb, p, !direction);
            } else {
                if (node.rt != null) return contains(node.rt, p, !direction);
            }
        }
        
        return false;
    }
    
    /**
     * Recursive method to draw the tree below a certain given node.
     * 
     * @param node The root node being considered.
     * @param direction Direction of the partition at the current level
     */
    private void draw(Node node, boolean direction) {
        // Return back if end of path
        if (node == null) return;
        
        // Else draw the partitioning line depending on orientation
        if (direction == VERTICAL) {
            double x0, y0, x1, y1;
            x0 = node.p.x();
            x1 = node.p.x();
            y0 = node.rect.ymin();
            y1 = node.rect.ymax();
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x0, y0, x1, y1);
        } else {
            double x0, y0, x1, y1;
            y0 = node.p.y();
            y1 = node.p.y();
            x0 = node.rect.xmin();
            x1 = node.rect.xmax();
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x0, y0, x1, y1);
        }
        
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        
        // Plot the point
        node.p.draw();
        
        // Recurse on subtrees
        draw(node.lb, !direction);
        draw(node.rt, !direction);
    }
    
    /**
     * Private helper method to determine all nodes that fall in a 2D range
     * below a certain gien root node.
     * 
     * @param node The ode being considered root
     * @param rect The given 2D range
     * @param points The current stack of points found
     */
    private void range(Node node, RectHV rect, Stack<Point2D> points, 
                       boolean direction) {
        // Return back if end of path
        if (node == null) return;
        
        // Add point in current node if in range
        if (rect.contains(node.p))    
            points.push(node.p);
        
        // Recurse on subtrees if they intersect with the range
        if (node.lb != null && node.lb.rect.intersects(rect)) {
            range(node.lb, rect, points, !direction);
        }
        if (node.rt != null && node.rt.rect.intersects(rect)) {
            range(node.rt, rect, points, !direction);
        }
    }
    
    /**
     * Private helper method to find the nearest point to a given point
     * below a certain root node.
     * 
     * @param node The node being considered root
     * @param p The query point
     * @param nearestPt The currently found nearest point
     */
    private void nearest(Node node, Point2D p, NearestPt nearestPt, 
                         boolean direction) {
        // Return back if end of path
        if (node == null) return;
        
        /*
         * Replace the currently found nearest point with the point in this
         * node if it is closer or the nearest point is null.
         */
        if (nearestPt.p == null) {
            nearestPt.p = node.p;
            nearestPt.dist = p.distanceSquaredTo(node.p);
        } else {
            double dist = p.distanceSquaredTo(node.p);
            if (dist < nearestPt.dist) {
                nearestPt.p = node.p;
                nearestPt.dist = dist;
            }
        }
        
        /*
         * Recurse on appropriate sub tree or both depending on need.
         * 
         * Eg: If point seems to be on left side of partition, recurse on that
         * side first. After returning back if the right partition is not closer
         * than the already determned nearest point, then no point recursing
         * in that sub tree.
         * 
         * Similar logic for horizontal partition.
         */
        if (direction == VERTICAL) {
            if (p.x() <= node.p.x()) {
                nearest(node.lb, p, nearestPt, !direction);
                if (node.rt != null 
                        && node.rt.rect.distanceSquaredTo(p) < nearestPt.dist) {
                    nearest(node.rt, p, nearestPt, !direction);
                }
            } else if (p.x() >= node.p.x()) {
                nearest(node.rt, p, nearestPt, !direction);
                if (node.lb != null
                        && node.lb.rect.distanceSquaredTo(p) < nearestPt.dist) {
                    nearest(node.lb, p, nearestPt, !direction);
                }
            }
        } else {
            if (p.y() <= node.p.y()) {
                nearest(node.lb, p, nearestPt, !direction);
                if (node.rt != null 
                        && node.rt.rect.distanceSquaredTo(p) < nearestPt.dist) {
                    nearest(node.rt, p, nearestPt, !direction);
                }
            } else if (p.y() >= node.p.y()) {
                nearest(node.rt, p, nearestPt, !direction);
                if (node.lb != null 
                        && node.lb.rect.distanceSquaredTo(p) < nearestPt.dist) {
                    nearest(node.lb, p, nearestPt, !direction);
                }
            }
        }
    }
}