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
public class OwnerController {

	@Autowired
	OwnerRepository repo;

	/**
	 * Fetch all owners.
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
	 * @throws Exception
	 */
	@PutMapping("/owners/{id}")
	public ResponseEntity<Owner> updateOwner(@PathVariable(value = "id") Long ownerId, @Valid @RequestBody Owner newOwner)
			throws Exception {

		Owner oldOwner = repo.findById(ownerId).orElseThrow(() -> new Exception("Owner " + ownerId + " not found"));
		oldOwner.setName(newOwner.getName());
		oldOwner.setShips(newOwner.getShips());

		return ResponseEntity.ok(repo.save(oldOwner));
	}

	/**
	 * Delete Owner.
	 *
	 * @param ownerId id of the owner to be deleted.
	 * @throws Exception
	 */
	@DeleteMapping("/owners/{id}")
	public void updateOwner(@PathVariable(value = "id") Long ownerId) throws Exception {
		Owner owner = repo.findById(ownerId).orElseThrow(() -> new Exception("Owner " + ownerId + " not found"));
		repo.deleteById(ownerId);
	}

}
