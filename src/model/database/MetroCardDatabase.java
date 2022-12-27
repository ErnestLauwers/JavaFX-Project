package model.database;

import application.MetroMain;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;
import model.MetroCard;

import java.io.IOException;
import java.time.LocalDate;
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

    public MetroCard getMetroCard(int id) {
        for(MetroCard m : getMetroCardList()){
            if (m.getMetroCardId() == id) {
                return m;
            }
        }
        return null;
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

    public String validateMetroCard(int metroCardId) {
        MetroCard scannedCard = getMetroCard(metroCardId);
        int monthNow = Integer.parseInt(String.valueOf(LocalDate.now().getMonthValue()));
        int yearNow = Integer.parseInt(String.valueOf(LocalDate.now().getYear()));
        if (scannedCard.getDateOfCreation().length() == 7) {
            int monthScanned = Integer.parseInt(scannedCard.getDateOfCreation().substring(0,2));
            int yearScanned = Integer.parseInt(scannedCard.getDateOfCreation().substring(scannedCard.getDateOfCreation().length() - 4));
            if (yearNow - yearScanned >= 2 || yearNow - yearScanned == 1 && monthNow - monthScanned >= 1) {
                return "expired";
            }
        } else {
            int monthScanned = Integer.parseInt(scannedCard.getDateOfCreation().substring(0,1));
            int yearScanned = Integer.parseInt(scannedCard.getDateOfCreation().substring(scannedCard.getDateOfCreation().length() - 4));
            if (yearNow - yearScanned >= 2 || yearNow - yearScanned == 1 && monthNow - monthScanned >= 1) {
                return "expired";
            }
        }
        if (scannedCard.getActiveRides() <= 0) {
            return "noRides";
        }
        return "valid";
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
