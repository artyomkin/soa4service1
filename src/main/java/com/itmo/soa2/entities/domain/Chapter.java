package com.itmo.soa2.entities.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Getter
@Setter
@XmlRootElement(name="chapter")
public class Chapter {
    @Id
    @NotNull(message = "chapterName")
    private String name;
    @NotNull(message = "parentLegion")
    private String parentLegion;
    @NotNull(message = "world")
    private String world;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentLegion() {
        return parentLegion;
    }

    public void setParentLegion(String parentLegion) {
        this.parentLegion = parentLegion;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }
}
