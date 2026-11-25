package com.ticketbooking.model;

public class SilverSeat extends Seat{

    public SilverSeat(String seatNumber, double basePrice) {
        super(seatNumber, basePrice);
    }

    @Override
    public double calculatePrice(){
        return getPrice();
    }
}
