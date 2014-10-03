package com.ofg.microservice.fraud

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
        
        //call decision maker

        return responseEntity;
    }
}
