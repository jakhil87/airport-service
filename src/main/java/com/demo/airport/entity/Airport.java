package com.demo.airport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airport {
    @Id
    private String id;
    private String ident;
    private String type;
    private String name;
    private String latitudeDeg;
    private String longtitudeDeg;
    private String elevationFt;
    private String continent;
    private String isoCountry;
    private String isoRegion;
    private String municipality;
    private String scheduledService;
    private String gpsCode;
    private String iataCode;
    private String localCode;
    private String homeLink;
    private String wikipediaLink;
    @Lob
    private String keywords;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "airportRef")
    private List<Runway> runways = new ArrayList<>();
}
