/**
 * @author Ernest Lauwers
 */
package model.metroGateStates;

import model.MetroGate;

public class Inactive extends MetroGateState {

    private MetroGate metroGate;

    public Inactive(MetroGate metroGate) {
        super(metroGate);
    }
}
