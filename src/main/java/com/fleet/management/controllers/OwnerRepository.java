package com.fleet.management.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fleet.management.models.Owner;

/**
 * Owners repository used for basic CRUD operations..
 *
 * @author Octav
 *
 */
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
