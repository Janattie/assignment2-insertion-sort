package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;


public class InsertionSort {
    private final PerformanceTracker tracker;
    private final boolean binaryInsertion;

    public InsertionSort(PerformanceTracker tracker, boolean binaryInsertion) {
        this.tracker = tracker;
        this.binaryInsertion = binaryInsertion;
    }

    public int[] sort(int[] input) {
        if (input == null) return null;
        int n = input.length;
        int[] a = Arrays.copyOf(input, n);

        tracker.reset();
        tracker.start();

        for (int i = 1; i < n; i++) {
            tracker.incArrayAccesses(1);
            int key = a[i];

            if (binaryInsertion) {
                int pos = binarySearchPosition(a, 0, i, key);

                for (int j = i - 1; j >= pos; j--) {
                    tracker.incArrayAccesses(2);
                    a[j + 1] = a[j];
                    tracker.incAssignments(1);
                    tracker.incShifts(1);
                }
                tracker.incArrayAccesses(1);
                a[pos] = key;
                tracker.incAssignments(1);
            } else {
                int j = i - 1;
                while (j >= 0) {
                    tracker.incComparisons();
                    tracker.incArrayAccesses(1);
                    if (a[j] >= key) {
                        tracker.incArrayAccesses(2);
                        a[j + 1] = a[j];
                        tracker.incAssignments(1);
                        tracker.incShifts(1);
                        j--;
                    } else {
                        break;
                    }
                }
                tracker.incArrayAccesses(1);
                a[j + 1] = key;
                tracker.incAssignments(1);
            }
        }

        tracker.stop();
        return a;
    }

    private int binarySearchPosition(int[] a, int lo, int hi, int key) {
        int left = lo, right = hi;
        while (left < right) {
            int mid = (left + right) >>> 1;
            tracker.incArrayAccesses(1);
            tracker.incComparisons();
            if (a[mid] <= key) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
