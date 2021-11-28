package com.parking.model.request;

import com.parking.enums.CarType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Schema
public class CarEntranceRequest {
  @NotBlank
  @NotNull(message = "Plate Number mandotory")
  private String plate;
  @NotNull(message = "Car Type mandotory")
  private CarType carType;
  @NotBlank
  @NotNull(message = "Colour mandotory")
  private String colour;
}
