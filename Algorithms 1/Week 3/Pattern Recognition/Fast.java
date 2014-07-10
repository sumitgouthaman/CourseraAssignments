/**-----------------------------------------------------------------------------
 * Author:           Sumit Gouthaman
 * Written:          30 June 2014
 * Last updated:     9 July 2014
 * 
 * Compilation:      javac-algs4 Fast.java
 * Execution:        java-algs4 Fast
 * 
 * Fast algorithm to find collinear points in a plane.
 * 
 *--------------------------------------------------------------------------- */
import java.util.Arrays;

/**
 * Implements a fast algorithms to find all sets of line segments formed by
 * collinear points in a plane.
 * 
 * Uses a fast sorting algorithms to sort based on polar coordinates and then
 * uses the result to find collinear points.
 * 
 * @author Sumit Gouthaman
 */
public class Fast {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        In in = new In(args[0]);          // Input file
        int P = in.readInt();             // No of total points
        
        Point[] orgPoints = new Point[P]; // Array for original set of points
        
        for (int p = 0; p < P; p++) {
            // Read and draw all the points
            int x = in.readInt();
            int y = in.readInt();
            orgPoints[p] = new Point(x, y);
            orgPoints[p].draw();
        }
        
        Point[] points = new Point[P];    // Array for temporary copy
        
        for (Point origin: orgPoints) {
            // For each point
            System.arraycopy(orgPoints, 0, points, 0, P);
            // Sort other points by slope order compared to it
            Arrays.sort(points, origin.SLOPE_ORDER);
            // Find sets of collinear points. They have same polar slope.
            int i = 1;
            int j = 1;
            while (i < P) {
                while ((j < P) && origin.slopeTo(points[i]) 
                           == origin.slopeTo(points[j]))
                    j++;
                j--;
                int length = (j - i) + 1;
                if (length > 2) {
                    points[i - 1] = origin;
                    Arrays.sort(points, i - 1, j + 1);
                    if (points[i - 1].compareTo(origin) == 0) {
                        printLine(points, i - 1, j);
                        points[i - 1].drawTo(points[j]);
                    }
                }
                i = j + 1;
                j = i;
            }
        }
        
        StdDraw.show(0);
    }
    
    /**
     * Exchange two points in the points array
     * 
     * @param pts The array of points
     * @param x The first index
     * @param y The second index
     */
    private static void exch(Point[] pts, int x, int y) {
        Point temp = pts[x];
        pts[y] = pts[x];
        pts[x] = temp;
    }
    
    /**
     * Print out a section of the points array.
     * 
     * @param pts The points array
     * @param i The first index
     * @param j The second index
     */
    private static void printLine(Point[] pts, int i, int j) {
        int iCopy = i;
        while (iCopy <= j) {
            StdOut.print(pts[iCopy]);
            if (iCopy < j) {
                StdOut.print(" -> ");
            }
            iCopy++;
        }
        StdOut.println();
    }
}