/**
 * @author Benjamin Joossens
 */
package view.panels;

import controller.ControlCenterPaneController;
import controller.MetroStationViewController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.MetroFacade;
import model.database.MetroCardDatabase;

import java.io.IOException;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class ControlCenterPane extends GridPane {

    private ControlCenterPaneController controlCenterPaneController;
    private Button openMetroButton;
    private Button closeMetroButton;
    private int soldTickets;
    private double totalTickets;
    private Label ticketNumberLabel = new Label("Number of sold tickets:");
    private Label ticketAmountLabel = new Label("Total € amount of sold tickets:");
    private TextField ticketNumberField = new TextField();
    private TextField ticketAmountField = new TextField();
    private Label metroStatus = new Label("");
    private Button[] buttons1;
    private Button[] buttons2;
    private Button[] buttons3;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label1b;
    private Label label2b;
    private Label label3b;
    private Label label1c;
    private Label label2c;
    private Label label3c;
    private TextArea textArea;

    public ControlCenterPane(ControlCenterPaneController controlCenterPaneController) {
        this.controlCenterPaneController = controlCenterPaneController;
        controlCenterPaneController.setControlCenterPane(this);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        HBox hBox1 = new HBox();
        hBox1.setSpacing(20);

        openMetroButton = new Button("Open Metrostation");
        openMetroButton.setOnAction(event -> {
            try {
                if (controlCenterPaneController.getStatusStation()) {
                    metroStatus.setText("The station is already opened");
                } else {
                    controlCenterPaneController.setStationStatus();
                    controlCenterPaneController.openMetroStation();
                    metroStatus.setText("The station is opened!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        closeMetroButton = new Button("Close Metrostation");
        closeMetroButton.setOnAction(event -> {
            try {
                if (!controlCenterPaneController.getStatusStation()) {
                    metroStatus.setText("The station is already closed!");
                } else {
                    controlCenterPaneController.setStationStatus();
                    controlCenterPaneController.closeMetroStation();
                    metroStatus.setText("The station has saved the data and is closed.");

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ticketNumberField.setEditable(false);
        ticketAmountField.setEditable(false);

        HBox ticketStat1HBox = new HBox();
        ticketStat1HBox.setSpacing(20);

        HBox ticketStat2HBox = new HBox();
        ticketStat2HBox.setSpacing(20);

        VBox ticketVBox = new VBox();
        ticketVBox.setSpacing(20);

        ticketStat1HBox.getChildren().addAll(ticketNumberLabel, ticketNumberField);
        ticketStat2HBox.getChildren().addAll(ticketAmountLabel, ticketAmountField);

        ticketVBox.getChildren().addAll(ticketStat1HBox, ticketStat2HBox);

        hBox1.getChildren().addAll(openMetroButton, closeMetroButton , metroStatus);

        VBox vbox1 = new VBox();

        label1 = new Label("Gate 1 / Inactive");
        vbox1.getChildren().add(label1);

        buttons1 = new Button[2];

        for (int i = 0; i < 2; i++) {
            buttons1[i] = new Button();
            if (i == 0) {
                buttons1[i].setText("Activate");
            } else if (i == 1) {
                buttons1[i].setText("Deactivate");
            }
            vbox1.getChildren().add(buttons1[i]);
        }

        textArea = new TextArea();
        textArea.setPrefRowCount(6);
        textArea.setPrefColumnCount(50);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setScrollTop(0);
        textArea.setScrollLeft(0);
        textArea.setText("\n!!Alerts!!");
        this.add(textArea, 0, 3);

        buttons1[0].setOnAction(event -> {
            try {
                if (!controlCenterPaneController.getStatusStation()) {
                    textArea.insertText(0, "\nThe station must be open to activate a gate!");
                }
                else if (controlCenterPaneController.getGateStatus(1)) {
                    textArea.insertText(0, "\nGate 1 is already activated");
                } else {
                    controlCenterPaneController.setGateStatus(1);
                    label1.setText("Gate 1 / Active");
                    vbox1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-background-color: white");
                    textArea.insertText(0, "\nGate 1 is activated");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttons1[1].setOnAction(event -> {
            try {
                if (!controlCenterPaneController.getStatusStation()) {
                    textArea.insertText(0, "\nThe station must be open to deactivate a gate!");
                }
                else if (!controlCenterPaneController.getGateStatus(1)) {
                    textArea.insertText(0, "\nGate 1 is already deactivated");
                } else {
                    controlCenterPaneController.setGateStatus(1);
                    label1.setText("Gate 1 / Inactive");
                    vbox1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-background-color: orange");
                    textArea.insertText(0, "\nGate 1 is deactivated");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        label1c = new Label("# scanned cards");
        vbox1.getChildren().add(label1c);
        label1b = new Label("0");
        label1b.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 5");
        vbox1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-background-color: orange");
        vbox1.getChildren().add(label1b);

        VBox vbox2 = new VBox();

        label2 = new Label("Gate 2 / Inactive");
        vbox2.getChildren().add(label2);

        buttons2 = new Button[2];

        for (int i = 0; i < 2; i++) {
            buttons2[i] = new Button();
            if (i == 0) {
                buttons2[i].setText("Activate");
            } else if (i == 1) {
                buttons2[i].setText("Deactivate");
            }
            vbox2.getChildren().add(buttons2[i]);
        }

        buttons2[0].setOnAction(event -> {
            try {
                if (!controlCenterPaneController.getStatusStation()) {
                    textArea.insertText(0, "\nThe station must be open to activate a gate!");
                }
                else if (controlCenterPaneController.getGateStatus(2)) {
                    textArea.insertText(0, "\nGate 2 is already activated");
                } else {
                    controlCenterPaneController.setGateStatus(2);
                    label2.setText("Gate 2 / Active");
                    vbox2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-background-color: white");
                    textArea.insertText(0, "\nGate 2 is activated");

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttons2[1].setOnAction(event -> {
            try {
                if (!controlCenterPaneController.getStatusStation()) {
                    textArea.insertText(0, "\nThe station must be open to deactivate a gate!");
                }
                else if (!controlCenterPaneController.getGateStatus(2)) {
                    textArea.insertText(0, "\nGate 2 is already deactivated");
                } else {
                    controlCenterPaneController.setGateStatus(2);
                    label2.setText("Gate 2 / Inactive");
                    vbox2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-background-color: orange");
                    textArea.insertText(0, "\nGate 2 is deactivated");

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        label2c = new Label("# scanned cards");
        vbox2.getChildren().add(label2c);
        label2b = new Label("0");
        label2b.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 5");
        vbox2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-background-color: orange");
        vbox2.getChildren().add(label2b);

        VBox vbox3 = new VBox();

        label3 = new Label("Gate 3 / Inactive");
        vbox3.getChildren().add(label3);

        buttons3 = new Button[2];

        for (int i = 0; i < 2; i++) {
            buttons3[i] = new Button();
            if (i == 0) {
                buttons3[i].setText("Activate");
            } else if (i == 1) {
                buttons3[i].setText("Deactivate");
            }
            vbox3.getChildren().add(buttons3[i]);
        }

        buttons3[0].setOnAction(event -> {
            try {
                if (!controlCenterPaneController.getStatusStation()) {
                    textArea.insertText(0, "\nThe station must be open to activate a gate!");
                }
                else if (controlCenterPaneController.getGateStatus(3)) {
                    textArea.insertText(0, "\nGate 3 is already activated");

                } else {
                    controlCenterPaneController.setGateStatus(3);
                    label3.setText("Gate 3 / Active");
                    vbox3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-background-color: white");
                    textArea.insertText(0, "\nGate 3 is activated");

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        buttons3[1].setOnAction(event -> {
            try {
                if (!controlCenterPaneController.getStatusStation()) {
                    textArea.insertText(0, "\nThe station must be open to deactivate a gate!");
                }
                else if (!controlCenterPaneController.getGateStatus(3)) {
                    textArea.insertText(0, "\nGate 3 is already deactivated");
                } else {
                    controlCenterPaneController.setGateStatus(3);
                    label3.setText("Gate 3 / Inactive");
                    vbox3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-background-color: orange");
                    textArea.insertText(0, "\nGate 3 is deactivated");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        label3c = new Label("# scanned cards");
        vbox3.getChildren().add(label3c);
        label3b = new Label("0");
        label3b.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 5");
        vbox3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-background-color: orange");
        vbox3.getChildren().add(label3b);

        vbox1.setSpacing(10);
        vbox2.setSpacing(10);
        vbox3.setSpacing(10);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(vbox1, vbox2, vbox3);
        hBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 5");
        hBox.setSpacing(40);

        this.add(hBox1, 0, 0);
        this.add(ticketVBox, 0, 1);
        this.add(hBox, 0, 2);
        this.getStylesheets().add("application/application.css");
    }

    public void updateScannedCards(int scanned1, int scanned2, int scanned3) {
        label1b.setText(String.valueOf(scanned1));
        label2b.setText(String.valueOf(scanned2));
        label3b.setText(String.valueOf(scanned3));
    }

    public void updateAlertsInvalid(int metroCardId, String gate) {
        if (gate.equals("expired")) {
            System.out.println("A");
            textArea.insertText(0, "\nCard " + metroCardId + " is expired!");
        }
        if (gate.equals("noRides")) {
            System.out.println("B");
            textArea.insertText(0, "\nCard " + metroCardId + " has no rides left!");
        }
    }

    public void updateSoldTickets(int amount, double totalprice) {
        soldTickets = amount;
        totalTickets = totalprice;
        ticketNumberField.setText(String.valueOf(soldTickets));
        ticketAmountField.setText("€" + totalTickets);
    }

    public void updateAlerts(int gate) {
        textArea.insertText(0, "\nGate " + gate + " is illegally breached!");
    }
}