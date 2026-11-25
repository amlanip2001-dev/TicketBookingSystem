package com.ticketbooking.model;

public class GoldSeat extends Seat{

    public GoldSeat(String seatNumber, double basePrice){
        super(seatNumber, basePrice);
    }

    @Override
    public double calculatePrice(){
        return getPrice()*1.25;
    }
}
