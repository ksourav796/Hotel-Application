package com.hyva.hotel.respositories;

import com.hyva.hotel.entities.Country;
import com.hyva.hotel.entities.Orders;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;


public interface OrdersRepository extends CrudRepository<Orders, Long> {

    Orders findByGuest(String guest);
    List<Orders> findAllByGuest(String guest);
    Orders findByGuestAndIdNotIn(String guest, Long id);

}
