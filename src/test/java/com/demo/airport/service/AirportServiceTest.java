package com.demo.airport.service;

import com.demo.airport.domain.CountryWithMaxAirports;
import com.demo.airport.entity.Airport;
import com.demo.airport.entity.Country;
import com.demo.airport.entity.Runway;
import com.demo.airport.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AirportServiceTest {
    private static final String SEARCH_STRING = "search-string";
    private final CountryRepository countryRepository = mock(CountryRepository.class);
    private final AirportService service = new AirportService(countryRepository);

    @Test
    void testGetAirportsByCountryNameContaining() {
        List<Country> response = new ArrayList<>();
        when(
            countryRepository.findCountryByNameContainingIgnoreCase(SEARCH_STRING)
        ).thenReturn(response);
        List<Country> countries = service.getAirportsByCountryNameContaining(SEARCH_STRING);
        assertThat(countries).isEqualTo(response);
        verify(countryRepository).findCountryByNameContainingIgnoreCase(SEARCH_STRING);
    }

    @Test
    void testGetCountriesWithMaxAirports() {

        CountryWithMaxAirports countryWithMaxAirports = CountryWithMaxAirports.builder()
            .totalAirports(5)
            .countryCode("IN")
            .build();
        List<CountryWithMaxAirports> response = List.of(countryWithMaxAirports);
        List<String[]> columns = Collections.singletonList(new String[]{
            countryWithMaxAirports.getCountryCode(), String.valueOf(countryWithMaxAirports.getTotalAirports())
        });
        when(
            countryRepository.findTopCountries(PageRequest.of(0, 5))
        ).thenReturn(columns);
        List<CountryWithMaxAirports> countries = service.getCountriesWithMaxAirports(5);
        assertThat(countries).isEqualTo(response);
        verify(countryRepository).findTopCountries(PageRequest.of(0, 5));
    }

    @Test
    void testGetRunwaysByCountryCodeOrName_Success() {
        Country country = new Country();
        Airport airport1 = new Airport();
        Runway runway = new Runway();
        runway.setId("id-1");
        airport1.setRunways(List.of(runway));
        country.setAirports(List.of(airport1));
        when(
            countryRepository.findCountryByCodeOrNameIgnoreCase(SEARCH_STRING, SEARCH_STRING)
        ).thenReturn(country);
        List<com.demo.airport.domain.Runway> runways = service.getRunwaysByCountryCodeOrName(SEARCH_STRING);
        assertThat(runways.get(0).getId()).isEqualTo("id-1");
        verify(countryRepository).findCountryByCodeOrNameIgnoreCase(SEARCH_STRING, SEARCH_STRING);
    }


}
