package model;

public interface Subject {
    void notifyObservers(MetroEvent event);
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
}
