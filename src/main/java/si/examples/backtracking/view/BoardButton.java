package si.examples.backtracking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BoardButton extends Button {
    private final int row;
    private final int col;

    public BoardButton(int row, int col){
        this.row = row;
        this.col = col;
        this.setPrefSize(36, 36);
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null)));
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        this.setMaxSize(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        GridPane.setHgrow(this, Priority.ALWAYS);
        GridPane.setVgrow(this, Priority.ALWAYS);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
