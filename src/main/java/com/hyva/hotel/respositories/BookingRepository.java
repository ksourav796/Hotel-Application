package com.hyva.hotel.respositories;

import com.hyva.hotel.entities.Booking;
import com.hyva.hotel.entities.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface BookingRepository extends CrudRepository<Booking, Long> {

    Booking findByGuest(String guest);
    List<Booking> findAllByGuest(String guest);
    Booking findByGuestAndBookingIdNotIn(String guest, Long id);

}
