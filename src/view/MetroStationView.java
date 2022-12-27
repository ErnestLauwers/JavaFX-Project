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

	public MetroStationView(MetroFacade facade){
		this.metroStationViewController = new MetroStationViewController(facade, this);
		stage.setTitle("METRO STATION VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(5);
		stage.setY(390);

		Group root = new Group();
		root.setLayoutX(30);
		root.setLayoutY(70);

		VBox vbox1 = new VBox();

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

		label1b = new Label("test ja nee");
		label1b.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 20");
		vbox1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		vbox1.getChildren().add(label1b);

		VBox vbox2 = new VBox();

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

		label2b = new Label("test ja nee");
		label2b.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 20");
		vbox2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		vbox2.getChildren().add(label2b);

		VBox vbox3 = new VBox();

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

		label3b = new Label("test ja nee");
		label3b.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 20");
		vbox3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 20");
		vbox3.getChildren().add(label3b);

		HBox hBox = new HBox();
		hBox.getChildren().addAll(vbox1, vbox2, vbox3);
		hBox.setSpacing(40);

		root.getChildren().add(hBox);
		Scene scene = new Scene(root, 650, 300);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
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
