package utils;/* *****************************************************************************
 *  Compilation:  javac-algs4 src.utils.ShowEnergy.java
 *  Execution:    java-algs4 src.utils.ShowEnergy input.png
 *  Dependencies: src.main.SeamCarver.java src.utils.SCUtility.java
 *
 *  Read image from file specified as command-line argument. Show original
 *  image (useful only if image is large enough).
 *
 *  % java-algs4 src.utils.ShowEnergy HJoceanSmall.png
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import main.SeamCarver;

public class ShowEnergy {

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("%d-by-%d image\n", picture.width(), picture.height());
        picture.show();
        SeamCarver sc = new SeamCarver(picture);

        StdOut.printf("Displaying energy calculated for each pixel.\n");
        SCUtility.showEnergy(sc);

    }

}
