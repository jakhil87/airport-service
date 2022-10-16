package com.demo.airport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    private String id;
    @Id
    private String code;
    private String name;
    private String continent;
    private String wikipediaLink;
    private String keywords;

    @OneToMany
    @JoinColumn(name = "isoCountry")
    private List<Airport> airports = new ArrayList<>();
}
