package com.demo.airport.mapper;

import com.demo.airport.domain.CountryWithMaxAirports;
import com.demo.airport.domain.Runway;
import com.demo.airport.entity.Country;
import com.demo.airport.exception.RecordNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class AirportResponseMapper {

    private AirportResponseMapper() {
        // private constructor for static class
    }

    public static List<CountryWithMaxAirports> mapCountryWithMaxAirport(List<String[]> results) {
        return results
            .stream()
            .map(result ->
                CountryWithMaxAirports.builder()
                    .countryCode(result[0])
                    .totalAirports(Integer.parseInt(result[1]))
                    .build()
            ).collect(Collectors.toList());
    }

    public static List<Runway> mapRunwaysForCountry(Country country) {
        if (country != null) {
            return country.getAirports().stream()
                .flatMap(airport -> airport.getRunways().stream())
                .map(runway -> Runway.builder()
                    .id(runway.getId())
                    .airportIdent(runway.getAirportIdent())
                    .airportRef(runway.getAirportRef())
                    .lengthFt(runway.getLengthFt())
                    .widthFt(runway.getWidthFt())
                    .surface(runway.getSurface())
                    .build()
                ).collect(Collectors.toList());
        } else
           throw new RecordNotFoundException("Requested country not found");
    }
}
