package model.database.utilities;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class TekstLoadSaveTemplate <K,V> {

    public final Map<K,V> load(File file) {
        Map<K,V> returnMap = new HashMap<K,V>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while (line != null && !line.trim().equals("")) {
                String[] tokens = line.split(",");
                V element = maakObject(tokens);
                K key = getKey(tokens);
                returnMap.put(key,element);
                line =  reader.readLine();
            }
        } catch (IOException e) {}
        return returnMap;
    }

    public final void save(Map<K,V> map, File file) {
        StringBuilder builder = new StringBuilder();
        for (HashMap.Entry<K, V> entry : map.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            builder.append(key).append(",").append(value.toString()).append("\n");
        }
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(builder.toString());
            myWriter.close();
        } catch (IOException e) {}
    }

    protected abstract V maakObject(String[] tokens);
    protected abstract K getKey(String[] tokens);
}
