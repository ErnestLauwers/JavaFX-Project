package view.panels;

import controller.ControlCenterPaneController;
import controller.MetroStationViewController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.MetroFacade;
import model.database.MetroCardDatabase;

import java.io.IOException;

public class ControlCenterPane extends GridPane {

    private ControlCenterPaneController controlCenterPaneController;
    private Button openMetroButton;
    private Button closeMetroButton;
    private int soldTickets;
    private double totalTickets;
    private Label ticketNumberLabel = new Label("Number of sold tickets:");
    private Label ticketAmoundLabel = new Label("Total € amount of sold tickets:");
    private TextField ticketNumberField = new TextField();
    private TextField ticketAmountField = new TextField();
    private Label metroStatus = new Label("");
    public ControlCenterPane(ControlCenterPaneController controlCenterPaneController) {
        this.controlCenterPaneController = controlCenterPaneController;
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        HBox hBox = new HBox();
        hBox.setSpacing(10);

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
                metroStatus.setText("The station has saved the data and is closed.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ticketNumberField.setEditable(false);
        ticketAmountField.setEditable(false);


        HBox ticketStat1HBox = new HBox();
        ticketStat1HBox.setSpacing(10);

        HBox ticketStat2HBox = new HBox();
        ticketStat2HBox.setSpacing(10);

        VBox ticketVBox = new VBox();
        ticketVBox.setSpacing(10);

        ticketStat1HBox.getChildren().addAll(ticketNumberLabel, ticketNumberField);
        ticketStat2HBox.getChildren().addAll(ticketAmoundLabel, ticketAmountField);

        ticketVBox.getChildren().addAll(ticketStat1HBox, ticketStat2HBox);

        hBox.getChildren().addAll(openMetroButton, closeMetroButton , metroStatus);

        this.add(hBox, 0, 0);
        this.add(ticketVBox, 0, 1);
    }
}
