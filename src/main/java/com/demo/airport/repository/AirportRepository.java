package com.demo.airport.repository;

import com.demo.airport.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, String> {
}