package model.database;

import application.MetroMain;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;
import model.MetroCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MetroCardDatabase {

    private TreeMap<String, MetroCard> metroCardMap;
    private LoadSaveStrategy loadSaveStrategy;
    private static MetroCardDatabase instance;

    public MetroCardDatabase(String strategy) {
        metroCardMap = new TreeMap<>();
        setLoadSaveStrategy(LoadSaveStrategyFactory.createLoadSaveStrategy(strategy));
    }

    public ArrayList<Integer> getMetroCardIDList() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (MetroCard metroCard : metroCardMap.values()) {
            ids.add(metroCard.getMetroCardId());
        }
        return ids;
    }

    public ArrayList<MetroCard> getMetroCardList() {
        return new ArrayList<>(metroCardMap.values());
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

    public void add(MetroCard newMetroCard) throws IOException {
        metroCardMap.put(String.valueOf(newMetroCard.getMetroCardId()), newMetroCard);
    }

    public int getNewId() {
        int currentSize = metroCardMap.size();
        return currentSize + 1;
    }

    public TreeMap<String, MetroCard> load() {
        metroCardMap = loadSaveStrategy.load();
        return metroCardMap;
    }

    public void save() {
        loadSaveStrategy.save(metroCardMap);
    }
}
