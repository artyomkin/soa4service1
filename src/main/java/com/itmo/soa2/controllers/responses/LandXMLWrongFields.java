package com.itmo.soa2.controllers.responses;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="LandWrongFields")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class LandXMLWrongFields implements XMLResponse {
    @XmlElement(name="wrongField")
    private List<String> wrongFields;

    @Override
    public Integer getCode() {
        return 400;
    }
}
