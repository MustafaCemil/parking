package com.parking.model.entity;

import com.parking.model.entity.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class TicketEntity extends BaseEntity<String> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ticket_id")
  private Long ticketId;
  @Column(name = "plate")
  private String plate;
  @Column(name = "car_type")
  private String carType;
  @Column(name = "car_colour")
  private String carColour;
  @Column(name = "ticket_status")
  private Boolean ticketStatus;
}
