package com.demo.airport.repository;

import com.demo.airport.entity.Runway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunwayRepository extends JpaRepository<Runway, String> {
}