package com.itmo.soa2.controllers.requests;

import com.itmo.soa2.controllers.requests.constraints.SortConstraint;
import com.itmo.soa2.entities.domain.Chapter;
import com.itmo.soa2.entities.domain.Coordinates;
import com.itmo.soa2.entities.domain.MeleeWeapon;
import lombok.Data;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name="SpaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarineRequest {
    @NotNull(message = "name")
    private String name;
    @NotNull(message = "coordinates")
    private Coordinates coordinates;
    @Min(value = 0, message = "health")
    @NotNull(message = "health")
    private Double health;
    @NotNull(message = "loyal")
    private String loyal;
    @Min(value = 0, message = "height")
    @NotNull(message = "height")
    private Double height;
    @NotNull(message = "meleeWeapon")
    private MeleeWeapon meleeWeapon;
    @NotNull(message = "chapter")
    private Chapter chapter;
    @NotNull(message = "starshipId")
    private Integer starshipId;
}

