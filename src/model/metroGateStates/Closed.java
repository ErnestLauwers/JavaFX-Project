/**
 * @author Ernest Lauwers
 */
package model.metroGateStates;

import model.MetroGate;

public class Closed extends MetroGateState {

    private MetroGate metroGate;

    public Closed(MetroGate metroGate) {
        super(metroGate);
    }
}
