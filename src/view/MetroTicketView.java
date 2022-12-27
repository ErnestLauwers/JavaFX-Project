package view;

import controller.ControlCenterPaneController;
import controller.MetroStationViewController;
import controller.MetroTicketViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	private double totalPrice;
	private Label totalPriceText = new Label("");
	private Label errorText = new Label("");
	private Button addButton;
	private Label metroCardlbl = new Label("Metro card price is 15 euro - 2 free rides included");
	private Label selectMetroCardlbl = new Label("Select metro card: ");
	private Label numberOfRideslbl = new Label("Number of rides: ");
	private Label totalPricelbl = new Label("Total price: ");
	private TextField priceText = new TextField("0");
	private CheckBox studentCheckBox = new CheckBox("Student?");
	private ToggleGroup ageGroup = new ToggleGroup();
	private RadioButton min26CheckBox = new RadioButton("younger than 26 years");
	private RadioButton plus64CheckBox = new RadioButton("older than 64 years");
	private RadioButton betweenCheckbox = new RadioButton("between 26 and 64 years");
	boolean ageDiscount = false;
	boolean studentDiscount = false;
	private Button saveButton = new Button("Save");
	private Button confirmButton = new Button("Confirm request");
	private Button cancelButton = new Button("Cancel request");
	private VBox mainGroup;
	Group root = new Group();

	public MetroTicketView(MetroFacade facade){
		this.metroTicketViewController = new MetroTicketViewController(facade, this);

		stage.setTitle("METROTICKET VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(5);
		stage.setY(5);

		root.setLayoutX(15);
		root.setLayoutY(15);

		if(metroTicketViewController.getStatusStation()){
			root.setDisable(false);
			root.setStyle("-fx-opacity: 1; -fx-background-color: grey;");
		}
		else {
			root.setDisable(false);
			root.setStyle("-fx-opacity: 0.5; -fx-background-color: white;");
		}

		mainGroup = new VBox();

		VBox allIdsGroup = new VBox();
		mainGroup.getChildren().add(allIdsGroup);

		VBox ticketGroup = new VBox();
		ticketGroup.setSpacing(10);
		ticketGroup.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-insets: 5;" +
				"-fx-border-radius: 5;" +
				"-fx-border-color: grey;");

		mainGroup.getChildren().add(ticketGroup);


		VBox newMetroCardGroup = new VBox();
		newMetroCardGroup.setSpacing(5);

		VBox priceGroup = new VBox();
		mainGroup.getChildren().add(priceGroup);

		priceText.setEditable(false);

		allIds = new ChoiceBox<>();
		addButton = new Button("New Metro Card");

		errorText.setStyle("-fx-text-fill: red;");

		addButton.setOnAction(event -> {
			if(metroTicketViewController.getStatusStation()){
				try {
					metroTicketViewController.buyMetroCard();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			else {
				errorText.setText("You can't buy a metro card when the station is closed");
			}
		});
		HBox newButtonHbox = new HBox();

		newButtonHbox.setSpacing(20);
		newButtonHbox.getChildren().addAll(addButton, metroCardlbl);
		allIdsGroup.getChildren().add(newButtonHbox);

		newButtonHbox.setStyle("-fx-alignment: center;");

		metroCardlbl.setStyle("-fx-text-fill: blue;" + "-fx-font-size: 15;");

		HBox selectMetroCardHbox = new HBox();
		selectMetroCardHbox.setSpacing(20);
		selectMetroCardHbox.getChildren().addAll(selectMetroCardlbl, allIds);

		numberOfRides = new TextField();

		min26CheckBox.setToggleGroup(ageGroup);
		plus64CheckBox.setToggleGroup(ageGroup);
		betweenCheckbox.setToggleGroup(ageGroup);

		HBox discountGroupHBox = new HBox();
		discountGroupHBox.setSpacing(20);
		discountGroupHBox.getChildren().addAll(min26CheckBox,betweenCheckbox, plus64CheckBox);

		HBox ridesHbox = new HBox();
		ridesHbox.setSpacing(20);
		ridesHbox.getChildren().addAll(numberOfRideslbl, numberOfRides);
		newMetroCardGroup.getChildren().addAll(selectMetroCardHbox, ridesHbox,  studentCheckBox, discountGroupHBox ,saveButton);

		betweenCheckbox.setSelected(true);

		saveButton.setOnAction(event -> {
			if(studentCheckBox.isSelected()) {
				studentDiscount = true;
			}
			else {
				studentDiscount = false;
			}

			if(betweenCheckbox.isSelected()) {
				ageDiscount = false;
			}
			else {
				ageDiscount = true;
			}
			int cardID = 0;

			if(allIds.getValue() == null) {
				errorText.setText("You have to select a metro card");
			}
			else {
				cardID = allIds.getValue();
			}

			MetroCard metroCard = MetroCardDatabase.getInstance().getMetroCard(cardID);

			price = metroTicketViewController.getPrice(ageDiscount, ageDiscount, studentDiscount, metroCard);


			if(numberOfRides.getText().isEmpty() || numberOfRides.getText().equals("0")) {
				errorText.setText("Please enter a number of rides");
				totalPriceText.setText("");
			}
			else {
				errorText.setText("");
				if(!numberOfRides.getText().matches("[0-9]*")){
					errorText.setText("Please enter numbers only");
				}
				else {
					totalPriceText.setText(metroTicketViewController.getPriceText(ageDiscount, ageDiscount, studentDiscount, metroCard));
					errorText.setText("");
					int rides = Integer.parseInt(numberOfRides.getText());
					if(rides <= 0) {
						errorText.setText("Number of rides must be higher than 0");
					}
					else {
						errorText.setText("");
						totalPrice = price * rides;
						priceText.setText(String.valueOf(totalPrice));
					}
				}
			}

		});

		if(allIds.getValue() != null){
			price = metroTicketViewController.getPrice(min26CheckBox.isSelected(), plus64CheckBox.isSelected(), studentCheckBox.isSelected(), MetroCardDatabase.getInstance().getMetroCard(allIds.getValue()));
		}

		HBox concelHbox = new HBox();
		concelHbox.setSpacing(20);
		concelHbox.getChildren().addAll(confirmButton, cancelButton);

		HBox priceTextHbox = new HBox();
		priceTextHbox.setSpacing(20);
		priceGroup.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-insets: 5;" +
				"-fx-border-radius: 5;" +
				"-fx-border-color: grey;");
		priceTextHbox.getChildren().addAll(totalPricelbl, priceText);

		priceGroup.getChildren().addAll(priceTextHbox, totalPriceText, concelHbox, errorText);

		ticketGroup.getChildren().addAll(newMetroCardGroup, priceGroup);


		confirmButton.setOnAction(event -> {
			if(metroTicketViewController.getStatusStation() && allIds.getValue() != null){
				if(totalPriceText.getText().isEmpty()) {
					errorText.setText("Please save your request first");
				}
				else {
					errorText.setText("");
					int cardID = allIds.getValue();
					if(numberOfRides.getText().isEmpty()) {
						errorText.setText("Please enter a number of rides.");
					}
					else {
						errorText.setText("");
					}
					int aantalRitten = Integer.parseInt(numberOfRides.getText());

					if(aantalRitten <= 0) {
						errorText.setText("Number of rides must be higher than 0.");
					}
					else {
						errorText.setText("");
						try {
							if(!MetroCardDatabase.getInstance().getMetroCard(cardID).isExpired()){
								metroTicketViewController.buyMetroCardTickets(cardID, aantalRitten, totalPrice);
								resetForm();

							}
							else {
								errorText.setText("This card is expired!");
							}

						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
			else {
				if(allIds.getValue() == null) {
					errorText.setText("You have to select a metro card");
				}
				else{
				errorText.setText("You can't buy a ticket because the station is closed.");
				}
			}
		});

		cancelButton.setOnAction(event -> {
			resetForm();
		});

		root.getChildren().addAll(mainGroup);
		root.getStylesheets().add("application/application.css");

		Scene scene = new Scene(root, 650, 350);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public void updateMetroCardIDList(ArrayList<Integer> ids) {
		this.metroCardIds = FXCollections.observableArrayList(ids);
		allIds.setItems(metroCardIds);
		allIds.setValue(metroCardIds.get(0));
		if(metroTicketViewController.getStatusStation()){
			root.setDisable(false);
			root.setStyle("-fx-opacity: 1; -fx-background-color: white;");

		}
		else {
			root.setDisable(true);
			root.setStyle("-fx-opacity: 0.5; -fx-background-color: white;");

		}
	}

	private void resetForm() {
		allIds.setValue(null);
		numberOfRides.setText("0");
		studentCheckBox.setSelected(false);
		min26CheckBox.setSelected(false);
		plus64CheckBox.setSelected(false);
		betweenCheckbox.setSelected(true);
		priceText.setText("0");
		totalPriceText.setText("");
		errorText.setText("");
	}
}
