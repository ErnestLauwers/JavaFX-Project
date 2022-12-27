package controller;

import model.MetroEventsEnum;
import model.MetroFacade;
import model.Observer;
import view.panels.ControlCenterPane;

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

    public void closeMetroStation() throws IOException {
        metroFacade.closeMetroStation();
    }

    public void setStationStatus( ) {
        metroFacade.setStationStatus();
    }

    public boolean getStatusStation() {
        return metroFacade.getStationStatus();
    }

    public void setGateStatus(int gateId) throws IOException {
        metroFacade.setGateStatus(gateId);
    }

    public boolean getGateStatus(int gateId) {
        return metroFacade.getGateStatus(gateId);
    }

    @Override
    public void update() {

    }
}
