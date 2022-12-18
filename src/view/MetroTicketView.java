package view;

import controller.MetroTicketViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MetroFacade;

import java.util.ArrayList;

public class MetroTicketView extends GridPane {

	private Stage stage = new Stage();
	private MetroTicketViewController metroTicketViewController;
	private ObservableList<Integer> metroCardIds;
	private ChoiceBox<Integer> allIds;
	private Button addButton;

	public MetroTicketView(MetroFacade facade){
		this.metroTicketViewController = new MetroTicketViewController(facade, this);
		stage.setTitle("METROTICKET VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(5);
		stage.setY(5);

		Group root = new Group();
		root.setLayoutX(30);
		root.setLayoutY(100);

		Group allIdsGroup = new Group();
		root.setLayoutX(100);
		root.setLayoutY(40);

		Group newMetroCardGroup = new Group();
		newMetroCardGroup.setLayoutX(100);
		newMetroCardGroup.setLayoutY(60);

		allIds = new ChoiceBox<>();
		addButton = new Button("New Metro Card");

		allIdsGroup.getChildren().addAll(allIds);
		newMetroCardGroup.getChildren().addAll(addButton);
		root.getChildren().addAll(allIdsGroup, newMetroCardGroup);

		Scene scene = new Scene(root, 650, 350);
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
