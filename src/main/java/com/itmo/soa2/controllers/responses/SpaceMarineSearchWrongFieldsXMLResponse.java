package com.itmo.soa2.controllers.responses;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="SpaceMarineSearchWrongFields")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SpaceMarineSearchWrongFieldsXMLResponse implements XMLResponse {
    @XmlElement(name="wrongField")
    private List<String> wrongFields;

    public SpaceMarineSearchWrongFieldsXMLResponse(List<String> wrongFields) {
        this.wrongFields = wrongFields;
    }

    @Override
    public Integer getCode() {
        return 400;
    }
}
