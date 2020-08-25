package main;

import edu.princeton.cs.algs4.Picture;

/**
 *
 */
public class SeamCarver {

    // Define pixel energy at the border to be greater than all interior pixels.
    private static final double BORDER_ENERGY = Double.POSITIVE_INFINITY;

    // Store pixel RGB values of the given picture.
    private int[] rgb;

    // Store pixel energies of the given picture.
    private double[] energy;


    /**
     * Constructs a {@code SeamCarver} using the given {@link Picture}.
     *
     * @param picture A {@link Picture}.
     */
    public SeamCarver(Picture picture) {

        if (picture == null) throw new IllegalArgumentException("'picture' is null.");


    }


    /**
     * @param col
     * @param row
     * @return
     */
    private int flatten2DIndex(int col, int row) {


    }


    /**
     * @param col
     * @param row
     * @return
     */
    private double calcPixelEnergy(int col, int row) {


    }


    /**
     * @param col
     * @param row
     * @return
     */
    private int getRed(int col, int row) {


    }


    /**
     * @param col
     * @param row
     * @return
     */
    private int getGreen(int col, int row) {


    }


    /**
     * @param col
     * @param row
     * @return
     */
    private int getBlue(int col, int row) {


    }


    // current picture
    public Picture picture() {


    }


    // width of current picture
    public int width() {


    }


    // height of current picture
    public int height() {


    }


    // energy of pixel at column x and row y
    public double energy(int x, int y) {


    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {


    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {


    }


    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {


    }


    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {


    }


    /**
     * Unit test.
     */
    public static void main(String[] args) {

    }
}
