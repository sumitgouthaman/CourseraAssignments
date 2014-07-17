/**-----------------------------------------------------------------------------
  * Author:          Sumit Gouthaman
  * Written:         14 July 2014
  * Last updated:    16 July 2014
  * 
  * Compilation:     javac-algs4 PointSET.java
  * 
  * Stores a set of points in a unit 2D space.
  *---------------------------------------------------------------------------*/
/**
 * Class that stires a set of Point2D objects and facilitates brute force
 * implementatios of nearest neighbor and range search operations.
 * 
 * @author Sumit Gouthaman
 */
public class PointSET {
    private SET<Point2D> points;            // SET of stored points
    
    /**
     * Public contructor
     */
    public PointSET() {
        points = new SET<Point2D>();
    }
    
    /**
     * Whether the set is empty.
     * 
     * @returns boolean representing if the set is empty
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }
    
    /**
     * @returns Size of the set of points
     */
    public int size() {
        return points.size();
    }
    
    /**
     * Inserts a point into the set.
     * 
     * @param p The point to be inserted
     */
    public void insert(Point2D p) {
        points.add(p);
    }
    
    /**
     * Determines if a point is already present in the set.
     * 
     * @param p The point to be checked
     * @returns boolean representing if the point is present in the set
     */
    public boolean contains(Point2D p) {
        return points.contains(p);
    }
    
    /**
     * Cause all points in the set to be redrawn using StdDraw.
     */
    public void draw() {
        for (Point2D p: points) {
            p.draw();
        }
    }
    
    /**
     * 2D range search on the set of points.
     * 
     * @param rect The rectangle representing the 2D range
     * @returns Iterable over set of points in the range
     */
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> pointsInRange = new Stack<Point2D>();
        for (Point2D p: points) {
            if (rect.contains(p)) {
                pointsInRange.push(p);
            }
        }
        return pointsInRange;
    }
    
    /**
     * Determine a point in the set that is closest to the given point.
     * 
     * @param p The query point
     * @returns A point in the set that is nearest to the query point, 
     *          null if set is empty.
     */
    public Point2D nearest(Point2D p) {
        double minDist = -1;
        Point2D minPoint = null;
        for (Point2D point: points) {
            if (minPoint == null) {
                minDist = point.distanceSquaredTo(p);
                minPoint = point;
                continue;
            }
            double dist = point.distanceSquaredTo(p);
            if (dist < minDist) {
                minDist = dist;
                minPoint = point;
            }
        }
        return minPoint;
    }
}