package view.panels;


import Controller.AdminViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.database.MetroCardDatabase;


public class MetroCardOverviewPane<E> extends GridPane{
	private TableView<E> table;
	private AdminViewController adminViewController;
	private ObservableList metroCards;

	public MetroCardOverviewPane(AdminViewController adminViewController) {
		this.adminViewController = adminViewController;
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);        
		this.add(new Label("List of Metro cards:"), 0, 0, 1, 1);

		table = new TableView<>();
		this.addRow(1, table);
		refresh();

		TableColumn<E, String> metroCardId = new TableColumn<>("Id");
		metroCardId.setMinWidth(300);
		metroCardId.setCellValueFactory(new PropertyValueFactory<>("metroCardId"));
		TableColumn<E, Double> metroCardDate = new TableColumn<>("Date of creation");
		metroCardDate.setMinWidth(100);
		metroCardDate.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
		TableColumn<E, Integer> metroCardActiveRides = new TableColumn<>("Active rides");
		metroCardActiveRides.setMinWidth(100);
		metroCardActiveRides.setCellValueFactory(new PropertyValueFactory<>("activeRides"));
		TableColumn<E, Integer> metroCardUsedRides = new TableColumn<>("Used rides");
		metroCardUsedRides.setMinWidth(100);
		metroCardUsedRides.setCellValueFactory(new PropertyValueFactory<>("usedRides"));
		table.getColumns().addAll(metroCardId, metroCardDate, metroCardActiveRides, metroCardUsedRides);

	}

	public void refresh() {
		metroCards = FXCollections.observableArrayList(adminViewController.getMetroCardDatabase().load().values());
		table.setItems(metroCards);
		table.refresh();
	}
}
