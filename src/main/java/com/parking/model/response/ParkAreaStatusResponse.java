package com.parking.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkAreaStatusResponse {
  private String plate;
  private String colour;
  private List<Integer> fields;
}
