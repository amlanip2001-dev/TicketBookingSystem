package com.ticketbooking.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Screen {
    private String screenId;
    private String name;
    List<Seat> seats;

    public Screen(String screenId, String name, List<Seat> seats){
        this.screenId=screenId;
        this.name=name;
        this.seats=new ArrayList<>(seats);
    }
    public String getScreenId(){
        return screenId;
    }
    public String getName(){
        return name;
    }
    public List<Seat> getSeats(){
        return Collections.unmodifiableList(seats);
    }


}
