package com.parking.service.impl;


import com.parking.enums.ResponseType;
import com.parking.exception.OperationResutlException;
import com.parking.model.dto.ParkAreaDto;
import com.parking.model.dto.TicketDto;
import com.parking.model.entity.TicketEntity;
import com.parking.enums.CarType;
import com.parking.model.mapper.TicketMapper;
import com.parking.model.request.CarEntranceRequest;
import com.parking.model.response.OperationResult;
import com.parking.model.response.TicketResponse;
import com.parking.repository.TicketRepository;
import com.parking.service.ParkAreaService;
import com.parking.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

  private final TicketRepository ticketRepository;
  private final ParkAreaService parkAreaService;

  @Transactional
  @Override
  public TicketResponse carEntranceForTicket(CarEntranceRequest request) {
    Integer startSlot = NumberUtils.INTEGER_ZERO;
    Integer countSlot = NumberUtils.INTEGER_ONE;
    final Integer carSLot = CarType.getSlot(request.getCarType().name());

    final List<ParkAreaDto> emptySlots = parkAreaService.findParkAreaInEmptyFields();
    for (int index = NumberUtils.INTEGER_ZERO; index < emptySlots.size(); index++) {
      if (index + NumberUtils.INTEGER_ONE == emptySlots.size()) {
        if (carSLot < emptySlots.size()) {
          startSlot = emptySlots.get(index - 1).getParkAreaNumber();
        } else {
          startSlot = emptySlots.get(index).getParkAreaNumber();
        }
        break;
      }

      Integer currentSlotNumberPlusOne = emptySlots.get(index).getParkAreaNumber() + NumberUtils.INTEGER_ONE;
      Integer nextSlotNumber = emptySlots.get(index + NumberUtils.INTEGER_ONE).getParkAreaNumber();
      if (carSLot.equals(NumberUtils.INTEGER_ONE)) {
        countSlot = 1;
        startSlot = emptySlots.get(index).getParkAreaNumber();
        break;
      } else if (currentSlotNumberPlusOne.equals(nextSlotNumber)) {
        if (startSlot.equals(NumberUtils.INTEGER_ZERO)) {
          startSlot = emptySlots.get(index).getParkAreaNumber();
        }

        if (countSlot.equals(carSLot)) {
          break;
        }

        countSlot++;
      } else {
        countSlot = NumberUtils.INTEGER_ONE;
        startSlot = NumberUtils.INTEGER_ZERO;
      }
    }

    if (countSlot.equals(carSLot)) {
      final TicketEntity ticketEntity = TicketEntity.builder()
          .carType(request.getCarType().name())
          .carColour(request.getColour())
          .ticketStatus(Boolean.TRUE)
          .plate(request.getPlate()).build();
      ticketRepository.save(ticketEntity);

      TicketDto ticketDto = TicketMapper.INSTANCE.toTicketDto(ticketEntity);
      parkAreaService.carEntrance(carSLot, startSlot, emptySlots, ticketDto);

      return TicketResponse.builder()
          .ticketId(ticketEntity.getTicketId())
          .ticketCreateDate(ticketEntity.getCreationDate())
          .build();
    } else {
      throw OperationResutlException.builder()
          .operationResult(
              OperationResult.builder()
                  .operationCode(ResponseType.ERORR.name())
                  .operationMessage("Garage is full!")
                  .build())
          .build();
    }
  }

  @Transactional
  @Override
  public OperationResult carExitForTicket(Long ticketId) {
    TicketEntity ticketEntity = ticketRepository.findById(ticketId)
        .orElseThrow(() -> OperationResutlException.builder()
            .operationResult(
                OperationResult.builder()
                    .operationCode(ResponseType.ERORR.name())
                    .operationMessage("Ticket not found")
                    .build())
            .build());

    ticketEntity.setTicketStatus(Boolean.FALSE);
    ticketRepository.save(ticketEntity);

    return parkAreaService.carExit(ticketEntity.getTicketId());
  }
}
