package view.panels;

import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.GridPane;
import model.database.MetroCardDatabase;

import java.awt.*;

public class ControlCenterPane<MetroCard> extends GridPane {

    private MetroCardDatabase metroCardDatabase;
    private TableView<MetroCard> table;
    private Button openButton;

    public ControlCenterPane() {
        this.metroCardDatabase = new MetroCardDatabase("TXTMETROCARD");

        openButton = new Button("Open Metrostation");

        /*openButton.setOnAction(event->
        {metroCardDatabase.load();
        refresh();
        }*/
    }
}
