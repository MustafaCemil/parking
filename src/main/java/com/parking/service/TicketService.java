package com.parking.service;

import com.parking.model.request.CarEntranceRequest;
import com.parking.model.response.OperationResult;
import com.parking.model.response.TicketResponse;

public interface TicketService {

  TicketResponse carEntranceForTicket(CarEntranceRequest request);

  OperationResult carExitForTicket(Long ticketId);
}
