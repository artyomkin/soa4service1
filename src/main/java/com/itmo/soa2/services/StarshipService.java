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
import com.itmo.soa2.exceptions.StarshipWrongFieldsException;
import com.itmo.soa2.exceptions.UnloadWrongIdException;
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

    public Starship save(Integer id, String name) throws StarshipWrongFieldsException {
        if (starshipRepo.findById(id).isPresent()){
            throw new StarshipWrongFieldsException("id");
        }
        Starship starship = new Starship();
        starship.setId(id);
        starship.setName(name);
        return starshipRepo.save(starship);
    }

    public XMLResponse findAll() {
        StarshipXMLResponse response = new StarshipXMLResponse();
        response.setStarships(starshipRepo.findAll());
        return response;
    }

    public int unload(Integer starshipId, Integer spaceMarineId) throws UnloadWrongIdException {
        Optional<SpaceMarine> optionalSpaceMarine = spaceMarineRepository.findById(spaceMarineId);
        if (optionalSpaceMarine.isEmpty()){
            throw new UnloadWrongIdException("spaceMarineId");
        }
        if (starshipRepo.findById(starshipId).isEmpty()){
            throw new UnloadWrongIdException("spaceMarineId");
        }
        SpaceMarine spaceMarine = optionalSpaceMarine.get();
        try{
            if (!spaceMarine.getStarshipId().equals(starshipId)){
                throw new UnloadWrongIdException("spaceMarineId and starshipId");
            }
        } catch (NullPointerException e){
            throw new UnloadWrongIdException("spaceMarineId and starshipId");
        }
        spaceMarine.setStarshipId(null);
        spaceMarineRepository.save(spaceMarine);
        return 204;
    }
}
