package com.example.ribijoy.smarttravelguide;

public class Blog {
    private String name;
     String review;
    private String image;
    private double lat;
    private double lon;
    private String address;
    private String type;
    private String city;


    public Blog(String name, String review, String image, double lat, double lon, String address, String type, String city) {
        this.name = name;
        this.review = review;
        this.image = image;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.type = type;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Blog(){

    }

}
