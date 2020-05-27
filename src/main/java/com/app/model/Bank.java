package com.app.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Bank {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="bank_name", unique = true)
    private String bankName;
}
