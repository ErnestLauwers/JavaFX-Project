package model.database.loadSaveStrategies;

public enum LoadSaveStrategyEnum {
    EXCELMETROCARD("Excel Metro Card", "model.database.loadSaveStrategies.MetroCardsExcelLoadSaveStrategy"),
    TXTMETROCARD("Text Metro Card", "model.database.loadSaveStrategies.MetroCardsTekstLoadSaveStrategy"),
    TXTSETTINGS("Text Settings", "model.database.loadSaveStrategies.SettingsTekstLoadSaveStrategy");

    private final String description;
    private final String className;

    LoadSaveStrategyEnum(String description, String className) {
        this.description = description;
        this.className = className;
    }
    public String getClassName() {
        return className;
    }
    public String getDescription() {
        return description;
    }
}
