package com.parking.controller;

import com.parking.model.request.CarEntranceRequest;
import com.parking.model.response.OperationResult;
import com.parking.model.response.TicketResponse;
import com.parking.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/ticket")
@RequiredArgsConstructor
@Tag(name = "Car Entrance or Exit", description = "Car Entrance or Exit API Controller")
public class TicketController {

  private final TicketService ticketService;

  @Operation(summary = "Car Entrance", description = "Car Entrance Api")
  @PostMapping(path = "/entrance")
  public ResponseEntity<TicketResponse> carEntranceForTicket(@Valid @RequestBody CarEntranceRequest request) {
    return ResponseEntity.ok(ticketService.carEntranceForTicket(request));
  }

  @Operation(summary = "Car Exit", description = "Car Exit Api")
  @GetMapping(path = "/exit/{ticketId}")
  public ResponseEntity<OperationResult> carExitForTicket(@PathVariable(name = "ticketId") Long ticketId) {
    return ResponseEntity.ok(ticketService.carExitForTicket(ticketId));
  }
}
