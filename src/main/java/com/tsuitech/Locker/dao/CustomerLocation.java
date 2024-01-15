package com.tsuitech.Locker.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class CustomerLocation {
  @Id
    private Long id;
    private Long userId;
    private Double latitude;
    private Double longitude;
    private String address;
}
