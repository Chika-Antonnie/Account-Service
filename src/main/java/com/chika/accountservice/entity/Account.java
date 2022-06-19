package com.chika.accountservice.entity;


import com.chika.accountservice.util.enums.AccountStatus;
import com.chika.accountservice.util.enums.AccountType;
import com.chika.accountservice.util.enums.UpdateStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="account")
public class Account extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @GeneratedValue(generator = "acct-sequence-generator")
    @GenericGenerator(name = "acct-sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@Parameter(name = "sequence_name", value = "account_sequence"), @Parameter(name = "initial_value", value = "3000"),
                    @Parameter(name = "increment_size", value = "1")}
    )
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private BigDecimal availableBalance;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UpdateStatus lastBalanceUpdateStatus;
}
