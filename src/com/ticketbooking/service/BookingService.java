package com.ticketbooking.service;

import com.ticketbooking.exception.InvalidBookingException;
import com.ticketbooking.exception.SeatUnavailableException;
import com.ticketbooking.model.*;
import com.ticketbooking.payment.PaymentStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class BookingService {
    private final List<Theatre> theatres = new ArrayList<>();
    private final List<Show> shows = new ArrayList<>();
    private final Map<String, Booking> bookings = new HashMap<>();

    public void addTheatre(Theatre theatre) {
        if (theatre == null)
            throw new InvalidBookingException("Theatre can't be blank");
        theatres.add(theatre);
    }

    public void addShow(Show show) {
        if (show == null)
            throw new InvalidBookingException("Show can't be empty");
        shows.add(show);
    }

    public List<Show> searchShowsByMovieAndCity(String movieTitle, String city) {
        if (movieTitle == null || city == null)
            return Collections.emptyList();
        return shows.stream().filter(show -> show.getMovie().getTitle().equalsIgnoreCase(movieTitle))
                .filter(show -> show.getTheatre().getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public Booking bookTickets(User user, Show show, List<String> seatNumbers, PaymentStrategy paymentStrategy) throws SeatUnavailableException {
        if (user == null || show == null || seatNumbers== null || seatNumbers.isEmpty() || paymentStrategy == null)
            throw new InvalidBookingException("Enter the valid booking details");
        List<Seat> selectedSeats = new ArrayList<>();
        synchronized (show) {
            for (String seatNum : seatNumbers) {
                Seat seat = show.getSeat(seatNum);
                if (seat.getStatus() != SeatStatus.AVAILABLE)
                    throw new SeatUnavailableException("Seat :" +seatNum+" is already taken");
                selectedSeats.add(seat);
            }
            for (Seat seat : selectedSeats) {
                seat.setStatus(SeatStatus.LOCKED);
            }
        }
        String bookingId = new StringBuilder().append(user.getName().substring(0, 2)).append("-").append(UUID.randomUUID().toString().substring(0, 8)).toString();
        Booking booking = new Booking(bookingId, user, show, selectedSeats);

        try{
            boolean paid= paymentStrategy.pay(booking.getTotalAmount());
            if(!paid)
                throw new InvalidBookingException("Payment Failed");
            for(Seat seat: selectedSeats){
                seat.setStatus(SeatStatus.BOOKED);
            }
            booking.confirmBooking();
            bookings.put(bookingId, booking);
            return booking;

        }catch (Exception e){
            for(Seat seat: selectedSeats){
                seat.setStatus(SeatStatus.AVAILABLE);
            }
            booking.cancelBooking();
            throw e;
        }
    }
    public void cancelBooking(String bookingId){

        Booking booking= bookings.get(bookingId);
        if(booking == null || booking.getStatus() == BookingStatus.CANCELLED)
            throw new InvalidBookingException("Booking not found or already cancelled");
        synchronized (booking.getShow()){
            List<Seat> cancelSeats=new ArrayList<>(booking.getBookedSeats());
            for(Seat seat : cancelSeats){
                seat.setStatus(SeatStatus.AVAILABLE);
            }
            booking.cancelBooking();
        }
    }
}
