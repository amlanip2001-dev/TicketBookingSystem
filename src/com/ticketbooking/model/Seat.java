package com.ticketbooking.model;

import com.ticketbooking.exception.InvalidBookingException;

import java.util.Objects;

public abstract class Seat {
    private String seatNumber;
    private double basePrice;
    private SeatStatus status;

    public Seat(String seatNumber, double basePrice){
        if(seatNumber == null || seatNumber.isBlank())
            throw new InvalidBookingException("Seat Number Can't be Blank");
        if(basePrice <= 0)
            throw new InvalidBookingException("Price must be greater than Zero");

        this.seatNumber=seatNumber;
        this.basePrice=basePrice;
        this.status=SeatStatus.AVAILABLE;

    }
    public abstract double calculatePrice();
    public String getSeat(){
        return seatNumber;
    }
    public double getPrice(){
        return basePrice;
    }
    public SeatStatus getStatus(){
        return status;
    }
    public void setStatus(SeatStatus status){
        this.status=status;
    }
    @Override
    public boolean equals(Object o){
        if(this ==o )
            return true;
        if(o== null || getClass() != o.getClass())
            return false;
        Seat seat=(Seat) o;
        return Objects.equals(seatNumber,seat.seatNumber);
    }
    @Override
    public int hashCode(){
        return Objects.hash(seatNumber);
    }

}
