package view;

import controller.MetroTicketViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.MetroCard;
import model.MetroFacade;
import model.database.MetroCardDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class MetroTicketView extends GridPane {

	private Stage stage = new Stage();
	private MetroTicketViewController metroTicketViewController;
	private ObservableList<Integer> metroCardIds;
	private ChoiceBox<Integer> allIds;
	private TextField numberOfRides;
	private double price;
	private double totaalPrice;
	private Button addButton;
	private Label metroCardlbl = new Label("Metro card price is 15 euro - 2 free rides included");
	private Label selectMetroCardlbl = new Label("Select metro card: ");
	private Label numberOfRideslbl = new Label("Number of rides: ");
	private Label totalPricelbl = new Label("Total price: ");
	private Label totalPrice = new Label();

	private CheckBox studentCheckBox = new CheckBox("Student?");

	private ToggleGroup ageGroup = new ToggleGroup();
	private RadioButton min26CheckBox = new RadioButton("younger than 26 years");
	private RadioButton plus64CheckBox = new RadioButton("older than 64 years");
	private RadioButton betweenCeckbox = new RadioButton("between 26 and 64 years");

	boolean ageDiscount = false;
	boolean studentDiscount = false;

	private Button saveButton = new Button("Save");



	public MetroTicketView(MetroFacade facade){
		this.metroTicketViewController = new MetroTicketViewController(facade, this);
		stage.setTitle("METROTICKET VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(5);
		stage.setY(5);

		Group root = new Group();
		root.setLayoutX(30);
		root.setLayoutY(100);

		VBox allIdsGroup = new VBox();
		root.setLayoutX(100);
		root.setLayoutY(40);

		VBox newMetroCardGroup = new VBox();
		newMetroCardGroup.setLayoutX(100);
		newMetroCardGroup.setLayoutY(60);

		allIds = new ChoiceBox<>();
		addButton = new Button("New Metro Card");

		addButton.setOnAction(event -> {
			try {
				metroTicketViewController.buyMetroCard();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		allIdsGroup.getChildren().addAll(addButton, metroCardlbl);

		newMetroCardGroup.getChildren().addAll(selectMetroCardlbl, allIds);

		numberOfRides = new TextField();
		newMetroCardGroup.getChildren().addAll(numberOfRideslbl, numberOfRides);
		newMetroCardGroup.getChildren().addAll(studentCheckBox);

		min26CheckBox.setToggleGroup(ageGroup);
		plus64CheckBox.setToggleGroup(ageGroup);
		betweenCeckbox.setToggleGroup(ageGroup);

		newMetroCardGroup.getChildren().addAll(min26CheckBox, betweenCeckbox, plus64CheckBox);

		newMetroCardGroup.getChildren().add(saveButton);

		saveButton.setOnAction(event -> {
			if(studentCheckBox.isSelected()) {
				studentDiscount = true;
			}
			if(min26CheckBox.isSelected() || plus64CheckBox.isSelected()) {
				ageDiscount = true;
			}

			int cardID = (int) allIds.getValue();
			MetroCard metroCard = MetroCardDatabase.getInstance().getMetroCard(cardID);

			price = metroTicketViewController.getPrice(ageDiscount, ageDiscount, studentDiscount, metroCard);
			System.out.println(price);
			System.out.println(metroTicketViewController.getPriceText(ageDiscount, ageDiscount, studentDiscount, metroCard));

			int rides = Integer.parseInt(numberOfRides.getText());

			totaalPrice = price * rides;
			System.out.println(totaalPrice);
		});

		VBox totalGroup = new VBox();
		totalGroup.setLayoutX(100);
		totalGroup.setLayoutY(60);

//		totalPrice.setText(MetroCardDatabase.getInstance().getTotalPrice() + " euro");
		if(allIds.getValue() != null){
			price = metroTicketViewController.getPrice(min26CheckBox.isSelected(), plus64CheckBox.isSelected(), studentCheckBox.isSelected(), MetroCardDatabase.getInstance().getMetroCard(allIds.getValue()));
			System.out.println(price);
		}

		//todo: get totaalprijs met het aantal en prijs
		totalGroup.getChildren().add(totalPricelbl);

		root.getChildren().addAll(allIdsGroup, newMetroCardGroup);


		Scene scene = new Scene(root, 650, 350);
		scene.getStylesheets().add("application/application.css");
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
