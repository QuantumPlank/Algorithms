import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null argument");
        }
        int numberOfPoints = points.length;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Null point");
            }
            for (int j = 0; j < i; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Repeated point");
                }
            }
        }
        Point[] sortedPoints = Arrays.copyOf(points, numberOfPoints);
        Arrays.sort(sortedPoints);
        ArrayList<LineSegment> lineSegmentList = new ArrayList<LineSegment>();
        for (int i = 0; i < sortedPoints.length - 3; i++) {
            for (int candidateIndex = i + 1; candidateIndex < sortedPoints.length - 1; candidateIndex++) {
                double candidateSlope = sortedPoints[i].slopeTo(sortedPoints[candidateIndex]);
                int collinearPoints = 2;
                for (int searcher = candidateIndex + 1; searcher < sortedPoints.length; searcher++) {
                    if (sortedPoints[i].slopeTo(sortedPoints[searcher]) == candidateSlope) {
                        collinearPoints++;
                        if (collinearPoints == 4) {
                            LineSegment newSegment = new LineSegment(sortedPoints[i], sortedPoints[searcher]);
                            lineSegmentList.add(newSegment);
                        }
                    }
                }
            }
        }
        lineSegments = lineSegmentList.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}