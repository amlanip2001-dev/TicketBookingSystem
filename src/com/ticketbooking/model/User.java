package com.ticketbooking.model;

import com.ticketbooking.exception.InvalidBookingException;

import java.util.Objects;

public class User {
    private String userId;
    private String name;
    private String email;

    public User(String userId, String name, String email){
        if(userId ==null || userId.isBlank() || name==null || name.isBlank() || email==null || email.isBlank())
            throw new InvalidBookingException("Invalid user details");
        this.userId=userId;
        this.name=name;
        this.email=email;
    }
    public String getUserId(){
        return  userId;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
       return  email;
    }
    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o ==null || getClass() != o.getClass())
            return false;
        return Objects.equals(userId, ((User) o).userId);
    }
    public int hashCode(){
        return Objects.hash(userId);
    }
}
