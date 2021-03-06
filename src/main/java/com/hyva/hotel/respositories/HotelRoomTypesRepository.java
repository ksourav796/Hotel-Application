package com.hyva.hotel.respositories;

import com.hyva.hotel.entities.RoomTypes;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface HotelRoomTypesRepository extends JpaRepository<RoomTypes, Long> {
    RoomTypes findById(Long id);
    List<RoomTypes> findByTitleAndIdNotIn(String title, long id);
    List<RoomTypes> findByTitle(String title);
    List<RoomTypes> findAllByTitleContaining(String searchText, Pageable pageable);
    List<RoomTypes> findAllByTitleContaining(String searchText);
    RoomTypes findFirstBy(Sort sort);
    RoomTypes findFirstByTitleContaining(String searchText, Sort sort);
    List<RoomTypes> findAllBy(Pageable pageable);
//    List<RoomTypes> findAllByTitleContainingOrSlugContaining(String title, String slug);

}
