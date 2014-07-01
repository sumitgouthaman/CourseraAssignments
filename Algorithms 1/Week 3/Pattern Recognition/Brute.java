/**-----------------------------------------------------------------------------
 * Author:           Sumit Gouthaman
 * Written:          30 June 2014
 * Last updated:     30 June 2014
 * 
 * Compilation:      javac-algs4 Brute.java
 * Execution:        java-algs4 Brute
 * 
 * Uses brute force to calculate collinear points in a plane
 * 
 *--------------------------------------------------------------------------- */
import java.io.File;
import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        In in = new In(new File(args[0]));
        int P = in.readInt();
        Point[] points = new Point[P];
        for (int p = 0; p < P; p++) {
            int x = in.readInt();
            int y = in.readInt();
            points[p] = new Point(x, y);
        }
        
        for (int i1 = 0; i1 < (P - 3); i1++) {
            for (int i2 = (i1 + 1); i2 < (P - 2); i2++) {
                for (int i3 = (i2 + 1); i3 < (P - 1); i3++) {
                    for (int i4 = (i3 + 1); i4 < P; i4++) {
                        checkCollinear(points[i1], points[i2], points[i3], 
                                       points[i4]);
                    }
                }
            }
        }
        
        StdDraw.show(0);
    }
    private static void checkCollinear(Point p1, Point p2, Point p3, Point p4) {
        Point[] pts = {p1, p2, p3, p4};
        Arrays.sort(pts);
        if (pts[0].slopeTo(pts[1]) == pts[0].slopeTo(pts[2])) {
            if (pts[0].slopeTo(pts[1]) == pts[0].slopeTo(pts[3])) {
                StdOut.println(pts[0] + " -> " + pts[1] + " -> " + pts[2] 
                                   + " -> " + pts[3]);
                pts[0].drawTo(pts[3]);
            }
        }
    }
}