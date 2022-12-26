package view;

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
	private TextField priceText = new TextField();
	private CheckBox studentCheckBox = new CheckBox("Student?");
	private ToggleGroup ageGroup = new ToggleGroup();
	private RadioButton min26CheckBox = new RadioButton("younger than 26 years");
	private RadioButton plus64CheckBox = new RadioButton("older than 64 years");
	private RadioButton betweenCeckbox = new RadioButton("between 26 and 64 years");
	boolean ageDiscount = false;
	boolean studentDiscount = false;
	private Button saveButton = new Button("Save");
	private Button confirmButton = new Button("Confirm Confirm request");
	private Button cancelButton = new Button("Cancel request");

	public MetroTicketView(MetroFacade facade){
		this.metroTicketViewController = new MetroTicketViewController(facade, this);
		stage.setTitle("METROTICKET VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(5);
		stage.setY(5);

		Group root = new Group();
		root.setLayoutX(15);
		root.setLayoutY(15);

		VBox mainGroup = new VBox();
		mainGroup.setSpacing(10);

		VBox allIdsGroup = new VBox();
		allIdsGroup.setSpacing(2);
		mainGroup.getChildren().add(allIdsGroup);

		VBox newMetroCardGroup = new VBox();
		newMetroCardGroup.setSpacing(2);
		mainGroup.getChildren().add(newMetroCardGroup);

		VBox priceGroup = new VBox();
		priceGroup.setSpacing(2);
		mainGroup.getChildren().add(priceGroup);

		priceText.setEditable(false);

		allIds = new ChoiceBox<>();
		addButton = new Button("New Metro Card");

		addButton.setOnAction(event -> {
			try {
				metroTicketViewController.buyMetroCard();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		HBox newButtonHbox = new HBox();
		newButtonHbox.setSpacing(5);;
		newButtonHbox.getChildren().addAll(addButton, metroCardlbl);
		allIdsGroup.getChildren().add(newButtonHbox);

		newMetroCardGroup.getChildren().addAll(selectMetroCardlbl, allIds);

		numberOfRides = new TextField();
		newMetroCardGroup.getChildren().addAll(numberOfRideslbl, numberOfRides);
		newMetroCardGroup.getChildren().addAll(studentCheckBox);

		min26CheckBox.setToggleGroup(ageGroup);
		plus64CheckBox.setToggleGroup(ageGroup);
		betweenCeckbox.setToggleGroup(ageGroup);

		HBox discountGroupHBox = new HBox();
		discountGroupHBox.setSpacing(5);
		discountGroupHBox.getChildren().addAll(min26CheckBox,betweenCeckbox, plus64CheckBox);
		newMetroCardGroup.getChildren().addAll(discountGroupHBox ,saveButton);

		betweenCeckbox.setSelected(true);


		saveButton.setOnAction(event -> {
			if(studentCheckBox.isSelected()) {
				studentDiscount = true;
			}
			else {
				studentDiscount = false;
			}
			if(min26CheckBox.isSelected() || plus64CheckBox.isSelected()) {
				ageDiscount = true;
			}
			else {
				ageDiscount = false;
			}

			int cardID = (int) allIds.getValue();

			MetroCard metroCard = MetroCardDatabase.getInstance().getMetroCard(cardID);

			price = metroTicketViewController.getPrice(ageDiscount, ageDiscount, studentDiscount, metroCard);
			System.out.println("Prijs per ticket: " + price);


			if(numberOfRides.getText().isEmpty() || numberOfRides.getText().equals("0")) {
				errorText.setText("Please enter a number of rides");
				totalPriceText.setText("");
			}
			else {
				errorText.setText("");
				totalPriceText.setText(metroTicketViewController.getPriceText(ageDiscount, ageDiscount, studentDiscount, metroCard));
				if(!numberOfRides.getText().matches("[0-9]*")){
					errorText.setText("Please enter numbers only");
				}
				else {
					errorText.setText("");
					int rides = Integer.parseInt(numberOfRides.getText());
					if(rides <= 0) {
						errorText.setText("Number of rides must be higher than 0");
					}
					else {
						errorText.setText("");
						totalPrice = price * rides;
						System.out.println("Totaal: " + totalPrice);
						System.out.println(totalPriceText.getText());

						priceText.setText(String.valueOf(totalPrice));
					}
				}
			}

		});

		if(allIds.getValue() != null){
			price = metroTicketViewController.getPrice(min26CheckBox.isSelected(), plus64CheckBox.isSelected(), studentCheckBox.isSelected(), MetroCardDatabase.getInstance().getMetroCard(allIds.getValue()));
		}

		HBox concelHbox = new HBox();
		concelHbox.setSpacing(5);
		concelHbox.getChildren().addAll(confirmButton, cancelButton);

		priceGroup.getChildren().addAll(totalPricelbl, priceText, totalPriceText, concelHbox, errorText);

		confirmButton.setOnAction(event -> {
			if(totalPriceText.getText().isEmpty()) {
				errorText.setText("Please save your request first");
			}
			else {
				errorText.setText("");
				int cardID = (int) allIds.getValue();
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
							metroTicketViewController.buyMetroCardTickets(cardID, aantalRitten);
							System.out.println("Active rides: " + aantalRitten);
							resetForm();

						}
						else {
							errorText.setText("This card is expired!");
							System.out.println("Card is expired");
						}

					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}

		});

		cancelButton.setOnAction(event -> {
			resetForm();
		});

		root.getChildren().addAll(mainGroup);

		Scene scene = new Scene(root, 650, 350);
		this.getStylesheets().add("application/application.css");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public void updateMetroCardIDList(ArrayList<Integer> ids) {
		this.metroCardIds = FXCollections.observableArrayList(ids);
		allIds.setItems(metroCardIds);
		allIds.setValue(metroCardIds.get(0));
	}

	private void resetForm() {
		allIds.setValue(1);
		numberOfRides.clear();
		studentCheckBox.setSelected(false);
		min26CheckBox.setSelected(false);
		plus64CheckBox.setSelected(false);
		betweenCeckbox.setSelected(true);
		priceText.clear();
		totalPriceText.setText("");
		errorText.setText("");
	}
}
