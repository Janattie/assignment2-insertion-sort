package metrics;

public class PerformanceTracker {
    private long comparisons;
    private long assignments;
    private long arrayAccesses;
    private long shifts;
    private long startTimeNs;
    private long elapsedNs;
    private long memBefore;
    private long memAfter;

    public void reset() {
        comparisons = assignments = arrayAccesses = shifts = 0;
        elapsedNs = 0;
        memBefore = memAfter = 0;
    }

    public void start() {
        System.gc();
        try { Thread.sleep(5); } catch (InterruptedException ignored) {}
        memBefore = usedMemory();
        startTimeNs = System.nanoTime();
    }

    public void stop() {
        elapsedNs = System.nanoTime() - startTimeNs;
        memAfter = memBefore;

    }

    private long usedMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    public void incComparisons() { comparisons++; }
    public void incAssignments(long k) { assignments += k; }
    public void incArrayAccesses(long k) { arrayAccesses += k; }
    public void incShifts(long k) { shifts += k; }

    public long getComparisons() { return comparisons; }
    public long getAssignments() { return assignments; }
    public long getArrayAccesses() { return arrayAccesses; }
    public long getShifts() { return shifts; }
    public long getElapsedNs() { return elapsedNs; }
    public long getMemDeltaBytes() { return Math.max(0, memAfter - memBefore); }
}
