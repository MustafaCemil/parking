package com.parking.model.entity;

import com.parking.model.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "park_area")
public class ParkAreaEntity extends BaseEntity<String> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "park_area_id")
  private Long parkAreaId;
  @Column(name = "park_area_number")
  private Integer parkAreaNumber;
  @Column(name = "park_area_status")
  private Boolean parkAreaStatus;
  @ManyToOne(fetch = FetchType.EAGER)
  private TicketEntity ticket;
}
