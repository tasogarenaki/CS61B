import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        final int R = 256;
        int max = Integer.MIN_VALUE;
        String[] aux = new String[R];

        // return an empty array for an empty input
        if (asciis.length == 0) {
            return new String[0];
        }

        // find the maximum string length in the array
        for (String s : asciis) {
            max = max > s.length() ? max : s.length();
        }

        // pad all strings to the same length by adding leading zeros
        for (int i = 0; i < asciis.length; i++) {
            while (asciis[i].length() < max) {
                asciis[i] = "0" + asciis[i];
            }
        }

        for (int position = max - 1; position >= 0;  position--) {
            int[] count = new int[R];

            // gather all the counts for each valid value
            for (String s : asciis) {
                int item = (position < s.length()) ? s.charAt(position) : 0;
                count[item]++;
            }

            // calculate start positions
            int[] starts = new int[R];
            int pos = 0;
            for (int i = 0; i < R; i++) {
                starts[i] = pos;
                pos += count[i];
            }

            // Build the sorted array
            for (int i = 0; i < asciis.length; i++) {
                int item = (position < asciis[i].length()) ? asciis[i].charAt(position) : 0;
                int place = starts[item];
                aux[place] = asciis[i];
                starts[item]++;
            }

            System.arraycopy(aux, 0, asciis, 0, asciis.length);
        }

        // remove leading zeros from the sorted strings
        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = asciis[i].replaceFirst("^0+", "");
        }

        return asciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }


    public static void main(String[] args) {
        // Test case 1: Basic test with ASCII strings
        String[] testArray1 = {"abc", "ab", "aba", "ae", "ac", "aa"};
        System.out.println("Original Array: " + Arrays.toString(testArray1));
        String[] sortedArray1 = RadixSort.sort(testArray1);
        System.out.println("Sorted Array:   " + Arrays.toString(sortedArray1));
        System.out.println();

        // Test case 2: Test with empty array
        String[] testArray2 = {};
        System.out.println("Original Array: " + Arrays.toString(testArray2));
        String[] sortedArray2 = RadixSort.sort(testArray2);
        System.out.println("Sorted Array:   " + Arrays.toString(sortedArray2));
        System.out.println();

        // Test case 3: Test with single-element array
        String[] testArray3 = {"b", "c", "f", "e", "d", "a"};
        System.out.println("Original Array: " + Arrays.toString(testArray3));
        String[] sortedArray3 = RadixSort.sort(testArray3);
        System.out.println("Sorted Array:   " + Arrays.toString(sortedArray3));
        System.out.println();

        // Test case 4: Test with numbers
        String[] unsorted4 = {"170", "90", "802", "2", "24", "45", "75", "66"};
        System.out.println("Original Array (Test Case 4): " + Arrays.toString(unsorted4));
        String[] sorted4 = RadixSort.sort(unsorted4);
        System.out.println("Sorted Array (Test Case 4):   " + Arrays.toString(sorted4));
        System.out.println();

        // Test case 5: Test with more numbers
        String[] unsorted5 = {"4356", "112", "904", "294", "209", "820", "394", "810"};
        System.out.println("Original Array (Test Case 5): " + Arrays.toString(unsorted5));
        String[] sorted5 = RadixSort.sort(unsorted5);
        System.out.println("Sorted Array (Test Case 5):   " + Arrays.toString(sorted5));
        System.out.println();
    }
}
