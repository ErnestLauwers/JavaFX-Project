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

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class SetupPane extends GridPane {

    private ChoiceBox<String> loadSavePreference;
    private VBox kortingvbox;
    private Button saveButton;
    CheckBox age64CheckBox = new CheckBox("64 plus korting");
    CheckBox christmasBox = new CheckBox("Kerst korting");
    CheckBox frequentTravelBox = new CheckBox("Frequent travel korting");
    CheckBox studentBox = new CheckBox("Student korting");
    private SetupPaneController setupPaneController;

    public SetupPane(SetupPaneController setupPaneController) {
        this.setupPaneController = setupPaneController;
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/bestanden/settings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.add(new Label("LoadSaveStrategy:"), 0, 0, 1, 1);
        loadSavePreference = new ChoiceBox<>();
        loadSavePreference.getItems().add("Text File");
        loadSavePreference.getItems().add("Excel File");
        loadSavePreference.setValue(properties.getProperty("LoadSaveStrategy"));
        this.add(loadSavePreference, 0, 1);


        this.add(new Label("Kortingen:"), 0, 8, 1, 1);
        kortingvbox = new VBox();
        kortingvbox.getChildren().addAll(age64CheckBox, christmasBox, frequentTravelBox, studentBox);

        String korting = properties.getProperty("Korting");

        for (String s : korting.split(",")) {
            System.out.println(s);

            switch (s) {
                case "AGE64DISCOUNT":
                    age64CheckBox.setSelected(true);
                    break;
                case "CHRISTMASLEAVEDISCOUNT":
                    christmasBox.setSelected(true);
                    break;
                case "FREQUENTTRAVELLERDISCOUNT":
                    frequentTravelBox.setSelected(true);
                    break;
                case "STUDENTDISCOUNT":
                    studentBox.setSelected(true);
                    break;
            }
        }

        this.add(kortingvbox, 0, 9);

        saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                if (setupPaneController.getStatusStation()) {
                    Text error = new Text("The Station is already opened!");
                    this.add(error, 1, 12);
                } else {
                    Collection<String> kortingen = new ArrayList<>();
                    String kortingoutput = "";
                    if (age64CheckBox.isSelected()) {
                        kortingen.add("AGE64DISCOUNT");
                    }
                    if (christmasBox.isSelected()) {
                        kortingen.add("CHRISTMASLEAVEDISCOUNT");
                    }
                    if (frequentTravelBox.isSelected()) {
                        kortingen.add("FREQUENTTRAVELLERDISCOUNT");
                    }
                    if (studentBox.isSelected()) {
                        kortingen.add("STUDENTDISCOUNT");
                    }
                    kortingoutput = String.join(",", kortingen);

                    setupPaneController.save(loadSavePreference.getValue(), kortingoutput);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.add(saveButton, 0, 10);
    }
}
