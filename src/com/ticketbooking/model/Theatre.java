package com.ticketbooking.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Theatre {
    private String theatreId;
    private String name;
    private String city;
    private List<Screen> screens;

    public Theatre(String theatreId, String name, String city, List<Screen> screens){
        this.theatreId=theatreId;
        this.name=name;
        this.city=city;
        this.screens =(screens== null) ? new ArrayList<>() :new ArrayList<>(screens);
    }
    public void addScreen(Screen screen){
        if(screen ==null){
            return;
        }
        screens.add(screen);
    }
    public String getTheatreId(){
        return theatreId;
    }
    public String getName(){
        return name;
    }
    public String getCity(){
        return city;
    }
    public List<Screen> getScreens(){
        return Collections.unmodifiableList(screens);
    }
}
