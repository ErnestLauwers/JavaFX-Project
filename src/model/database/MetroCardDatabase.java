package model.database;

import application.MetroMain;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;
import model.MetroCard;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MetroCardDatabase {

    private Map<String, MetroCard> metroCardMap;
    private LoadSaveStrategy loadSaveStrategy;
    private static MetroCardDatabase instance;

    public MetroCardDatabase(String strategy) {
        metroCardMap = new TreeMap<>();
        setLoadSaveStrategy(LoadSaveStrategyFactory.createLoadSaveStrategy(strategy));
    }

    public Map<String, Integer> getMetroCards() {
        Map<String, Integer> activeRides = new HashMap<>();
        for (String metroCard : metroCardMap.keySet()) {
            activeRides.put(metroCard, metroCardMap.get(metroCard).getActiveRides());
        }
        return activeRides;
    }

    public void setLoadSaveStrategy(LoadSaveStrategy strategy) {
        this.loadSaveStrategy = strategy;
    }


    public static MetroCardDatabase getInstance() {
        if (instance == null) {
            instance = new MetroCardDatabase(MetroMain.strategyMetroCard);
        }
        return instance;
    }

    public Map<String, MetroCard> load() {
        metroCardMap = loadSaveStrategy.load();
        return metroCardMap;
    }

    public void save() {
        loadSaveStrategy.save(metroCardMap);
    }
}
