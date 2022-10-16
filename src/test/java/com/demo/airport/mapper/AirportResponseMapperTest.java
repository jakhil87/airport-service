package com.demo.airport.mapper;

import com.demo.airport.domain.CountryWithMaxAirports;
import com.demo.airport.entity.Airport;
import com.demo.airport.entity.Country;
import com.demo.airport.entity.Runway;
import com.demo.airport.exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AirportResponseMapperTest {

    @Test
    void testMapCountryWithMaxAirport() {
        List<String[]> results = List.of(
            new String[]{"IN", "5"},
            new String[]{"NL", "3"}
        );
        List<CountryWithMaxAirports> response = AirportResponseMapper.mapCountryWithMaxAirport(results);
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getCountryCode()).isEqualTo("IN");
        assertThat(response.get(0).getTotalAirports()).isEqualTo(5);
        assertThat(response.get(1).getCountryCode()).isEqualTo("NL");
        assertThat(response.get(1).getTotalAirports()).isEqualTo(3);
    }

    @Test
    void testMapRunwaysForCountry_Success() {

        List<com.demo.airport.domain.Runway> response = AirportResponseMapper.mapRunwaysForCountry(country());
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getId()).isEqualTo("1");
        assertThat(response.get(0).getAirportIdent()).isEqualTo("AP1");
        assertThat(response.get(0).getAirportRef()).isEqualTo("AR1");
        assertThat(response.get(0).getSurface()).isEqualTo("SF1");
        assertThat(response.get(0).getLengthFt()).isEqualTo(10000);
        assertThat(response.get(0).getWidthFt()).isEqualTo(1000);

        assertThat(response.get(1).getId()).isEqualTo("2");
        assertThat(response.get(1).getAirportIdent()).isEqualTo("AP2");
        assertThat(response.get(1).getAirportRef()).isEqualTo("AR2");
        assertThat(response.get(1).getSurface()).isEqualTo("SF2");
        assertThat(response.get(1).getLengthFt()).isEqualTo(5000);
        assertThat(response.get(1).getWidthFt()).isEqualTo(500);
    }

    @Test
    void testMapRunwaysForCountry_NotFound() {
        assertThrows(RecordNotFoundException.class, () ->
            AirportResponseMapper.mapRunwaysForCountry(null)
        );
    }

    private Country country(){
        Runway runway1 = new Runway();
        runway1.setId("1");
        runway1.setAirportIdent("AP1");
        runway1.setAirportRef("AR1");
        runway1.setSurface("SF1");
        runway1.setLengthFt(10000);
        runway1.setWidthFt(1000);

        Runway runway2 = new Runway();
        runway2.setId("2");
        runway2.setAirportIdent("AP2");
        runway2.setAirportRef("AR2");
        runway2.setSurface("SF2");
        runway2.setLengthFt(5000);
        runway2.setWidthFt(500);

        Airport airport1 = new Airport();
        airport1.setRunways(List.of(runway1));
        Airport airport2 = new Airport();
        airport2.setRunways(List.of(runway2));

        Country country = new Country();
        country.setAirports(List.of(airport1, airport2));
        return country;
    }

}
