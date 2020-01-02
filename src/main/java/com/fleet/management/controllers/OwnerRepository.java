package com.fleet.management.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fleet.management.models.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
