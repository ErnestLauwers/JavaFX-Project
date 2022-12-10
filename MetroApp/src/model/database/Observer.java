package model.database;

import model.Subject;

public interface Observer {
    void update(MetroEvent metroEvent);
}
