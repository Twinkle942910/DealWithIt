package com.example.twinkle94.dealwithit.events;

public class Location
{
    private int id;

    private int event_id;
    private String street;
    private String city;
    private String country;

    public Location(int id, int event_id, String street, String city, String country)
    {
        this.id = id;
        this.event_id = event_id;
        this.street = street;
        this.city = city;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
