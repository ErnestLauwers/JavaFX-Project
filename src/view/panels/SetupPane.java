/**
 * @author Benjamin Joossens
 */
package view.panels;

import controller.SetupPaneController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.TicketPriceDecorator.TicketPriceDiscountEnum;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class SetupPane extends GridPane {

    private ChoiceBox<String> loadSavePreference;
    private Button saveButton;
    private Label kortingLabel = new Label("Discounts:");
    private Label loadSaveLabel = new Label("Load/Save preference:");
    private Label confirmationLabel = new Label("");
    private SetupPaneController setupPaneController;

    public SetupPane(SetupPaneController setupPaneController) {
        this.setupPaneController = setupPaneController;
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        VBox mainvbox = new VBox();
        mainvbox.setSpacing(10);

        VBox loadSaveVBox = new VBox();

        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/bestanden/settings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadSaveVBox.getChildren().add(loadSaveLabel);
        loadSavePreference = new ChoiceBox<>();
        loadSavePreference.getItems().add("Text File");
        loadSavePreference.getItems().add("Excel File");
        loadSavePreference.setValue(properties.getProperty("LoadSaveStrategy"));
        loadSaveVBox.getChildren().add(loadSavePreference);
        VBox kortingVBox = new VBox();
        kortingVBox.getChildren().addAll(kortingLabel);

        String korting = properties.getProperty("Korting");
        ArrayList<String> kortingList = new ArrayList<>();
        for (String s : korting.split(",")) {
            kortingList.add(s);
        }

        TicketPriceDiscountEnum[] ticketPriceDiscountEnums = TicketPriceDiscountEnum.values();

        ArrayList<CheckBox> checkBoxes = new ArrayList<>();

        for(TicketPriceDiscountEnum ticketPriceDiscountEnum : ticketPriceDiscountEnums) {
            CheckBox checkBox  = new CheckBox(ticketPriceDiscountEnum.toString());
            checkBoxes.add(checkBox);

            if(kortingList.contains(ticketPriceDiscountEnum.toString())) {
                checkBox.setSelected(true);
            }
            kortingVBox.getChildren().add(checkBox);
        }

        saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                if (setupPaneController.getStatusStation()) {
                    Text error = new Text("The Station is already opened!");
                    this.add(error, 1, 12);
                } else {
                    Collection<String> kortingen = new ArrayList<>();
                    String kortingoutput = "";
                    for(CheckBox checkBox : checkBoxes) {
                        if(checkBox.isSelected()) {
                            if(!kortingen.contains(checkBox.getText())) {
                                kortingen.add(checkBox.getText());
                                kortingoutput += checkBox.getText() + ",";
                            }
                        }
                    }
                    kortingoutput = String.join(",", kortingen);

                    setupPaneController.save(loadSavePreference.getValue(), kortingoutput);
                    System.out.println("Saved");
                    confirmationLabel.setText("Saved");
                    kortingVBox.getChildren().add(confirmationLabel);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        mainvbox.getChildren().addAll(loadSaveVBox, kortingVBox, saveButton);
        this.add(mainvbox, 0, 0);
    }
}
