package com.itmo.soa2.entities;

import _8080.api.v1.space_marines.UpdateSpaceMarineRequest;
import com.itmo.soa2.controllers.requests.SpaceMarineRequest;
import com.itmo.soa2.controllers.responses.SpaceMarineXMLResponse;
import com.itmo.soa2.entities.domain.Chapter;
import com.itmo.soa2.entities.domain.Coordinates;
import com.itmo.soa2.entities.domain.MeleeWeapon;
import _8080.api.v1.space_marines.CreateSpaceMarineRequest;
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

    public SpaceMarine(CreateSpaceMarineRequest req) {
        this.name = req.getName();
        this.coordinates = new Coordinates();
        this.coordinates.setX(req.getCoordinates().getX());
        this.coordinates.setY(req.getCoordinates().getY());
        this.creationDate = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        DateFormat df = new SimpleDateFormat(pattern);
        this.creationDateStr = df.format(this.creationDate);
        this.creationDateStr = this.creationDateStr.replaceAll("\\s+", "T");
        this.creationDateStr += "Z";
        this.health = req.getHealth();
        this.loyal = req.isLoyal();
        this.height = req.getHeight();
        this.meleeWeapon = MeleeWeapon.valueOf(req.getMeleeWeapon().toString());
        this.chapter = new Chapter();
        this.chapter.setName(req.getChapter().getName());
        this.chapter.setParentLegion(req.getChapter().getParentLegion());
        this.chapter.setWorld(req.getChapter().getWorld());
        this.starshipId = req.getStarshipId();
    }

    public SpaceMarine(UpdateSpaceMarineRequest req) {
        this.name = req.getName();
        this.coordinates = new Coordinates();
        this.coordinates.setX(req.getCoordinates().getX());
        this.coordinates.setY(req.getCoordinates().getY());
        this.creationDate = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        DateFormat df = new SimpleDateFormat(pattern);
        this.creationDateStr = df.format(this.creationDate);
        this.creationDateStr = this.creationDateStr.replaceAll("\\s+", "T");
        this.creationDateStr += "Z";
        this.health = req.getHealth();
        this.loyal = req.isLoyal();
        this.height = req.getHeight();
        this.meleeWeapon = MeleeWeapon.valueOf(req.getMeleeWeapon().toString());
        this.chapter = new Chapter();
        this.chapter.setName(req.getChapter().getName());
        this.chapter.setParentLegion(req.getChapter().getParentLegion());
        this.chapter.setWorld(req.getChapter().getWorld());
        this.starshipId = req.getStarshipId();
    }

    public Integer getStarshipId() {
        return starshipId;
    }

    public SpaceMarine(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationDateStr() {
        return creationDateStr;
    }

    public void setCreationDateStr(String creationDateStr) {
        this.creationDateStr = creationDateStr;
    }

    public Double getHealth() {
        return health;
    }

    public void setHealth(Double health) {
        this.health = health;
    }

    public Boolean getLoyal() {
        return loyal;
    }

    public void setLoyal(Boolean loyal) {
        this.loyal = loyal;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void setStarshipId(Integer starshipId) {
        this.starshipId = starshipId;
    }
}
