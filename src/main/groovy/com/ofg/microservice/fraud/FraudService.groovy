package com.ofg.microservice.fraud

import org.springframework.stereotype.Service

/**
 * Created by Ceyhun.Hallac on 03/10/2014.
 */
@Service
class FraudService {
    
    private static final Integer MIN_AGE = 18 
    private static final Integer MAX_AGE = 65
    private static final BigDecimal FIRST_THRESHOLD = 1000
    private static final BigDecimal SECOND_THRESHOLD = 2000
    private static final Integer MIN_NAME_LENGTH = 2
    private static final Integer MAX_NAME_LENGTH = 25
   

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

    boolean isAmountBetweenThresholds(LoanApplication loanApplication) {
        return loanApplication.amount > FIRST_THRESHOLD && loanApplication.amount < SECOND_THRESHOLD
    }
    
    boolean isClientNameTooShort(LoanApplication loanApplication) {
        return loanApplication.firstName.length() < 2 ||
                loanApplication.lastName() < 2
    }

    boolean isClientNameTooLong(LoanApplication loanApplication) {
        return loanApplication.firstName.length() > 25 ||
                loanApplication.lastName() < 25
    }
    
    boolean isClientFishy(LoanApplication loanApplication) {
        return JobPosition.FINANCE_SECTOR.getName().equalsIgnoreCase(loanApplication.job) &&
                loanApplication.age > MAX_AGE &&
                isAmountBetweenThresholds(loanApplication) &&
                isClientNameTooLong(loanApplication.firstName, loanApplication.lastName)
    }

    boolean isClientOK(LoanApplication loanApplication) {
        return JobPosition.IT.getName().equalsIgnoreCase(loanApplication.job) &&
                loanApplication.age > MIN_AGE &&
                loanApplication.age < MAX_AGE &&
                isAmountLessThanFirstThreshold(loanApplication) &&
                isClientNameOk(loanApplication)
    }
    
    boolean isAmountLessThanFirstThreshold(LoanApplication loanApplication) {
        return loanApplication.amount < FIRST_THRESHOLD
    }
    
    boolean isClientNameOk(LoanApplication loanApplication) {
        return (loanApplication.firstName > MIN_NAME_LENGTH && loanApplication.firstName < MAX_NAME_LENGTH) ||
                (loanApplication.lastName > MIN_NAME_LENGTH && loanApplication.lastName < MAX_NAME_LENGTH)
    }
    
}
