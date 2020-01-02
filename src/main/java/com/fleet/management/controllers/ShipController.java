package com.fleet.management.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleet.management.ResourceNotFoundException;
import com.fleet.management.models.Ship;

/**
 * Ships REST API controller.
 *
 * @author Octav
 *
 */
@RestController
public class ShipController {

	@Autowired
	ShipRepository repo;

	/**
	 * Get all ships.
	 *
	 * @return list of ships.
	 */
	@GetMapping("/ships")
	public List<Ship> getShips() {
		return repo.findAll();
	}

	/**
	 * Get ship by Id.
	 *
	 * @param id - id of the ship.
	 * @return the ship entity.
	 */
	@RequestMapping("/ships/{id}")
	public Optional<Ship> getShipById(@PathVariable long id) {
		return repo.findById(id);
	}

	/**
	 * Create a ship.
	 *
	 * @param ship - ship to be added.
	 * @return the ship entity.
	 */
	@PostMapping("/ships")
	public Ship addShip(@Valid @RequestBody Ship ship) {
		return repo.save(ship);
	}

	/**
	 * Update Ship.
	 *
	 * @param shipId  id of the ship to be updated.
	 * @param newShip the new ship.
	 * @return the updated ship entity.
	 * @throws @{ResourceNotFoundException} if the ship was not found.
	 */
	@PutMapping("/ships/{id}")
	public ResponseEntity<Ship> updateShip(@PathVariable(value = "id") Long shipId, @Valid @RequestBody Ship newShip)
			throws ResourceNotFoundException {

		Ship oldShip = repo.findById(shipId).orElseThrow(() -> new ResourceNotFoundException("Ship " + shipId + " not found"));
		oldShip.setName(newShip.getName());
		oldShip.setImoNumber(newShip.getImoNumber());
		oldShip.setCategory(newShip.getCategory());

		return ResponseEntity.ok(repo.save(oldShip));
	}

	/**
	 * Delete Ship.
	 *
	 * @param shipId id of the ship to be deleted.
	 * @throws @{ResourceNotFoundException} if the ship was not found.
	 */
	@DeleteMapping("/ships/{id}")
	public ResponseEntity<?> deleteShip(@PathVariable(value = "id") Long shipId) throws ResourceNotFoundException {
		Ship ship = repo.findById(shipId).orElseThrow(() -> new ResourceNotFoundException("Ship " + shipId + " not found"));

		/*
		for (Owner o : ship.getOwners()) {
			o.getShips().remove(ship);
		}
		*/

		repo.deleteById(shipId);
		return ResponseEntity.ok().build();
	}
}
