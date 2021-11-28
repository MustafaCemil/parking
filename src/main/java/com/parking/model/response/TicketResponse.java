package com.parking.model.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
  private Long ticketId;
  private Date ticketCreateDate;
}
