package com.parking;

import com.parking.enums.CarType;
import com.parking.enums.ResponseType;
import com.parking.model.dto.ParkAreaDto;
import com.parking.model.dto.TicketDto;
import com.parking.model.entity.ParkAreaEntity;
import com.parking.model.entity.TicketEntity;
import com.parking.model.mapper.ParkAreaMapper;
import com.parking.model.mapper.TicketMapper;
import com.parking.model.request.CarEntranceRequest;
import com.parking.model.response.OperationResult;
import com.parking.model.response.TicketResponse;
import com.parking.repository.ParkAreaRepository;
import com.parking.repository.TicketRepository;
import com.parking.service.impl.ParkAreaServiceImpl;
import com.parking.service.impl.TicketServiceImpl;
import com.parking.util.GeneralConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class TicketTest {

  @InjectMocks
  private TicketServiceImpl ticketService;
  @Mock
  private TicketRepository ticketRepository;
  @Mock
  private ParkAreaServiceImpl parkAreaService;
  @Mock
  private TicketMapper ticketMapper = Mappers.getMapper(TicketMapper.class);

  private CarEntranceRequest request;
  private TicketEntity ticketEntity;
  private TicketDto ticketDto;
  private TicketResponse ticketResponse;
  private List<ParkAreaDto> parkAreaDtos = new ArrayList<>();

  private Integer startSlot = NumberUtils.INTEGER_ZERO;
  private Integer carSLot;

  @BeforeEach
  public void initData() {
    request = new CarEntranceRequest();
    request.setCarType(CarType.CAR);
    request.setColour("White");
    request.setPlate("34 AA45");

    ticketEntity = TicketEntity.builder()
        .carType(request.getCarType().name())
        .carColour(request.getColour())
        .ticketStatus(Boolean.TRUE)
        .plate(request.getPlate()).build();

    ticketResponse = TicketResponse.builder()
        .ticketId(ticketEntity.getTicketId())
        .ticketCreateDate(ticketEntity.getCreationDate())
        .build();

    for (int index = 1; index <= GeneralConstants.PARK_SLOT; index++) {
      final ParkAreaDto parkAreaDto = ParkAreaDto.builder()
          .parkAreaNumber(index)
          .parkAreaStatus(Boolean.FALSE)
          .build();
      parkAreaDtos.add(parkAreaDto);
    }

    TicketDto ticketDto = TicketMapper.INSTANCE.toTicketDto(ticketEntity);

    carSLot = CarType.getSlot(request.getCarType().name());
  }

  @Test
  @DisplayName(value = "Car Entrance For Ticket Test")
  public void carEntranceForTicketTest() {
    Mockito.when(parkAreaService.findParkAreaInEmptyFields()).thenReturn(parkAreaDtos);
    Mockito.when(ticketRepository.save(Mockito.any())).thenReturn(ticketEntity);
    Mockito.when(ticketMapper.toTicketDto(ticketEntity)).thenReturn(ticketDto);
    Mockito.when(parkAreaService.carEntrance(NumberUtils.INTEGER_ZERO, startSlot, parkAreaDtos, ticketDto)).thenReturn(parkAreaDtos);

    final TicketResponse response = ticketService.carEntranceForTicket(request);

    Assertions.assertThat(response.getTicketId()).isEqualTo(ticketResponse.getTicketId());
  }
}

