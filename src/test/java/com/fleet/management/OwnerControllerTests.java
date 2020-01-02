package com.fleet.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.fleet.management.controllers.OwnerController;
import com.fleet.management.controllers.ShipController;
import com.fleet.management.models.Owner;
import com.fleet.management.models.Ship;

@SpringBootTest(classes = FleetManagementApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class OwnerControllerTests {

	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	OwnerController ownerController;
	@Autowired
	ShipController shipController;

	@Test
	@Order(1)
	public void integrationTestGetAllOwners() {
		assertTrue(restTemplate.getForObject("http://localhost:" + port + "/owners", List.class).size() == 3);
	}

	@Test
	@Order(2)
	public void integrationTestGetOwnerById() {
		Owner owner = restTemplate.getForObject("http://localhost:" + port + "/owners/{id}/", Owner.class, 1);
		assertTrue(owner.getId() == 1);
		assertEquals(owner.getName(), "Holland America");
	}

	@Test
	@Order(3)
	public void integrationTestResponseAddOwner() {
		Owner owner = new Owner();
		owner.setName("Test Owner");
		Set<Ship> ships = new HashSet<>();
		ships.add(shipController.getShipById(1).get());
		ships.add(shipController.getShipById(2).get());
		owner.setShips(ships);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/owners",
				owner, String.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	@Order(4)
	public void integrationTestObjectAddOwner() {
		Owner owner = new Owner();
		owner.setName("Test Owner");

		Owner result = restTemplate.postForObject("http://localhost:" + port + "/owners", owner, Owner.class);
		assertEquals(result.getName(), owner.getName());
	}

	@Test
	@Order(5)
	public void integrationTestUpdateOwner() {

		Owner updated = new Owner();
		updated.setName("Updated Test Owner");
		restTemplate.put("http://localhost:" + port + "/owners/{id}", updated, 3);

		Owner owner = restTemplate.getForObject("http://localhost:" + port + "/owners/{id}/", Owner.class, 3);
		assertEquals(owner.getName(), "Updated Test Owner");
	}

	@Test
	@Order(5)
	public void integrationTestDeleteOwner() {
		restTemplate.delete("http://localhost:" + port + "/owners/{id}", 3);
		assertTrue(restTemplate.getForObject("http://localhost:" + port + "/owners", List.class).size() == 4);
	}

	/*************************************************
	 ************ Controller Unit Testing ************
	 *************************************************/
	@Test
	@Order(6)
	void test_add_owner() {

		int size = ownerController.getOwners().size();

		Owner owner = new Owner();
		owner.setName("Test Owner");

		Set<Ship> ships = new HashSet<>();
		ships.add(shipController.getShipById(1).get());
		ships.add(shipController.getShipById(2).get());
		owner.setShips(ships);

		// Test return value
		assertTrue(ownerController.addOwner(owner) != null);

		// Test that owners size has increased by 1
		assertTrue(ownerController.getOwners().size() == size + 1);

		// Test that the ships were added for this owner
		Owner addedOwner = ownerController.getOwners().get(ownerController.getOwners().size() - 1);
		assertTrue(addedOwner.getShips().size() == 2);
	}

	@Test
	@Order(7)
	void test_delete_owner() {

		int size = ownerController.getOwners().size();

		// Delete owner (id-1) who owns two ships
		Owner owner = ownerController.getOwnerById(1).get();
		try {
			ownerController.deleteOwner(owner.getId());
		} catch (Exception e) {
		}

		// Test that the owners size has decreased by 1
		assertTrue(ownerController.getOwners().size() == size - 1);
	}

	@Test
	@Order(8)
	void test_update_owner() {
		Owner owner = ownerController.getOwnerById(2).get();

		// Add one ship(id-3) to owner(id-2)
		Set<Ship> ships = new HashSet<>();
		ships.add(shipController.getShipById(3).get());
		owner.setShips(ships);

		// Update Owner name
		owner.setName("Updated");
		owner.setShips(ships);
		try {
			ownerController.updateOwner(owner.getId(), owner);
		} catch (Exception e) {
		}

		Owner updated = ownerController.getOwnerById(2).get();
		assertTrue(updated.getName().equals("Updated"));
		assertTrue(updated.getShips().size() == 1);
	}

}
