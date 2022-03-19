package si.examples.backtracking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ToolButton extends Button {
    public ToolButton(String text){
        this.setText(text);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        this.setMaxSize(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        HBox.setHgrow(this, Priority.ALWAYS);
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null)));
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
    }
}
