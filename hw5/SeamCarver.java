public class SeamCarver {
    public SeamCarver(Picture picture) {

    }

    /**
     * Current picture.
     */
    public Picture picture() {
        return null;

    }

    /**
     * Width of current picture.
     */
    public int width() {
        return 0;
    }

    /**
     * Height of current picture.
     */
    public int height() {
        return 0;
    }

    /**
     * Energy of pixel at column x and row y.
     * Energy = GradX^2 + GradY^2
     */
    public double energy(int x, int y) {
        return 0.0;
    }


    /**
     * GradX^2 = Rx^2 + Gx^2 + Bx^2, where Rx, Gx and Bx are the absolute value in differences of
     * red, green and blue between pixel (x + 1, y) and pixel (x − 1, y).
     */
    // TODO



    /**
     * Sequence of indices for vertical seam.
     */
    public int[] findVerticalSeam() {
        return null;
    }



    /**
     * Sequence of indices for horizontal seam.
     */
    public int[] findHorizontalSeam() {
        return null;
    }



    /**
     * Remove horizontal seam from picture.
     */
    public void removeHorizontalSeam(int[] seam) {

    }

    /**
     * Remove vertical seam from picture.
     */
    public void removeVerticalSeam(int[] seam) {

    }
}