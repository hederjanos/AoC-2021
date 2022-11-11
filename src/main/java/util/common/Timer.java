package util.common;

public class Timer {

    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
        endTime = startTime;
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public void printExecutionTime() {
        long executionTime = (endTime - startTime);
        double millis = (double) executionTime / 1000000;
        System.out.format("Execution time was : %.2f ms.%n", millis);
    }
}
