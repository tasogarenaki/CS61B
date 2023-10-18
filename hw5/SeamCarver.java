import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

/**
 * SeamCarver
 *
 * @author Terry
 */
public class SeamCarver {

    private Picture picture;
    private double[][] energies;    // cost of minimum cost path ending at (i, j)

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    /**
     * Current picture.
     */
    public Picture picture() {
        return new Picture(picture);
    }

    /**
     * Width of current picture.
     */
    public int width() {
        return picture.width();
    }

    /**
     * Height of current picture.
     */
    public int height() {
        return picture.height();
    }

    /**
     * Energy of pixel at column x and row y.
     * Energy = GradX^2 + GradY^2
     */
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new IndexOutOfBoundsException("Pixel is out of bounds.");
        }

        double gradX = calculateGradX(x, y);
        double gradY = calculateGradY(x, y);

        return gradX + gradY;
    }

    /**
     * GradX^2 = Rx^2 + Gx^2 + Bx^2, where Rx, Gx and Bx are the absolute value in differences of
     * red, green and blue between pixel (x + 1, y) and pixel (x − 1, y).
     */
    private double calculateGradX(int x, int y) {
        int decrX = (x - 1 + width()) % width();
        int inrX = (x + 1 + width()) % width();
        Color decrColor = picture.get(decrX, y);
        Color inrColor = picture.get(inrX, y);

        int deltaRed = inrColor.getRed() - decrColor.getRed();
        int deltaGreen = inrColor.getGreen() - decrColor.getGreen();
        int deltaBlue = inrColor.getBlue() - decrColor.getBlue();

        return Math.pow(deltaRed, 2) + Math.pow(deltaGreen, 2) + Math.pow(deltaBlue, 2);
    }

    private double calculateGradY(int x, int y) {
        int decrY = (y - 1 + height()) % height();
        int inrY = (y + 1 + height()) % height();
        Color decrColor = picture.get(x, decrY);
        Color inrColor = picture.get(x, inrY);

        int deltaRed = inrColor.getRed() - decrColor.getRed();
        int deltaGreen = inrColor.getGreen() - decrColor.getGreen();
        int deltaBlue = inrColor.getBlue() - decrColor.getBlue();

        return Math.pow(deltaRed, 2) + Math.pow(deltaGreen, 2) + Math.pow(deltaBlue, 2);
    }

    /**
     * Sequence of indices for vertical seam.
     *
     * M: energies - cost of minimum cost path ending at (i, j)
     * e: energy - energy cost of pixel at location (i, j)
     */
    public int[] findVerticalSeam() {
        energies = new double[width()][height()];
        int[][] path = new int[width()][height()-1];
        int[] verticalSeam = new int[height()];

        /* M(x, 0) = e(x, 0) */
        for (int x = 0; x < width(); x++) {
            energies[x][0] = energy(x, 0);
        }

        /* M(x, y) = e(x, y) + min(M(x - 1, y - 1), M(x, y - 1), M( x + 1, y - 1) */
        for (int y = 1; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                int index = findMinIndex(x, y);
                energies[x][y] = energy(x, y) + energies[index][y - 1];
                path[x][y - 1] = index;
            }
        }

        /* Find the minimum energy seam by tracing back from the bottom row to the top. */
        int minIndex = 0;
        // last row
        for (int x = 1; x < width(); x++) {
            if (energies[x][height() - 1] < energies[minIndex][height() - 1]) {
                minIndex = x;
            }
        }
        verticalSeam[height() - 1] = minIndex;
        // start from the second-to-last row
        for (int y = height() - 2; y >= 0; y--) {
            minIndex = path[minIndex][y];
            verticalSeam[y] = minIndex;
        }

        return verticalSeam;
    }

    /**
     * Sequence of indices for horizontal seam.
     */
    public int[] findHorizontalSeam() {
        /* Transpose the image and find the vertical seam. */
        transpose();
        int[] horizonSeam = findVerticalSeam();

        /* Transpose the image back. */
        transpose();

        return horizonSeam;
    }

    /**
     * Finds the index of the minimum energy candidate in the specified row (y) for a given x-coordinate.
     */
    private int findMinIndex(int x, int y) {
        int[] candidates = {x, x - 1, x + 1};
        int index = 0;

        if (width() == 1) {
            return 0;
        }

        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i] >= 0 && candidates[i] < width()
                    && energies[candidates[i]][y - 1] < energies[candidates[index]][y - 1]) {
                index = i;
            }
        }

        return candidates[index];
    }

    /**
     * Helper method to transpose the image (swap width and height).
     */
    private void transpose() {
        Picture newPicture = new Picture(height(), width());

        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                Color c = picture.get(x, y);
                newPicture.set(y, x, c);
            }
        }

        picture = newPicture;
    }

    /**
     * Remove horizontal seam from picture.
     */
    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
    }

    /**
     * Remove vertical seam from picture.
     */
    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, seam);
    }
}
