/* *****************************************************************************
 *  Description: Visualizes a 2d-tree implementation using the level-order
 *               travesal of a KdTreeST. Use this to check your KdTreeST
 *               implementation.
 *
 *  Notes: Handles only inputs within the unit square.
 *         Assumes input is at least 1 point.
 *         This program has not been thoroughly tested with incorrect
 *         level-order traversals!
 *
 *  If your execution hits an exception, your traversal is probably incorrect.
 *
 *  Compilation:  javac-algs4 KdTreeVisualizer.java
 *  Execution:    java-algs4 KdTreeVisualizer input.txt
 *  Dependencies: KdTreeST.java
 *
 *  Based on an implementation by Bill Zhang '19 (wyzhang).
 *  Significantly overhauled by Connor Hainje '21 (chainje) in March 2020.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTreeVisualizer {

    // Dimensions of unit square, global min/max dimensions
    private static final double MINX = 0.0;
    private static final double MAXX = 1.0;
    private static final double MINY = 0.0;
    private static final double MAXY = 1.0;

    // Enhanced Point2D with more information
    private static class PointE {
        private Point2D p;      // the original point
        private boolean vert;   // indicates vertical or horizontal
        private RectHV lb;      // left bounding box
        private RectHV rt;      // right bounding box
        private PointE left;    // pointer to left child
        private PointE right;   // pointer to right child

        // constructor initializes the pointE (leaves children null)
        private PointE(Point2D p, boolean vert, RectHV lb, RectHV rt) {
            this.p = p;
            this.vert = vert;
            this.lb = lb;
            this.rt = rt;
        }


        // makes left child from `point` and draws it
        private PointE makeLeftChild(Point2D point) {
            PointE newChild = makeChild(point, this.lb, this.vert);
            this.left = newChild;
            drawSegment(newChild, this.lb);
            return newChild;
        }

        // makes right child from `point` and draws it
        private PointE makeRightChild(Point2D point) {
            PointE newChild = makeChild(point, this.rt, this.vert);
            this.right = newChild;
            drawSegment(newChild, this.rt);
            return newChild;
        }

        // helper method to reduce redundant code in makeLeftChild and makeRightChild
        private static PointE makeChild(Point2D point, RectHV box, boolean parentVert) {
            RectHV newlb;
            RectHV newrt;

            if (parentVert) { // if vertical division
                newlb = new RectHV(box.xmin(), box.ymin(), box.xmax(), point.y());
                newrt = new RectHV(box.xmin(), point.y(), box.xmax(), box.ymax());
            }
            else { // if horizontal
                newlb = new RectHV(box.xmin(), box.ymin(), point.x(), box.ymax());
                newrt = new RectHV(point.x(), box.ymin(), box.xmax(), box.ymax());
            }

            return new PointE(point, !parentVert, newlb, newrt);
        }
    }

    // Draws a point, segment, and text with coordinates
    private static void drawSegment(PointE pointE, RectHV rect) {
        double x = pointE.p.x();
        double y = pointE.p.y();

        if (pointE.vert) {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x, rect.ymin(), x, rect.ymax()); // vertical line
        }
        else {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), y, rect.xmax(), y); // horizontal line
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        StdDraw.point(x, y);
        StdDraw.textLeft(x + 0.01, y + 0.025, "(" + x + ", " + y + ")");
    }

    // Helper method: determines if point `p` is on the right or top bound of rect `r`
    private static boolean onBoundary(Point2D p, RectHV r) {
        double x = p.x();
        double y = p.y();
        return (r.xmax() == x || r.ymax() == y);
    }

    // Helper method: validate a given point (x,y) is in the unit square
    private static void validate(double x, double y) {
        if (x <= MINX || x >= MAXX) {
            throw new IllegalArgumentException("x must be between -1 and +1");
        }
        if (y <= MINY || y >= MAXY) {
            throw new IllegalArgumentException("y must be between -1 and +1");
        }
    }

    // Reads data, constructs kdtree, uses level-order traversal to draw tree
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        // set up the StdDraw environment
        StdDraw.setCanvasSize(900, 900);
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);
        StdDraw.setFont(StdDraw.getFont().deriveFont(10.0f));
        StdDraw.enableDoubleBuffering();

        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();

        // create kd-tree
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            validate(x, y);

            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
        }

        // obtain level-order traversal and store in points[]
        Point2D[] points = new Point2D[kdtree.size()];
        int size = 0;
        for (Point2D p : kdtree.points()) {
            points[size] = p;
            size++;
        }

        // queue of parents
        Queue<PointE> queue = new Queue<PointE>();

        // handles root element, special case
        Point2D root = points[0];
        RectHV lb = new RectHV(MINX, MINY, root.x(), MAXY);
        RectHV rt = new RectHV(root.x(), MINY, MAXX, MAXY);
        PointE rootpt = new PointE(root, true, lb, rt);

        // enqueue to parent queue and draw
        queue.enqueue(rootpt);
        drawSegment(rootpt, rt);

        int current = 1; // current index

        // try to place each point
        while (current < size) {
            // get current parent (but don't dequeue yet)
            PointE parent = queue.peek();

            // find `parent`'s left child
            for (int i = current; i < size; i++) {
                Point2D p = points[i];
                if (parent.lb.contains(p) && !onBoundary(p, parent.lb)) {
                    current = i + 1;
                    queue.enqueue(parent.makeLeftChild(p));
                    break;
                }
            }
            // now, `parent`'s left child has been found OR `parent` has no left child
            // find `parent`'s right child
            for (int i = current; i < size; i++) {
                Point2D p = points[i];
                if (parent.rt.contains(p) && !onBoundary(p, parent.rt)) {
                    current = i + 1;
                    queue.enqueue(parent.makeRightChild(p));
                    break;
                }
            }
            // now, both children have been found or they do not exist
            // dequeue the current parent off the queue, start again with next parent
            queue.dequeue();
        }

        StdDraw.show();
    }
}
