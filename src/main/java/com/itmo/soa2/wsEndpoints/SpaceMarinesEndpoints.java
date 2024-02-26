package com.itmo.soa2.wsEndpoints;

import com.itmo.soa2.entities.SpaceMarine;
import com.itmo.soa2.entities.domain.MeleeWeapon;
import com.itmo.soa2.exceptions.InvalidSortParamsException;
import com.itmo.soa2.exceptions.ServiceFault;
import com.itmo.soa2.exceptions.ServiceFaultException;
import com.itmo.soa2.exceptions.SpaceMarineWrongFieldsException;
import com.itmo.soa2.services.SpaceMarineService;
import _8080.api.v1.space_marines.*;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SpaceMarinesEndpoints {

    private static final String NAMESPACE_URI = "8080/api/v1/space-marines";
    @Autowired
    private SpaceMarineService spaceMarineService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSpaceMarineRequest")
    @ResponsePayload
    public GetSpaceMarineResponse getSpaceMarineById(@RequestPayload GetSpaceMarineRequest getSpaceMarineRequest){
        try{
            SpaceMarine spaceMarine = spaceMarineService.findById(getSpaceMarineRequest.getId());
            GetSpaceMarineResponse response = new GetSpaceMarineResponse();
            response.setSpaceMarine(SpaceMarineConverter.convertToWsSpaceMarine(spaceMarine));
            return response;
        } catch (SpaceMarineWrongFieldsException e) {
            throw new ServiceFaultException(e.getMessage(), new ServiceFault("400", e.getMessage()));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllSpaceMarinesRequest")
    @ResponsePayload
    public GetAllSpaceMarinesResponse getAllSpaceMarines(@RequestPayload GetAllSpaceMarinesRequest getAllSpaceMarinesRequest) throws IllegalAccessException, InvalidSortParamsException {
        GetAllSpaceMarinesResponse response = new GetAllSpaceMarinesResponse();
        response.getSpaceMarines().addAll(spaceMarineService.getAll(getAllSpaceMarinesRequest)
                .stream()
                .map(SpaceMarineConverter::convertToWsSpaceMarine)
                .toList());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createSpaceMarineRequest")
    @ResponsePayload
    public CreateSpaceMarineResponse createSpaceMarine(@RequestPayload CreateSpaceMarineRequest createSpaceMarinesRequest) throws SpaceMarineWrongFieldsException {
        CreateSpaceMarineResponse response = new CreateSpaceMarineResponse();
        response.setSpaceMarine(SpaceMarineConverter.convertToWsSpaceMarine(spaceMarineService.save(createSpaceMarinesRequest)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateSpaceMarineRequest")
    @ResponsePayload
    public UpdateSpaceMarineResponse updateSpaceMarine(@RequestPayload UpdateSpaceMarineRequest updateSpaceMarinesRequest) throws SpaceMarineWrongFieldsException {
        UpdateSpaceMarineResponse response = new UpdateSpaceMarineResponse();
        response.setSpaceMarine(SpaceMarineConverter.convertToWsSpaceMarine(spaceMarineService.update(updateSpaceMarinesRequest.getId(), updateSpaceMarinesRequest)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteSpaceMarineRequest")
    @ResponsePayload
    public DeleteSpaceMarineResponse deleteSpaceMarine(@RequestPayload DeleteSpaceMarineRequest deleteSpaceMarinesRequest) {
        spaceMarineService.delete(deleteSpaceMarinesRequest.getId());
        DeleteSpaceMarineResponse response = new DeleteSpaceMarineResponse();
        response.setCode(204);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMinSpaceMarineRequest")
    @ResponsePayload
    public GetMinSpaceMarineResponse getMinSpaceMarine() {
        SpaceMarine spaceMarine = spaceMarineService.getMinByCoords();
        GetMinSpaceMarineResponse response = new GetMinSpaceMarineResponse();
        response.setSpaceMarine(SpaceMarineConverter.convertToWsSpaceMarine(spaceMarine));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "countByHealthRequest")
    @ResponsePayload
    public CountByHealthResponse countByHealthResponse(@RequestPayload CountByHealthRequest countByHealthRequest) {
        CountByHealthResponse response = new CountByHealthResponse();
        response.setCount(spaceMarineService.countByHealth(countByHealthRequest.getHealth()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteByMeleeWeaponRequest")
    @ResponsePayload
    public DeleteByMeleeWeaponResponse deleteSpaceMarineResponse(@RequestPayload DeleteByMeleeWeaponRequest deleteByMeleeWeaponRequest) {
        spaceMarineService.deleteByMeleeWeapon(MeleeWeapon.valueOf(deleteByMeleeWeaponRequest.getMeleeWeapon().toString()));
        DeleteByMeleeWeaponResponse response = new DeleteByMeleeWeaponResponse();
        response.setCode(204);
        return response;
    }

}
