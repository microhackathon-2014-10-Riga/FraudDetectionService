package com.ofg.microservice.fraud

import org.springframework.stereotype.Service

/**
 * Created by Ceyhun.Hallac on 03/10/2014.
 */
@Service
class FraudService {
    
    private final Integer MIN_AGE = 18 
    private final Integer MAX_AGE = 65
    private final BigDecimal FIRST_THRESHOLD = 1000
    private final BigDecimal SECOND_THRESHOLD = 2000

    FraudStatus checkLoanApplication(LoanApplication loanApplication) {

        final FraudStatus status
        if(isClientFraud(loanApplication)) {
            status = FraudStatus.FRAUD
        } else if(isClientFishy(loanApplication)) {
            status = FraudStatus.FISHY
        } else if(isClientOK(loanApplication)) {
            status = FraudStatus.OK
        }
        
        return status
    }
    
    boolean isClientFraud(LoanApplication loanApplication) {
        return JobPosition.OTHER.getName().equalsIgnoreCase(loanApplication.job) && 
                loanApplication.age < MIN_AGE && 
                isAmountGreaterThanSecondThreshold(loanApplication) &&
                isClientNameTooShort(loanApplication)
    }
    
    boolean isAmountGreaterThanSecondThreshold(LoanApplication loanApplication) {
        return loanApplication.amount > SECOND_THRESHOLD
    }
    
    boolean isClientNameTooShort(LoanApplication loanApplication) {
        return loanApplication.firstName.length() < 2 ||
                loanApplication.lastName() < 2
    }
    
    boolean isClientFishy(LoanApplication loanApplication) {
        return JobPosition.FINANCE_SECTOR.getName().equalsIgnoreCase(loanApplication.job) &&
                loanApplication.age > MAX_AGE &&
                loanApplication.amount > FIRST_THRESHOLD &&
                loanApplication.amount < SECOND_THRESHOLD &&
                isCustomerNameFinance(loanApplication.firstName, loanApplication.lastName)
    }

    boolean isClientOK(LoanApplication loanApplication) {
        return false
    }
    
}
