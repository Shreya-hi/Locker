package com.tsuitech.Locker.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class AddCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String userName;
    private String emailId;
    private String mobile;
    private String imeiNumber1;
    private String imeiNumber2;
    private String code;
    private Date createdDate;
    private String city;
    private String manufacturer;
    private String serialNo;
    private Date purchaseDate;
    private Integer customerPin;
}
