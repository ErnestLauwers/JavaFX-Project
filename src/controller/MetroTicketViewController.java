package controller;

import model.MetroEventsEnum;
import model.MetroFacade;
import model.Observer;
import view.MetroTicketView;

import java.util.ArrayList;

public class MetroTicketViewController implements Observer {

    private MetroFacade metroFacade;
    private MetroTicketView metroTicketView;

    public MetroTicketViewController(MetroFacade facade, MetroTicketView metroTicketView) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(MetroEventsEnum.OPEN_METROSTATION, this);
        this.metroTicketView = metroTicketView;
    }

    @Override
    public void update() {
        ArrayList<Integer> metroCardsId = this.metroFacade.getMetroCardIDList();
        this.metroTicketView.updateMetroCardIDList(metroCardsId);
    }
}
