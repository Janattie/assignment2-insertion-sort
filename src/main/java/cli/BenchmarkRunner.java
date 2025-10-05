package cli;

import algorithms.InsertionSort;
import metrics.PerformanceTracker;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Map<String, String> opts = parseArgs(args);
        boolean binary = Boolean.parseBoolean(opts.getOrDefault("binary", "true"));
        String distributions = opts.getOrDefault("dists", "random,sorted,reversed,nearly");
        String sizesStr = opts.getOrDefault("sizes", "100,1000,10000");
        int runs = Integer.parseInt(opts.getOrDefault("runs", "5"));
        Path out = Paths.get(opts.getOrDefault("out", "docs/performance-data.csv"));

        List<Integer> sizes = new ArrayList<>();
        for (String s : sizesStr.split(",")) sizes.add(Integer.parseInt(s.trim()));
        List<String> dists = new ArrayList<>();
        for (String d : distributions.split(",")) dists.add(d.trim());

        Files.createDirectories(out.getParent());
        writeHeader(out);

        Random rnd = new Random(42);
        for (String dist : dists) {
            for (int n : sizes) {
                for (int r = 1; r <= runs; r++) {
                    int[] arr = genArray(n, dist, rnd);
                    PerformanceTracker tracker = new PerformanceTracker();
                    InsertionSort sorter = new InsertionSort(tracker, binary);
                    int[] sorted = sorter.sort(arr);
                    if (!isSorted(sorted)) {
                        throw new IllegalStateException("Result not sorted for dist=" + dist + ", n=" + n);
                    }
                    append(out, String.join(",",
                            "InsertionSort",
                            String.valueOf(n),
                            dist,
                            String.valueOf(r),
                            String.valueOf(tracker.getElapsedNs()),
                            String.valueOf(tracker.getComparisons()),
                            String.valueOf(tracker.getShifts()),
                            String.valueOf(tracker.getArrayAccesses()),
                            String.valueOf(tracker.getMemDeltaBytes()),
                            String.valueOf(binary)
                    ) + System.lineSeparator());
                }
            }
        }
        System.out.println("Done. CSV -> " + out.toAbsolutePath());
    }

    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (String a : args) {
            if (a.startsWith("--") && a.contains("=")) {
                String[] kv = a.substring(2).split("=", 2);
                map.put(kv[0], kv[1]);
            }
        }
        return map;
    }

    private static void writeHeader(Path out) throws IOException {
        String header = String.join(",",
                "algorithm","n","distribution","run","time_ns","comparisons","shifts","array_accesses","mem_bytes","binary_insertion") + System.lineSeparator();
        if (!Files.exists(out)) Files.writeString(out, header);
        else if (Files.size(out) == 0) Files.writeString(out, header);
    }

    private static void append(Path out, String row) throws IOException {
        Files.writeString(out, row, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private static int[] genArray(int n, String dist, Random rnd) {
        int[] a = new int[n];
        switch (dist) {
            case "sorted" -> {
                for (int i = 0; i < n; i++) a[i] = i;
            }
            case "reversed" -> {
                for (int i = 0; i < n; i++) a[i] = n - i;
            }
            case "nearly" -> {
                for (int i = 0; i < n; i++) a[i] = i;
                int swaps = Math.max(1, n / 100);
                for (int k = 0; k < swaps; k++) {
                    int i = rnd.nextInt(n);
                    int j = rnd.nextInt(n);
                    int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
                }
            }
            default -> {
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
            }
        }
        return a;
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }
}
