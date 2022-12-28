/**
 * @author Benjamin Joossens
 */
package model.TicketPriceDecorator;

public class FrequentTravelDiscount extends TicketPriceDiscountDecorator {

    private TicketPrice ticketPrice;

    public FrequentTravelDiscount(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public double getPrice() {
        return ticketPrice.getPrice() - 0.2;
    }

    @Override
    public String getPriceText() {
        return ticketPrice.getPriceText() + " - €0,2 Frequent Travel Discount";
    }
}

