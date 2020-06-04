package com.hyva.hotel.respositories;

import com.hyva.hotel.entities.Services;
import com.hyva.hotel.entities.RoomTypes;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Long> {
    List<Services> findByRoomTypes(RoomTypes roomType);
   List<Services>  findByTitle(String name);
    List<Services> findByTitleAndIdNotIn(String title, Long id);
    List<Services> findAllByTitleContaining(String searchText, Pageable pageable);
    List<Services> findAllByTitleContaining(String searchText);
    Services findFirstBy(Sort sort);
    Services findFirstByTitleContaining(String searchText, Sort sort);
    List<Services> findAllBy(Pageable pageable);
    List<Services> findAllByTitleContainingOrDescriptionContaining(String title, String description);

}
