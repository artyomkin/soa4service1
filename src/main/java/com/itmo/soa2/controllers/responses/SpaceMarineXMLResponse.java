package com.itmo.soa2.controllers.responses;

import com.itmo.soa2.controllers.requests.SpaceMarineRequest;
import com.itmo.soa2.entities.SpaceMarine;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="SpaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SpaceMarineXMLResponse implements XMLResponse{

    private SpaceMarine spaceMarine;

    public SpaceMarineXMLResponse(SpaceMarine spaceMarine){
        this.spaceMarine = spaceMarine;
    }
    public SpaceMarineXMLResponse(){
    }

    @Override
    public Integer getCode() {
        return 201;
    }
}

