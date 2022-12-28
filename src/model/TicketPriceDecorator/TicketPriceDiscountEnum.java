/**
 * @author Benjamin Joossens
 */
package model.TicketPriceDecorator;

public enum TicketPriceDiscountEnum {
    AGE64DISCOUNT("korting indien ouder dan 64 jaar", "model.ticketPriceDecorator.Age64Korting"),
    CHRISTMASLEAVEDISCOUNT("korting tijdens kerstperiode", "model.ticketPriceDecorator.ChristmasKorting"),
    STUDENTDISCOUNT("korting voor studenten", "model.ticketPriceDecorator.StudentKorting"),
    FREQUENTTRAVELLERDISCOUNT("korting voor frequente reizigers", "model.ticketPriceDecorator.FrequentKorting");
    private final String name;
    private final String propertyName;

    TicketPriceDiscountEnum(String name, String propertyName) {
        this.name = name;
        this.propertyName = propertyName;
    }

    public String getName() {
        return name;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
