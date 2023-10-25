package com.itmo.soa2.controllers.requests;

import com.itmo.soa2.controllers.responses.XMLResponse;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Starship")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class StarshipRequest {
    private Integer id;
    private String name;
}
