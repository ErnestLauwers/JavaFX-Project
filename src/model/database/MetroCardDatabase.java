package model.database;

import application.MetroMain;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;
import model.MetroCard;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MetroCardDatabase {

    private TreeMap<Integer, MetroCard> metroCardMap;
    private LoadSaveStrategy loadSaveStrategy;
    private static MetroCardDatabase instance;

    public MetroCardDatabase(String strategy) {
        metroCardMap = new TreeMap<>();
        setLoadSaveStrategy(LoadSaveStrategyFactory.createLoadSaveStrategy(strategy));
    }

    public Map<Integer, Integer> getMetroCards() {
        Map<Integer, Integer> activeRides = new HashMap<>();
        for (int metroCardId : metroCardMap.keySet()) {
            activeRides.put(metroCardId, metroCardMap.get(metroCardId).getActiveRides());
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

    public TreeMap<Integer, MetroCard> load() {
        metroCardMap = loadSaveStrategy.load();
        return metroCardMap;
    }

    public void save() {
        loadSaveStrategy.save(metroCardMap);
    }
}
