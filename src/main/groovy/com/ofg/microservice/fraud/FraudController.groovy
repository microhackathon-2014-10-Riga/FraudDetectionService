package com.ofg.microservice.fraud

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import javax.validation.constraints.NotNull

@Slf4j
@RestController
@RequestMapping('/api')
class FraudController {

    @Autowired
    FraudService fraudService;

    @Autowired
    ServiceRestClient serviceRestClient;

    @RequestMapping(value = "/loanApplication/{loanApplicationId}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> verifyLoanApplication(
            @PathVariable @NotNull String loanApplicationId,
            @RequestBody @Valid LoanApplication loanApplication, BindingResult bindingResult) {

        final ResponseEntity<Object> responseEntity;

        if (bindingResult.hasErrors()) {
            responseEntity = new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
        }

        if (fraudService.checkLoanApplication(loanApplication).is(FraudStatus.FISHY)) {
            String statusCode = informDecisionMaker(loanApplicationId, loanApplication)
            if (statuCode.'2xxSuccessful') {
                responseEntity = new ResponseEntity<Object>(HttpStatus.SERVICE_UNAVAILABLE)
            }
        }

        return responseEntity;
    }

    String informDecisionMaker(String loanApplicationId, LoanApplication loanApplication) {

        LoanApplicationDecision decision = new LoanApplicationDecision(firstName: loanApplication.firstName, 
                                                                        lastName: loanApplication.lastName, 
                                                                        job: loanApplication.job, 
                                                                        amount: loanApplication.amount, 
                                                                        text: "This Client is fishy")

        serviceRestClient.forService("decision-maker").
                put().
                onUrl("/api/loanApplication/" + loanApplication).
                body(decision).
                withHeaders().
                contentTypeJson().
                andExecuteFor().
                aResponseEntity().
                ofType(Object).statusCode

    }
}
