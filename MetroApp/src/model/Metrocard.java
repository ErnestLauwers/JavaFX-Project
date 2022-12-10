package model;

import java.time.LocalDate;

public class Metrocard {

    private String metrocardId;
    private double dateOfCreation;
    private int activeRides;
    private int usedRides;

    public Metrocard(String metrocardId, double dateOfCreation, int activeRides, int usedRides) {
        this.metrocardId = metrocardId;
        this.dateOfCreation = dateOfCreation;
        this.activeRides = activeRides;
        this.usedRides = usedRides;
    }

    public Metrocard() {

    }

    public String getMetrocardId() {
        return metrocardId;
    }
}
