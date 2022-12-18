package controller;

import model.MetroEventsEnum;
import model.MetroFacade;
import model.Observer;
import view.MetroStationView;

import java.util.ArrayList;

public class MetroStationViewController implements Observer {

    private MetroFacade metroFacade;
    private MetroStationView metroStationView;

    public MetroStationViewController(MetroFacade facade, MetroStationView metroStationView) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(MetroEventsEnum.OPEN_METROSTATION, this);
        this.metroStationView = metroStationView;
    }

    @Override
    public void update() {
        ArrayList<Integer> metroCardsId = this.metroFacade.getMetroCardIDList();
        this.metroStationView.updateMetroCardIDList(metroCardsId);
    }
}
