package com.fleet.management;

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

@RestController
public class ShipController {

	@Autowired
	ShipRepository repo;

	/**
	 * Fetch all ships.
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
		System.out.println(repo.findById(id));
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
	 * @return the update ship entity.
	 * @throws Exception
	 */
	@PutMapping("/ships/{id}")
	public ResponseEntity<Ship> updatePhone(@PathVariable(value = "id") Long shipId, @Valid @RequestBody Ship newShip)
			throws Exception {

		Ship oldShip = repo.findById(shipId).orElseThrow(() -> new Exception("Ship " + shipId + " not found"));
		oldShip.setName(newShip.getName());
		oldShip.setImoNumber(newShip.getImoNumber());
		oldShip.setCategory(newShip.getCategory());

		return ResponseEntity.ok(repo.save(oldShip));
	}

	/**
	 * Delete Ship.
	 *
	 * @param shipId id of the ship to be deleted.
	 *
	 * @return the update ship entity.
	 * @throws Exception
	 */
	@DeleteMapping("/ships/{id}")
	public void updatePhone(@PathVariable(value = "id") Long shipId) throws Exception {

		Ship ship = repo.findById(shipId).orElseThrow(() -> new Exception("Ship " + shipId + " not found"));
		repo.deleteById(shipId);
	}

}
