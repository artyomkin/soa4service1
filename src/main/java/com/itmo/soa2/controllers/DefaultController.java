package com.itmo.soa2.controllers;

import com.itmo.soa2.controllers.responses.XMLResponse;
import com.itmo.soa2.controllers.responses.exception_response.UnexpectedError;
import com.itmo.soa2.dto.XMLParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.bind.JAXBException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class DefaultController {
    XMLParser<XMLResponse> parser = new XMLParser<>();
    @RequestMapping("/**")
    public ResponseEntity defaultController() throws JAXBException {
        return new ResponseEntity(parser.convertToXML(new UnexpectedError(404, "Not found.")), HttpStatus.valueOf(404));
    }
}
