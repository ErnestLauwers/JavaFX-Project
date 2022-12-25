package model.TicketPriceDecorator;

public class ChristmasLeaveDiscount extends TicketPriceDiscountDecorator {

    private TicketPrice ticketPrice;

    public ChristmasLeaveDiscount(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public double getPrice() {
        return ticketPrice.getPrice() - 0.1;
    }

    @Override
    public String getPriceText() {
        return ticketPrice.getPriceText() + " (inc. Christmas Leave Discount)";
    }
}
