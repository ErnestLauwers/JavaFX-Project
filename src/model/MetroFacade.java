package model;

import model.database.MetroCardDatabase;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;

import java.io.IOException;
import java.util.*;

public class MetroFacade implements Subject {

    private final MetroCardDatabase metroCardDatabase;
    private Map<MetroEventEnum, List<Observer>> observers;
    private boolean statusStation;
    private LoadSaveStrategyFactory loadSaveStrategyFactory;

    public MetroFacade() {
        observers = new HashMap<>();
        metroCardDatabase = MetroCardDatabase.getInstance();
        statusStation = false;
        loadSaveStrategyFactory = new LoadSaveStrategyFactory();
        for (MetroEventEnum event :  MetroEventEnum.values()) {
            observers.put(event, new ArrayList<Observer>());
        }
    }

    public void openMetroStation() throws IOException {
        Properties properties = new Properties();
        LoadSaveStrategy loadSaveStrategy = (LoadSaveStrategy) loadSaveStrategyFactory.createLoadSaveStrategy(properties.getProperty("LoadSaveStrategy"));
        metroCardDatabase.setLoadSaveStrategy(loadSaveStrategy);
        metroCardDatabase.load();
        notifyObservers(MetroEventEnum.OPEN_METROSTATION);
    }

    public void setStationStatus() {
        this.statusStation =  true;
    }

    public boolean getStationStatus() {
        return this.statusStation;
    }

    public Map<Integer, Integer> getMetroCards() {
        return metroCardDatabase.getMetroCards();
    }

    public MetroCardDatabase getMetroCardDatabase() {
        return metroCardDatabase;
    }

    /*public void saveLoadSaveStrategieToFile(String strategy) {
        Map<String, String> lsMap = new HashMap<>();
        lsMap.put("strategy", strategy);

        LoadSaveStrategyFactory.createLoadSaveStrategy("TXTSETTINGS").save(lsMap);
    }*/


    @Override
    public void notifyObservers(MetroEventEnum event) {
        for (Observer observer : observers.get(event)) {
            observer.update();
        }
    }

    @Override
    public void registerObserver(MetroEventEnum event, Observer observer) {
        observers.get(event).add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}

