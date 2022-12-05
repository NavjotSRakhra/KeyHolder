package controllers;

public class TimeController {
    private final long timeTakenNano;
    private boolean isOver;
    private long endTimeNano;

    public TimeController(long timeTakenNano) throws IllegalTimeValueException {
        if (timeTakenNano < 0)
            throw new IllegalTimeValueException("Time taken cannot be negative.");
        endTimeNano = -1;
        isOver = false;
        this.timeTakenNano = timeTakenNano;
    }

    public void start() {
        endTimeNano = timeTakenNano + System.nanoTime();
    }

    public boolean isOver() {
        if (endTimeNano == -1)
            return true;
        if (!isOver) {
            return endTimeNano <= System.nanoTime();
        }
        return true;
    }

    public void over() {
        isOver = true;
    }

    @Override
    public String toString() {
        return "isOver: " +
                isOver +
                ", endTimeNano: " +
                endTimeNano +
                ", timeTimeNano: " +
                timeTakenNano +
                ", System.nanoTime(): " +
                System.nanoTime();
    }
}
