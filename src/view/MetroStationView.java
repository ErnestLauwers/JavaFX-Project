package view;

import controller.MetroStationViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MetroFacade;

import java.util.ArrayList;

public class MetroStationView {

	private Stage stage = new Stage();
	private MetroStationViewController metroStationViewController;
	private ObservableList<Integer> metroCardIds;
	private ChoiceBox<Integer> allIds;

	public MetroStationView(MetroFacade facade){
		this.metroStationViewController = new MetroStationViewController(facade, this);
		stage.setTitle("METRO STATION VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(5);
		stage.setY(390);

		Group root = new Group();
		root.setLayoutX(30);
		root.setLayoutY(100);

		Group allIdsGroup = new Group();
		root.setLayoutX(100);
		root.setLayoutY(40);

		allIds = new ChoiceBox<>();
		allIdsGroup.getChildren().addAll(allIds);
		root.getChildren().addAll(allIdsGroup);

		Scene scene = new Scene(root, 650, 300);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public void updateMetroCardIDList(ArrayList<Integer> ids) {
		this.metroCardIds = FXCollections.observableArrayList(ids);
		allIds.setItems(metroCardIds);
		allIds.setValue(metroCardIds.get(0));
	}
}
