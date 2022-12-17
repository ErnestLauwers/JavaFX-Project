package view.panels;

import controller.MetroCardOverviewPaneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class MetroCardOverviewPane<E> extends GridPane {
	private TableView<E> metroCardsTable;
	private ObservableList metroCards;
	private MetroCardOverviewPaneController metroCardOverviewPaneController;

	public MetroCardOverviewPane(MetroCardOverviewPaneController metroCardOverviewPaneController) {
		this.metroCardOverviewPaneController = metroCardOverviewPaneController;
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setVgap(5);
		this.setHgap(5);
		this.add(new Label("List of Metro cards:"), 0, 0, 1, 1);

		metroCardsTable = new TableView<>();
		this.addRow(1, metroCardsTable);
		TableColumn<E, String> colMetroCardId = new TableColumn<>("id");
		colMetroCardId.setMinWidth(300);
		colMetroCardId.setCellValueFactory(new PropertyValueFactory<>("metroCardId"));
		TableColumn<E, Double> colMetroCardDateOfCreation = new TableColumn<>("date of creation");
		colMetroCardDateOfCreation.setMinWidth(100);
		colMetroCardDateOfCreation.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
		TableColumn<E, Integer> colMetroCardActiveRides = new TableColumn<>("Active Rides");
		colMetroCardActiveRides.setMinWidth(100);
		colMetroCardActiveRides.setCellValueFactory(new PropertyValueFactory<>("activeRides"));
		TableColumn<E, Integer> colMetroCardUsedRides = new TableColumn<>("Used Rides");
		colMetroCardUsedRides.setMinWidth(100);
		colMetroCardUsedRides.setCellValueFactory(new PropertyValueFactory<>("usedRides"));
		metroCardsTable.getColumns().addAll(colMetroCardId, colMetroCardDateOfCreation, colMetroCardActiveRides, colMetroCardUsedRides);

		refresh();
	}

	public void refresh() {
		metroCards = FXCollections.observableArrayList(metroCardOverviewPaneController.getMetroCardsDatabase().load().values());
		metroCardsTable.setItems(metroCards);
		metroCardsTable.refresh();
	}
}
