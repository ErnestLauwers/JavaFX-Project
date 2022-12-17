package model;

public interface Subject {
    void notifyObservers(MetroEventEnum event);
    void registerObserver(MetroEventEnum event, Observer observer);
    void removeObserver(Observer observer);
}
