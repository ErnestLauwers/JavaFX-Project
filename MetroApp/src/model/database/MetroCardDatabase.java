package model.database;

import application.MetroMain;
import model.Metrocard;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyEnum;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MetroCardDatabase {

    private static MetroCardDatabase instance;
    private Map<String, Metrocard> metrocardsTreeMap;
    private LoadSaveStrategy loadSaveStrategy;

    public MetroCardDatabase (String strategy) {
        metrocardsTreeMap = new TreeMap<>();
        setLoadSaveStrategy(LoadSaveStrategyFactory.createLoadSaveStrategy(strategy));
    }

    private void setLoadSaveStrategy(LoadSaveStrategy loadSaveStrategy) {
        this.loadSaveStrategy = loadSaveStrategy;
    }

    public static MetroCardDatabase getInstance() {
        if (instance == null) {
            instance = new MetroCardDatabase(MetroMain.metrocardStrategy);
        }
        return instance;
    }

    public Map<String, Integer> getMetrocardList() {
        Map<String, Integer> metrocardList = new HashMap<>();
        for (String metroCard : metrocardsTreeMap.keySet()) {
            int intMetroId = Integer.parseInt(metrocardsTreeMap.get(metroCard).getMetrocardId());
            metrocardList.put(metroCard, intMetroId);
        }
        return metrocardList;
    }

    public Metrocard getMetrocard(String naam) {
        return metrocardsTreeMap.get(naam);
    }

    public Map<String, Metrocard> load() {
        metrocardsTreeMap = loadSaveStrategy.load();
        return metrocardsTreeMap;
    }

    public void save() {
        loadSaveStrategy.save(metrocardsTreeMap);
    }
}
