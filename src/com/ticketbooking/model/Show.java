package com.ticketbooking.model;

import com.ticketbooking.exception.InvalidBookingException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Show {
    private String showId;
    private Theatre theatre;
    private Movie movie;
    private Screen screen;
    private LocalDateTime startTime;
    private Map<String, Seat> showSeats;

    public Show(String showId, Theatre theatre, Movie movie, Screen screen, LocalDateTime startTime){
        if(showId == null || movie ==null || screen ==null || startTime == null) throw new InvalidBookingException("Show details can't be null");
        this.showId=showId;
        this.theatre=theatre;
        this.movie=movie;
        this.screen=screen;
        this.startTime=startTime;
        this.showSeats=new HashMap<>();
        initializeSeats(screen);

    }
    private void initializeSeats(Screen screen){
        for(Seat seat: screen.getSeats()){
            Seat copySeat;
            if(seat instanceof SilverSeat){
                copySeat =new SilverSeat(seat.getSeat(), seat.getPrice());
            }
            else if(seat instanceof  GoldSeat){
                copySeat=new GoldSeat(seat.getSeat(), seat.getPrice());
            }
            else if(seat instanceof ReclinerSeat){
                copySeat=new ReclinerSeat(seat.getSeat(), seat.getPrice());
            }
            else
                throw new InvalidBookingException("Unknown seat type");
            this.showSeats.put(copySeat.getSeat(), copySeat);
        }
    }
    public Seat getSeat(String seatNumber){
        if(showSeats.get(seatNumber)==null)
            throw new InvalidBookingException("Seat " +seatNumber+ "does not exist");
        else
            return showSeats.get(seatNumber);
    }
    public Collection<Seat> getAllSeats(){
        return Collections.unmodifiableCollection(showSeats.values());
    }
    public String getShowId(){
        return showId;
    }
    public Theatre getTheatre(){
        return theatre;
    }
    public Movie getMovie(){
        return  movie;
    }
    public Screen getScreen(){
        return screen;
    }
    public LocalDateTime getStartTime(){
        return startTime;
    }

}
