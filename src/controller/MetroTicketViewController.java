package controller;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.MetroEventsEnum;
import model.MetroFacade;
import model.Observer;
import view.MetroTicketView;

import java.io.IOException;
import java.util.ArrayList;

public class MetroTicketViewController implements Observer {

    private MetroFacade metroFacade;
    private MetroTicketView metroTicketView;

    public MetroTicketViewController(MetroFacade facade, MetroTicketView metroTicketView) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(MetroEventsEnum.OPEN_METROSTATION, this);
        this.metroTicketView = metroTicketView;
        this.metroFacade.registerObserver(MetroEventsEnum.BUY_METROCARD, this);
    }

    public void buyMetroCard() throws IOException{
        metroFacade.buyMetroCard();
    }

    @Override
    public void update() {
        ArrayList<Integer> metroCardsId = this.metroFacade.getMetroCardIDList();
        this.metroTicketView.updateMetroCardIDList(metroCardsId);
    }
}
