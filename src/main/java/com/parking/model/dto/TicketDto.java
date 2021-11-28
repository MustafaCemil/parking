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
public class TicketDto extends BaseDto {
  private Long ticketId;
  private String plate;
  private String carType;
  private String carColour;
  private Boolean ticketStatus;
}
