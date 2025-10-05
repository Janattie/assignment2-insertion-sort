package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;



public class InsertionSortTest {

    private int[] runSort(int[] input, boolean binary) {
        PerformanceTracker tracker = new PerformanceTracker();
        InsertionSort sorter = new InsertionSort(tracker, binary);
        return sorter.sort(input);
    }

    @Test
    void handlesEmptyArray() {
        int[] res = runSort(new int[]{}, true);
        assertArrayEquals(new int[]{}, res);
    }

    @Test
    void handlesSingleElement() {
        int[] res = runSort(new int[]{42}, false);
        assertArrayEquals(new int[]{42}, res);
    }

    @Test
    void handlesDuplicates() {
        int[] arr = {5, 3, 5, 3, 5};
        int[] res = runSort(arr, true);
        assertArrayEquals(new int[]{3,3,5,5,5}, res);
    }

    @Test
    void handlesSortedInput() {
        int[] arr = {1,2,3,4,5};
        assertArrayEquals(arr, runSort(arr, true));
    }

    @Test
    void handlesReversedInput() {
        int[] arr = {5,4,3,2,1};
        assertArrayEquals(new int[]{1,2,3,4,5}, runSort(arr, false));
    }
    @Test
    void randomizedPropertyTest() {
        Random rnd = new Random(123);
        for (int t = 0; t < 30; t++) {
            int n = rnd.nextInt(100);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(1000) - 500;
            int[] expected = Arrays.copyOf(a, n);
            Arrays.sort(expected);
            int[] actual = runSort(a, t % 2 == 0);
            assertArrayEquals(expected, actual);
        }
    }
}
