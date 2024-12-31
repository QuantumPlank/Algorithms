import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    SET<Point2D> points;
    public PointSET() {
        points = new SET<>();
    }
    public boolean isEmpty() {
        return points.isEmpty();
    }
    public int size() {
        return points.size();
    }
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null Argument");
        }
        points.add(p);
    }
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null Argument");
        }
        return points.contains(p);
    }
    public void draw() {
        for (Point2D point: points) {
            point.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("Null Argument");
        }
        Queue<Point2D> range = new Queue<>();
        for (Point2D point: points) {
            if (rect.contains(point)) {
                range.enqueue(point);
            }
        }
        return range;
    }
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null Argument");
        }
        Point2D nearest = null;
        for (Point2D point: points) {
            if (nearest == null || p.distanceTo(nearest) > p.distanceTo(point)) {
                nearest = point;
            }
        }
        return nearest;
    }
 
    public static void main(String[] args) {

    }
 }