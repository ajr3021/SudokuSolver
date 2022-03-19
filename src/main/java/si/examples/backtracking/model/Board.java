package si.examples.backtracking.model;

import java.io.*;
import java.util.Arrays;

public class Board {
    public static final int NUM_ROWS = 9;
    public static final int NUM_COLS = 9;

    private int[][] board = new int[NUM_ROWS][NUM_COLS];

    private BoardObserver observer;

    public Board() throws IOException {
        int randomBoard = (int) (Math.random() * 1000000);

        File puzzles = new File("src/main/java/si/examples/backtracking/model/puzzles.txt");
        FileReader fileReader = new FileReader(puzzles);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        int counter = 0;
        String line;
        while(((line = bufferedReader.readLine()) != null) && counter != randomBoard){
            counter++;
        }

        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLS; j++){
                int value = Integer.parseInt(String.valueOf(line.charAt((i * NUM_ROWS) + j)));
                this.makeMove(i, j, value);
            }
        }
    }

    private Board(String arg){
        this.board = new int[Board.NUM_ROWS][Board.NUM_COLS];
    }

    public Board boardCopy(){
        Board board = new Board("By passing default");

        for(int j = 0; j < board.getBoard().length; j++) {
            board.getBoard()[j] = Arrays.copyOf(this.board[j], Board.NUM_ROWS);
        }

        return board;
    }

    public boolean makeMove(int row, int col, int value){
        this.board[row][col] = value;
        boardUpdated(row, col);
        return isValidMove(row, col);
    }

    public void removeMove(int row, int col){
        this.board[row][col] = 0;
        boardUpdated(row, col);
    }

    public boolean isValidMove(int row, int col){
        if(board[row][col] == 0){
            return true;
        }
        for(int currentRow = 0; currentRow < NUM_ROWS; currentRow++){
            for(int currentCol = 0; currentCol < NUM_COLS; currentCol++){
                if((row == currentRow || col == currentCol || ((currentRow / 3) == (row / 3) && (currentCol / 3) == col / 3))
                && board[row][col] == board[currentRow][currentCol] && (row != currentRow || col != currentCol)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void newBoard() throws IOException {
        this.board = new int[NUM_ROWS][NUM_COLS];
        int randomBoard = (int) (Math.random() * 1000000);

        File puzzles = new File("src/main/java/si/examples/backtracking/model/puzzles.txt");
        FileReader fileReader = new FileReader(puzzles);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        int counter = 0;
        String line;
        while(((line = bufferedReader.readLine()) != null) && counter != randomBoard){
            counter++;
        }

        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLS; j++){
                int value = Integer.parseInt(String.valueOf(line.charAt((i * NUM_ROWS) + j)));
                this.makeMove(i, j, value);
            }
        }
    }

    public void makeMoves(int[][] board){
        for(int row = 0; row < Board.NUM_ROWS; row++){
            for(int col = 0; col < Board.NUM_COLS; col++){
                this.makeMove(row, col, board[row][col]);
            }
        }
    }

    public void setOnUpdate(BoardObserver boardObserver){
        this.observer = boardObserver;
    }

    public void boardUpdated(int row, int col){
        if(observer != null){
            observer.boardUpdated(this, row, col);
        }
    }

    public int[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < Board.NUM_ROWS; i++){
            result.append(Arrays.toString(board[i])).append('\n');
        }

        return result.toString();
    }
}
