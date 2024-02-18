package com.itmo.soa2.entities.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@Entity
@Getter
@Setter
@XmlRootElement(name="coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;
    @NotNull(message = "coordinatesX")
    private Double x;
    @NotNull(message = "coordinatesY")
    private Double y;

    public Coordinates(){};
    public Coordinates(Double coordinatesX, Double coordinatesY) {
        this.x = coordinatesX;
        this.y = coordinatesY;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
