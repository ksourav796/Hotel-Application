package com.hyva.hotel.mapper;

import com.hyva.hotel.entities.Booking;
import com.hyva.hotel.entities.Orders;
import com.hyva.hotel.pojo.BookingPojo;
import com.hyva.hotel.pojo.OrdersPojo;

public class BookingMapper {
    public static Booking mapPojoToEntity(BookingPojo ordersPojo){
        Booking orders = new Booking();
        orders.setAdults(ordersPojo.getAdults());
        orders.setAmount(ordersPojo.getAmount());
        orders.setCheck_in(ordersPojo.getCheck_in());
        orders.setCheck_out(ordersPojo.getCheck_out());
        orders.setBookingId(ordersPojo.getBookingId());
        orders.setOrder_no(ordersPojo.getOrder_no());
        orders.setPaid_service_amount(ordersPojo.getPaid_service_amount());
        orders.setRoom_id(ordersPojo.getRoom_id());
        orders.setRoom_no(ordersPojo.getRoom_no());
        orders.setRoom_rent(ordersPojo.getRoom_rent());
        orders.setRoom_type_id(ordersPojo.getRoom_type_id());
        orders.setStatus(ordersPojo.getStatus());
        orders.setTaxamount(ordersPojo.getTaxamount());
        orders.setTotalamount(ordersPojo.getTotalamount());
        orders.setCountry(ordersPojo.getCountry());
        orders.setState(ordersPojo.getState());
        orders.setCity(ordersPojo.getCity());
        orders.setReviews(ordersPojo.getReviews());
        orders.setRating(ordersPojo.getRating());
        orders.setWifi(ordersPojo.getWifi());
        orders.setMeals(ordersPojo.getMeals());
        orders.setAirConditioning(ordersPojo.getAirConditioning());
        orders.setHotelAvailable(ordersPojo.getHotelAvailable());
        orders.setStayCost(ordersPojo.getStayCost());
        orders.setNoOfRoom(ordersPojo.getNoOfRoom());
        orders.setGuest(ordersPojo.getGuest());
        return orders;
    }
    public static BookingPojo EntityToPojo(Booking orders){
        BookingPojo ordersPojo = new BookingPojo();
        ordersPojo.setAdults(orders.getAdults());
        ordersPojo.setAmount(orders.getAmount());
        ordersPojo.setCheck_in(orders.getCheck_in());
        ordersPojo.setCheck_out(orders.getCheck_out());
        ordersPojo.setBookingId(orders.getBookingId());
        ordersPojo.setOrder_no(orders.getOrder_no());
        ordersPojo.setPaid_service_amount(orders.getPaid_service_amount());
        ordersPojo.setRoom_id(orders.getRoom_id());
        ordersPojo.setRoom_no(orders.getRoom_no());
        ordersPojo.setRoom_rent(orders.getRoom_rent());
        ordersPojo.setRoom_type_id(orders.getRoom_type_id());
        ordersPojo.setStatus(orders.getStatus());
        ordersPojo.setTaxamount(orders.getTaxamount());
        ordersPojo.setTotalamount(orders.getTotalamount());
        ordersPojo.setCountry(orders.getCountry());
        ordersPojo.setState(orders.getState());
        ordersPojo.setCity(orders.getCity());
        ordersPojo.setReviews(orders.getReviews());
        ordersPojo.setRating(orders.getRating());
        ordersPojo.setWifi(orders.getWifi());
        ordersPojo.setMeals(orders.getMeals());
        ordersPojo.setAirConditioning(orders.getAirConditioning());
        ordersPojo.setHotelAvailable(orders.getHotelAvailable());
        ordersPojo.setStayCost(orders.getStayCost());
        ordersPojo.setNoOfRoom(orders.getNoOfRoom());
        ordersPojo.setGuest(orders.getGuest());
//        ordersPojo.setId(orders.getId());
        return ordersPojo;
    }


}
