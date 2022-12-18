package controller;

import model.MetroEventsEnum;
import model.MetroFacade;
import model.Observer;

import java.io.IOException;

public class ControlCenterPaneController implements Observer {

    private MetroFacade metroFacade;

    public ControlCenterPaneController(MetroFacade facade) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(MetroEventsEnum.OPEN_METROSTATION, this);
    }

    public void openMetroStation() throws IOException {
        metroFacade.openMetroStation();
    }

    public void setStationStatus() {
        metroFacade.setStationStatus();
    }

    @Override
    public void update() {

    }
}
