import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class KdTree {
    private Node root;
    private final int dimensions;
    private int size;
    private final int minxSize = 0;
    private final int minySize = 0;
    private final int maxxSize = 1;
    private final int maxySize = 1;

    private class Node{
        private final Point2D point;
        private Node left;
        private Node right;
        
        public Node(Point2D point) {
            this.point = point;
            left = null;
            right = null;
        }
    }

    private boolean isLeftSearch(int depth, Point2D pointA, Point2D pointB) {
        if (depth % dimensions == 0) {
            // Vertical division
            if (pointA.x() < pointB.x()) {
                return true;
            }
        }
        else {
            // Horizontal division
            if (pointA.y() < pointB.y()) {
                return true;
            }
        }
        return false;
    }

    private Node insert(Node node, Point2D point, int depth) {
        if (node == null) {
            return new Node(point);
        }
        if (isLeftSearch(depth, node.point, point)) {
            node.left = insert(node.left, point, depth + 1);
        }
        else {
            node.right = insert(node.right, point, depth + 1);
        }

        return node;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null Argument");
        }
        if (!contains(p)) {
            root = insert(root, p, 0);
            size++;
        }
    }
    
    private boolean contains(Node node, Point2D point, int depth) {
        if (node == null) {
            return false;
        }
        if (node.point.equals(point)) {
            return true;
        }
        if (isLeftSearch(depth, node.point, point)) {
            return contains(node.left, point, depth + 1);
        }
        else {
            return contains(node.right, point, depth + 1);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null Argument");
        }
        return contains(root, p, 0);
    }

    public void draw(Node node, int depth) {
        if (node == null) {
            return;
        }
        
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        node.point.draw();

        StdDraw.setPenRadius();
        if (depth % dimensions == 0){
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), minySize, node.point.x(), minySize);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(minxSize, node.point.y(), maxxSize, node.point.y());
        }
        
        draw(node.left, depth + 1);
        draw(node.right, depth + 1);
    }

    public void draw() {
        draw(root, 0);
    }
    private void range(Queue<Point2D> range, Node node, RectHV rect, int depth) {
        if (node == null) {
            return;
        }
        
        if (rect.contains(node.point)) {
            range.enqueue(node.point);
        }

        if (depth % dimensions == 0) {
            if (node.point.x() > rect.xmin()) {
                range(range, node.left, rect, depth + 1);
            }
            if (node.point.x() <= rect.xmax()) {
                range(range, node.right, rect, depth + 1);
            }
        }
        else {
            if (node.point.y() > rect.ymin()) {
                range(range, node.left, rect, depth + 1);
            }
            if (node.point.y() <= rect.ymax()) {
                range(range, node.right, rect, depth + 1);
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("Null Argument");
        }
        Queue<Point2D> range = new Queue<>();
        range(range, root, rect, 0);
        return range;
    }

    // private void nearest(Node node, Point2D point, int depth, Point2D nearest) {
    //     if (node == null) {
    //         return;
    //     }

    //     if (nearest == null || node.point.distanceTo(point) < nearest.distanceTo(point)) {
    //         nearest = node.point;
    //         StdOut.print("CHamp!");
    //     }
    //     if (isLeftSearch(depth, node.point, point)) {
    //         if (node.point.x() > point.x()) {
    //             nearest(node.left, point, depth + 1, nearest);
    //             nearest(node.right, point, depth + 1, nearest);
    //         } else {
    //             nearest(node.right, point, depth + 1, nearest);
    //             nearest(node.left, point, depth + 1, nearest);
    //         }
    //     } else {
    //         if (node.point.y() > point.y()) {
    //             nearest(node.left, point, depth + 1, nearest);
    //             nearest(node.right, point, depth + 1, nearest);
    //         } else {
    //             nearest(node.right, point, depth + 1, nearest);
    //             nearest(node.left, point, depth + 1, nearest);
    //         }
    //     }
    // }
    // public Point2D nearest(Point2D p) {
    //     if (p == null) {
    //         throw new NullPointerException("Null Argument");
    //     }
    //     if (isEmpty()) {
    //         return null;
    //     }
    //     Point2D nearest = null;
    //     nearest(root, p, 0, nearest);
    //     StdOut.print("Is champ? ");
    //     StdOut.print(String.valueOf(nearest.x()));
    //     return nearest;
    // }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point");
        if (size == 0) return null;

        Point2D re = null;
        Node cur;
        Stack<Node> s = new Stack<Node>();
        double curDistance;
        double smallestDistance = Integer.MAX_VALUE;
        s.push(root);
        while (!s.isEmpty()) {
            cur = s.pop();
            curDistance = p.distanceTo(cur.point);    
            if (curDistance < smallestDistance) {
                smallestDistance = curDistance;
                re = cur.point;
            }
            if (cur.left != null) {
                s.push(cur.left);
            }
            if (cur.right != null) {
                s.push(cur.right);
            }
        }
        return re;
    }

    public KdTree() {
        size = 0;
        dimensions = 2;
        root = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
 
    public static void main(String[] args) {

    }
 }