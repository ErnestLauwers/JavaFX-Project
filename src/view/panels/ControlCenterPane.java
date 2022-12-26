package view.panels;

import controller.ControlCenterPaneController;
import controller.MetroStationViewController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ControlCenterPane extends GridPane {

    private ControlCenterPaneController controlCenterPaneController;
    private Button openMetroButton;

    private Button closeMetroButton;

    public ControlCenterPane(ControlCenterPaneController controlCenterPaneController) {
        this.controlCenterPaneController = controlCenterPaneController;
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        VBox vBox = new VBox();
        openMetroButton = new Button("Open Metrostation");
        vBox.getChildren().add(openMetroButton);

        openMetroButton.setOnAction(event -> {
            if(controlCenterPaneController.getStationStatus()) {
                vBox.getChildren().add(new Text("The station is already open."));
            }
            else {
                try {
                    controlCenterPaneController.openMetroStation();
                    controlCenterPaneController.setStationStatus();
                    String open ="The Station is now opened.";
                    Text confirmation = new Text(open);
                    vBox.getChildren().add(confirmation);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        closeMetroButton = new Button("Close Metrostation");
        vBox.getChildren().add(closeMetroButton);
        closeMetroButton.setOnAction(event -> {
            try {
                controlCenterPaneController.closeMetroStation();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        this.add(vBox, 0, 0);
    }
}
