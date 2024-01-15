package com.tsuitech.Locker.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class RegistrationDao {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Long mobile;
    private String email;
}

