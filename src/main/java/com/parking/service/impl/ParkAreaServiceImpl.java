package com.parking.service.impl;

import com.parking.enums.ResponseType;
import com.parking.exception.OperationResutlException;
import com.parking.model.dto.ParkAreaDto;
import com.parking.model.dto.TicketDto;
import com.parking.model.entity.ParkAreaEntity;
import com.parking.model.entity.TicketEntity;
import com.parking.model.mapper.ParkAreaMapper;
import com.parking.model.response.OperationResult;
import com.parking.model.response.ParkAreaStatusResponse;
import com.parking.repository.ParkAreaRepository;
import com.parking.service.ParkAreaService;
import com.parking.util.GeneralConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ParkAreaServiceImpl implements ParkAreaService {

  private final ParkAreaRepository parkAreaRepository;

  @Transactional
  @Override
  public OperationResult createParkArea() {
    List<ParkAreaEntity> parkAreaEntities = new ArrayList<>();
    for (int index = 1; index <= GeneralConstants.PARK_SLOT; index++) {
      final ParkAreaEntity entity = ParkAreaEntity.builder()
          .parkAreaNumber(index)
          .parkAreaStatus(Boolean.FALSE)
          .build();
      parkAreaEntities.add(entity);
    }

    parkAreaRepository.saveAll(parkAreaEntities);

    return OperationResult.builder()
        .operationCode(ResponseType.SUCCESS.name())
        .operationMessage(ResponseType.SUCCESS.getMessage())
        .build();
  }

  @Transactional(readOnly = true)
  @Override
  public List<ParkAreaDto> findParkAreaInEmptyFields() {
    List<ParkAreaEntity> emptyEntity = parkAreaRepository.findAllByParkAreaStatusOrderByParkAreaNumber(Boolean.FALSE);
    return Optional.ofNullable(emptyEntity)
        .map(ParkAreaMapper.INSTANCE::toParkAreaDtoList)
        .orElseThrow(() ->
            OperationResutlException.builder()
                .operationResult(
                    OperationResult.builder()
                        .operationCode(ResponseType.ERORR.name())
                        .operationMessage("Garage is full!")
                        .build())
                .build());
  }

  @Transactional
  @Override
  public List<ParkAreaDto> carEntrance(Integer carSlot, Integer startNumber, List<ParkAreaDto> parkAreaDtos, TicketDto ticketDto) {
    List<ParkAreaDto> parkAreaDtoList = new ArrayList<>();
    for (int i = 0; i < carSlot; i++) {
      Integer finalStartNumber = startNumber;
      final ParkAreaDto parkAreaDto = parkAreaDtos.stream()
          .filter(park -> park.getParkAreaNumber().equals(finalStartNumber))
          .findAny()
          .orElse(null);
      if (Objects.nonNull(parkAreaDto)) {
        parkAreaDto.setParkAreaStatus(Boolean.TRUE);
        parkAreaDto.setTicket(ticketDto);
        parkAreaDtoList.add(parkAreaDto);
        startNumber++;
      }
    }

    final List<ParkAreaEntity> entities = ParkAreaMapper.INSTANCE.toParkAreaEntityList(parkAreaDtoList);
    parkAreaRepository.saveAll(entities);

    return ParkAreaMapper.INSTANCE.toParkAreaDtoList(entities);
  }

  @Transactional
  @Override
  public OperationResult carExit(Long ticketId) {
    final List<ParkAreaEntity> entities = parkAreaRepository.findAllByTicketTicketId(ticketId);
    entities.forEach(garageSlotEntity -> {
      garageSlotEntity.setTicket(null);
      garageSlotEntity.setParkAreaStatus(Boolean.FALSE);
    });
    parkAreaRepository.saveAll(entities);

    return OperationResult.builder()
        .operationCode(ResponseType.SUCCESS.name())
        .operationMessage(ResponseType.SUCCESS.getMessage())
        .build();
  }

  @Transactional(readOnly = true)
  @Override
  public List<ParkAreaStatusResponse> parkAreaStatus() {
    final List<ParkAreaStatusResponse> responseList = new ArrayList<>();

    final List<ParkAreaEntity> entities = parkAreaRepository.findAllByParkAreaStatusOrderByParkAreaNumber(Boolean.TRUE);
    final Map<TicketEntity, List<ParkAreaEntity>> ticketEntityListMap = entities.stream()
        .collect(Collectors.groupingBy(ParkAreaEntity::getTicket));

    ticketEntityListMap.forEach((ticketEntity, parkAreaEntities) -> {
      final List<Integer> parkAreaNumbers = parkAreaEntities.stream()
          .map(ParkAreaEntity::getParkAreaNumber)
          .collect(Collectors.toList());

      final ParkAreaStatusResponse response = ParkAreaStatusResponse.builder()
          .plate(ticketEntity.getPlate())
          .colour(ticketEntity.getCarColour())
          .fields(parkAreaNumbers)
          .build();

      responseList.add(response);
    });

    return responseList;
  }
}
