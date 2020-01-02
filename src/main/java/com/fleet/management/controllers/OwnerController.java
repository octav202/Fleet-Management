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
import com.fleet.management.models.Owner;

/**
 * Owners REST API controller.
 *
 * @author Octav
 *
 */
@RestController
public class OwnerController {

	@Autowired
	OwnerRepository repo;

	/**
	 * Get all owners.
	 *
	 * @return list of owners.
	 */
	@GetMapping("/owners")
	public List<Owner> getOwners() {
		return repo.findAll();
	}

	/**
	 * Get owner by Id.
	 *
	 * @param id - id of the owner.
	 * @return the owner entity.
	 */
	@RequestMapping("/owners/{id}")
	public Optional<Owner> getOwnerById(@PathVariable long id) {
		return repo.findById(id);
	}

	/**
	 * Create an owner.
	 *
	 * @param owner - owner to be added.
	 * @return the owner entity.
	 */
	@PostMapping("/owners")
	public Owner addOwner(@Valid @RequestBody Owner owner) {
		return repo.save(owner);
	}

	/**
	 * Update Owner.
	 *
	 * @param ownerId  id of the owner to be updated.
	 * @param newOwner the new owner.
	 * @return the updated owner entity.
	 * @throws ResourceNotFoundException if the owner was not found.
	 */
	@PutMapping("/owners/{id}")
	public ResponseEntity<Owner> updateOwner(@PathVariable(value = "id") Long ownerId, @Valid @RequestBody Owner newOwner)
			throws ResourceNotFoundException {

		Owner oldOwner = repo.findById(ownerId).orElseThrow(() -> new ResourceNotFoundException("Owner " + ownerId + " not found"));
		oldOwner.setName(newOwner.getName());
		oldOwner.setShips(newOwner.getShips());
		return ResponseEntity.ok(repo.save(oldOwner));
	}

	/**
	 * Delete Owner.
	 *
	 * @param ownerId id of the owner to be deleted.
	 * @return the response entity
	 * @throws ResourceNotFoundException if the owner was not found.
	 */
	@DeleteMapping("/owners/{id}")
	public ResponseEntity<?> deleteOwner(@PathVariable(value = "id") Long ownerId) throws ResourceNotFoundException {
		Owner owner = repo.findById(ownerId).orElseThrow(() -> new ResourceNotFoundException("Owner " + ownerId + " not found"));

		/*
		for (Ship s : owner.getShips()) {
			s.getOwners().remove(owner);
		}
		*/
		repo.deleteById(ownerId);
		return ResponseEntity.ok().build();
	}

}
