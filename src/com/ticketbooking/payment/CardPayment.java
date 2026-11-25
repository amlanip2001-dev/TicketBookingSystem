package com.ticketbooking.payment;

import com.ticketbooking.exception.InvalidBookingException;

public class CardPayment implements PaymentStrategy {
    private final String cardNumber;
    private final String cardHolderName;

    public CardPayment(String cardNumber, String cardHolderName){
        if(cardNumber == null || cardNumber.length() <4){
            throw new InvalidBookingException("Invalid Card number");
        }
        if(cardHolderName == null || cardHolderName.isBlank()){
            throw new InvalidBookingException("Card Holder name ca't be blank");
        }
        this.cardNumber=cardNumber;
        this.cardHolderName=cardHolderName;
    }
    private String getMaskedNumber(String cardNumber){
        String lastFour=cardNumber.substring(cardNumber.length()-4);
        return "XXXX-XXXX-XXXX-"+lastFour+"";
    }
    @Override
    public boolean pay(double amount){
        if(amount <= 0)
            return false;
        System.out.println("Paid Rs" +amount+ " via Card with " +getMaskedNumber(cardNumber)) ;
        return true;
    }
}
