package model;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.database.MetroCardDatabase;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

public class MetroFacade implements Subject {

    private final MetroCardDatabase metroCardDatabase;
    private Map<MetroEventsEnum, List<Observer>> observers;
    private boolean statusStation;
    private LoadSaveStrategyFactory loadSaveStrategyFactory;

    public MetroFacade() {
        observers = new HashMap<>();
        metroCardDatabase = MetroCardDatabase.getInstance();
        statusStation = false;
        loadSaveStrategyFactory = new LoadSaveStrategyFactory();
        for (MetroEventsEnum event :  MetroEventsEnum.values()) {
            observers.put(event, new ArrayList<Observer>());
        }
    }

    public void openMetroStation() throws IOException {
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
            metroCardDatabase.setLoadSaveStrategy(loadSaveStrategy);
        } else if (properties.getProperty("LoadSaveStrategy").equals("Excel File")){
            LoadSaveStrategy loadSaveStrategy = (LoadSaveStrategy) loadSaveStrategyFactory.createLoadSaveStrategy("EXCELMETROCARD");
            metroCardDatabase.setLoadSaveStrategy(loadSaveStrategy);
        }
        metroCardDatabase.load();
        notifyObservers(MetroEventsEnum.OPEN_METROSTATION);
    }

    public void buyMetroCard() throws IOException{
        int newId = metroCardDatabase.getNewId();
        String month = String.valueOf(LocalDate.now().getMonthValue());
        String year = String.valueOf(LocalDate.now().getYear());
        String dateOfCreation = month + "#" + year;
        MetroCard newMetroCard = new MetroCard(newId, dateOfCreation, 5, 0);
        metroCardDatabase.add(newMetroCard);
        notifyObservers(MetroEventsEnum.BUY_METROCARD);
        notifyObservers(MetroEventsEnum.OPEN_METROSTATION);
    }

    public void setStationStatus() {
        this.statusStation =  true;
    }

    public boolean getStationStatus() {
        return this.statusStation;
    }

    public ArrayList<Integer> getMetroCardIDList() {
        return metroCardDatabase.getMetroCardIDList();
    }

    public ArrayList<MetroCard> getMetroCardList() {
        return metroCardDatabase.getMetroCardList();
    }

    public MetroCardDatabase getMetroCardDatabase() {
        return metroCardDatabase;
    }

    public void setLoadSaveStrategy(LoadSaveStrategy strategy) {
        metroCardDatabase.setLoadSaveStrategy(strategy);
    }

    @Override
    public void notifyObservers(MetroEventsEnum event) {
        for (Observer observer : observers.get(event)) {
            observer.update();
        }
    }

    @Override
    public void registerObserver(MetroEventsEnum event, Observer observer) {
        observers.get(event).add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}