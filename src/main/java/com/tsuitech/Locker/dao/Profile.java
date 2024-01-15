package com.tsuitech.Locker.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fullName;
    private Long mobile;
    private String email;
    private String distributor;
    private String companyName;
    private String address;
    private String state;
    private String city;
    private String paymentOption;
    private String retailerSignature;
}
