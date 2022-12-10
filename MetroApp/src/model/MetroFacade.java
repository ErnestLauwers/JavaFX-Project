package model;

import model.database.MetroCardDatabase;
import model.database.MetroEvent;

import java.util.ArrayList;
import java.util.Observer;

public class MetroFacade implements Subject {

    private final MetroCardDatabase metroCardDatabase;
    private ArrayList<Observer> observers;
    private String loadSaveStrategy;

    public MetroFacade() {
        observers = new ArrayList<Observer>();
        metroCardDatabase = MetroCardDatabase.getInstance();
        this.loadSaveStrategy = "TXTMETROCARD";
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(MetroEvent metroEvent) {
        /*for(Observer observer : observers) {
            observer.update(metroEvent);
        }*/
    }

    public MetroCardDatabase getMetroCardDatabase() {
        return metroCardDatabase;
    }

    public String getLoadSaveStrategy() {
        return this.loadSaveStrategy;
    }
/*
    public void setLoadSaveStrategy() {
        this.loadSaveStrategy = (String) LoadSaveStrategyFactory.createLoadSaveStrategy("TXTMETROCARD");
    }*/
}
