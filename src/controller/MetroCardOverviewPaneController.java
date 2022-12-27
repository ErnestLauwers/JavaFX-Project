package controller;

import model.MetroCard;
import model.MetroEventsEnum;
import model.MetroFacade;
import model.Observer;
import model.database.MetroCardDatabase;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;
import view.panels.MetroCardOverviewPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class MetroCardOverviewPaneController implements Observer {

    private MetroFacade metroFacade;
    private MetroCardOverviewPane metroCardOverviewPane;
    private LoadSaveStrategyFactory loadSaveStrategyFactory;

    public MetroCardOverviewPaneController(MetroFacade facade, MetroCardOverviewPane metroCardOverviewPane) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(MetroEventsEnum.OPEN_METROSTATION, this);
        this.metroCardOverviewPane = metroCardOverviewPane;
        this.loadSaveStrategyFactory = new LoadSaveStrategyFactory();
        this.metroFacade.registerObserver(MetroEventsEnum.SCAN_METROGATE, this);
    }

    public MetroCardDatabase getMetroCardsDatabase() {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/bestanden/settings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (properties.getProperty("LoadSaveStrategy").equals("Text File")) {
            LoadSaveStrategy loadSaveStrategy = (LoadSaveStrategy) loadSaveStrategyFactory.createLoadSaveStrategy("TXTMETROCARD");
            metroFacade.setLoadSaveStrategy(loadSaveStrategy);
        } else if (properties.getProperty("LoadSaveStrategy").equals("Excel File")){
            LoadSaveStrategy loadSaveStrategy = (LoadSaveStrategy) loadSaveStrategyFactory.createLoadSaveStrategy("EXCELMETROCARD");
            metroFacade.setLoadSaveStrategy(loadSaveStrategy);
        }
        return metroFacade.getMetroCardDatabase();
    }

    @Override
    public void update(String event) {
        ArrayList<MetroCard> metroCards = this.metroFacade.getMetroCardList();
        this.metroCardOverviewPane.updateMetroCardList(metroCards);
    }
}
