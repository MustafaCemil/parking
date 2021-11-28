package com.parking.service;

import com.parking.model.dto.ParkAreaDto;
import com.parking.model.dto.TicketDto;
import com.parking.model.response.OperationResult;
import com.parking.model.response.ParkAreaStatusResponse;

import java.util.List;

public interface ParkAreaService {

  OperationResult createParkArea();

  List<ParkAreaDto> findParkAreaInEmptyFields();

  List<ParkAreaDto> carEntrance(Integer carSlot, Integer startNumber, List<ParkAreaDto> parkAreaDtos, TicketDto ticketDto);

  OperationResult carExit(Long ticketId);

  List<ParkAreaStatusResponse> parkAreaStatus();
}
