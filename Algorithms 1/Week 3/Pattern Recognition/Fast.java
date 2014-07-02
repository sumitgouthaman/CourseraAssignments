/**-----------------------------------------------------------------------------
 * Author:           Sumit Gouthaman
 * Written:          30 June 2014
 * Last updated:     1 July 2014
 * 
 * Compilation:      javac-algs4 Fast.java
 * Execution:        java-algs4 Fast
 * 
 * Fast algorithm to find collinear points in a plane.
 * 
 *--------------------------------------------------------------------------- */
import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        In in = new In(args[0]);
        int P = in.readInt();
        Point[] orgPoints = new Point[P];
        for (int p = 0; p < P; p++) {
            int x = in.readInt();
            int y = in.readInt();
            orgPoints[p] = new Point(x, y);
            orgPoints[p].draw();
        }
        
        Point[] points = new Point[P];
        
        
        for (Point origin: orgPoints) {
            System.arraycopy(orgPoints, 0, points, 0, P);
            Arrays.sort(points, origin.SLOPE_ORDER);
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
    
    private static void exch(Point[] pts, int x, int y) {
        Point temp = pts[x];
        pts[y] = pts[x];
        pts[x] = temp;
    }
    
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