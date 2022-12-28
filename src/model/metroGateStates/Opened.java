/**
 * @author Ernest Lauwers
 */
package model.metroGateStates;

import model.MetroGate;

public class Opened extends MetroGateState {

    private MetroGate metroGate;

    public Opened(MetroGate metroGate) {
        super(metroGate);
    }
}
