package com.demo.airport.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryWithMaxAirports {
    private String countryCode;
    private int totalAirports;
}
