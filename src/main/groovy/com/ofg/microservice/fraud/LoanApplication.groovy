package com.ofg.microservice.fraud

import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.Range

/**
 * Created by Bogdan.Aioanei on 10/3/2014.
 */
class LoanApplication {
    @NotEmpty
    String firstName
    
    @NotEmpty
    String lastName
    
    @NotEmpty
    String job
    
    @Range
    BigDecimal amount
    
    @Range
    Integer number
}
