package com.itmo.soa2.wsEndpoints;

import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

public class SpaceMarineConverter {
    public static _8080.api.v1.space_marines.SpaceMarine convertToWsSpaceMarine(com.itmo.soa2.entities.SpaceMarine inSm) {
        _8080.api.v1.space_marines.SpaceMarine outSm = new _8080.api.v1.space_marines.SpaceMarine();
        outSm.setId(inSm.getId());
        outSm.setName(inSm.getName());
        outSm.setCoordinates(convertToWsCoordinate(inSm.getCoordinates()));
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(inSm.getCreationDate());
        outSm.setCreationDate(DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(c));
        outSm.setHealth(inSm.getHealth());
        outSm.setLoyal(inSm.getLoyal());
        outSm.setHeight(inSm.getHeight());
        outSm.setMeleeWeapon(_8080.api.v1.space_marines.MeleeWeapon.fromValue(inSm.getMeleeWeapon().toString()));
        outSm.setChapter(convertToWsChapter(inSm.getChapter()));
        outSm.setStarshipId(inSm.getStarshipId());
        return outSm;
    }

    public static _8080.api.v1.space_marines.Coordinates convertToWsCoordinate(com.itmo.soa2.entities.domain.Coordinates inC){
        _8080.api.v1.space_marines.Coordinates outC = new _8080.api.v1.space_marines.Coordinates();
        outC.setId(inC.getId());
        outC.setX(inC.getX());
        outC.setY(inC.getY());
        return outC;
    }

    public static _8080.api.v1.space_marines.Chapter convertToWsChapter(com.itmo.soa2.entities.domain.Chapter inC){
        _8080.api.v1.space_marines.Chapter outC = new _8080.api.v1.space_marines.Chapter();
        outC.setName(inC.getName());
        outC.setWorld(inC.getWorld());
        outC.setParentLegion(inC.getParentLegion());
        return outC;
    }
}
