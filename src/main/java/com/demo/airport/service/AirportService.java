package com.demo.airport.service;

import com.demo.airport.domain.CountryWithMaxAirports;
import com.demo.airport.domain.Runway;
import com.demo.airport.entity.Country;
import com.demo.airport.mapper.AirportResponseMapper;
import com.demo.airport.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AirportService {

    private final CountryRepository repository;

    public List<Country> getAirportsByCountryNameContaining(String searchString) {
        return repository.findCountryByNameContainingIgnoreCase(searchString);
    }

    public List<CountryWithMaxAirports> getCountriesWithMaxAirports(Integer maxRecords) {
        List<String[]> results = repository.findTopCountries(PageRequest.of(0, maxRecords));
        return AirportResponseMapper.mapCountryWithMaxAirport(results);
    }

    public List<Runway> getRunwaysByCountryCodeOrName(String searchString) {
        Country country = repository.findCountryByCodeOrNameIgnoreCase(searchString, searchString);
        return AirportResponseMapper.mapRunwaysForCountry(country);
    }
}
