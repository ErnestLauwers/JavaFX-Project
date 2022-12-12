package view.panels;

import javafx.scene.control.TableView;

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
    }
}
