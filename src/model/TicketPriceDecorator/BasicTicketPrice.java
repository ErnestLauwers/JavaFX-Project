package model.TicketPriceDecorator;

public class BasicTicketPrice extends TicketPrice {
    @Override
    public double getPrice() {
        return 2.10;
    }

    @Override
    public String getPriceText() {
        return "De prijs van een basis ticket is: " + getPrice();
    }
}

