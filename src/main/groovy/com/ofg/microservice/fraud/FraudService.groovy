package com.ofg.microservice.fraud

import org.springframework.stereotype.Service

/**
 * Created by Ceyhun.Hallac on 03/10/2014.
 */
@Service
class FraudService {
    
    private final String JOB_POSITION_OTHER = 'OTHER' 
    private final String JOB_POSITION_FINANCE_SECTOR = 'FINANCE SECTOR' 
    private final String JOB_POSITION_IT = 'IT' 
    private final Integer MIN_AGE = 18 
    private final Integer MAX_AGE = 65
    private final BigDecimal MIN_AMOUNT = 1000
    private final BigDecimal MAX_AMOUNT = 2000

    FraudStatus checkLoanApplication(LoanApplication loanApplication) {

        final FraudStatus status
        if(isClientFraud(loanApplication)) {
            status = FraudStatus.FRAUD
        } else if(isClientFishy(loanApplication)) {
            status = FraudStatus.FISHY
        } else if(isClientOK(loanApplication)) {
            status = FraudStatus.OK
        }
        
        return status;
    }
    
    boolean isClientFraud(LoanApplication loanApplication) {
        return JOB_POSITION_OTHER.equals(loanApplication.job) && 
                loanApplication.age < MIN_AGE && 
                loanApplication.amount > MAX_AMOUNT && 
                isCustomerNameFraud(loanApplication.firstName, loanApplication.lastName)
    }
    
    boolean isClientFishy(LoanApplication loanApplication) {
        //TODO to be implemented
    }
    
    boolean isClientOK(LoanApplication loanApplication) {
        //TODO to be implemented
    }
    
    boolean isCustomerNameOK(String firstName, String lastName) {
        //TODO to be implemented
    }
    
    
}
