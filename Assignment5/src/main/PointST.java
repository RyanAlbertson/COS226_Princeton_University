package main;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implements a left-leaning red-black binary search tree as a symbol table to store
 * cartesian coordinates (points) and the values mapped to each.
 *
 * @author Ryan Albertson
 */
public class PointST<V> {


    // Store points in a left-leaning red-black binary search tree.
    private RedBlackBST<Point2D, V> bst;


    /**
     * Constructs an empty ST.
     */
    public PointST() {

        bst = new RedBlackBST<Point2D, V>();
    }


    /**
     * Returns true if ST is empty, false otherwise.
     */
    public boolean isEmpty() {

        return bst.isEmpty();
    }


    /**
     * Returns the number of points currently stored in the ST.
     */
    public int size() {

        return bst.size();
    }


    /**
     * Adds the given key-value pair to the ST. If the key is already in the ST,
     * then its value is overwritten.
     *
     * @param p   [key] Point to be added to the ST, if it isn't currently in the ST.
     * @param val [value] Value to be mapped to the point.
     * @throws IllegalArgumentException If p is null.
     * @throws IllegalArgumentException If val is null.
     */
    public void put(Point2D p, V val) {

        if (p == null) { throw new IllegalArgumentException("p is null."); }
        if (val == null) { throw new IllegalArgumentException("val is null."); }

        bst.put(p, val);
    }


    /**
     * Returns the value mapped to the key which is the given point.
     *
     * @param p Key in the ST. It's value is returned.
     * @throws IllegalArgumentException If p is null.
     */
    public V get(Point2D p) {

        if (p == null) { throw new IllegalArgumentException("p is null."); }

        return bst.get(p);
    }


    /**
     * Returns true if given point is a key in the ST, false otherwise.
     *
     * @param p Key in the ST.
     * @throws IllegalArgumentException If p is null.
     */
    public boolean contains(Point2D p) {

        if (p == null) { throw new IllegalArgumentException("p is null."); }

        return bst.contains(p);
    }


    /**
     * Returns an iterable of all points (keys) in the ST.
     */
    public Iterable<Point2D> points() {

        return bst.keys();
    }


    /**
     * Returns an iterable of all points (keys) that are in both this ST as well as
     * the given RectHV.
     *
     * @param rect Points are compared to the range of this RectHV.
     * @throws IllegalArgumentException If rect is null.
     */
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) { throw new IllegalArgumentException("rect is null."); }

        Deque<Point2D> range = new ArrayDeque<Point2D>();

        // Iterate through all points, add each to range of they're within bounds.
        for (Point2D point : points()) {

            if (rect.contains(point)) {

                range.add(point);
            }
        }

        return range;
    }


    /**
     * Returns the nearest neighbor to the given point. Returns null if ST is empty.
     *
     * @param p All other points are compared in distance to this point.
     * @throws IllegalArgumentException If p is null.
     */
    public Point2D nearest(Point2D p) {

        // Return null if there are curently no points in the ST.
        if (isEmpty()) { return null; }

        if (p == null) { throw new IllegalArgumentException("p is null."); }

        // Track the nearest neighbor and its distance to the the given point.
        Point2D nearest = null;
        double minDist = Double.POSITIVE_INFINITY;

        // Get all points in the ST.
        Iterable<Point2D> points = points();

        // Find the nearest neighbor.
        for (Point2D point : points) {

            double currentDist = point.distanceSquaredTo(p);

            // Exclude the given point itself from comparison.
            if (currentDist == 0.0) { continue; }

            // Replace current nearest neighbor.
            if (currentDist < minDist) {

                nearest = point;
                minDist = currentDist;
            }
        }

        return nearest;
    }


    public static void main(String[] args) {

        // Unit test located at ~Tests/PointST_Test.java
    }
}
