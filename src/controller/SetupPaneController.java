/**
 * @author Benjamin Joossens
 * @author Ernest Lauwers
 */
package controller;

import model.MetroEventsEnum;
import model.MetroFacade;
import model.Observer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SetupPaneController implements Observer {

    private MetroFacade metroFacade;

    public SetupPaneController(MetroFacade facade) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(MetroEventsEnum.OPEN_METROSTATION, this);
    }

    public void save(String strategy, String kortingen) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream;
        try {
            inputStream = new FileInputStream("src/bestanden/settings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        properties.setProperty("LoadSaveStrategy", strategy);
        properties.setProperty("Korting", kortingen);
        properties.store(new FileOutputStream("src/bestanden/settings.properties"), null);
    }

    public boolean getStatusStation() {
        return metroFacade.getStationStatus();
    }

    @Override
    public void update(String event) {

    }
}
