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

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE)
        }

        if(fraudService.checkLoanApplication(loanApplication).is(FraudStatus.FISHY)) {
            String statusCode = informDecisionMaker(loanApplicationId, loanApplication)
            if (statuCode.'2xxSuccessful') {
                return new ResponseEntity<Object>(HttpStatus.SERVICE_UNAVAILABLE)
            }
        }

        new ResponseEntity<Object>(HttpStatus.OK)
    }
    
    String informDecisionMaker(String loanApplicationId, LoanApplication loanApplication) {
        serviceRestClient.forService("decision-maker").
                         put().
                         onUrl("/api/loanApplication/" + loanApplication).
                         body(loanApplication).
                         withHeaders().
                            contentTypeJson().
                         andExecuteFor().
                         aResponseEntity().
                         ofType(Object).statusCode
                            
    }
}
