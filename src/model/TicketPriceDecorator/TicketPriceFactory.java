package model.TicketPriceDecorator;

import model.MetroCard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class TicketPriceFactory {

    public TicketPriceFactory() {
    }

    public static TicketPrice createTicketPrice(ArrayList<TicketPriceDiscountEnum> ticketPriceDiscountEnums, MetroCard metrocard) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/bestanden/settings.properties");
        }
        catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }

        try {
            properties.load(inputStream);
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }

        String property = properties.getProperty("Korting");

        try {
            inputStream.close();
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }

        ArrayList<TicketPriceDiscountEnum> discountStringToEnum = new ArrayList<>();
        String[] arrayListEnumString = property.split(",");

        if(!property.equals("")) {
            for (String discountString : arrayListEnumString) {
                discountStringToEnum.add(TicketPriceDiscountEnum.valueOf(discountString));
            }
        }

        TicketPrice ticket = new BasicTicketPrice();
        for (TicketPriceDiscountEnum PriceDiscountEnum : discountStringToEnum) {
            switch (PriceDiscountEnum) {
                case AGE64DISCOUNT:
                    if (ticketPriceDiscountEnums.contains(PriceDiscountEnum)) {
                        ticket = new Age64PlusDiscount(ticket);
                    }
                    break;
                case CHRISTMASLEAVEDISCOUNT:
                    ticket = new ChristmasLeaveDiscount(ticket);
                    break;
                case STUDENTDISCOUNT:
                    if (ticketPriceDiscountEnums.contains(PriceDiscountEnum)) {
                        ticket = new StudentDiscount(ticket);
                    }
                    break;
                case FREQUENTTRAVELLERDISCOUNT:
                    if (metrocard.getUsedRides() > 50) {
                        ticket = new FrequentTravelDiscount(ticket);
                    }
                    break;
            }
        }
        return ticket;
    }

}
