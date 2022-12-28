/**
 * @author Ernest Lauwers
 */
package model.metroGateStates;

import model.MetroGate;

public abstract class MetroGateState {

    private MetroGate metroGate;

    public MetroGateState(MetroGate metroGate) {
        this.setMetroGate(metroGate);
    }

    public void scanMetroGate() {

    }

    protected void setMetroGate(MetroGate metroGate) {
        if (metroGate == null) {
            throw new IllegalArgumentException("The gate cannot be empty!");
        }
        this.metroGate = metroGate;
    }

    public MetroGate getMetroGate() {
        return this.metroGate;
    }
}