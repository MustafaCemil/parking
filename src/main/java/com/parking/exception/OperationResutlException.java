package com.parking.exception;

import com.parking.model.response.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationResutlException extends RuntimeException{
  private OperationResult operationResult;
}