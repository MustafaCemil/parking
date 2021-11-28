package com.parking.repository;

import com.parking.model.entity.ParkAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkAreaRepository extends JpaRepository<ParkAreaEntity, Long> {

  List<ParkAreaEntity> findAllByParkAreaStatusOrderByParkAreaNumber(Boolean isEmpty);

  List<ParkAreaEntity> findAllByTicketTicketId(Long ticketId);
}
