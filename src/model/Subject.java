/**
 * @author Ernest Lauwers
 */
package model;

public interface Subject {
    void notifyObservers(MetroEventsEnum event);
    void registerObserver(MetroEventsEnum event, Observer observer);
    void removeObserver(Observer observer);
}
