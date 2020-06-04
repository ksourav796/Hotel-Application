package com.hyva.hotel.respositories;

import com.hyva.hotel.entities.Guests;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HotelGuestsRepository extends JpaRepository<Guests, Long> {
    List<Guests> findByFirstnameAndLastnameAndIdNotIn(String firstname, String lastname, Long id);
    List<Guests> findByFirstnameAndLastname(String firstname, String lastname);
  Guests findFirstByFirstnameAndLastnameContaining(String searchText, Sort sort);
  List<Guests> findAllByLastnameContaining(String searchText, Pageable pageable);
  Guests findFirstBy(Sort sort);
  List<Guests> findAllBy(Pageable pageable);
  List<Guests> findAllByLastnameContaining(String searchText);
}