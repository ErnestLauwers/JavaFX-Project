/**
 * @author Ernest Lauwers
 */
package model.database.loadSaveStrategies;

import model.MetroCard;
import model.database.utilities.ExcelLoadSaveTemplate;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public class MetroCardsExcelLoadSaveStrategy extends ExcelLoadSaveTemplate implements LoadSaveStrategy {

    private final File file = new File("src/bestanden/metrocards.xls");

    @Override
    public TreeMap load() {
        return super.load(file);
    }

    @Override
    public void save(Map input) {
        super.save(input, file);
    }

    @Override
    protected MetroCard maakObject(String[] tokens) {
        MetroCard metroCard = new MetroCard(Integer.parseInt(tokens[0]), tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
        return metroCard;
    }

    @Override
    protected String getKey(String[] tokens) {
        return tokens[0];
    }
}

