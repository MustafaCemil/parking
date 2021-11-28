package com.parking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseType {
  SUCCESS("Operation success"),
  ERORR("Operation error"),
  EXCEPTION("Operation exception"),
  WARNING("Operation warning");

  private String message;
}
