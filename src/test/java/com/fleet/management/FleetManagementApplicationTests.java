package com.fleet.management;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FleetManagementApplicationTests {

	@Autowired
	ShipController shipController;

	@Autowired
	CategoryController catController;

	@Autowired
	OwnerController ownerController;

	@Test
	void test_add_ship() {
		int size = shipController.getShips().size();

		Ship ship = new Ship();
		ship.setImoNumber("2468");
		ship.setName("Test Ship 1");

		Owner owner = ownerController.getOwnerById(3).get();
		Set<Owner> owners = new HashSet<>();
		owners.add(owner);
		ship.setOwners(owners);

		assertTrue(shipController.addShip(ship) != null);
		Category category = test_add_category(ship);
		ship.setCategory(category);

		// Test Name
		Ship addedShip = shipController.getShips().get(shipController.getShips().size() - 1);
		assertTrue(addedShip.getName().equals(ship.getName()));
		// Test IMO Number
		assertTrue(addedShip.getImoNumber().equals(ship.getImoNumber()));
		// Test Category
		assertTrue(addedShip.getCategory().getId() == category.getId());

		// Test Owners
		assertTrue(addedShip.getOwners().size() == 1);

		// Test ships size
		assertTrue(shipController.getShips().size() == size + 1);
	}

	@Test
	void test_delete_ship() {

		// Add a ship to be deleted
		Ship ship = new Ship();
		ship.setImoNumber("13579");
		ship.setName("Test Delete Ship");
		shipController.addShip(ship);

		int size = shipController.getShips().size();
		Ship lastShip = shipController.getShips().get(shipController.getShips().size() - 1);

		try {
			shipController.deleteShip(lastShip.getId());
		} catch (Exception e) {
			// Ship not found - size should not change
			assertTrue(shipController.getShips().size() == size);
		}

		assertTrue(shipController.getShips().size() == size - 1);
	}

	@Test
	void test_get_ship_by_id() {

		// Add a test ship
		Ship ship = new Ship();
		ship.setImoNumber("22222");
		ship.setName("Test Ship 2");
		long id = shipController.addShip(ship).getId();

		// Get Ship By ID
		Ship lastShip = shipController.getShips().get(shipController.getShips().size() - 1);
		assertTrue(id == lastShip.getId());
		assertTrue(ship.getName().equals(lastShip.getName()));
		assertTrue(ship.getImoNumber().equals(lastShip.getImoNumber()));
	}

	@Test
	void test_update_ship() {

		// Update last ship
		Ship lastShip = shipController.getShipById(1).get();
		lastShip.setName("Updated");
		lastShip.setImoNumber("99999");
		lastShip.getCategory().setShipTonnage(11111);
		lastShip.getCategory().setShipType("Updated Type");

		try {
			shipController.updateShip(lastShip.getId(), lastShip);
		} catch (Exception e) {
		}

		// Check if ship was properly updated
		Ship ship = shipController.getShips().get(shipController.getShips().size() - 1);
		assertTrue(ship.getName().equals("Updated"));
		assertTrue(ship.getImoNumber().equals("99999"));
		assertTrue(ship.getCategory().getShipType().equals("Updated Type"));
		assertTrue(ship.getCategory().getShipTonnage() == 11111);
	}

	@Test
	Category test_add_category(Ship ship) {
		Category c = new Category();
		c.setShipTonnage(50000);
		c.setShipType("Test Type");
		c.setShip(ship);
		assertTrue(catController.addCategory(c) != null);
		return c;
	}

	@Test
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
