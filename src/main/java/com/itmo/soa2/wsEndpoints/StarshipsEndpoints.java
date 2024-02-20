package com.itmo.soa2.wsEndpoints;

import com.itmo.soa2.entities.SpaceMarine;
import com.itmo.soa2.entities.Starship;
import com.itmo.soa2.exceptions.StarshipWrongFieldsException;
import com.itmo.soa2.exceptions.UnloadWrongIdException;
import com.itmo.soa2.services.SpaceMarineService;
import com.itmo.soa2.services.StarshipService;
import _8080.api.v1.space_marines.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class StarshipsEndpoints {

    private static final String NAMESPACE_URI = "8080/api/v1/space-marines";
    @Autowired
    private StarshipService starshipService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createStarshipRequest")
    @ResponsePayload
    public CreateStarshipResponse createStarship(@RequestPayload CreateStarshipRequest createStarshipRequest) throws StarshipWrongFieldsException {
        Starship starship = starshipService.save(createStarshipRequest.getId(), createStarshipRequest.getName());
        CreateStarshipResponse response = new CreateStarshipResponse();
        response.setStarship(StarshipConverter.convertToWsStarship(starship));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "unloadSpaceMarineRequest")
    @ResponsePayload
    public UnloadSpaceMarineResponse unloadSpaceMarine(@RequestPayload UnloadSpaceMarineRequest unloadSpaceMarineRequest) throws UnloadWrongIdException {
        int returnCode = starshipService.unload(unloadSpaceMarineRequest.getStarshipId(), unloadSpaceMarineRequest.getSpaceMarineId());
        UnloadSpaceMarineResponse response = new UnloadSpaceMarineResponse();
        response.setCode(returnCode);
        return response;
    }
}
