package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.MetroFacade;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;
import view.AdminView;
import view.MetroStationView;
import view.MetroTicketView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MetroMain extends Application {

	public static String strategyMetroCard;

	@Override
	public void start(Stage primaryStage) {
		Properties properties = new Properties();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("src/bestanden/settings.properties");
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (properties.getProperty("LoadSaveStrategy").equals("Text File")) {
			strategyMetroCard = "TXTMETROCARD";
		}
		if (properties.getProperty("LoadSaveStrategy").equals("Excel File")) {
			strategyMetroCard = "EXCELMETROCARD";
		}

		MetroFacade metroFacade = new MetroFacade();

		AdminView adminView = new AdminView();
		MetroTicketView metroTicketView = new MetroTicketView();
		MetroStationView metroStationView = new MetroStationView();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
