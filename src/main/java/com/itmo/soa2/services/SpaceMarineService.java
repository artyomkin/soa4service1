package com.itmo.soa2.services;

import com.itmo.soa2.controllers.requests.SpaceMarineRequest;
import com.itmo.soa2.controllers.requests.SpaceMarineSearchRequest;
import com.itmo.soa2.controllers.responses.*;
import com.itmo.soa2.controllers.responses.exception_response.UnexpectedError;
import com.itmo.soa2.entities.SpaceMarine;
import com.itmo.soa2.entities.domain.Chapter;
import com.itmo.soa2.entities.domain.Coordinates;
import com.itmo.soa2.entities.domain.MeleeWeapon;
import com.itmo.soa2.filters.Comparison;
import com.itmo.soa2.filters.Condition;
import com.itmo.soa2.repos.ChapterRepo;
import com.itmo.soa2.repos.CoordinatesRepo;
import com.itmo.soa2.repos.SpaceMarineRepository;
import com.itmo.soa2.filters.Filter;
import com.itmo.soa2.repos.StarshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpaceMarineService {

    @Autowired
    private SpaceMarineRepository spaceMarineRepo;
    @Autowired
    private CoordinatesRepo coordinatesRepo;

    @Autowired
    private ChapterRepo chapterRepo;

    @Autowired
    private StarshipRepo starshipRepo;
    public XMLResponse getAll(SpaceMarineSearchRequest request){
        Map<String, String> parameters = new HashMap<>();
        try {
            parameters = convertToParameters(request);
        } catch (IllegalAccessException e) {
            UnexpectedError response = new UnexpectedError("Cannot process request parameters because of illegal access to fields.");
            return response;
        }

        if (!sortIsValid(parameters.get("sort"), parameters.get("order"))){
            SpaceMarineSearchWrongFieldsXMLResponse response = new SpaceMarineSearchWrongFieldsXMLResponse();
            response.setWrongFields(Arrays.asList("sort", "order"));
            return response;
        }

        Map<String, String> searchParameters = parameters.entrySet().stream()
                .filter(parameter -> !Arrays.asList("page", "size", "sort", "order", "coordinatesX", "coordinatesY", "loyal", "meleeWeapon", "chapterName").contains(parameter.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Filter filter = buildFilter(searchParameters);
        List<SpaceMarine> result;
        if (filter.isEmpty()){
            if (parameters.get("sort") != null){
                result = spaceMarineRepo.findAll(PageRequest.of(
                        request.getPage()-1,
                        request.getSize(),
                        Sort.by(request.getOrder(),request.getSort()))).getContent();
            } else {
                result = spaceMarineRepo.findAll(PageRequest.of(
                        request.getPage()-1,
                        request.getSize())).getContent();
            }
        } else {
            if (parameters.get("sort") != null){
                result = spaceMarineRepo.findAll(filter, PageRequest.of(
                        request.getPage()-1,
                        request.getSize(),
                        Sort.by(request.getOrder(),request.getSort()))).getContent();
            } else {
                result = spaceMarineRepo.findAll(filter, PageRequest.of(
                        request.getPage()-1,
                        request.getSize())).getContent();
            }
        }

        Map<String, String> finalParameters = parameters;
        if (parameters.get("coordinatesX") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine.getCoordinates().getX() == Double.valueOf(finalParameters.get("coordinatesX")))
                    .collect(Collectors.toList());
        }
        if (parameters.get("coordinatesY") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine.getCoordinates().getY() == Double.valueOf(finalParameters.get("coordinatesY")))
                    .collect(Collectors.toList());
        }
        if (parameters.get("loyal") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine.getLoyal().toString().equals(finalParameters.get("loyal")))
                    .collect(Collectors.toList());
        }
        if (parameters.get("meleeWeapon") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine
                            .getMeleeWeapon()
                            .toString()
                            .equals(finalParameters.get("meleeWeapon")))
                    .collect(Collectors.toList());
        }
        if (parameters.get("chapterName") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine.getChapter().getName().equals(finalParameters.get("chapterName")))
                    .collect(Collectors.toList());
        }

        return SpaceMarineListXMLResponse.ok(result);
    }
    public XMLResponse save(SpaceMarineRequest spaceMarineRequest) {
        try {
            SpaceMarine spaceMarine = new SpaceMarine(spaceMarineRequest);
            Coordinates coordinates = coordinatesRepo.save(spaceMarine.getCoordinates());
            spaceMarine.setCoordinates(coordinates);
            if (!starshipRepo.existsById(spaceMarine.getStarshipId())){
                throw new IllegalArgumentException("starshipId");
            }
            return new SpaceMarineXMLResponse(spaceMarineRepo.save(spaceMarine));
        } catch (IllegalArgumentException e){
            SpaceMarineSearchWrongFieldsXMLResponse errorResponse = new SpaceMarineSearchWrongFieldsXMLResponse();
            errorResponse.setWrongFields(Arrays.asList(e.getMessage()));
            return errorResponse;
        }
    }

    private Filter buildFilter(Map<String, String> parameters){
        Filter filter = new Filter();
        parameters.entrySet().stream()
            .forEach(parameter -> filter.addCondition(new Condition.Builder().setComparison(Comparison.eq)
                .setField(parameter.getKey())
                .setValue(parameter.getValue())
                .build()
            ));
        return filter;
    }
    private Map<String, String> convertToParameters(SpaceMarineSearchRequest request) throws IllegalAccessException{
        Map<String, String> resultParameters = Arrays.stream(request.getClass().getDeclaredFields())
                .filter(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(request) != null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return new AbstractMap.SimpleEntry<String, String>(field.getName(), field.get(request).toString());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return resultParameters;
    }

    private boolean sortIsValid(String sort, String order) {
        return sort == null && order == null || sort != null && order != null;
    }

    public XMLResponse findById(Integer id) {
        SpaceMarineXMLResponse response = new SpaceMarineXMLResponse();
        response.setSpaceMarine(spaceMarineRepo.findById(id).get());
        return response;
    }

    public XMLResponse update(Integer id, SpaceMarineRequest spaceMarineRequest) {
        SpaceMarineXMLResponse response = new SpaceMarineXMLResponse();
        if (spaceMarineRepo.findById(id).isPresent()){
            try {
                SpaceMarine spaceMarine = new SpaceMarine(spaceMarineRequest);
                spaceMarine.setId(id);
                Coordinates coordinates = coordinatesRepo.save(spaceMarine.getCoordinates());
                spaceMarine.setCoordinates(coordinates);
                if (!chapterRepo.existsById(spaceMarine.getName())){
                     chapterRepo.save(spaceMarine.getChapter());
                }
                if (!starshipRepo.existsById(spaceMarine.getStarshipId())){
                    throw new IllegalArgumentException("starshipId");
                }
                response.setSpaceMarine(spaceMarineRepo.save(spaceMarine));
            } catch (IllegalArgumentException e){
                SpaceMarineSearchWrongFieldsXMLResponse errorResposne = new SpaceMarineSearchWrongFieldsXMLResponse();
                errorResposne.setWrongFields(Arrays.asList(e.getMessage()));
                return errorResposne;
            }
        } else {
            SpaceMarineSearchWrongFieldsXMLResponse errorResposne = new SpaceMarineSearchWrongFieldsXMLResponse();
            errorResposne.setWrongFields(Arrays.asList("id"));
            return errorResposne;
        }
        return response;
    }

    public void delete(Integer id) {
        spaceMarineRepo.deleteById(id);
    }

    public void deleteByMeleeWeapon(MeleeWeapon meleeWeapon) {
        spaceMarineRepo.findAllByMeleeWeapon(meleeWeapon).stream()
                        .forEach(spaceMarine -> spaceMarineRepo.deleteById(spaceMarine.getId()));
    }

    public XMLResponse getMinByCoords() {
        return new SpaceMarineXMLResponse(spaceMarineRepo.findByMinCoords().get(0));
    }

    public Integer countByHealth(Double health){
        return spaceMarineRepo.count(health);
    }

    //public boolean isNumeric(String s){
    //    try{
    //        Double n = Double.parseDouble(s);
    //        return true;
    //    } catch (NumberFormatException e){
    //        return false;
    //    }
    //}

    //public List<String> validate(SpaceMarineRequest spaceMarineRequest){
    //    List<String> wrongFields = new ArrayList<>();

    //jk}
}
