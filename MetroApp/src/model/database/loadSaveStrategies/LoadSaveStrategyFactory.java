package model.database.loadSaveStrategies;

public class LoadSaveStrategyFactory {

    public static LoadSaveStrategy createLoadSaveStrategy(String name) {
        LoadSaveStrategyEnum loadSaveStrategyEnum = LoadSaveStrategyEnum.valueOf(name);
        String className = loadSaveStrategyEnum.getKlasseNaam();
        LoadSaveStrategy loadSaveStrategy = null;
        try {
            Class<?> dbClass = Class.forName(className);
            Object dbObject = dbClass.newInstance();
            loadSaveStrategy = (LoadSaveStrategy) dbObject;
        } catch (Exception e){
            e.printStackTrace();
        }
        return loadSaveStrategy;
    }
}
