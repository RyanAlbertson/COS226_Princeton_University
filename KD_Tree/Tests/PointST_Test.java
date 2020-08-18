import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PointST_Test {

    private Point2D point1;
    private Point2D point2;
    private Point2D point3;
    private Point2D point4;

    private PointST<String> st1;
    private PointST<Integer> st2;

    private RectHV rect1;
    private RectHV rect2;

    @Before
    public void setUp() throws Exception {

        point1 = new Point2D(0, 0);
        point2 = new Point2D(1, 2);
        point3 = new Point2D(2, 2);
        point4 = new Point2D(99, 100);

        st1 = new PointST<String>();
        st2 = new PointST<Integer>();

        st1.put(point1, "val1");
        st1.put(point2, "val2");
        st1.put(point4, "val4");
        st1.put(point3, "val3");

        rect1 = new RectHV(-1, -1, 4, 5);
        rect2 = new RectHV(1, 1, 1, 1);
    }


    @Test
    public void test_isEmpty() {

        assertFalse(st1.isEmpty());
        assertTrue(st2.isEmpty());
    }


    @Test
    public void test_size() {

        int expectedSize1 = 4;
        assertEquals(st1.size(), expectedSize1);

        int expectedSize2 = 0;
        assertEquals(st2.size(), expectedSize2);
    }


    @Test
    public void test_put() {

        String expected1 = "val1";
        assertEquals(st1.get(point1), expected1);

        st1.put(point1, "val44");
        String expected2 = "val44";
        assertEquals(st1.get(point1), expected2);

        // Iterable<Point2D> points = st1.points();
        // points.forEach(System.out::println);
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_put_IllegalArgument_p() {

        st1.put(null, "val");
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_put_IllegalArgument_val() {

        st1.put(point1, null);
    }


    @Test
    public void test_get() {

        String expected = "val2";

        assertEquals(st1.get(point2), expected);
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_get_IllegalArgument() {

        st1.get(null);
    }


    @Test
    public void test_contains() {

        assertTrue(st1.contains(point1));

        Point2D point5 = new Point2D(99, 99);

        assertFalse(st1.contains(point5));
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_contains_IllegalArgument() {

        st1.contains(null);
    }


    @Test
    public void test_points() {

        ArrayList<Point2D> expectedPoints = new ArrayList<Point2D>();
        expectedPoints.add(point1);
        expectedPoints.add(point2);
        expectedPoints.add(point3);
        expectedPoints.add(point4);

        Iterable<Point2D> points = st1.points();

        ArrayList<Point2D> actualPoints = new ArrayList<Point2D>();
        points.forEach(actualPoints::add);

        assertEquals(actualPoints, expectedPoints);
    }


    @Test
    public void test_range() {

        ArrayList<Point2D> expectedPoints = new ArrayList<Point2D>(3);
        expectedPoints.add(point1);
        expectedPoints.add(point2);
        expectedPoints.add(point3);

        Iterable<Point2D> points = st1.range(rect1);
        Iterable<Point2D> points2 = st1.range(rect2);

        ArrayList<Point2D> actualPoints = new ArrayList<Point2D>(3);
        points.forEach(actualPoints::add);
        assertEquals(actualPoints, expectedPoints);

        ArrayList<Point2D> actualPoints2 = new ArrayList<Point2D>(3);
        points2.forEach(actualPoints2::add);
        assertEquals(actualPoints2, new ArrayList<Point2D>());
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_range_IllegalArgument() {

        st1.range(null);
    }


    @Test
    public void test_nearest() {

        Point2D expected = point3;

        assertEquals(st1.nearest(point4), expected);
    }
    

    @Test(expected = IllegalArgumentException.class)
    public void test_nearest_IllegalArgument() {

        st1.nearest(null);
    }
}
