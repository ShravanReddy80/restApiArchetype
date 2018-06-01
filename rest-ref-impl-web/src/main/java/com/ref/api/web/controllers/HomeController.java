package com.ref.api.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value="home", tags = { "home" }, description="home operations")
@RequestMapping("/")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @ApiOperation(value="", 
            httpMethod="GET", 
            notes="Base method/URL that can be used to check if the API is up and running",
            produces="application/json, application/xml", tags= {"home"})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
   
    @GetMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> healthCheck() {
        
        logger.debug("Inside health check \"/\" method");
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Test", "test-value");
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        
        String response = "{\"message\":\"health check successful\"}";
       
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

}