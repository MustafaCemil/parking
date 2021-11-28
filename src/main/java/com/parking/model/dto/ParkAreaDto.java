package com.parking.model.dto;

import com.parking.model.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ParkAreaDto extends BaseDto {
  private Long parkAreaId;
  private Integer parkAreaNumber;
  private Boolean parkAreaStatus;
  private TicketDto ticket;
}
