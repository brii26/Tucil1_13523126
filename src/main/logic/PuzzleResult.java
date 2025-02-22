package main.logic;

public class PuzzleResult {
    private final boolean solutionFound;
    private final long attempts;
    private final long time;
    private final char[][] gridSolution;

    public PuzzleResult(boolean solutionFound, long attempts, long time, char[][] gridSolution) {
        this.solutionFound = solutionFound;
        this.attempts = attempts;
        this.time = time;
        this.gridSolution = gridSolution;
    }

    public boolean isSolutionFound() {
        return solutionFound;
    }

    public long getAttempts() {
        return attempts;
    }

    public long getTime() {
        return time;
    }

    public char[][] getGridSolution() {
        return gridSolution;
    }
}