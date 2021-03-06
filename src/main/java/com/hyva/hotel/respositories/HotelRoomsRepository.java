package com.hyva.hotel.respositories;

import com.hyva.hotel.entities.RoomTypes;
import com.hyva.hotel.entities.Rooms;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface HotelRoomsRepository extends JpaRepository<Rooms, Long> {
    List<Rooms> findByRoomTypeId(RoomTypes roomTypesObj);
    List<Rooms> findByRoomnoAndIdNotIn(String roomno, Long id);
    List<Rooms> findByRoomno(String roomno);
    List<Rooms> findAllByRoomTypeIdAndRoomnoNotIn(RoomTypes roomTypesObj, List<String> roomNos);
    List<Rooms> findAllByRoomnoContaining(String searchText, Pageable pageable);
    List<Rooms> findAllByRoomnoContaining(String searchText);
    Rooms findFirstBy(Sort sort);
    Rooms findFirstByRoomnoContaining(String searchText, Sort sort);
    List<Rooms> findAllBy(Pageable pageable);
//    List<Rooms> findAllByRoomnoContainingAndFloorIdContaining(String roomno, String floorId);

}
