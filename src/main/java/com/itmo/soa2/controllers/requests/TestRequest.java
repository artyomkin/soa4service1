package com.itmo.soa2.controllers.requests;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class TestRequest {
    public Integer t;
}
