package view;

import controller.MetroStationViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MetroFacade;

import java.io.IOException;
import java.util.ArrayList;

public class MetroStationView {

	private Stage stage = new Stage();
	private MetroStationViewController metroStationViewController;
	private ObservableList<Integer> metroCardIds;
	private ChoiceBox<Integer> checkBox;
	private ChoiceBox<Integer> checkBox2;
	private ChoiceBox<Integer> checkBox3;
	private Button[] buttons1;
	private Button[] buttons2;
	private Button[] buttons3;
	private Label label1;
	private Label label2;
	private Label label3;
	private Label label1b;
	private Label label2b;
	private Label label3b;
	private Group root;
	private Scene scene;
	private VBox vbox1;
	private VBox vbox2;
	private VBox vbox3;
	private int scannedCard1;
	private int scannedCard2;
	private int scannedCard3;

	public MetroStationView(MetroFacade facade){
		this.metroStationViewController = new MetroStationViewController(facade, this);
		stage.setTitle("METRO STATION VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(5);
		stage.setY(390);

		root = new Group();
		root.setLayoutX(30);
		root.setLayoutY(50);

		vbox1 = new VBox();

		label1 = new Label("Gate 1");
		vbox1.getChildren().add(label1);
		checkBox = new ChoiceBox<Integer>();
		vbox1.getChildren().add(checkBox);

		buttons1 = new Button[2];

		for (int i = 0; i < 2; i++) {
			buttons1[i] = new Button();
			if (i == 0) {
				buttons1[i].setText("Scan Metro Card");
			} else if (i == 1) {
				buttons1[i].setText("Walk Through Gate");
			}
			vbox1.getChildren().add(buttons1[i]);
		}

		buttons1[0].setOnAction(event -> {
			try {
				if (metroStationViewController.getGateNow(1).equals("Opened")) {
					label1b.setText("Gate is already open!");
				} else {
					scannedCard1 = checkBox.getValue();
					metroStationViewController.scanMetroGate(checkBox.getValue(), 1);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		buttons1[1].setOnAction(event -> {
			try {
				System.out.println(metroStationViewController.getGateNow(1));
				if (metroStationViewController.getGateNow(1).equals("Opened")) {
					metroStationViewController.walkThroughGate(1);
				}
				else if (metroStationViewController.getGateNow(1).equals("Closed")) {
					label1b.setText("!!ALERT!!");
					metroStationViewController.walkThroughGate(1);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		if (!metroStationViewController.getStatusStation()) {
			vbox1.setDisable(true);
			vbox1.setStyle("-fx-opacity: 0.5; -fx-background-color: grey;");
		}
		if (metroStationViewController.getStatusStation() && metroStationViewController.getGateStatus(1)){
			vbox1.setDisable(false);
			vbox1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		}

		label1b = new Label("");
		label1b.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 5");
		vbox1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		vbox1.getChildren().add(label1b);

		vbox2 = new VBox();

		label2 = new Label("Gate 2");
		vbox2.getChildren().add(label2);
		checkBox2 = new ChoiceBox<Integer>();
		vbox2.getChildren().add(checkBox2);

		buttons2 = new Button[2];

		for (int i = 0; i < 2; i++) {
			buttons2[i] = new Button();
			if (i == 0) {
				buttons2[i].setText("Scan Metro Card");
			} else if (i == 1) {
				buttons2[i].setText("Walk Through Gate");
			}
			vbox2.getChildren().add(buttons2[i]);
		}

		buttons2[0].setOnAction(event -> {
			try {
				if (metroStationViewController.getGateNow(2).equals("Opened")) {
					label2b.setText("Gate is already open!");
				} else {
					scannedCard2 = checkBox2.getValue();
					metroStationViewController.scanMetroGate(checkBox2.getValue(), 2);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		buttons2[1].setOnAction(event -> {
			try {
				if (metroStationViewController.getGateNow(2).equals("Opened")) {
					metroStationViewController.walkThroughGate(2);
				}
				else if (metroStationViewController.getGateNow(2).equals("Closed")) {
					label2b.setText("!!ALERT!!");
					metroStationViewController.walkThroughGate(2);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		if (!metroStationViewController.getStatusStation()) {
			vbox2.setDisable(true);
			vbox2.setStyle("-fx-opacity: 0.5; -fx-background-color: grey;");
		}
		if (metroStationViewController.getStatusStation() && metroStationViewController.getGateStatus(2)){
			vbox2.setDisable(false);
			vbox2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		}

		label2b = new Label("");
		label2b.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 5");
		vbox2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		vbox2.getChildren().add(label2b);

		vbox3 = new VBox();

		label3 = new Label("Gate 3");
		vbox3.getChildren().add(label3);
		checkBox3 = new ChoiceBox<Integer>();
		vbox3.getChildren().add(checkBox3);

		buttons3 = new Button[2];

		for (int i = 0; i < 2; i++) {
			buttons3[i] = new Button();
			if (i == 0) {
				buttons3[i].setText("Scan Metro Card");
			} else if (i == 1) {
				buttons3[i].setText("Walk Through Gate");
			}
			vbox3.getChildren().add(buttons3[i]);
		}

		buttons3[0].setOnAction(event -> {
			try {
				if (metroStationViewController.getGateNow(3).equals("Opened")) {
					label3b.setText("Gate is already open!");
				} else {
					scannedCard3 = checkBox3.getValue();
					metroStationViewController.scanMetroGate(checkBox3.getValue(), 3);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		buttons3[1].setOnAction(event -> {
			try {
				if (metroStationViewController.getGateNow(3).equals("Opened")) {
					metroStationViewController.walkThroughGate(3);
				}
				else if (metroStationViewController.getGateNow(3).equals("Closed")) {
					label3b.setText("!!ALERT!!");
					metroStationViewController.walkThroughGate(3);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		if (!metroStationViewController.getStatusStation()) {
			vbox3.setDisable(true);
			vbox3.setStyle("-fx-opacity: 0.5; -fx-background-color: grey;");
		}
		if (metroStationViewController.getStatusStation() && metroStationViewController.getGateStatus(3)){
			vbox3.setDisable(false);
			vbox3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		}

		label3b = new Label("");
		label3b.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 5");
		vbox3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		vbox3.getChildren().add(label3b);

		vbox1.setSpacing(10);
		vbox2.setSpacing(10);
		vbox3.setSpacing(10);

		HBox hBox = new HBox();
		hBox.getChildren().addAll(vbox1, vbox2, vbox3);
		hBox.setSpacing(40);

		root.getChildren().add(hBox);
		scene = new Scene(root, 650, 300);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public void updateGates() {
		vbox1.setDisable(false);
		vbox1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		vbox2.setDisable(false);
		vbox2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		vbox3.setDisable(false);
		vbox3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		if (metroStationViewController.getStatusStation() && metroStationViewController.getGateStatus(1)){
			vbox1.setDisable(false);
			vbox1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		} else {
			vbox1.setDisable(true);
			vbox1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-opacity: 0.5; -fx-background-color: grey; ");
		}
		if (metroStationViewController.getStatusStation() && metroStationViewController.getGateStatus(2)){
			vbox2.setDisable(false);
			vbox2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		} else {
			vbox2.setDisable(true);
			vbox2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-opacity: 0.5; -fx-background-color: grey; ");
		}
		if (metroStationViewController.getStatusStation() && metroStationViewController.getGateStatus(3)){
			vbox3.setDisable(false);
			vbox3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		} else {
			vbox3.setDisable(true);
			vbox3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20; -fx-opacity: 0.5; -fx-background-color: grey; ");
		}
	}

	public void updateScanGate(int gate) {
		if (gate == 1) {
			if (metroStationViewController.getGateEvent(1).equals("expired")) {
				label1b.setText("Card " + scannedCard1 + " is expired");
			}
			if (metroStationViewController.getGateEvent(1).equals("noRides")) {
				label1b.setText("Card " + scannedCard1 + " has 0 rides");
			}
			if (metroStationViewController.getGateEvent(1).equals("valid")) {
				label1b.setText("Card " + scannedCard1 + " is scanned");
			}
		}
		if (gate == 2) {
			if (metroStationViewController.getGateEvent(2).equals("expired")) {
				label2b.setText("Card " + scannedCard2 + " is expired");
			}
			if (metroStationViewController.getGateEvent(2).equals("noRides")) {
				label2b.setText("Card " + scannedCard2 + " has 0 rides");
			}
			if (metroStationViewController.getGateEvent(2).equals("valid")) {
				label2b.setText("Card " + scannedCard2 + " is scanned");
			}
		}
		if (gate == 3) {
			if (metroStationViewController.getGateEvent(3).equals("expired")) {
				label3b.setText("Card " + scannedCard3 + " is expired");
			}
			if (metroStationViewController.getGateEvent(3).equals("noRides")) {
				label3b.setText("Card " + scannedCard3 + " has 0 rides");
			}
			if (metroStationViewController.getGateEvent(3).equals("valid")) {
				label3b.setText("Card " + scannedCard3 + " is scanned");
			}
		}
	}

	public void updateWalkThroughGate(int gate) {
		if (gate == 1) {
			label1b.setText("Card " + scannedCard1 + " passed gate");
		} else if (gate == 2) {
			label2b.setText("Card " + scannedCard2 + " passed gate");
		} else if (gate == 3) {
			label3b.setText("Card " + scannedCard3 + " passed gate");
		}
	}

	public void updateMetroCardIDList(ArrayList<Integer> ids) {
		this.metroCardIds = FXCollections.observableArrayList(ids);
		checkBox.setItems(metroCardIds);
		checkBox.setValue(metroCardIds.get(0));
		checkBox2.setItems(metroCardIds);
		checkBox2.setValue(metroCardIds.get(0));
		checkBox3.setItems(metroCardIds);
		checkBox3.setValue(metroCardIds.get(0));
	}
}