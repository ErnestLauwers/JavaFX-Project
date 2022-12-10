package model.database.loadSaveStrategies;

import model.Metrocard;
import model.database.utilities.TekstLoadSaveTemplate;

import java.io.File;
import java.util.Map;

public class MetroCardsTekstLoadSaveStrategy extends TekstLoadSaveTemplate implements LoadSaveStrategy{

    private final File file = new File("src/bestanden/metrocards.txt");

    @Override
    public Map load() {
        return super.load(file);
    }

    @Override
    public void save(Map input) {
        super.save(input, file);
    }

    @Override
    protected String getKey(String[] tokens) {
        return tokens[0];
    }

    @Override
    protected Metrocard maakObject(String[] tokens) {
        Metrocard metrocard = new Metrocard(tokens[0], Double.parseDouble(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
        return metrocard;
    }
}
