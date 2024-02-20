package com.itmo.soa2.wsEndpoints;

public class StarshipConverter {
    public static _8080.api.v1.space_marines.Starship convertToWsStarship(com.itmo.soa2.entities.Starship inS){
        _8080.api.v1.space_marines.Starship outS = new _8080.api.v1.space_marines.Starship();
        outS.setId(inS.getId());
        outS.setName(inS.getName());
        return outS;
    }
}
