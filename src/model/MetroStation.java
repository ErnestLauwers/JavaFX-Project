package model;

import java.util.ArrayList;

public class MetroStation {

    private ArrayList<MetroGate> metroGates;

    public MetroStation () {
        this.metroGates = new ArrayList<>();
        this.metroGates.add(new MetroGate(1));
        this.metroGates.add(new MetroGate(2));
        this.metroGates.add(new MetroGate(3));
    }

    public void scanMetroGate(int gateId) {
        for (MetroGate gate : metroGates) {
            if (gate.getGateId() == gateId) {
                gate.scanMetroGate();
            }
        }
    }

    public void walkThroughGate(int gateId) {
        for (MetroGate gate : metroGates) {
            if (gate.getGateId() == gateId) {
                gate.walkThroughGate();
            }
        }
    }

    public void setMetroGateEvent(int gateId, String event) {
        for (MetroGate gate : metroGates) {
            if (gate.getGateId() == gateId) {
                gate.setGateEvent(event);
            }
        }
    }
    public void setGateStatus(int gateId) {
        for (MetroGate gate : metroGates) {
            if (gate.getGateId() == gateId) {
                gate.setGateStatus();
            }
        }
    }

    public String getGateEvent(int gateId) {
        for (MetroGate gate : metroGates) {
            if (gate.getGateId() == gateId) {
                return gate.getGateEvent();
            }
        }
        return "";
    }

    public int getScannedCards(int gateId) {
        for (MetroGate gate : metroGates) {
            if (gate.getGateId() == gateId) {
                return gate.getScannedCards();
            }
        }
        return 0;
    }

    public String getGateNow(int gateId) {
        for (MetroGate gate : metroGates) {
            if (gate.getGateId() == gateId) {
                return gate.getNow().getClass().getSimpleName();
            }
        }
        return "";
    }

    public boolean getGateStatus(int gateId) {
        return !metroGates.get(gateId - 1).getNow().getClass().getSimpleName().equals("Inactive");
    }
}