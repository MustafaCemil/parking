package com.parking.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationResult {
  private String operationCode;
  private String operationMessage;
}