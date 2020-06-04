package com.hyva.hotel.pojo;

import com.hyva.hotel.entities.Guests;

import java.util.Date;
import java.util.List;

public class OrdersPojo {
    private Long id;
    private String order_no;
    private String guest;
    private String status;
    private String room_no;
    private String room_rent;
    private Long adults;
    private Long room_type_id;
    private Long room_id;
    private double totalamount;
    private double taxamount;
    private double paid_service_amount;
    private double amount;
    private Date check_in;
    private Date check_out;
    private String description;
    private String invoice;
    private String country;
    private String state;
    private String city;
    private String rating;
    private String reviews;
    private String Wifi;
    private String meals;
    private String airConditioning;
    private String hotelAvailable;
    private String stayCost;
    private String noOfRoom;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getRoom_rent() {
        return room_rent;
    }

    public void setRoom_rent(String room_rent) {
        this.room_rent = room_rent;
    }

    public Long getAdults() {
        return adults;
    }

    public void setAdults(Long adults) {
        this.adults = adults;
    }

    public Long getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(Long room_type_id) {
        this.room_type_id = room_type_id;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public double getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(double taxamount) {
        this.taxamount = taxamount;
    }

    public double getPaid_service_amount() {
        return paid_service_amount;
    }

    public void setPaid_service_amount(double paid_service_amount) {
        this.paid_service_amount = paid_service_amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCheck_in() {
        return check_in;
    }

    public void setCheck_in(Date check_in) {
        this.check_in = check_in;
    }

    public Date getCheck_out() {
        return check_out;
    }

    public void setCheck_out(Date check_out) {
        this.check_out = check_out;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getWifi() {
        return Wifi;
    }

    public void setWifi(String wifi) {
        Wifi = wifi;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(String airConditioning) {
        this.airConditioning = airConditioning;
    }

    public String getHotelAvailable() {
        return hotelAvailable;
    }

    public void setHotelAvailable(String hotelAvailable) {
        this.hotelAvailable = hotelAvailable;
    }

    public String getStayCost() {
        return stayCost;
    }

    public void setStayCost(String stayCost) {
        this.stayCost = stayCost;
    }

    public String getNoOfRoom() {
        return noOfRoom;
    }

    public void setNoOfRoom(String noOfRoom) {
        this.noOfRoom = noOfRoom;
    }
}
