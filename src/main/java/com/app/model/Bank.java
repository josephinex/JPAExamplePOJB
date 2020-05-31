package com.app.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "banks")
@Getter
@Setter
@EqualsAndHashCode
public class Bank {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="bank_name", unique = true)
    private String bankName;

    @Column(nullable = false)
    private String phone;

    @Column(insertable = false)
    private String address;
}
