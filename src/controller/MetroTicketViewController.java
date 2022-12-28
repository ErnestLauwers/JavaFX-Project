/**
 * @author Benjamin Joossens
 */
package controller;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.MetroCard;
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
        this.metroFacade.registerObserver(MetroEventsEnum.CLOSE_METROSTATION, this);
    }

    public void buyMetroCard() throws IOException{
        metroFacade.buyMetroCard();
    }

    public double getPrice(boolean is24Min, boolean is64Plus, boolean isStudent, MetroCard metroCard) {
        return metroFacade.getPrice(is24Min, is64Plus, isStudent, metroCard);
    }

    public String getPriceText(boolean is24Min, boolean is64Plus, boolean isStudent, MetroCard metroCard) {
        return metroFacade.getPriceText(is24Min, is64Plus, isStudent, metroCard);
    }

    public void buyMetroCardTickets(int id , int amount, double totalPrice) throws IOException{
        metroFacade.buyMetroCardTickets(id, amount, totalPrice);
    }

    public boolean getStatusStation() {
        return metroFacade.getStationStatus();
    }

    @Override
    public void update(String event) {
        if (event.equals(MetroEventsEnum.OPEN_METROSTATION.toString())) {
            ArrayList<Integer> metroCardsId = this.metroFacade.getMetroCardIDList();
            this.metroTicketView.updateMetroCardIDList(metroCardsId);
        }
        if (event.equals(MetroEventsEnum.CLOSE_METROSTATION.toString())) {
            this.metroTicketView.updateCloseStation();
        }
    }
}
