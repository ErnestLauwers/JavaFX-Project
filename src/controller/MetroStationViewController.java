/**
 * @author Ernest Lauwers
 */
package controller;

import model.MetroEventsEnum;
import model.MetroFacade;
import model.Observer;
import view.MetroStationView;

import java.io.IOException;
import java.util.ArrayList;

public class MetroStationViewController implements Observer {

    private MetroFacade metroFacade;
    private MetroStationView metroStationView;

    public MetroStationViewController(MetroFacade facade, MetroStationView metroStationView) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(MetroEventsEnum.OPEN_METROSTATION, this);
        this.metroStationView = metroStationView;
        this.metroFacade.registerObserver(MetroEventsEnum.SCAN_METROGATE, this);
        this.metroFacade.registerObserver(MetroEventsEnum.ACTIVATE_METROGATE, this);
        this.metroFacade.registerObserver(MetroEventsEnum.INVALID_SCAN, this);
        this.metroFacade.registerObserver(MetroEventsEnum.WALKTHROUGH_GATE, this);
        this.metroFacade.registerObserver(MetroEventsEnum.CLOSE_METROSTATION, this);
    }

    public boolean getStatusStation() {
        return metroFacade.getStationStatus();
    }

    public boolean getGateStatus(int gateId) {
        return metroFacade.getGateStatus(gateId);
    }

    public void scanMetroGate(int metroCardId, int gateId) throws IOException{
        metroFacade.scanMetroGate(metroCardId, gateId);
    }

    public void walkThroughGate(int gateId) throws IOException{
        metroFacade.walkThroughGate(gateId);
    }

    public String getGateEvent(int gateId) {
        return metroFacade.getGateEvent(gateId);
    }

    public String getGateNow(int gateId) {
        return this.metroFacade.getGateNow(gateId);
    }

    public int getLastUsedGate() {
        return metroFacade.getLastUsedGate();
    }

    @Override
    public void update(String event) {
        if (event.equals(MetroEventsEnum.OPEN_METROSTATION.toString())) {
            ArrayList<Integer> metroCardsId = this.metroFacade.getMetroCardIDList();
            this.metroStationView.updateMetroCardIDList(metroCardsId);
        }
        if (event.equals(MetroEventsEnum.ACTIVATE_METROGATE.toString())) {
            this.metroStationView.updateGates();
        }
        if (event.equals(MetroEventsEnum.INVALID_SCAN.toString()) || event.equals(MetroEventsEnum.SCAN_METROGATE.toString())) {
            this.metroStationView.updateScanGate(getLastUsedGate());
        }
        if (event.equals(MetroEventsEnum.WALKTHROUGH_GATE.toString())) {
            this.metroStationView.updateWalkThroughGate(getLastUsedGate());
        }
        if (event.equals(MetroEventsEnum.CLOSE_METROSTATION.toString())) {
            this.metroStationView.updateCloseStation();
        }
    }
}
