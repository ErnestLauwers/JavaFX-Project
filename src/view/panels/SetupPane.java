package view.panels;

import controller.SetupPaneController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SetupPane extends GridPane {

    private ChoiceBox<String> loadSavePreference;
    private Button saveButton;
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

        loadSavePreference = new ChoiceBox<>();
        loadSavePreference.getItems().add("Text File");
        loadSavePreference.getItems().add("Excel File");
        loadSavePreference.setValue(properties.getProperty("LoadSaveStrategy"));
        this.add(loadSavePreference, 55, 25);

        saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                if (setupPaneController.getStatusStation()) {
                    Text error = new Text("The Station is already opened!");
                    this.add(error, 55, 12);
                } else {
                    setupPaneController.save(loadSavePreference.getValue());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.add(saveButton, 55, 40);
    }
}
