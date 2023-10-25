package com.itmo.soa2.entities;

import com.itmo.soa2.controllers.requests.SpaceMarineRequest;
import com.itmo.soa2.controllers.responses.SpaceMarineXMLResponse;
import com.itmo.soa2.entities.domain.Chapter;
import com.itmo.soa2.entities.domain.Coordinates;
import com.itmo.soa2.entities.domain.MeleeWeapon;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "space_marine")
public class SpaceMarine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;
    private Date creationDate;
    private Double health;
    private Boolean loyal;
    private Double height;

    private MeleeWeapon meleeWeapon;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="chapter_name", referencedColumnName = "name")
    private Chapter chapter;
    private Integer starshipId;

    public SpaceMarine(){
    }
    public SpaceMarine(SpaceMarineRequest req){
        this.name = req.getName();
        this.coordinates = req.getCoordinates();
        this.creationDate = new Date();
        this.health = req.getHealth();
        if (req.getLoyal().equals("true")){
            this.loyal = true;
        } else if (req.getLoyal().equals("false")) {
            this.loyal = false;
        } else {
            throw new IllegalArgumentException("loyal");
        }
        this.height = req.getHeight();
        this.meleeWeapon = req.getMeleeWeapon();
        this.chapter = req.getChapter();
        this.starshipId = req.getStarshipId();
    }
}
