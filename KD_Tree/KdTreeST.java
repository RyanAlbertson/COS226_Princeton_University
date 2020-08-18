import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * Implements a 2D binary space partitioning tree such that the tree's Nodes have
 * keys that are cartesian coordinates which have corresponding generic values.
 */
public class KdTreeST<V> {


    // Reference to the root node of the 2D tree.
    private Node root;

    // Track # of nodes currently in the 2D tree.
    private int size;


    /**
     * Implements a Node for use in the 2D array.
     */
    private class Node {


        // 2D key of this node.
        private Point2D key;

        // Value that is mapped to the key.
        private V val;

        // Rectangle that minimally encloses the tree rooted at this node.
        private RectHV rect;

        // Root node of the left subtree of this node.
        private Node left;

        // Root node of the right subtree of this node.
        private Node right;


        /**
         * Constructs a node in a 2D tree.
         *
         * @param point Cartesian point to be used as a 2D key.
         * @param val   Generic value to be mapped to the key.
         * @param rect  Rectangle defining the bounds of all points contained within
         *              the subtree of this node.
         */
        private Node(Point2D point, V val, RectHV rect) {

            if (point == null) { throw new IllegalArgumentException("point' is null."); }
            if (val == null) { throw new IllegalArgumentException("val' is null."); }
            if (rect == null) { throw new IllegalArgumentException("rect' is null."); }

            key = point;
            this.val = val;
            this.rect = rect;
        }


        /**
         * Returns this node's key (Point2D).
         */
        public Point2D getPoint() {

            return key;
        }


        /**
         * Returns this node's value.
         */
        public V getVal() {

            return val;
        }
    }


    /**
     * Contructs an empty 2D tree.
     */
    public KdTreeST() {

        size = 0;
        root = null;
    }


    /**
     * Returns true if 2D tree is currently empty, false otherwise.
     */
    public boolean isEmpty() {

        return size == 0;
    }


    /**
     * Returns the number of Nodes currently in the 2D tree.
     */
    public int size() {

        return size;
    }

    /**
     * Adds the given key-value pair to the 2D tree. If the key is already in the tree
     *
     * @param key Point to be added to the 2D tree, if it isn't already in the tree.
     * @param val Generic value to be mapped to the given 'key'.
     */
    public void put(Point2D key, V val) {

        if (key == null) { throw new IllegalArgumentException("key is null."); }
        if (val == null) { throw new IllegalArgumentException("val is null."); }

        // Construct a boundless initial rectangle.
        double negInf = Double.NEGATIVE_INFINITY;
        double posInf = Double.POSITIVE_INFINITY;
        RectHV initRect = new RectHV(negInf, negInf, posInf, posInf);

        // Construct a new node using the key-value pair.
        Node insertNode = new Node(key, val, initRect);

        // Insert node into the 2D tree and update the tree's root node, either if needed.
        root = insert(root, insertNode, initRect, 'x');

    }


    /**
     * Inserts a given node into the 2D tree, if it doesn't already exist. In which
     * case the node's value is updated. Also returns the tree's root node, in case
     * it changes as a result of the insertion.
     *
     * @param rootNode   Root node of the current subtree within the 2D tree.
     * @param insertNode Node to be inserted into the 2D tree.
     * @param rect       Minimally encloses all keys in the current subtree.
     * @param axis       Defines the orientation of 'rect'.
     * @return Root node of the 2D tree after insertion of the key-value node.
     */
    private Node insert(Node rootNode, Node insertNode, RectHV rect, char axis) {

        // Insertion spot found.
        if (rootNode == null) {

            insertNode.rect = rect;
            size++;

            return insertNode;
        }

        // x-axis or y-axis coordinates of root node and insert node.
        double rootCoord;
        double insertCoord;

        if (axis == 'x') {

            // X-coordinates of root node and insert node.
            rootCoord = rootNode.key.x();
            insertCoord = insertNode.key.x();
        }
        else {

            // Y-coordinates of root node and insert node.
            rootCoord = rootNode.key.y();
            insertCoord = insertNode.key.y();
        }

        // Flip axis orientation with each recursive step.
        char nextAxis = (axis == 'x' ? 'y' : 'x');

        // Calculated rect for the rootNode passed to each recursive call.
        RectHV nextRect;

        // Traverse left.
        if (insertCoord < rootCoord) {

            if (axis == 'x') {
                nextRect = new RectHV(rect.xmin(), rect.ymin(), rootCoord, rect.ymax());
            }
            else {
                nextRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), rootCoord);
            }

            // Recurse to the left subtree.
            rootNode.left = insert(rootNode.left, insertNode, nextRect, nextAxis);
        }
        else {

            // Traverse right.
            if (insertCoord > rootCoord) {

                if (axis == 'x') {
                    nextRect = new RectHV(rootCoord, rect.ymin(), rect.xmax(), rect.ymax());
                }
                else {
                    nextRect = new RectHV(rect.xmin(), rootCoord, rect.xmax(), rect.ymax());
                }

                // Recurse to the right subtree.
                rootNode.right = insert(rootNode.right, insertNode, nextRect, nextAxis);
            }
            else {

                // If x-coords are equal but y-coords are not.
                if (insertCoord != rootCoord) {

                    if (axis == 'x') {
                        nextRect = new RectHV(rootCoord, rect.ymin(), rect.xmax(), rect.ymax());
                    }
                    else {
                        nextRect = new RectHV(rect.xmin(), rootCoord, rect.xmax(), rect.ymax());
                    }

                    // Recurse to the right subtree.
                    rootNode.right = insert(rootNode.right, insertNode, nextRect, nextAxis);
                }

                // If both x-coords and y-coords are equal.
                else {

                    // Node already exists, so overwrite its value.
                    rootNode.val = insertNode.val;
                }
            }
        }

        // Returns root node, whether or not it has changed.
        return rootNode;
    }


    /**
     * Searches 2D tree for the given key. If found, returns the value mapped to the
     * key. Otherwise, null is returned.
     *
     * @param point Key to search 2D tree for.
     */
    public V get(Point2D point) {

        if (point == null) { throw new IllegalArgumentException("'point' is null."); }

        // Track a search node.
        Node current = root;

        // Initially allign to the x-axis.
        char axis = 'x';

        // Loop until key is either found or not.
        while (current != null) {

            // Store corresponding component of 2D key that's at the insert point.
            double insertKey;

            // Store corresponding component of 2D key that's at current search node.
            double currentKey;

            // If currently alligned to x-axis.
            if (axis == 'x') {

                insertKey = point.x();
                currentKey = current.getPoint().x();

                // Rotate axis allignment.
                axis = 'y';
            }

            // If currently alligned to y-axis.
            else {
                insertKey = point.y();
                currentKey = current.getPoint().y();

                axis = 'x';
            }

            // Traverse right.
            if (insertKey > currentKey) {

                current = current.right;
            }
            else {

                // Traverse left.
                if (insertKey < currentKey) {

                    current = current.left;
                }
                else {

                    if (insertKey == currentKey) {

                        // Check that entire 2D keys match.
                        if (point.equals(current.getPoint())) {

                            // Key found, return its value.
                            return current.getVal();
                        }

                        // Traverse right.
                        else {

                            current = current.right;
                        }
                    }
                }
            }
        }

        // Key not found.
        return null;
    }


    /**
     * Returns true if the given point is a key in the 2D tree, otherwise false.
     *
     * @param point Key to search 2D tree for.
     */
    public boolean contains(Point2D point) {

        if (point == null) { throw new IllegalArgumentException("'point' is null."); }

        return get(point) != null;
    }


    /**
     * Returns an iterable of all points (keys) in level order.
     */
    public Iterable<Point2D> points() {

        // Store needed root nodes in a deque.
        Deque<Node> nodes = new ArrayDeque<Node>();

        // Store all points in a separate deque.
        Deque<Point2D> points = new ArrayDeque<Point2D>(size());

        // First add the 2D tree's root to node deque.
        nodes.addFirst(root);

        // Iterate until no more children nodes are found.
        while (!nodes.isEmpty()) {

            // Get current node from the front of the node deque.
            Node current = nodes.pollLast();

            // Add current point to the end of the points deque.
            points.addLast(current.getPoint());

            // Check for a left child node.
            if (current.left != null) {

                nodes.addLast(current.left);
            }

            // Check for a right child node.
            if (current.right != null) {

                nodes.addLast(current.right);
            }
        }

        return points;
    }


    /**
     * Searches the 2D tree for all points that are within the given bounds. Returns
     * the points as a deque.
     *
     * @param node  Root node of the current subtree within the 2D tree.
     * @param rect  Include points within this defined bounds.
     * @param axis  Defines the orientation of 'rect'.
     * @param range Reference to a deque that stores all points found within range.
     */
    private void rangeSearch(Node node, RectHV rect, char axis, Deque<Point2D> range) {

        // No more nodes to search for.
        if (node == null) { return; }

        // Store coordinates, depending on current axis orientation.
        double nodeCoord;
        double rectMin;
        double rectMax;

        char nextAxis;

        // If rectangle is oriented with respect to the x-axis.
        if (axis == 'x') {

            nodeCoord = node.getPoint().x();
            rectMin = rect.xmin();
            rectMax = rect.xmax();
            nextAxis = 'y';
        }

        // If rectangle is oriented with respect to the y-axis.
        else {

            nodeCoord = node.getPoint().y();
            rectMin = rect.ymin();
            rectMax = rect.ymax();
            nextAxis = 'x';
        }

        // Node is greater than the upper bound.
        if (nodeCoord > rectMax) {

            // Recurse to left subtree.
            rangeSearch(node.left, rect, nextAxis, range);
        }
        else {

            // Node is less than the lower bound.
            if (nodeCoord < rectMin) {

                // Recurse to right subtree.
                rangeSearch(node.right, rect, nextAxis, range);
            }

            // Node is within bounds with respect to at least one axis.
            else {

                // Check that node is within bounds for other axis.
                if (rect.contains(node.getPoint())) {

                    // Node found.
                    range.addLast(node.getPoint());
                }

                // Recurse to both subtrees.
                rangeSearch(node.left, rect, nextAxis, range);
                rangeSearch(node.right, rect, nextAxis, range);
            }
        }
    }


    /**
     * Returns an iterable of all points (keys) that are contained within the
     * given rectangular bounds.
     *
     * @param rect Bounds to search within for points.
     */
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) { throw new IllegalArgumentException("'rect' is null"); }

        // Store points in a deque.
        Deque<Point2D> range = new ArrayDeque<Point2D>();

        // Recursively search the 2D tree for all points within the given bounds.
        rangeSearch(root, rect, 'x', range);

        return range;
    }

    /**
     * Searches the 2D tree for the estimated nearest point to the given point. Uses
     * a pruning method to limit recursive calls. Returns the nearest point. If one
     * wasn't found then it returns null.
     *
     * @param node        Current node to compare with 'point'.
     * @param point       Point that nodes are compared to.
     * @param axis        Defines the orientation of the rectangle of each node.
     * @param nearest
     * @param nearestDist
     */
    private Point2D nearestNeighborSearch(Node node, Point2D point, char axis, Point2D nearest,
                                          double nearestDist) {

        // End of search.
        if (node == null) { return nearest; }

        // Distance to current node.
        double currentDist = node.getPoint().distanceSquaredTo(point);

        // Ignore if comparing the point to itself.
        if (currentDist == 0) {
            // Ignore.
        }
        else {

            // Update if current node is new nearest neighbor.
            if (currentDist < nearestDist) {

                nearestDist = currentDist;
                nearest = node.getPoint();
            }
        }

        // Store which child node is in the same side of the rect that node is in.
        Node sameSide;
        Node otherSide;

        // The x-axis or y-axis coordinate that splits the rectangle into 2 sides.
        double rectSplit;

        // The relative coordinate of the point that we're searching around.
        double pointCoord;

        char nextAxis;

        // If rectangle is oriented with respect to the x-axis.
        if (axis == 'x') {

            rectSplit = node.getPoint().x();
            pointCoord = point.x();
            nextAxis = 'y';
        }

        // If rectangle is oriented with respect to the y-axis.
        else {

            rectSplit = node.getPoint().y();
            pointCoord = point.y();
            nextAxis = 'x';
        }

        // Determine which child node is in same side of the split rect that point is in.
        if (pointCoord < rectSplit) {

            sameSide = node.left;
            otherSide = node.right;
        }
        else {

            sameSide = node.right;
            otherSide = node.left;
        }

        // The same-side node gets priority for the search.
        if (sameSide != null) {

            // Only search if a potentially nearer point exists.
            if (sameSide.rect.distanceSquaredTo(point) < nearestDist) {

                // Recurse to this subtree.
                nearest = nearestNeighborSearch(sameSide, point, nextAxis, nearest, nearestDist);
            }
        }

        if (otherSide != null) {

            // Only search if a potentially nearer point exists.
            if (otherSide.rect.distanceSquaredTo(point) < nearestDist) {

                // Recurse to this subtree.
                nearest = nearestNeighborSearch(otherSide, point, nextAxis, nearest, nearestDist);
            }
        }

        // End of search.
        return nearest;
    }


    /**
     * Returns the estimated nearest point to the given point.
     */
    public Point2D nearest(Point2D point) {

        if (point == null) { throw new IllegalArgumentException("'point' is null"); }

        // Track current nearest neighbor and its distance.
        Point2D nearest = null;
        double nearestDist = Double.POSITIVE_INFINITY;

        // Recursively search the 2D tree for it's estimated nearest neighbor.
        return nearestNeighborSearch(root, point, 'x', nearest, nearestDist);
    }


    /**
     * Define an order for Point2D objects using each point's distance to the given
     * point. For use in the max priority queue used in nearestNeighborSearch().
     */
    class ByDistToPoint implements Comparator<Point2D> {

        // Compare points using their respective distances to this other point.
        private Point2D point;

        /**
         * Constructor.
         *
         * @param point Comparable points use a calculated distance to this point.
         */
        public ByDistToPoint(Point2D point) {

            this.point = point;
        }

        /**
         * Returns a comparison between two points using their distance to the
         * instance point.
         *
         * @param p1 First point to compare.
         * @param p2 Second point to compare.
         */
        public int compare(Point2D p1, Point2D p2) {

            double p1Dist = p1.distanceSquaredTo(point);
            double p2Dist = p2.distanceSquaredTo(point);

            return Double.compare(p1Dist, p2Dist);
        }
    }


    /**
     * Overloaded nearest() that returns an iterator of a given number of
     * nearest neighbors to the given point.
     *
     * @param point        Point to search around.
     * @param numNeighbors Max number of neighbors to return in the iterator.
     */
    public Iterable<Point2D> nearest(Point2D point, int numNeighbors) {

        if (point == null) { throw new IllegalArgumentException("'point' is null"); }

        // Get all points currently in the 2D tree.
        Iterable<Point2D> points = points();

        // Store the k-nearest neighbors in a max heap, based on dist to given point.
        PriorityQueue<Point2D> neighbors = new PriorityQueue<Point2D>(numNeighbors,
                                                                      new ByDistToPoint(point));

        // Get distance of all neighbors. Only keep the given number of nearest neighbors.
        for (Point2D potentialNeighbor : points) {

            // If max heap is full,
            if (neighbors.size() > 0 && neighbors.size() == numNeighbors) {

                // Add current point if it's less than the max of the heap.
                double neighborsMaxDist = neighbors.peek().distanceSquaredTo(point);
                double potientialNeighborDist = potentialNeighbor.distanceSquaredTo(point);
                if (potientialNeighborDist < neighborsMaxDist) {

                    neighbors.poll();
                    neighbors.add(potentialNeighbor);
                }
            }
            else {

                neighbors.add(potentialNeighbor);
            }
        }

        return neighbors;
    }


    public static void main(String[] args) {


    }
}
