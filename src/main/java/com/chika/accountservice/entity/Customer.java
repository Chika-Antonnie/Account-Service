package com.chika.accountservice.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import org.hibernate.annotations.Parameter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customer")
public class Customer extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;

    private String address;

    private String phone;

    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(name = "sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@Parameter(name = "sequence_name", value = "customer_sequence"), @Parameter(name = "initial_value", value = "100"), @Parameter(name = "increment_size", value = "1")}
    )
    private Long customerId;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Account> accounts;


}
