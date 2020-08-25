package main;

import edu.princeton.cs.algs4.Picture;

/**
 *
 */
public class SeamCarver {

    // Define pixel energy at the border to be greater than all interior pixels.
    private static final double BORDER_ENERGY = Double.POSITIVE_INFINITY;

    // Define string literals used for RGB calculations.
    private static final String R = "red";
    private static final String G = "green";
    private static final String B = "blue";

    // Store pixel RGB values of current picture.
    private int[][] rgb;

    // Store pixel energies of the current picture.
    private double[][] energy;

    // Store current width and height of picture.
    private int width;
    private int height;

    // True if current picture is transposed, false otherwise.
    private boolean isTransposed;


    /**
     * Constructs a {@code SeamCarver} using the given {@link Picture}.
     *
     * @param picture A {@link Picture}.
     * @throws IllegalArgumentException If {@code picture} is null.
     */
    public SeamCarver(Picture picture) {

        if (picture == null) throw new IllegalArgumentException("'picture' is null.");

        width  = picture.width();
        height = picture.height();
        rgb    = new int[width()][height()];
        energy = new double[width()][height()];

        // Get RGB values of each pixel.
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                rgb[col][row] = picture.getRGB(col, row);
            }
        }
        // Calculate energy of each pixel using RGB values.
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                energy[col][row] = calcPixelEnergy(row, col);
            }
        }
    }


    /**
     * Calculates the energy of a given pixel using the dual-gradient energy function.
     *
     * @param col x-coordinate of given pixel.
     * @param row y-coordinate of given pixel.
     * @return Energy value of the given pixel.
     */
    private double calcPixelEnergy(int col, int row) {

        // Assign distinct energy value to border pixels.
        if (col == 0 || row == 0 || col == width() - 1 || row == height() - 1) {
            return BORDER_ENERGY;
        }

        return Math.sqrt(
            Math.pow(getRGB(col + 1, row, R) - getRGB(col - 1, row, R), 2)
                + Math.pow(getRGB(col + 1, row, G) - getRGB(col - 1, row, G), 2)
                + Math.pow(getRGB(col + 1, row, B) - getRGB(col - 1, row, B), 2)
                + Math.pow(getRGB(col, row + 1, R) - getRGB(col, row - 1, R), 2)
                + Math.pow(getRGB(col, row + 1, G) - getRGB(col, row - 1, G), 2)
                + Math.pow(getRGB(col, row + 1, B) - getRGB(col, row - 1, B), 2));
    }


    /**
     * Uses binary arithmetic to get the red component of an RGB value.
     *
     * @param col       x-coordinate of given pixel.
     * @param row       y-coordinate of given pixel.
     * @param component The red, green, or blue component of an RGB value.
     * @return The {@code component} of an RGB value.
     * @throws IllegalArgumentException If invalid {@code component} is given.
     */
    private int getRGB(int col, int row, String component) {

        if (component.equals(R)) return (rgb[col][row] >> 16) & 0xFF;
        if (component.equals(G)) return (rgb[col][row] >> 8) & 0xFF;
        if (component.equals(B)) return rgb[col][row] & 0xFF;

        throw new IllegalArgumentException("invalid RGB component");
    }


    /**
     * Builds and returns a {@link Picture} of the current picture.
     */
    public Picture picture() {

        //CHECK IF TRANSPOSED

        Picture picture = new Picture(width, height);

        // Fill in picture using current RGB values.
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height; row++) {
                picture.setRGB(col, row, rgb[row][col]);
            }
        }
        return picture;
    }


    /**
     * Returns current {@code width} of the picture.
     */
    public int width() {

        return width;
    }


    /**
     * Returns current {@code height} of the picture.
     */
    public int height() {

        return height;
    }


    /**
     * Returns the energy at the given pixel.
     *
     * @param col x-coordinate of given pixel.
     * @param row y-coordinate of given pixel.
     * @throws IllegalArgumentException If {@code col} or {@code row}
     *                                  is out of bounds.
     */
    public double energy(int col, int row) {

        //CHECK IF TRANSPOSED

        validate(col, row);
        return energy[col][row];

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
     * Checks that given pixel coordinates are within bounds of the current picture.
     *
     * @param col x-coordinate of given pixel.
     * @param row y-coordinate of given pixel.
     */
    private void validate(int col, int row) {

        if (col < 0 || col >= width) throw new IllegalArgumentException("col out of bounds");
        if (row < 0 || row >= height) throw new IllegalArgumentException("row out of bounds");
    }


    /**
     * Checks that given seam is non-null, and a valid seam.
     *
     * @param seam Sequence of indices representing a seam that's relative to an axis.
     */
    private void validate(int[] seam) {


    }


    /**
     * Unit test.
     */
    public static void main(String[] args) {

    }
}
