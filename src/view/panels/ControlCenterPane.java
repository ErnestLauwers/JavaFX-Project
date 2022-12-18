package view.panels;

import controller.ControlCenterPaneController;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControlCenterPane extends GridPane {

    private ControlCenterPaneController controlCenterPaneController;
    private Button openMetroButton;

    public ControlCenterPane(ControlCenterPaneController controlCenterPaneController) {
        this.controlCenterPaneController = controlCenterPaneController;
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        openMetroButton = new Button("Open Metrostation");
        this.add(openMetroButton, 55, 40);
        openMetroButton.setOnAction(event -> {
            try {
                controlCenterPaneController.openMetroStation();
                controlCenterPaneController.setStationStatus();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
