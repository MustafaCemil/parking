package com.parking.model.mapper;

import com.parking.model.dto.TicketDto;
import com.parking.model.entity.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {
  TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

  TicketDto toTicketDto(TicketEntity source);

  TicketEntity toTicketEntity(TicketDto source);

  List<TicketDto> toTicketDtoList(List<TicketEntity> entityList);

  List<TicketEntity> toTicketEntityList(List<TicketDto> dtoList);
}
