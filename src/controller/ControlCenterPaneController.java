/**
 * @author Benjamin Joossens
 * @author Ernest Lauwers
 */
package controller;

import model.MetroEventsEnum;
import model.MetroFacade;
import model.Observer;
import view.panels.ControlCenterPane;

import java.io.IOException;
import java.text.DecimalFormat;

import static java.lang.Math.round;

public class ControlCenterPaneController implements Observer {

    private MetroFacade metroFacade;
    private ControlCenterPane controlCenterPane;
    DecimalFormat df = new DecimalFormat("0.00");


    public ControlCenterPaneController(MetroFacade facade) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(MetroEventsEnum.OPEN_METROSTATION, this);
        this.metroFacade.registerObserver(MetroEventsEnum.SCAN_METROGATE, this);
        this.metroFacade.registerObserver(MetroEventsEnum.ILLEGAL_WALKTHROUGH, this);
        this.metroFacade.registerObserver(MetroEventsEnum.INVALID_SCAN, this);
        this.metroFacade.registerObserver(MetroEventsEnum.BUY_METROCARD_TICKETS, this);
    }

    public void openMetroStation() throws IOException {
        metroFacade.openMetroStation();
    }

    public void closeMetroStation() throws IOException {
        metroFacade.closeMetroStation();
    }

    public void setStationStatus() {
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

    public int getScannedCards(int gateId) {
        return this.metroFacade.getScannedCards(gateId);
    }

    public int getLastUsedGate() {
        return metroFacade.getLastUsedGate();
    }

    public void setControlCenterPane(ControlCenterPane controlCenterPane) {
        this.controlCenterPane = controlCenterPane;
    }

    public int getLastUsedCard() {
        return metroFacade.getLastUsedCard();
    }

    public String getGateEvent(int gateId) {
        return metroFacade.getGateEvent(gateId);
    }

    @Override
    public void update(String event) {
        if (event.equals(MetroEventsEnum.INVALID_SCAN.toString())) {
            this.controlCenterPane.updateAlertsInvalid(getLastUsedCard(), getGateEvent(getLastUsedGate()));
        }
        if (event.equals(MetroEventsEnum.SCAN_METROGATE.toString())) {
            this.controlCenterPane.updateScannedCards(getScannedCards(1), getScannedCards(2), getScannedCards(3));
        }
        if (event.equals(MetroEventsEnum.ILLEGAL_WALKTHROUGH.toString())) {
            this.controlCenterPane.updateAlerts(getLastUsedGate());
        }
        if (event.equals(MetroEventsEnum.BUY_METROCARD_TICKETS.toString())) {
            int soldTickets = this.metroFacade.getSoldTickets();
            double totalSoldTickets = Double.parseDouble(df.format(this.metroFacade.getTotalPrice()));
            controlCenterPane.updateSoldTickets(soldTickets, totalSoldTickets);
        }
    }
}