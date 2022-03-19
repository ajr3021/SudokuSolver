package si.examples.backtracking.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import si.examples.backtracking.model.Backtracker;
import si.examples.backtracking.model.Board;
import si.examples.backtracking.model.BoardObserver;
import si.examples.backtracking.model.SudokuSolver;

import java.io.IOException;

public class Sudoku extends Application implements BoardObserver {
    public static final int NUM_GRIDS = 3;
    public static final int GRID_SIZE = 3;

    private int selectedRow = 0;
    private int selectedCol = 0;

    private final BoardButton[][] boardButtons = new BoardButton[Board.NUM_ROWS][Board.NUM_COLS];
    private final Board board = new Board();
    private final Board originalBoard = board.boardCopy();

    public Sudoku() throws IOException {
    }

    @Override
    public void start(Stage stage){
        board.setOnUpdate(this);
        BorderPane gui = new BorderPane();

        GridPane boardGUI = new GridPane();

        for(int i = 0; i < NUM_GRIDS; i++){
            for(int j = 0; j < NUM_GRIDS; j++){
                GridPane grid = new GridPane();
                grid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));
                grid.setGridLinesVisible(true);

                for(int k = 0; k < GRID_SIZE; k++){
                    for(int l = 0; l < GRID_SIZE; l++){
                        BoardButton button = new BoardButton((GRID_SIZE * i) + k, (GRID_SIZE * j) + l);
                        boardButtons[button.getRow()][button.getCol()] = button;
                        button.setOnAction(e ->{
                            selectedRow = button.getRow();
                            selectedCol = button.getCol();
                            this.highlightBoard();
                        });
                        grid.add(button, l, k);
                    }
                }

                grid.setMaxSize(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
                GridPane.setHgrow(grid, Priority.ALWAYS);
                GridPane.setVgrow(grid, Priority.ALWAYS);

                boardGUI.add(grid, j, i);
            }
        }

        this.boardUpdated(board, 0, 0);
        gui.setCenter(boardGUI);

        HBox toolButtons = new HBox();

        Button erase = new ToolButton("Erase");
        erase.setOnAction(e ->{
            board.removeMove(selectedRow, selectedCol);
        });

        Button newGame = new ToolButton("New Game");
        newGame.setOnAction(e -> {
            try {
                board.newBoard();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Button solve = new ToolButton("Solve");
        solve.setOnAction(e ->{
            solve();
        });


        toolButtons.getChildren().add(erase);
        toolButtons.getChildren().add(newGame);
        toolButtons.getChildren().add(solve);


        HBox numbers = new HBox();

        for(int i = 0; i < Board.NUM_ROWS; i++){
            Button number = new ToolButton(Integer.toString(i + 1));

            numbers.getChildren().add(number);

            number.setOnAction(e -> {
                board.makeMove(selectedRow, selectedCol, Integer.parseInt(number.getText()));
            });
        }

        VBox tools = new VBox();
        tools.getChildren().add(toolButtons);
        tools.getChildren().add(numbers);

        gui.setBottom(tools);

        Scene scene = new Scene(gui);
        stage.setScene(scene);
        stage.setTitle("SI Sudoku");
        stage.show();
    }

    public void highlightBoard(){
        BoardButton selected = boardButtons[selectedRow][selectedCol];
        selected.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, null)));
        selected.setTextFill(Color.BLUE);
        for(int row = 0; row < Board.NUM_ROWS; row++){
            for(int col = 0; col < Board.NUM_COLS; col++){
                BoardButton current = boardButtons[row][col];
                current.setTextFill(Color.BLACK);

                if(board.getBoard()[row][col] == board.getBoard()[selectedRow][selectedCol]){
                    current.setTextFill(Color.BLUE);
                }else{
                    current.setTextFill(Color.BLACK);
                }

                if(row == selectedRow || col == selectedCol || ((selectedRow / 3) == (row / 3) && (selectedCol / 3) == col / 3)){
                    current.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, null)));
                    if(board.getBoard()[row][col] == board.getBoard()[selectedRow][selectedCol] && (row != selectedRow || col != selectedCol)){
                        current.setTextFill(Color.RED);
                        selected.setTextFill(Color.RED);
                    }
                }else{
                    current.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null)));
                }
            }
        }
    }

    private void solve(){
        SudokuSolver solver = new SudokuSolver(0, 0, originalBoard, true);
        Backtracker backtracker = new Backtracker(false);

        SudokuSolver solution = (SudokuSolver) backtracker.solve(solver);

        if(solution != null){
            // this is kind of bad naming on my part
            board.makeMoves(solution.getBoard().getBoard());
        }else{
            System.out.println("NO SOLUTIONS");
        }
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void boardUpdated(Board board, int row, int col) {
        int[][] gameBoard = board.getBoard();

        for(int i = 0; i < Board.NUM_ROWS; i++){
            for(int j = 0; j < Board.NUM_COLS; j++){
                boardButtons[i][j].setText(gameBoard[i][j] == 0 ? "" : Integer.toString(gameBoard[i][j]));
            }
        }

        highlightBoard();
    }
}