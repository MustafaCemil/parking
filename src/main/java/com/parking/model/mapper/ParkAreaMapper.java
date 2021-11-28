package com.parking.model.mapper;


import com.parking.model.dto.ParkAreaDto;
import com.parking.model.entity.ParkAreaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParkAreaMapper {
  ParkAreaMapper INSTANCE = Mappers.getMapper(ParkAreaMapper.class);

  ParkAreaDto toParkAreaDto(ParkAreaEntity source);

  ParkAreaEntity toParkAreaEntity(ParkAreaDto source);

  List<ParkAreaDto> toParkAreaDtoList(List<ParkAreaEntity> list);

  List<ParkAreaEntity> toParkAreaEntityList(List<ParkAreaDto> list);
}
