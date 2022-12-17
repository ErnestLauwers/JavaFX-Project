package view.panels;

import controller.ControlCenterPaneController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

public class ControlCenterPane extends GridPane {

    private ObservableList metroCards;
    private ControlCenterPaneController controlCenterPaneController;
    private Button saveButton;

    public ControlCenterPane(ControlCenterPaneController controlCenterPaneController) {
        this.controlCenterPaneController = controlCenterPaneController;
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        saveButton = new Button("Open Metrostation");
        this.add(saveButton, 55, 40);
    }
}
