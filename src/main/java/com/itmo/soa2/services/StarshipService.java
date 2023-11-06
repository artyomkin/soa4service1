package com.itmo.soa2.services;

import com.itmo.soa2.controllers.requests.SpaceMarineRequest;
import com.itmo.soa2.controllers.requests.StarshipRequest;
import com.itmo.soa2.controllers.responses.LandXMLWrongFields;
import com.itmo.soa2.controllers.responses.StarshipWrongFieldsXMLResponse;
import com.itmo.soa2.controllers.responses.StarshipXMLResponse;
import com.itmo.soa2.controllers.responses.XMLResponse;
import com.itmo.soa2.controllers.responses.exception_response.UnexpectedError;
import com.itmo.soa2.entities.SpaceMarine;
import com.itmo.soa2.entities.Starship;
import com.itmo.soa2.repos.SpaceMarineRepository;
import com.itmo.soa2.repos.StarshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class StarshipService {
    @Autowired
    StarshipRepo starshipRepo;
    @Autowired
    SpaceMarineRepository spaceMarineRepository;

    public XMLResponse save(Integer id, String name){
        if (starshipRepo.findById(id).isPresent()){
            StarshipWrongFieldsXMLResponse response = new StarshipWrongFieldsXMLResponse();
            response.setWrongFields(Arrays.asList("id"));
            return response;
        }
        Starship starship = new Starship();
        starship.setId(id);
        starship.setName(name);
        StarshipXMLResponse response = new StarshipXMLResponse();
        response.setStarships(Arrays.asList(starshipRepo.save(starship)));
        return response;
    }

    public XMLResponse findAll() {
        StarshipXMLResponse response = new StarshipXMLResponse();
        response.setStarships(starshipRepo.findAll());
        return response;
    }

    public XMLResponse unload(Integer starshipId, Integer spaceMarineId) {
        Optional<SpaceMarine> optionalSpaceMarine = spaceMarineRepository.findById(spaceMarineId);
        if (optionalSpaceMarine.isEmpty()){
            LandXMLWrongFields response = new LandXMLWrongFields();
            response.setWrongFields(Arrays.asList("spaceMarineId"));
            return response;
        }
        if (starshipRepo.findById(starshipId).isEmpty()){
            LandXMLWrongFields response = new LandXMLWrongFields();
            response.setWrongFields(Arrays.asList("starshipId"));
            return response;
        }
        SpaceMarine spaceMarine = optionalSpaceMarine.get();
        if (spaceMarine.getStarshipId() != starshipId){
            return new UnexpectedError(401, "Space marine was not on starship " + starshipId.toString() + ".");
        }
        spaceMarine.setStarshipId(null);
        spaceMarineRepository.save(spaceMarine);
        XMLResponse response = new XMLResponse() {
            public Integer getCode() {
                return 204;
            }
        };
        return null;
    }
}
