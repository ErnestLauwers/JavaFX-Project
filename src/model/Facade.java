package model;

import model.database.MetroCardDatabase;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;
import java.util.*;

public class Facade implements Subject {

    private final MetroCardDatabase metroCardDatabase;
    private String loadSaveStrategy;
    private ArrayList<Observer> observers;

    public Facade() {
        observers = new ArrayList<>();
        metroCardDatabase = MetroCardDatabase.getInstance();
        setLoadSaveStrategy();
    }

    public Map<String, Integer> getMetroCards() {
        return metroCardDatabase.getMetroCards();
    }

    public MetroCardDatabase getMetroCardDatabase() {
        return metroCardDatabase;
    }

    public void saveLoadSaveStrategieToFile(String strategy) {
        Map<String, String> lsMap = new HashMap<>();
        lsMap.put("strategy", strategy);

        LoadSaveStrategyFactory.createLoadSaveStrategy("TXTSETTINGS").save(lsMap);
    }

    public void setLoadSaveStrategy() {
        this.loadSaveStrategy = (String) LoadSaveStrategyFactory.createLoadSaveStrategy("TXTSETTINGS").load().get("strategy");
    }

    public String getLoadSaveStrategy() {
        return loadSaveStrategy;
    }

    public void notifyObservers(MetroEvent event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}

