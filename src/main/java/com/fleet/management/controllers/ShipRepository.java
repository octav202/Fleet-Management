package com.fleet.management.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fleet.management.models.Ship;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
}
