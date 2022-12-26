package model.database.utilities;

import model.MetroCard;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class TekstLoadSaveTemplate<K, V> {

    public TreeMap<K, V> load(File file) {
        TreeMap<K, V> returnMap = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null && !line.trim().equals("")) {
                String[] tokens = line.split(";");
                V element = maakObject(tokens);
                K key = getKey(tokens);
                returnMap.put(key, element);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnMap;
    }

    public void save(Map<K, V> map, File file) {
        StringBuilder builder = new StringBuilder();
        for (HashMap.Entry<K, V> entry : map.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            MetroCard metroCard = (MetroCard) value;
            String date = metroCard.getDateOfCreation();
            int activeRides = metroCard.getActiveRides();
            int usedRides = metroCard.getUsedRides();
            builder.append(key).append(";").append(date).append(";").append(activeRides).append(";").append(usedRides).append("\n");
        }
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(builder.toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract V maakObject(String[] tokens);

    protected abstract K getKey(String[] tokens);
}
