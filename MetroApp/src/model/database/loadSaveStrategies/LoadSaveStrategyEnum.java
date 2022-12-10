package model.database.loadSaveStrategies;

public enum LoadSaveStrategyEnum {
    TXTMETROCARD("Tekst Metrocard", "model.database.loadSaveStrategies.MetroCardsTekstLoadSaveStrategy"),
    EXCELMETROCARD("Excel Metrocard", "model.database.loadSaveStrategies.MetroCardsExcelLoadSaveStrategy");

    private final String omschrijving;
    private final String klasseNaam;

    LoadSaveStrategyEnum(String omschrijving, String klasseNaam) {
        this.omschrijving = omschrijving;
        this.klasseNaam = klasseNaam;
    }

    public String getOmschrijving() { return omschrijving; }
    public String getKlasseNaam() { return klasseNaam; }

}
