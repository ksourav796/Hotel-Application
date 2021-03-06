package com.hyva.hotel.respositories;

import com.hyva.hotel.entities.Cities;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<Cities, Long> {
    List<Cities> findAllBy(String status);
    Cities findByNameAndCountryAndStateAndIdNotIn(String name, String countryName, String state, Long id);
    Cities findByNameAndCountryAndState(String name, String countryName, String state);
    List<Cities> findAllByState(String state);
    List<Cities> findAllByNameContaining(String searchText, Pageable pageable);
    List<Cities> findAllByNameContainingOrCountryContainingOrStateContaining(String searchText, String searchText1, String searchText2);
    List<Cities> findAllByNameContaining(String searchText);
    Cities findFirstBy(Sort sort);
    Cities findFirstByNameContaining(String searchText, Sort sort);
    List<Cities> findAllBy(Pageable pageable);

}