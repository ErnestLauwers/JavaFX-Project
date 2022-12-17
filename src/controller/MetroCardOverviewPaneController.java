package controller;

import model.MetroEventEnum;
import model.MetroFacade;
import model.Observer;
import model.database.MetroCardDatabase;

public class MetroCardOverviewPaneController implements Observer {

    private MetroFacade metroFacade;

    public MetroCardOverviewPaneController(MetroFacade facade) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(MetroEventEnum.OPEN_METROSTATION, this);
    }

    public MetroCardDatabase getMetroCardsDatabase() {
        return metroFacade.getMetroCardDatabase();
    }

    @Override
    public void update() {

    }
}
