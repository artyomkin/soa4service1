package com.itmo.soa2.services;

import _8080.api.v1.space_marines.UpdateSpaceMarineRequest;
import com.itmo.soa2.controllers.requests.SpaceMarineRequest;
import com.itmo.soa2.controllers.responses.*;
import com.itmo.soa2.entities.SpaceMarine;
import com.itmo.soa2.entities.domain.Chapter;
import com.itmo.soa2.entities.domain.Coordinates;
import com.itmo.soa2.entities.domain.MeleeWeapon;
import com.itmo.soa2.exceptions.InvalidSortParamsException;
import com.itmo.soa2.exceptions.SpaceMarineWrongFieldsException;
import com.itmo.soa2.filters.Comparison;
import com.itmo.soa2.filters.Condition;
import com.itmo.soa2.repos.ChapterRepo;
import com.itmo.soa2.repos.CoordinatesRepo;
import com.itmo.soa2.repos.SpaceMarineRepository;
import com.itmo.soa2.filters.Filter;
import com.itmo.soa2.repos.StarshipRepo;
import _8080.api.v1.space_marines.CreateSpaceMarineRequest;
import _8080.api.v1.space_marines.GetAllSpaceMarinesRequest;
import _8080.api.v1.space_marines.GetAllSpaceMarinesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<SpaceMarine> getAll(GetAllSpaceMarinesRequest request) throws IllegalAccessException, InvalidSortParamsException {
        Map<String, String> parameters = convertToParameters(request);
        if (!sortIsValid(parameters.get("sort"), parameters.get("order"))){
            throw new InvalidSortParamsException("Invalid sort params.", Arrays.asList("sort", "order"));
        }
        parameters.entrySet().stream().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
        Map<String, String> searchParameters = parameters.entrySet().stream()
                .filter(parameter -> !Arrays.asList("page", "size", "sort", "order", "coordinatesX", "coordinatesY", "loyal", "meleeWeapon", "chapterName", "chapterParentLegion", "chapterWorld").contains(parameter.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Filter filter = buildFilter(searchParameters);
        List<SpaceMarine> result;
        parameters.putIfAbsent("page", "1");
        parameters.putIfAbsent("size", "1000");
        parameters.putIfAbsent("order", "ASC");
        if (filter.isEmpty()){
            if (parameters.get("sort") != null){
                result = spaceMarineRepo.findAll(PageRequest.of(
                        Integer.parseInt(parameters.get("page")) - 1,
                        Integer.parseInt(parameters.get("size")),
                        Sort.by(Sort.Direction.valueOf(parameters.get("order")), parameters.get("sort")))).getContent();
            } else {
                result = spaceMarineRepo.findAll(PageRequest.of(
                        Integer.parseInt(parameters.get("page")) - 1,
                        Integer.parseInt(parameters.get("size")))).getContent();
            }
        } else {
            if (parameters.get("sort") != null){
                result = spaceMarineRepo.findAll(filter, PageRequest.of(
                        Integer.parseInt(parameters.get("page")) - 1,
                        Integer.parseInt(parameters.get("size")),
                        Sort.by(Sort.Direction.valueOf(parameters.get("order")), parameters.get("sort")))).getContent();
            } else {
                result = spaceMarineRepo.findAll(filter, PageRequest.of(
                        Integer.parseInt(parameters.get("page")) - 1,
                        Integer.parseInt(parameters.get("size")))).getContent();
            }
        }
        Map<String, String> finalParameters = parameters;
        if (parameters.get("coordinatesX") != null){
            try{
                result = result.stream()
                        .filter(spaceMarine -> spaceMarine.getCoordinates().getX().equals(Double.valueOf(finalParameters.get("coordinatesX"))))
                        .collect(Collectors.toList());
            } catch (NumberFormatException e){
                result.clear();
            }
        }
        if (parameters.get("coordinatesY") != null){
            try{
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine.getCoordinates().getY().equals(Double.valueOf(finalParameters.get("coordinatesY"))))
                    .collect(Collectors.toList());
            } catch (NumberFormatException e){
                result.clear();
            }
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
        if (parameters.get("chapterParentLegion") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine.getChapter().getParentLegion().equals(finalParameters.get("chapterParentLegion")))
                    .collect(Collectors.toList());
        }
        if (parameters.get("chapterWorld") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine.getChapter().getWorld().equals(finalParameters.get("chapterWorld")))
                    .collect(Collectors.toList());
        }

        if (result.size() > 0){
            result.forEach(sm -> System.out.println(sm.getId()));
        } else {
            System.out.println("result is empty.");
        }
        return result;
    }
    public SpaceMarine save(CreateSpaceMarineRequest createSpaceMarineRequest) throws SpaceMarineWrongFieldsException {
        try {
            SpaceMarine spaceMarine = new SpaceMarine(createSpaceMarineRequest);
            if (spaceMarine.getStarshipId() != null && !starshipRepo.existsById(spaceMarine.getStarshipId())){
                throw new IllegalArgumentException("starshipId");
            }
            if (chapterRepo.existsByName(spaceMarine.getChapter().getName())){
                throw new IllegalArgumentException("chapterName");
            }
            return spaceMarineRepo.save(spaceMarine);
        } catch (IllegalArgumentException e){
            throw new SpaceMarineWrongFieldsException(e.getMessage());
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
    private Map<String, String> convertToParameters(GetAllSpaceMarinesRequest request) throws IllegalAccessException{
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
        resultParameters = resultParameters.entrySet().stream()
                .filter(entry -> !Objects.equals(entry.getValue(), "0") && !entry.getValue().isBlank())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return resultParameters;
    }

    private boolean sortIsValid(String sort, String order) {
        return sort == null && order == null || sort != null && order != null;
    }

    public SpaceMarine findById(Integer id) throws SpaceMarineWrongFieldsException {
        try{
            return spaceMarineRepo.findById(id).get();
        } catch (NoSuchElementException e){
            System.out.println("Space marine with id = " + id + " not found.");
            throw new SpaceMarineWrongFieldsException("id");
        }
    }

    @Transactional
    public SpaceMarine update(Integer id, UpdateSpaceMarineRequest updateSpaceMarineRequest) throws SpaceMarineWrongFieldsException {
        SpaceMarineXMLResponse response = new SpaceMarineXMLResponse();
        if (spaceMarineRepo.findById(id).isPresent()){
            SpaceMarine spaceMarine = new SpaceMarine(updateSpaceMarineRequest);
            spaceMarine.setId(id);
            Coordinates coordinates = coordinatesRepo.save(spaceMarine.getCoordinates());
            spaceMarine.setCoordinates(coordinates);
            String oldName = spaceMarineRepo.findById(id).get().getChapter().getName();
            String newName = spaceMarine.getChapter().getName();
            if (!newName.equals(oldName) && chapterRepo.existsByName(newName)){
                throw new IllegalArgumentException("chapterName");
            }
            chapterRepo.save(spaceMarine.getChapter());
            if (spaceMarine.getStarshipId() != null && !starshipRepo.existsById(spaceMarine.getStarshipId())){
                throw new IllegalArgumentException("starshipId");
            }
            spaceMarine = spaceMarineRepo.save(spaceMarine);
            return spaceMarine;
        } else {
            throw new SpaceMarineWrongFieldsException("id");
        }
    }

    @Transactional
    public void delete(Integer id) {
        SpaceMarine spaceMarine = spaceMarineRepo.findById(id).get();
        if (spaceMarine == null){
            return;
        }
        chapterRepo.deleteByName(spaceMarine.getChapter().getName());
        coordinatesRepo.deleteById(spaceMarine.getCoordinates().getId());
        spaceMarineRepo.deleteById(id);
    }

    public void deleteByMeleeWeapon(MeleeWeapon meleeWeapon) {
        spaceMarineRepo.findAllByMeleeWeapon(meleeWeapon).stream()
                        .forEach(spaceMarine -> spaceMarineRepo.deleteById(spaceMarine.getId()));
    }

    public SpaceMarine getMinByCoords() {
        return spaceMarineRepo.findByMinCoords().get(0);
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
