package com.parking.controller;

import com.parking.model.response.OperationResult;
import com.parking.model.response.ParkAreaStatusResponse;
import com.parking.service.ParkAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/park")
@RequiredArgsConstructor
@Tag(name = "Park Area", description = "Park Area API Controller")
public class ParkAreaController {

  private final ParkAreaService parkAreaService;

  @Operation(summary = "Create Park Area", description = "Create Park Area Api")
  @PostMapping("/create")
  public ResponseEntity<OperationResult> createParkArea() {
    return ResponseEntity.ok(parkAreaService.createParkArea());
  }

  @Operation(summary = "Inquire Park Area Status", description = "Inqure Park Area Status Api")
  @GetMapping(path = "/status")
  public ResponseEntity<List<ParkAreaStatusResponse>> parkAreaStatus() {
    return ResponseEntity.ok(parkAreaService.parkAreaStatus());
  }

}
