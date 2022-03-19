module si.examples.backtracking {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    exports si.examples.backtracking.view;
    exports si.examples.backtracking.model;
    opens si.examples.backtracking.view to javafx.fxml;
}