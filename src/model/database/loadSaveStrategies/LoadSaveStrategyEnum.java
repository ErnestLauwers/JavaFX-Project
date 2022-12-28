/**
 * @author Ernest Lauwers
 */
package model.database.loadSaveStrategies;

public enum LoadSaveStrategyEnum {
    EXCELMETROCARD("ExcelMetroCard", "model.database.loadSaveStrategies.MetroCardsExcelLoadSaveStrategy"),
    TXTMETROCARD("TextMetroCard", "model.database.loadSaveStrategies.MetroCardsTekstLoadSaveStrategy");

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
