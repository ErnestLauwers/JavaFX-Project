package model.database.loadSaveStrategies;

import java.util.Map;
import java.util.TreeMap;

public interface LoadSaveStrategy<K, V> {
    TreeMap<K, V> load();
    void save(Map<K, V> input);
}