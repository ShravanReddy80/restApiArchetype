package com.ref.api.web.controllers;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ref.api.model.Customer;
import com.ref.api.util.errors.CustomMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "customers", tags = { "customers" }, description="home operations")
@RequestMapping("/customers")
public class CustomersController {

    private static final Logger logger = LoggerFactory.getLogger(CustomersController.class);

    private Map<Long, Customer> customers = new HashMap<>();
    
    

    @ApiOperation(value = "Create a customer", httpMethod = "POST", notes = "create the customer", produces = "application/json, application/xml", tags = { "customers" })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Success", response = String.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })

    @PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer) {
        logger.debug("Inside root POST path \"/\" method");

        LocalDateTime localTime = LocalDateTime.now();
        if (null == customer.getCreatedDate()) {
            customer.setCreatedDate(localTime);
        }

        if (null == customer.getModifiedDate()) {
            customer.setModifiedDate(localTime);
        }
        
        if (null != customers.get(customer.getId())) {
            logger.error("Unable to create. A Customer with Id {} already exist", customer.getId());
            return new ResponseEntity<>(
                    new CustomMessage("Unable to create. A Customer with id " + customer.getId() + " already exist."),
                    HttpStatus.CONFLICT);
        }
        

        customers.put(customer.getId(), customer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.getId())
                .toUri();

        HttpHeaders responseHeaders = createHttpResponseHeaders();
        responseHeaders.setLocation(location);
        
        return new ResponseEntity<>(customer, responseHeaders, HttpStatus.CREATED);

    }

    
    @ApiOperation(value = "Find all the customers", httpMethod = "GET", notes = "get all the customers", produces = "application/json, application/xml", tags = { "customers" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })

    @GetMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getCustomers() throws JsonProcessingException {
        logger.debug("Inside root GET path \"/\" method");

        HttpHeaders responseHeaders = createHttpResponseHeaders();
        if (customers.isEmpty()) {
            logger.error("No customers found");
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }

        // String json = new ObjectMapper().writeValueAsString(customers);

        return new ResponseEntity<>(customers, responseHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "Find a customer by Id", httpMethod = "GET", notes = "get the customer with an Id", produces = "application/json, application/xml", tags = { "customers" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getCustomerById(@PathVariable("id") long id) {
        logger.debug("Inside root GET path \"/{id}\" method");

        HttpHeaders responseHeaders = createHttpResponseHeaders();
        
        Customer customer = customers.get(id);
        if (customers.isEmpty()) {
            logger.error("Customer with id {} not found.", id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }


        if (customer == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity<>(new CustomMessage("Customer with id " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customer, responseHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "Update a customer completely by id", httpMethod = "PUT", notes = "completely replace the customer", produces = "application/json, application/xml", tags = { "customers" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer customer) {
        logger.debug("Inside  update path \"/{id}\" method");


        HttpHeaders responseHeaders = createHttpResponseHeaders();

        LocalDateTime localTime = LocalDateTime.now();
        if (null == customer.getCreatedDate()) {
            customer.setCreatedDate(localTime);
        }

        if (null == customer.getModifiedDate()) {
            customer.setModifiedDate(localTime);
        }
        
        if (null != customers.get(customer.getId())) {
            logger.error("Creating a new Customer with Id {}. No customer exists currently", customer.getId());
           
            customers.put(customer.getId(), customer);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(customer.getId()).toUri();

            responseHeaders.setLocation(location);

            return new ResponseEntity<>(customer, responseHeaders, HttpStatus.CREATED);
        }
       
        customers.put(customer.getId(), customer);
        return new ResponseEntity<>(customer, responseHeaders, HttpStatus.OK);

    }

    @ApiOperation(value = "Delete a customer by id", httpMethod = "DELETE", notes = "delete the customer with an Id", produces = "application/json, application/xml", tags = { "customers" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> deleteCustomerById(@PathVariable("id") long id) {
        logger.debug("Inside root DELETE path \"/{id}\" method");

        HttpHeaders responseHeaders = createHttpResponseHeaders();

        if (customers.isEmpty()) {
            return new ResponseEntity<>(new CustomMessage("No customers found"), HttpStatus.OK);
        }

        Customer customer = customers.remove(id);
        if (customer == null) {
            logger.error("Customer with id {} not found.", id);
            return new ResponseEntity<>(new CustomMessage("Customer with id " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }

        String message = "Found the customer with id " + id + " and deleted it";
        return new ResponseEntity<>(new CustomMessage(message), responseHeaders, HttpStatus.NO_CONTENT);

    }
    
    
    private HttpHeaders createHttpResponseHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("X-Test", "test-value");
        responseHeaders.add("Access-Control-Allow-Origin", "*");
        responseHeaders.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseHeaders.add("Access-Control-Max-Age", "3600");
        responseHeaders.add("Access-Control-Allow-Headers", "x-requested-with");
        return responseHeaders;
    }


}