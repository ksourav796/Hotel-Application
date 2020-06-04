package com.hyva.hotel.respositories;

import com.hyva.hotel.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface HotelUserRepository extends JpaRepository<User, Long> {
        List<User> findByUserNameAndUseraccountIdNotIn(String username, Long id);
        List<User> findByUserName(String username);
        List<User> findAllByUserNameContaining(String searchText, Pageable pageable);
        List<User> findAllByUserNameContaining(String searchText);
        User findFirstBy(Sort sort);
        User findFirstByUserNameContaining(String searchText, Sort sort);
        List<User> findAllBy(Pageable pageable);
}

