package com.ticketbooking.model;

public class ReclinerSeat extends Seat{

    public ReclinerSeat(String seatNumber, double basePrice){
        super(seatNumber, basePrice);
    }
    @Override
    public double calculatePrice(){
        return getPrice()*1.50;
    }
}
