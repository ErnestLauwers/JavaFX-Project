package application;

import controller.AdminViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Facade;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;
import view.AdminView;
import view.MetroStationView;
import view.MetroTicketView;

public class MetroMain extends Application {

	public static String strategyMetroCard;

	@Override
	public void start(Stage primaryStage) {
		if (LoadSaveStrategyFactory.createLoadSaveStrategy("TXTSETTINGS").load().get("LoadSave").equals("Text file")) {
			strategyMetroCard = "TXTMETROCARD";
		}
		if (LoadSaveStrategyFactory.createLoadSaveStrategy("TXTSETTINGS").load().get("LoadSave").equals("Excel file")) {
			strategyMetroCard = "EXCELMETROCARD";
		}

		Facade metroFacade = new Facade();

		AdminView adminView = new AdminView(new AdminViewController(metroFacade));
		MetroTicketView metroTicketView = new MetroTicketView();
		MetroStationView metroStationView = new MetroStationView();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
