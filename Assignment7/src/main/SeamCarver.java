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

    // Used for vertical methods calls from a horizontal method.
    private boolean keepTranspose;


    /**
     * Constructs a {@code SeamCarver} using the given {@link Picture}.
     *
     * @param picture A {@link Picture}.
     * @throws IllegalArgumentException If {@code picture} is null.
     */
    public SeamCarver(Picture picture) {

        if (picture == null) throw new IllegalArgumentException("picture is null.");

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
                energy[col][row] = calcPixelEnergy(col, row);
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

        /* Surrounding pixels used to calculate energy of given pixel. For border
         * pixels, use opposite column or row instead. */
        int right = (col == width - 1) ? 0 : col + 1;
        int left  = (col == 0) ? width - 1 : col - 1;
        int up    = (row == 0) ? height - 1 : row - 1;
        int down  = (row == height - 1) ? 0 : row + 1;

        return Math.sqrt(
            Math.pow(getRGB(right, row, R) - getRGB(left, row, R), 2)
                + Math.pow(getRGB(right, row, G) - getRGB(left, row, G), 2)
                + Math.pow(getRGB(right, row, B) - getRGB(left, row, B), 2)
                + Math.pow(getRGB(col, up, R) - getRGB(col, down, R), 2)
                + Math.pow(getRGB(col, up, G) - getRGB(col, down, G), 2)
                + Math.pow(getRGB(col, up, B) - getRGB(col, down, B), 2));
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

        // if (isTransposed) {
        //     int temp = col;
        //     col = row;
        //     row = temp;
        // }

        if (component.equals(R)) return (rgb[col][row] >> 16) & 0xFF;
        if (component.equals(G)) return (rgb[col][row] >> 8) & 0xFF;
        if (component.equals(B)) return rgb[col][row] & 0xFF;

        throw new IllegalArgumentException("invalid RGB component");
    }


    /**
     * Builds and returns a {@link Picture} of the current picture.
     */
    public Picture picture() {

        // if (isTransposed) transpose();

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
     * Transposes the current picture.
     */
    private void transpose() {

        // Make a copy of current orientation of rgb and energy arrays.
        int[][] rgbCopy = new int[width][height];
        for (int col = 0; col < width; col++) {
            rgbCopy[col] = rgb[col].clone();
        }
        double[][] energyCopy = new double[width][height];
        for (int col = 0; col < width; col++) {
            energyCopy[col] = energy[col].clone();
        }

        // Swap axes.
        int temp = width;
        width  = height;
        height = temp;

        // Re-init arrays with swapped dimensions.
        rgb    = new int[width][height];
        energy = new double[width][height];

        // Swap individual pixels in rgb and energy arrays.
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                rgb[col][row]    = rgbCopy[row][col];
                energy[col][row] = energyCopy[row][col];
            }
        }

        // isTransposed = !isTransposed;
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

        validate(col, row);
        return energy[col][row];
    }


    /**
     * Returns a least-energy horizontal seam through the picture.
     */
    public int[] findHorizontalSeam() {

        // if (!isTransposed) transpose();
        // keepTranspose = true;
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }


    /**
     * Returns a least-energy vertical seam through the picture. Can also find a
     * least-energy horizontal seam if picture is transposed beforehand.
     */
    public int[] findVerticalSeam() {

        // Only keep tranposition if findHorizontalSeam() has called this.
        // if (!keepTranspose && isTransposed) transpose();

        int[] seam = new int[height()];

        // Tracks columns of least-energy paths.
        int[][] edgeTo = new int[width()][height()];

        // Store cumulative energy of the seams that goes through each pixel.
        double[][] distTo = new double[width()][height()];

        // Initialize all distances to infinity, except top row.
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                if (row == 0) distTo[col][row] = energy[col][row];
                else distTo[col][row] = Double.POSITIVE_INFINITY;
            }
        }

        // Builds least-energy paths through picture.
        for (int row = 1; row < height(); row++) {
            for (int col = 0; col < width(); col++) {

                //
                if (col > 0 && distTo[col - 1][row] > (energy[col - 1][row]
                    + distTo[col][row - 1])) {

                    distTo[col - 1][row] = energy[col - 1][row] + distTo[col][row - 1];
                    edgeTo[col - 1][row] = col;
                }
                //
                if (distTo[col][row] > (energy[col][row] + distTo[col][row - 1])) {

                    distTo[col][row] = energy[col][row] + distTo[col][row - 1];
                    edgeTo[col][row] = col;
                }
                //
                if (col < width() - 1 && distTo[col + 1][row] > (energy[col + 1][row]
                    + distTo[col][row - 1])) {

                    distTo[col + 1][row] = energy[col + 1][row] + distTo[col][row - 1];
                    edgeTo[col + 1][row] = col;
                }
            }
        }
        // Find the end of the least total energy seam.
        double leastEnergy = Double.POSITIVE_INFINITY;
        for (int col = 0; col < width() - 1; col++) {
            double currentEnergy = distTo[col][height() - 1];
            if (currentEnergy < leastEnergy) {
                leastEnergy        = currentEnergy;
                seam[height() - 1] = col;
            }
        }
        // Back-track from end of seam to beginning.
        for (int row = height() - 1; row > 0; row--) {
            seam[row - 1] = edgeTo[seam[row]][row];
        }
        // // Follow set at border of test examples
        // // helpful to reset energy when shifting array elements
        // // cutoff to be 0
        // seam[height() - 1] = Math.max(seam[height() - 2] - 1, 0);

        return seam;
    }


    /**
     * Removes the given seam from the picture.
     *
     * @param seam Array of column indices representing a horizontal seam.
     */
    public void removeHorizontalSeam(int[] seam) {

        // Transpose picture for use in the vertically oriented method.
        // if (!isTransposed) transpose();
        transpose();
        // keepTranspose = true;
        removeVerticalSeam(seam);
        transpose();
    }


    /**
     * Removes the given seam from the picture.
     *
     * @param seam Array of column indices representing a seam.
     */
    public void removeVerticalSeam(int[] seam) {

        validate(seam);

        // Only keep transposition if horizontal method has called this.
        // if (!keepTranspose && isTransposed) transpose();

        // Remove the seam. For pixels to the right of the seam, slide left by 1.
        for (int row = 0; row < height(); row++) {

            // Disregard right border pixel, width-- will ignore it.
            if (!(seam[row] == width() - 1)) {
                //I THINK WIDTH AND HEIGHT ARE BEING MISREFERENCED HERE
                System.arraycopy(rgb[row], seam[row] + 1, rgb[row], seam[row],
                                 width() - 1 - seam[row]);
            }
        }
        width--;

        // Update energy for pixels adjacent to the removed seam.
        for (int row = 1; row < height - 1; row++) {
            if (seam[row] > 1) energy[seam[row] - 1][row] = calcPixelEnergy(seam[row] - 1, row);
            if (seam[row] < width - 1) energy[seam[row]][row] = calcPixelEnergy(seam[row], row);
        }
    }


    /**
     * Checks that given pixel coordinates are within bounds of the current picture.
     *
     * @param col x-coordinate of given pixel.
     * @param row y-coordinate of given pixel.
     * @throws IllegalArgumentException If either {@code col} or {@code row} is
     *                                  out of bounds.
     */
    private void validate(int col, int row) {

        // if (isTransposed) {
        //     int temp = col;
        //     col = row;
        //     row = temp;
        // }

        if (col < 0 || col >= width) throw new IllegalArgumentException("col out of bounds");
        if (row < 0 || row >= height) throw new IllegalArgumentException("row out of bounds");
    }


    /**
     * Checks that given seam is non-null, and a valid seam.
     *
     * @param seam Sequence of indices representing a seam that's relative to an axis.
     * @throws IllegalArgumentException If {@code seam} is null, out of bounds, or
     *                                  deviates by more than 1 column or row.
     */
    private void validate(int[] seam) {

        if (seam == null) throw new IllegalArgumentException("seam is null");

        // if (isTransposed) {
        //     if (seam.length > width) {
        //         throw new IllegalArgumentException("seam is out of bounds");
        //     }
        // }
        // else if (seam.length > height) {
        //     throw new IllegalArgumentException("seam is out of bounds");
        // }

        for (int i = 0; i < seam.length; i++) {
            validate(seam[i], i);
            if (i > 0 && Math.abs(seam[i] - (seam[i] - 1)) > 1) {
                throw new IllegalArgumentException("invalid seam");
            }
        }
    }


    /**
     * Unit test.
     */
    public static void main(String[] args) {

    }
}
