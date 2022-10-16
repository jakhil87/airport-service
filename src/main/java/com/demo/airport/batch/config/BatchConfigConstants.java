package com.demo.airport.batch.config;

public class BatchConfigConstants {
    private BatchConfigConstants() {
        // Private constructor for static class
    }
    protected static final String TEXT_RECORD = "Record: ";

    protected static final String[] AIRPORT_FIELDS = {"id", "ident", "type", "name", "latitude_deg", "longitude_deg",
        "elevation_ft", "continent", "iso_country", "iso_region", "municipality", "scheduled_service", "gps_code",
        "iata_code", "local_code", "home_link", "wikipediaLink", "keywords"};

    protected static final String[] COUNTRIES_FIELDS = {"id", "code", "name", "continent", "wikipedia_link", "keywords"};

    protected static final String[] RUNWAYS_FIELDS = {"id", "airport_ref", "airport_indent", "lengthFt", "widthFt", "surface"};
}
