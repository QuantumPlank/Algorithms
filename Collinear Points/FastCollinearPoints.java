import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null argument");
        }
        int numberOfPoints = points.length;
        for (int i = 0; i < numberOfPoints; i++) {
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
        ArrayList<LineSegment> lineSegmentList = new ArrayList<LineSegment>();
        for (int startingPointIndex = 0; startingPointIndex < numberOfPoints; startingPointIndex++) {
            Arrays.sort(sortedPoints, points[startingPointIndex].slopeOrder());
            int candidateIndex = 0;
            while (candidateIndex < numberOfPoints) {
                double candidateSlope = points[startingPointIndex].slopeTo(sortedPoints[candidateIndex]);
                int collinearPoints = 1;
                int farthestPoint = candidateIndex;
                for (int searcher = candidateIndex; searcher < numberOfPoints; searcher++) {
                    if (candidateSlope == points[startingPointIndex].slopeTo(sortedPoints[searcher])) {
                        collinearPoints++;
                        int comparison = sortedPoints[farthestPoint].compareTo(sortedPoints[searcher]);
                        if (comparison <= 0) {
                            farthestPoint = searcher;
                        }
                        else if (comparison > 0) {
                            collinearPoints = 0;
                            break;
                        }
                    }
                    else {
                        candidateIndex = searcher - 1;
                        break;
                    }
                }
                if (collinearPoints > 3) {
                    LineSegment newSegment = new LineSegment(points[startingPointIndex], sortedPoints[farthestPoint]);
                    lineSegmentList.add(newSegment);
                }
                candidateIndex++;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }