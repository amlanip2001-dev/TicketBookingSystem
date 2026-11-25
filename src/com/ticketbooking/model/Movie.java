package com.ticketbooking.model;

import com.ticketbooking.exception.InvalidBookingException;

import java.util.Objects;

public class Movie {
    private String movieId;
    private String title;
    Genre genre;
    int durationInMinute;

    public Movie(String movieId, String title, Genre genre, int durationInMinute){

        if(title ==null || title.isBlank() ) throw new InvalidBookingException("Movie title can't be blank ");
        if(!(durationInMinute >0 )) throw new InvalidBookingException("Invalid time duration ");
        this.movieId=movieId;
        this.title=title;
        this.genre=genre;
        this.durationInMinute=durationInMinute;
    }
    public String getMovieId(){
        return movieId;
    }
    public String getTitle(){
        return title;
    }
    public Genre getGenre(){
        return genre;
    }
    public int getDurationInMinute(){
        return durationInMinute;
    }
    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        return Objects.equals(movieId, ((Movie) o).movieId);
    }
    @Override
    public int hashCode(){
        return Objects.hash(movieId);
    }
}