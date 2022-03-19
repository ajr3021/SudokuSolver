package si.examples.backtracking.model;

public interface BoardObserver {
    void boardUpdated(Board board, int row, int col);
}
