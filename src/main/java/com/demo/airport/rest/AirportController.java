package com.demo.airport.rest;

import com.demo.airport.domain.CountryWithMaxAirports;
import com.demo.airport.domain.Runway;
import com.demo.airport.entity.Country;
import com.demo.airport.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@SecurityScheme(
    name = "BasicAuth", // can be set to anything
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
@SecurityRequirement(name = "BasicAuth")
@Validated
public class AirportController {
    private final AirportService service;

    @Operation(
        summary = "Get countries with max number of airports",
        parameters = {
            @Parameter(
                name = "maxResults",
                description = "Parameter can be used to limit max number of results." +
                    "If not provided 10 results would be retrieved")
        },
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true,
                content = {
                    @Content(schema = @Schema(implementation = CountryWithMaxAirports.class))
                })
        }
    )
    @GetMapping("/countries-with-max-airports")
    @RolesAllowed({"USER", "ADMIN"})
    public List<CountryWithMaxAirports> getCountriesWithMaxAirports(
        @RequestParam(value = "maxResults", required = false) Integer maxRecords
    ) {
        log.debug("Retrieving getCountriesWithMaxAirports for with maxRecords: {}", maxRecords);
        return service.getCountriesWithMaxAirports((maxRecords != null) ? maxRecords : 10);
    }

    @Operation(
        summary = "Endpoint to search by country code or name",
        description = "Returns a list of runways for all the airports of the given country",
        parameters = {
            @Parameter(
                name = "search",
                description = "Filter country by Country code - case sensitive or Country name - case insensitive",
                required = true
            ),
        },
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true,
                content = {
                    @Content(schema = @Schema(implementation = Runway.class))
                }),
            @ApiResponse(responseCode = "404", description = "Country code not found")
        }
    )
    @GetMapping(value = "/runways-by-country")
    @RolesAllowed({"USER", "ADMIN"})
    public List<Runway> getRunwaysByCountryCodeOrName(@RequestParam(value = "search") String searchString) {
        log.debug("Retrieving getRunwaysByCountryCodeOrName for searchString: {}", searchString);
        return service.getRunwaysByCountryCodeOrName(searchString);
    }

    @Operation(
        summary = "Endpoint to search country with name containing search string for partial match",
        description = "Returns a list of runways for all the airports of the given country",
        parameters = {
            @Parameter(
                name = "search",
                description = "Filter country by Country name partially matching the search string of min length 3",
                required = true
            ),
        }
    )
    @GetMapping(value = "/countries-by-name")
    @RolesAllowed("ADMIN")
    public List<Country> getAirportsByCountryNameContaining(
        @RequestParam(name = "search", required = false) @Size(min = 3) String searchString
    ) {
        log.debug("Retrieving getAirportsByCountryNameContaining for searchString: {}", searchString);
        return service.getAirportsByCountryNameContaining(searchString);
    }
}
