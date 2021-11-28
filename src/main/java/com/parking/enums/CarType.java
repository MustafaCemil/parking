package com.parking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CarType {
  CAR(1),
  JEEP(2),
  TRUCK(4);

  private final Integer holdingSlot;

  public static Integer getSlot(String carType) {
    return Arrays.stream(CarType.values())
        .filter(type -> carType.equals(type.name()))
        .map(CarType::getHoldingSlot)
        .findAny()
        .orElse(null);
  }
}

