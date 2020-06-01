package com.app.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "clients")
@Getter
@Setter
@EqualsAndHashCode
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name", columnDefinition = "varchar(20)")
    private String firstName;

    @Column(name = "last_name", length = 25)
    private String lastName;

    @Column(name = "date_of_birth", updatable = false)
    private OffsetDateTime dateOfBirth;

    private String phone;

    private String address;

    @Column(name = "amount", precision = 5, scale = 3)
    private BigDecimal amount;
}
