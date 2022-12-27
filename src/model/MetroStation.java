package model;

import model.metroGateStates.MetroGateState;

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
        /*if (gateId == 1) {
            gate1.scanMetroGate();
        }
        if (gateId == 2) {
            gate2.scanMetroGate();
        }
        if (gateId == 3) {
            gate3.scanMetroGate();
        }*/
    }

    public void setGateStatus(int gateId) {
        for (MetroGate gate : metroGates) {
            if (gate.getGateId() == gateId) {
                gate.setGateStatus();
            }
        }
    }

    public boolean getGateStatus(int gateId) {
        return !metroGates.get(gateId - 1).getNow().getClass().getSimpleName().equals("Inactive");
    }
}