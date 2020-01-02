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

import com.fleet.management.controllers.CategoryController;
import com.fleet.management.controllers.OwnerController;
import com.fleet.management.controllers.ShipController;
import com.fleet.management.models.Category;
import com.fleet.management.models.Owner;
import com.fleet.management.models.Ship;

@SpringBootTest(classes = FleetManagementApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class ShipControllerTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	ShipController shipController;
	@Autowired
	OwnerController ownerController;
	@Autowired
	CategoryController catController;

	@Test
	@Order(1)
	public void integrationTestGetAllShips() {
		assertTrue(this.restTemplate.getForObject("http://localhost:" + port + "/ships", List.class).size() == 3);
	}

	@Test
	@Order(2)
	public void integrationTestGetShipById() {
		Ship ship = restTemplate.getForObject("http://localhost:" + port + "/ships/{id}/", Ship.class, 1);
		assertTrue(ship.getId() == 1);
		assertEquals(ship.getName(), "Symphony of the seas");
	}

	@Test
	@Order(3)
	public void integrationTestResponseAddShip() {
		Ship ship = new Ship();
		ship.setName("Test Response Ship");
		ship.setImoNumber("777");
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/ships", ship,
				String.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	@Order(4)
	public void integrationTestObjectAddShip() {
		Ship ship = new Ship();
		ship.setName("Test Object Ship");
		ship.setImoNumber("987654321");
		Ship result = restTemplate.postForObject("http://localhost:" + port + "/ships", ship, Ship.class);
		assertEquals(result.getName(), ship.getName());
		assertEquals(result.getImoNumber(), ship.getImoNumber());
	}

	@Test
	@Order(5)
	public void integrationTestUpdateShip() {

		Ship updated = new Ship();
		updated.setName("Updated Test Ship");
		updated.setImoNumber("Updated IMO Number");
		restTemplate.put("http://localhost:" + port + "/ships/{id}", updated, 3);

		Ship ship = restTemplate.getForObject("http://localhost:" + port + "/ships/{id}/", Ship.class, 3);
		assertEquals(ship.getName(), "Updated Test Ship");
		assertEquals(ship.getImoNumber(), "Updated IMO Number");
	}

	@Test
	@Order(6)
	public void integrationTestDeleteShip() {
		restTemplate.delete("http://localhost:" + port + "/ships/{id}", 3);
		assertTrue(restTemplate.getForObject("http://localhost:" + port + "/ships", List.class).size() == 4);
	}

	/*************************************************
	 ************ Controller Unit Testing ************
	 *************************************************/

	@Test
	@Order(7)
	void test_add_ship() {
		int size = shipController.getShips().size();

		Ship ship = new Ship();
		ship.setImoNumber("2468");
		ship.setName("Test Ship");

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
	@Order(8)
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
	@Order(9)
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
	@Order(10)
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
	@Order(11)
	Category test_add_category(Ship ship) {
		Category c = new Category();
		c.setShipTonnage(50000);
		c.setShipType("Test Type");
		c.setShip(ship);
		assertTrue(catController.addCategory(c) != null);
		return c;
	}

}
