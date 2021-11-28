package com.parking;

import com.parking.enums.ResponseType;
import com.parking.model.dto.ParkAreaDto;
import com.parking.model.entity.ParkAreaEntity;
import com.parking.model.mapper.ParkAreaMapper;
import com.parking.model.response.OperationResult;
import com.parking.repository.ParkAreaRepository;
import com.parking.service.impl.ParkAreaServiceImpl;
import com.parking.util.GeneralConstants;
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
public class ParkAreaTest {

  @InjectMocks
  private ParkAreaServiceImpl parkAreaService;
  @Mock
  private ParkAreaRepository parkAreaRepository;
  @Mock
  private ParkAreaMapper parkAreaMapper = Mappers.getMapper(ParkAreaMapper.class);

  private List<ParkAreaDto> parkAreaDtos = new ArrayList<>();
  private List<ParkAreaEntity> parkAreaEntities = new ArrayList<>();

  @BeforeEach
  public void initData() {
    for (int index = 1; index <= GeneralConstants.PARK_SLOT; index++) {
      final ParkAreaDto parkAreaDto = ParkAreaDto.builder()
          .parkAreaNumber(index)
          .parkAreaStatus(Boolean.FALSE)
          .build();
      parkAreaDtos.add(parkAreaDto);
    }
    parkAreaEntities = ParkAreaMapper.INSTANCE.toParkAreaEntityList(parkAreaDtos);
  }

  @Test
  @DisplayName(value = "Create Park Area Test")
  public void createParkAreaTest() {
    final OperationResult expectedResult = OperationResult.builder()
        .operationCode(ResponseType.SUCCESS.name())
        .operationMessage(ResponseType.SUCCESS.getMessage())
        .build();

    Mockito.when(parkAreaMapper.toParkAreaEntityList(parkAreaDtos)).thenReturn(parkAreaEntities);
    Mockito.when(parkAreaRepository.saveAll(Mockito.anyList())).thenReturn(parkAreaEntities);
    Mockito.when(parkAreaRepository.saveAll(Mockito.anyList())).thenReturn(parkAreaEntities);

    OperationResult operationResult = parkAreaService.createParkArea();

    Assertions.assertThat(operationResult.getOperationCode()).isEqualTo(expectedResult.getOperationCode());
  }

  @Test
  @DisplayName(value = "Find Park Area In Empty Fields Test")
  public void findParkAreaInEmptyFieldsTest() {
    Mockito.when(parkAreaRepository.findAllByParkAreaStatusOrderByParkAreaNumber(Boolean.FALSE)).thenReturn(parkAreaEntities);
    Mockito.when(parkAreaMapper.toParkAreaDtoList(parkAreaEntities)).thenReturn(parkAreaDtos);

    final List<ParkAreaDto> areaDtos = parkAreaService.findParkAreaInEmptyFields();

    Assertions.assertThat(areaDtos.size()).isEqualTo(parkAreaDtos.size());
  }
}
