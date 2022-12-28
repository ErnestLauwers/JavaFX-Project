/**
 * @author Benjamin Joossens
 */
package model;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.TicketPriceDecorator.TicketPrice;
import model.TicketPriceDecorator.TicketPriceDiscountEnum;
import model.TicketPriceDecorator.TicketPriceFactory;
import model.database.MetroCardDatabase;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class MetroFacade implements Subject {

    private final MetroCardDatabase metroCardDatabase;
    private Map<MetroEventsEnum, List<Observer>> observers;
    private boolean statusStation;
    private LoadSaveStrategyFactory loadSaveStrategyFactory;
    private MetroStation metroStation;
    private int totalAmountOfTickets = 0;
    private double totalPrice = 0.0;
    private int lastUsedGate;
    private int lastUsedCard;

    private static final DecimalFormat df = new DecimalFormat("0.00");


    public MetroFacade() {
        observers = new HashMap<>();
        metroCardDatabase = MetroCardDatabase.getInstance();
        statusStation = false;
        loadSaveStrategyFactory = new LoadSaveStrategyFactory();
        metroStation = new MetroStation();
        lastUsedGate = 0;
        lastUsedCard = 0;

        for (MetroEventsEnum event :  MetroEventsEnum.values()) {
            observers.put(event, new ArrayList<Observer>());
        }
    }

    public void openMetroStation() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/bestanden/settings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (properties.getProperty("LoadSaveStrategy").equals("Text File")) {
            LoadSaveStrategy loadSaveStrategy = (LoadSaveStrategy) loadSaveStrategyFactory.createLoadSaveStrategy("TXTMETROCARD");
            metroCardDatabase.setLoadSaveStrategy(loadSaveStrategy);
        } else if (properties.getProperty("LoadSaveStrategy").equals("Excel File")){
            LoadSaveStrategy loadSaveStrategy = (LoadSaveStrategy) loadSaveStrategyFactory.createLoadSaveStrategy("EXCELMETROCARD");
            metroCardDatabase.setLoadSaveStrategy(loadSaveStrategy);
        }
        metroCardDatabase.load();
        notifyObservers(MetroEventsEnum.OPEN_METROSTATION);
    }

    public void closeMetroStation(){
        metroCardDatabase.save();
        notifyObservers(MetroEventsEnum.CLOSE_METROSTATION);
    }

    public void scanMetroGate(int metroCardId, int gateId) {
        if (metroCardDatabase.validateMetroCard(metroCardId).equals("expired")) {
            metroStation.setMetroGateEvent(gateId, "expired");
            this.lastUsedGate = gateId;
            this.lastUsedCard = metroCardId;
            notifyObservers(MetroEventsEnum.INVALID_SCAN);
        }
        if (metroCardDatabase.validateMetroCard(metroCardId).equals("noRides")) {
            metroStation.setMetroGateEvent(gateId,"noRides");
            this.lastUsedGate = gateId;
            this.lastUsedCard = metroCardId;
            notifyObservers(MetroEventsEnum.INVALID_SCAN);
        }
        if (metroCardDatabase.validateMetroCard(metroCardId).equals("valid")){
            metroCardDatabase.getMetroCard(metroCardId).setActiveRides(metroCardDatabase.getMetroCard(metroCardId).getActiveRides() - 1);
            metroCardDatabase.getMetroCard(metroCardId).setUsedRides(metroCardDatabase.getMetroCard(metroCardId).getUsedRides() + 1);
            metroStation.scanMetroGate(gateId);
            this.lastUsedGate = gateId;
            this.lastUsedCard = metroCardId;
            notifyObservers(MetroEventsEnum.SCAN_METROGATE);
        }
    }

    public void setGateStatus(int gateId) {
        metroStation.setGateStatus(gateId);
        notifyObservers(MetroEventsEnum.ACTIVATE_METROGATE);
    }

    public boolean getGateStatus(int gateId) {
        return metroStation.getGateStatus(gateId);
    }

    public void walkThroughGate(int gateId) {
        if (getGateNow(gateId).equals("Closed")) {
            this.lastUsedGate = gateId;
            notifyObservers(MetroEventsEnum.ILLEGAL_WALKTHROUGH);
        } else {
            this.lastUsedGate = gateId;
            notifyObservers(MetroEventsEnum.WALKTHROUGH_GATE);
            metroStation.walkThroughGate(gateId);
        }
    }

    public int getLastUsedGate() {
        return this.lastUsedGate;
    }

    public int getLastUsedCard() {
        return this.lastUsedCard;
    }

    public String getGateNow(int gateId) {
        return metroStation.getGateNow(gateId);
    }

    public String getGateEvent(int gateId) {
        return metroStation.getGateEvent(gateId);
    }

    public void buyMetroCard() throws IOException{
        int newId = metroCardDatabase.getNewId();
        String month = String.valueOf(LocalDate.now().getMonthValue());
        String year = String.valueOf(LocalDate.now().getYear());
        String dateOfCreation = month + "#" + year;
        MetroCard newMetroCard = new MetroCard(newId, dateOfCreation, 2, 0);
        metroCardDatabase.add(newMetroCard);
        notifyObservers(MetroEventsEnum.BUY_METROCARD);
        notifyObservers(MetroEventsEnum.OPEN_METROSTATION);
    }

    public int getSoldTickets() {
        return totalAmountOfTickets;
    }
    public double getTotalPrice() {
        return totalPrice;
    }


    public void buyMetroCardTickets(int id, int amount, double price){
        MetroCard metroCard = metroCardDatabase.getMetroCard(id);
        metroCard.setActiveRides(metroCard.getActiveRides() + amount);
        metroCard.setDateOfCreation(LocalDate.now().getMonthValue() + "#" + LocalDate.now().getYear());
        totalAmountOfTickets += amount;
        totalPrice += price;
        notifyObservers(MetroEventsEnum.BUY_METROCARD_TICKETS);
        notifyObservers(MetroEventsEnum.OPEN_METROSTATION);
    }

    public void setStationStatus( ) {
        if (!statusStation) {
            this.statusStation =  true;
        } else {
            this.statusStation =  false;
        }
    }

    public int getScannedCards(int gateId) {
        return this.metroStation.getScannedCards(gateId);
    }

    public boolean getStationStatus() {
        return this.statusStation;
    }

    public ArrayList<Integer> getMetroCardIDList() {
        return metroCardDatabase.getMetroCardIDList();
    }

    public ArrayList<MetroCard> getMetroCardList() {
        return metroCardDatabase.getMetroCardList();
    }

    public MetroCardDatabase getMetroCardDatabase() {
        return metroCardDatabase;
    }

    public double getPrice(boolean is24Min, boolean is64Plus, boolean isStudent,MetroCard metroCard) {
        ArrayList<TicketPriceDiscountEnum> ticketPriceDiscountEnums = new ArrayList<>();
        if(is24Min || is64Plus) {
            ticketPriceDiscountEnums.add(TicketPriceDiscountEnum.AGE64DISCOUNT);
        }
        if(isStudent) {
            ticketPriceDiscountEnums.add(TicketPriceDiscountEnum.STUDENTDISCOUNT);
        }

        TicketPrice ticketPrice = TicketPriceFactory.createTicketPrice(ticketPriceDiscountEnums, metroCard);

        double price = Double.parseDouble(df.format(ticketPrice.getPrice()));
        return price;
    }
    public String getPriceText(boolean is24Min, boolean is64Plus, boolean isStudent, MetroCard metroCard) {
        ArrayList<TicketPriceDiscountEnum> ticketPriceDiscountEnums = new ArrayList<>();
        if(is24Min || is64Plus) {
            ticketPriceDiscountEnums.add(TicketPriceDiscountEnum.AGE64DISCOUNT);
        }
        if(isStudent) {
            ticketPriceDiscountEnums.add(TicketPriceDiscountEnum.STUDENTDISCOUNT);
        }

        TicketPrice ticketPrice = TicketPriceFactory.createTicketPrice(ticketPriceDiscountEnums, metroCard);
        String price = ticketPrice.getPriceText();
        return price;
    }
    public void setLoadSaveStrategy(LoadSaveStrategy strategy) {
        metroCardDatabase.setLoadSaveStrategy(strategy);
    }

    @Override
    public void notifyObservers(MetroEventsEnum event) {
        for (Observer observer : observers.get(event)) {
            observer.update(event.toString());
        }
    }

    @Override
    public void registerObserver(MetroEventsEnum event, Observer observer) {
        observers.get(event).add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}