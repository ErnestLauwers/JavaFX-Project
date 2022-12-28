/**
 * @author Benjamin Joossens
 * @author Ernest Lauwers
 */
package model;

import java.time.LocalDate;

public class MetroCard {
    private int metroCardId;
    private String dateOfCreation;
    private int activeRides;
    private int usedRides;

    public MetroCard(int metroCardId, String dateOfCreation, int activeRides, int usedRides) {
        this.setMetroCardId(metroCardId);
        this.setDateOfCreation(dateOfCreation);
        this.setActiveRides(activeRides);
        this.setUsedRides(usedRides);
    }

    protected void setMetroCardId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("The id cannot be negative");
        }
        this.metroCardId = id;
    }

    protected void setDateOfCreation(String dateOfCreation) {
        if (dateOfCreation.isEmpty()) {
            throw new IllegalArgumentException("The date of creation cannot be empty");
        }
        this.dateOfCreation = dateOfCreation;
    }

    protected void setActiveRides(int activeRides) {
        if (activeRides < 0) {
            throw new IllegalArgumentException("The active rides cannot be negative");
        }
        this.activeRides = activeRides;
    }

    protected void setUsedRides(int usedRides) {
        if (usedRides < 0) {
            throw new IllegalArgumentException("The used rides cannot be negative");
        }
        this.usedRides = usedRides;
    }

    public int getMetroCardId() {
        return this.metroCardId;
    }

    public String getDateOfCreation() {
        return this.dateOfCreation;
    }

    public int getActiveRides() {
        return this.activeRides;
    }

    public int getUsedRides() {
        return this.usedRides;
    }

    public boolean isExpired() {
        int maand = Integer.parseInt(dateOfCreation.split("#")[0]);
        int jaar = Integer.parseInt(dateOfCreation.split("#")[1]);
        return LocalDate.now().isAfter(LocalDate.of(jaar, maand, 1).plusYears(1).plusMonths(1));
    }
}