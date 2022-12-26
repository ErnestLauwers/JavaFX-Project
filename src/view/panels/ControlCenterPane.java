package view.panels;

import controller.ControlCenterPaneController;
import controller.MetroStationViewController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ControlCenterPane extends GridPane {

    private ControlCenterPaneController controlCenterPaneController;
    private Button openMetroButton;
    private Button closeMetroButton;

    private Label metroStatus = new Label("");
    public ControlCenterPane(ControlCenterPaneController controlCenterPaneController) {
        this.controlCenterPaneController = controlCenterPaneController;
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        VBox vBox = new VBox();

        openMetroButton = new Button("Open Metrostation");
        openMetroButton.setOnAction(event -> {
            if(controlCenterPaneController.getStationStatus()) {
                metroStatus.setText("Metrostation is already open.");
            }
            else {
                try {
                    controlCenterPaneController.openMetroStation();
                    controlCenterPaneController.setStationStatus();
                    metroStatus.setText("The station is open.");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        closeMetroButton = new Button("Close Metrostation");
        closeMetroButton.setOnAction(event -> {
            try {
                controlCenterPaneController.closeMetroStation();
                metroStatus.setText("The station is closed.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        vBox.getChildren().addAll(openMetroButton, closeMetroButton , metroStatus);

        this.add(vBox, 0, 0);
    }
}
