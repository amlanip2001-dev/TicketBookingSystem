package com.ticketbooking.payment;

import com.ticketbooking.exception.InvalidBookingException;

public class UpiPayment implements PaymentStrategy {
    private final String upiId;

    public UpiPayment(String upiId){
        if(upiId == null || upiId.isBlank())
            throw new InvalidBookingException("Upi id can't be null");
        this.upiId=upiId;
    }

    @Override
    public boolean pay(double amount){
        if(amount <=0)
            return false;
        System.out.println("paid RS" +amount+ " via upi with "+upiId);
        return true;
    }

}
