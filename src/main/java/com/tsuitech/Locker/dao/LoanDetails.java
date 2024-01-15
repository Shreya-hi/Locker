package com.tsuitech.Locker.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class LoanDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "retailer_id") // Name of the foreign key column in LoanDetails table
    private RetailerTable retailerTable;

    private Double loanAmount;
    private Integer tenure;
    private Double interestRate;
    private Double emi;
    private Date emiPaymentDate;
    private Double balance;
    private String customerName;
    private String email;
    private Double mobile;
}
