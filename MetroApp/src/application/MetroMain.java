package application;
	
import Controller.AdminViewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.MetroFacade;
import model.database.MetroCardDatabase;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;
import view.AdminView;
import view.MetroStationView;
import view.MetroTicketView;
import view.panels.MetroCardOverviewPane;

import java.util.Observable;


public class MetroMain extends Application {

	public static String metrocardStrategy;

	@Override
	public void start(Stage primaryStage) {
		metrocardStrategy = "TXTMETROCARD";
		MetroFacade metroFacade = new MetroFacade();

		AdminView adminView = new AdminView(new AdminViewController(metroFacade));
		MetroTicketView metroTicketView = new MetroTicketView();
		MetroStationView metroStationView = new MetroStationView();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
