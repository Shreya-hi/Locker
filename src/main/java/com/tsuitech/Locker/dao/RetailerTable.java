package com.tsuitech.Locker.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class RetailerTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer retailerId;

    private String username;
    private String email;
    private String password;
    private String mobile;

    @OneToMany(mappedBy = "retailerTable", cascade = CascadeType.ALL)
    private List<LoanDetails> loans;

}
