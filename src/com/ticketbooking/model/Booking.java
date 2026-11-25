package com.ticketbooking.model;

import com.ticketbooking.exception.InvalidBookingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Booking {
    private String bookingId;
    private User user;
    private Show show;
    private List<Seat> bookedSeats;
    private double totalAmount;
    private BookingStatus status;

    public Booking(String bookingId, User user, Show show, List<Seat> bookedSeats){

        if(bookedSeats ==null || bookedSeats.isEmpty()){
            throw new InvalidBookingException("Invalid data entered");
        }
        this.bookingId=bookingId;
        this.user=user;
        this.show=show;
        this.bookedSeats=new ArrayList<>(bookedSeats);
        this.status=BookingStatus.PENDING;
        this.totalAmount=bookedSeats.stream().mapToDouble(Seat :: calculatePrice).sum();
    }
    public void confirmBooking(){
        this.status=BookingStatus.CONFIRMED;
    }
    public void cancelBooking(){
        this.status=BookingStatus.CANCELLED;
    }
    public String generateTicketReceipt(){
        StringBuilder str=new StringBuilder();
        str.append("==== MOVIE TICKET RECEIPT ====").append(System.lineSeparator());
        str.append("Booking ID: ").append(bookingId).append(System.lineSeparator());
        str.append("Customer : ").append(user.getName()).append(System.lineSeparator());
        str.append("Theatre : ").append(show.getTheatre().getName()).append(System.lineSeparator());
        str.append("Movie : ").append(show.getMovie().getTitle()).append(System.lineSeparator());
        str.append("Screen: ").append(show.getScreen().getName()).append(System.lineSeparator());
        str.append("Show time: ").append(show.getStartTime()).append(System.lineSeparator());
        str.append("Seats : ");
        for(int i=0; i< bookedSeats.size();i++){
            str.append(bookedSeats.get(i).getSeat());
            if(i < bookedSeats.size()-1)
                str.append(", ");
        }
        str.append(System.lineSeparator());

        str.append("Total Paid: Rs").append(totalAmount).append(System.lineSeparator());
        str.append("Status: ").append(status).append(System.lineSeparator());
        str.append("==============================");

        return str.toString();
    }
    public String getBookingId(){
        return bookingId;
    }
    public User getUser(){
        return  user;
    }
    public Show getShow(){
        return show;
    }
    public List<Seat> getBookedSeats(){
        return Collections.unmodifiableList(bookedSeats);
    }
    public double getTotalAmount(){
        return totalAmount;
    }
    public BookingStatus getStatus(){
        return status;
    }

}
