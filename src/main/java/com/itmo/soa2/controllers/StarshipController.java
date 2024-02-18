package com.itmo.soa2.controllers;

import com.itmo.soa2.controllers.requests.StarshipRequest;
import com.itmo.soa2.controllers.responses.XMLResponse;
import com.itmo.soa2.dto.XMLParser;
import com.itmo.soa2.entities.Starship;
import com.itmo.soa2.services.StarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;

@RestController
@RequestMapping("/api/v1/starships")
public class StarshipController {
    @Autowired
    XMLParser<XMLResponse> parser;

    @Autowired
    StarshipService starshipService;

    //@PostMapping("{id}/{name}")
    //public ResponseEntity createStarship(@PathVariable("id") Integer id, @PathVariable("name") String name){
    //    XMLResponse response = starshipService.save(id, name);
    //    try {
    //        return new ResponseEntity(parser.convertToXML(response), HttpStatus.valueOf(response.getCode()));
    //    } catch (JAXBException e) {
    //        return ResponseEntity.internalServerError().body(e.getMessage());
    //    }
    //}

    //@GetMapping
    //public ResponseEntity getStarships(){
    //    XMLResponse response = starshipService.findAll();
    //    try {
    //        return new ResponseEntity(parser.convertToXML(response), HttpStatus.valueOf(200));
    //    } catch (JAXBException e) {
    //        return ResponseEntity.internalServerError().body(e.getMessage());
    //    }
    //}

    //@PutMapping("/{starshipId}/unload/{spaceMarineId}")
    //public ResponseEntity unload(@PathVariable("starshipId") Integer starshipId, @PathVariable("spaceMarineId") Integer spaceMarineId) {
    //    XMLResponse response = starshipService.unload(starshipId, spaceMarineId);
    //    try {
    //        if (response == null){
    //           return new ResponseEntity(HttpStatus.valueOf(204));
    //        }
    //        return new ResponseEntity(parser.convertToXML(response), HttpStatus.valueOf(response.getCode()));
    //    } catch (JAXBException e) {
    //        return ResponseEntity.internalServerError().body(e.toString());
    //    }
    //}
}
