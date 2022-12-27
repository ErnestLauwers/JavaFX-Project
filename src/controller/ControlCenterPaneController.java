package controller;

import model.MetroCard;
import model.MetroEventsEnum;
import model.MetroFacade;
import model.Observer;
import view.AdminView;
import view.panels.ControlCenterPane;

import java.io.IOException;
import java.util.ArrayList;

public class ControlCenterPaneController implements Observer {

    private MetroFacade metroFacade;
    ControlCenterPane controlCenterPane;

    public ControlCenterPaneController(MetroFacade facade) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(MetroEventsEnum.OPEN_METROSTATION, this);
    }

    public void setControlCenterPane(ControlCenterPane controlCenterPane) {
        this.controlCenterPane = controlCenterPane;
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
    public int getSoldTickets() {
        return metroFacade.getSoldTickets();
    }

    public double getTotalSoldTickets() {
        return metroFacade.getTotalPrice();
    }

    @Override
    public void update() {
        int soldTickets = this.metroFacade.getSoldTickets();
        double totalSoldTickets = this.metroFacade.getTotalPrice();
        controlCenterPane.updateSoldTickets(soldTickets, totalSoldTickets);
    }
}
