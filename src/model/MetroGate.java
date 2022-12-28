/**
 * @author Ernest Lauwers
 */
package model;

import model.metroGateStates.Closed;
import model.metroGateStates.Inactive;
import model.metroGateStates.MetroGateState;
import model.metroGateStates.Opened;

public class MetroGate {

    private MetroGateState inactive;
    private MetroGateState closed;
    private MetroGateState opened;
    private MetroGateState now;
    private int gateId;
    private String gateEvent;
    private int scannedCards;

    public MetroGate(int gateId) {
        if (gateId <= 0) {
            throw new IllegalArgumentException("The gate id must be positive!");
        }
        this.gateEvent = "";
        this.gateId = gateId;
        this.inactive = new Inactive(this);
        this.closed = new Closed(this);
        this.opened = new Opened(this);
        this.now = inactive;
        this.scannedCards = 0;
    }

    public void scanMetroGate() {
        now = opened;
        setGateEvent("valid");
        addScannedCards();
    }

    public void walkThroughGate() {
        now = closed;
    }

    public int getScannedCards() {
        return this.scannedCards;
    }

    public void addScannedCards() {
        this.scannedCards++;
    }

    public void setGateEvent(String event) {
        if (event.isEmpty()) {
            throw new IllegalArgumentException("The gate event cannot be empty");
        }
        this.gateEvent = event;
    }

    public void setGateStatus() {
        if (now == inactive) {
            now = closed;
        } else {
            now = inactive;
        }
    }

    protected void setNow(MetroGateState metroGateState) {
        if (metroGateState == null) {
            throw new IllegalArgumentException("The metro gate state cannot be empty!");
        }
        this.now = metroGateState;
    }

    public String getGateEvent() {
        return this.gateEvent;
    }

    public int getGateId() {
        return this.gateId;
    }

    public MetroGateState getNow() {
        return this.now;
    }
}
