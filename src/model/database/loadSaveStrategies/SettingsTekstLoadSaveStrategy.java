package model.database.loadSaveStrategies;

import model.database.utilities.TekstLoadSaveTemplate;

import java.io.File;
import java.util.Map;

public class SettingsTekstLoadSaveStrategy extends TekstLoadSaveTemplate implements LoadSaveStrategy {

    private final File file = new File("src/bestanden/settings.txt");

    @Override
    public Map load() {
        return super.load(file);
    }

    @Override
    public void save(Map input) {
        super.save(input, file);
    }

    @Override
    protected String maakObject(String[] tokens) {
        return tokens[1];
    }

    @Override
    protected String getKey(String[] tokens) {
        return tokens[0];
    }
}