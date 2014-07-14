/**-----------------------------------------------------------------------------
  * Author:          Sumit Gouthaman
  * Written:         14 July 2014
  * Last updated:    15 July 2014
  * 
  * Compilation:     javac-algs4 PointSET.java
  * 
  * Stores a set of points in a unit 2D space.
  *---------------------------------------------------------------------------*/

public class PointSET {
    private SET<Point2D> points;
    
    public PointSET() {
        points = new SET<Point2D>();
    }
    
    public boolean isEmpty() {
        return points.isEmpty();
    }
    
    public int size() {
        return points.size();
    }
    
    public void insert(Point2D p) {
        points.add(p);
    }
    
    public boolean contains(Point2D p) {
        return points.contains(p);
    }
    
    public void draw() {
        for (Point2D p: points) {
            p.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> pointsInRange = new Stack<Point2D>();
        for (Point2D p: points) {
            if (rect.contains(p)) {
                pointsInRange.push(p);
            }
        }
        return pointsInRange;
    }
    
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