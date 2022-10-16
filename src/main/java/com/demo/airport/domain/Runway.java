package com.demo.airport.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Runway {
    private String id;
    private String airportRef;
    private String airportIdent;
    private Integer lengthFt;
    private Integer widthFt;
    private String surface;
}
