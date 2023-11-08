package com.itmo.soa2.controllers.requests;

import com.itmo.soa2.controllers.requests.constraints.SortConstraint;
import com.itmo.soa2.entities.domain.MeleeWeapon;
import lombok.Data;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@XmlRootElement(name="SpaceMarine")
public class SpaceMarineSearchRequest {
    private Integer id;
    private Date creationDate;
    @Min(value = 1, message = "page")
    private Integer page = 1;
    @Min(value = 1, message = "size")
    private Integer size = 10;
    @SortConstraint
    private String sort;
    private Sort.Direction order;
    private String name;
    @Digits(integer = Integer.MAX_VALUE, fraction = 5, message = "coordinatesX")
    private Double coordinatesX;
    @Digits(integer = Integer.MAX_VALUE, fraction = 5, message = "coordinatesY")
    private Double coordinatesY;
    private String loyal;
    @Min(value = 0, message = "health")
    @Digits(integer = Integer.MAX_VALUE, fraction = 5, message = "health")
    private Double health;
    @Min(value = 0, message = "height")
    @Digits(integer = Integer.MAX_VALUE, fraction = 5, message = "height")
    private Double height;
    private String meleeWeapon;
    private String chapterName;
    private String chapterParentLegion;
    private String chapterWorld;
    private Integer starshipId;
}
