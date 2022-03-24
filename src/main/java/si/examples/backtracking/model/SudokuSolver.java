package si.examples.backtracking.model;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SudokuSolver implements Configuration{
    private final int row;
    private final int col;
    private final Board board;
    private boolean wasLastMoveValid;


    public SudokuSolver(int row, int col, Board board, boolean valid) {
        this.row = row;
        this.col = col;
        this.board = board;
        this.wasLastMoveValid = valid;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        List<Configuration> successors = new ArrayList<>();

        if(board.getBoard()[row][col] == 0){
            for(int i = 0; i < Board.NUM_ROWS; i++){

                Board newBoard = board.boardCopy();
                wasLastMoveValid = newBoard.makeMove(row, col, i + 1);
                int newCol = (col + 1) % Board.NUM_COLS;
                if(newCol == 0){
                    successors.add(new SudokuSolver(row + 1, newCol, newBoard, wasLastMoveValid));
                }else{
                    successors.add(new SudokuSolver(row, newCol, newBoard, wasLastMoveValid));
                }
            }
        }else {
            int newCol = (col + 1) % Board.NUM_COLS;
            if(newCol == 0){
                successors.add(new SudokuSolver(row + 1, newCol, board.boardCopy(), true));
            }else{
                successors.add(new SudokuSolver(row, newCol, board.boardCopy(), true));
            }
        }

        return successors;
    }

    @Override
    public boolean isValid() {
        return this.wasLastMoveValid;
    }

    @Override
    public boolean isGoal() {
        return board.isFull();
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "SudokuSolver{" +
                "board=\n" + board +
                '}';
    }
}
