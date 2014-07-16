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
    private static final boolean VERTICAL = true;
    private static final boolean HORIZOTAL = false;
    
    private Node root;
    private int size;
    
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
    
    public void insert(Point2D p) {
        if (p == null) return;
        RectHV rect;
        if (root == null) rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        else rect = root.rect;
        root = insert(root, p, rect, VERTICAL);
    }
    
    public boolean contains(Point2D p) {
        return contains(root, p, VERTICAL);
    }
    
    public void draw() {
        draw(root, VERTICAL);
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> points = new Stack<Point2D>();
        range(root, rect, points, VERTICAL);
        return points;
    }
    
    public Point2D nearest(Point2D p) {
        NearestPt nearestPt = new NearestPt();
        nearestPt.p = null;
        nearestPt.dist = Double.MAX_VALUE;
        nearest(root, p, nearestPt, VERTICAL);
        return nearestPt.p;
    }
    
    private void nearest(Node node, Point2D p, NearestPt nearestPt, 
                         boolean direction) {
        if (node == null) return;
        
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
    
    private void range(Node node, RectHV rect, Stack<Point2D> points, 
                       boolean direction) {
        if (node == null) return;
        
        if (rect.contains(node.p))    
            points.push(node.p);
        
        if (node.lb != null && node.lb.rect.intersects(rect)) {
            range(node.lb, rect, points, !direction);
        }
        if (node.rt != null && node.rt.rect.intersects(rect)) {
            range(node.rt, rect, points, !direction);
        }
    }
    
    private void draw(Node node, boolean direction) {
        if (node == null) return;
        
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
        node.p.draw();
        
        draw(node.lb, !direction);
        draw(node.rt, !direction);
    }
    
    private boolean contains(Node node, Point2D p, boolean direction) {
        if (node == null) return false;
        
        if (node.p.equals(p)) return true;
        
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
    
    private Node insert(Node parent, Point2D p, RectHV rect, boolean direction) {
        if (parent == null) {
            Node node = new Node();
            node.p = p;
            node.rect = rect;
            node.lb = null;
            node.rt = null;
            size++;
            return node;
        }
        
        if (parent.p.equals(p)) return parent;
        
        if (direction == VERTICAL) {
            if (p.x() < parent.p.x()) {
                if (parent.lb != null) {
                    parent.lb = insert(parent.lb, p, parent.lb.rect, !direction);
                    return parent;
                }
                RectHV newRect = new RectHV(rect.xmin(), rect.ymin(), 
                                            parent.p.x(), rect.ymax());
                parent.lb = insert(parent.lb, p, newRect, !direction);
            } else {
                if (parent.rt != null) {
                    parent.rt = insert(parent.rt, p, parent.rt.rect, !direction);
                    return parent;
                }
                RectHV newRect = new RectHV(parent.p.x(), rect.ymin(), 
                                            rect.xmax(), rect.ymax());
                parent.rt = insert(parent.rt, p, newRect, !direction);
            }
        } else {
            if (p.y() < parent.p.y()) {
                if (parent.lb != null) {
                    parent.lb = insert(parent.lb, p, parent.lb.rect, !direction);
                    return parent;
                }
                RectHV newRect = new RectHV(rect.xmin(), rect.ymin(), 
                                            rect.xmax(), parent.p.y());
                parent.lb = insert(parent.lb, p, newRect, !direction);
            } else {
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
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // axis-aligned rect corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }
    
    private static class NearestPt {
        private Point2D p;
        private double dist;
    }
}