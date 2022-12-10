package model.database.loadSaveStrategies;

import java.util.Map;

public interface LoadSaveStrategy<K,V> {
    Map<K,V> load();
    void save(Map<K,V> input);
}