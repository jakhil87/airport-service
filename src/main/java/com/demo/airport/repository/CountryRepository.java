package com.demo.airport.repository;

import com.demo.airport.entity.Country;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {
    @Query(
        value = "select isoCountry, count(ident) " +
            "from Airport group by isoCountry order by count(ident) desc "
    )
    public List<String[]> findTopCountries(PageRequest pageRequest);

    public Country findCountryByCodeOrNameIgnoreCase(
        String countryCode, String countryName
    );

    public List<Country> findCountryByNameContainingIgnoreCase(
        String countryName
    );
}