package com.itmo.soa2.entities;

import com.itmo.soa2.controllers.requests.SpaceMarineRequest;
import com.itmo.soa2.controllers.responses.SpaceMarineXMLResponse;
import com.itmo.soa2.entities.domain.Chapter;
import com.itmo.soa2.entities.domain.Coordinates;
import com.itmo.soa2.entities.domain.MeleeWeapon;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "space_marine")
@XmlRootElement(name="spaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(nillable = true)
    private Integer id;
    @XmlElement(nillable = true)
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="coordinates_id", referencedColumnName = "id")
    @XmlElement(nillable = true)
    private Coordinates coordinates;
    @XmlTransient
    private Date creationDate;

    @XmlElement(name = "creationDate")
    private String creationDateStr;
    @XmlElement(nillable = true)
    private Double health;
    @XmlElement(nillable = true)
    private Boolean loyal;
    @XmlElement(nillable = true)
    private Double height;

    @XmlElement(nillable = true)
    private MeleeWeapon meleeWeapon;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="chapter_name", referencedColumnName = "name")
    @XmlElement(nillable = true)
    private Chapter chapter;
    @XmlElement(nillable = true)
    private Integer starshipId;

    public SpaceMarine(){
    }
    public SpaceMarine(SpaceMarineRequest req){
        this.name = req.getName();
        this.coordinates = req.getCoordinates();
        this.creationDate = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        DateFormat df = new SimpleDateFormat(pattern);
        this.creationDateStr = df.format(this.creationDate);
        this.creationDateStr = this.creationDateStr.replaceAll("\\s+", "T");
        this.creationDateStr += "Z";
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
