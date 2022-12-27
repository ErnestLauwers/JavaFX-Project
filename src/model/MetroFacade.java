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

    private static final DecimalFormat df = new DecimalFormat("0.00");


    public MetroFacade() {
        observers = new HashMap<>();
        metroCardDatabase = MetroCardDatabase.getInstance();
        statusStation = false;
        loadSaveStrategyFactory = new LoadSaveStrategyFactory();
        metroStation = new MetroStation();

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
    }

    public void scanMetroGate(int metroCardId, int gateId) {
        if (!metroCardDatabase.validateMetroCard(metroCardId)) {
            throw new IllegalArgumentException("This card is invalid!");
        } else {
            metroStation.scanMetroGate(gateId);
        }
    }

    public void setGateStatus(int gateId) {
        metroStation.setGateStatus(gateId);
    }

    public boolean getGateStatus(int gateId) {
        return metroStation.getGateStatus(gateId);
    }

    public void buyMetroCard() throws IOException{
        int newId = metroCardDatabase.getNewId();
        String month = String.valueOf(LocalDate.now().getMonthValue());
        String year = String.valueOf(LocalDate.now().getYear());
        String dateOfCreation = month + "#" + year;
        MetroCard newMetroCard = new MetroCard(newId, dateOfCreation, 5, 0);
        metroCardDatabase.add(newMetroCard);
        notifyObservers(MetroEventsEnum.BUY_METROCARD);
        notifyObservers(MetroEventsEnum.OPEN_METROSTATION);
    }

    public void buyMetroCardTickets(int id, int amount){
        MetroCard metroCard = metroCardDatabase.getMetroCard(id);
        metroCard.setActiveRides(metroCard.getActiveRides() + amount);
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
            observer.update();
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