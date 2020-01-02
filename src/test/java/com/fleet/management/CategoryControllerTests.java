package com.fleet.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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

import com.fleet.management.models.Category;

@SpringBootTest(classes = FleetManagementApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class CategoryControllerTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@Order(1)
	public void integrationTestGetAllCategories() {
		assertTrue(this.restTemplate.getForObject("http://localhost:" + port + "/categories", List.class).size() == 3);
	}

	@Test
	@Order(2)
	public void integrationTestGetCategoryById() {
		Category cat = restTemplate.getForObject("http://localhost:" + port + "/categories/{id}/", Category.class, 1);
		assertTrue(cat.getId() == 1);
		assertEquals(cat.getShipType(), "Cruise");
		assertEquals(cat.getShipTonnage(), 208081);
	}

	@Test
	@Order(3)
	public void integrationTestResponseAddCategory() {
		Category category = new Category();
		category.setShipType("Test Type");

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/categories",
				category, String.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	@Order(4)
	public void integrationTestObjectAddCategory() {
		Category category = new Category();
		category.setShipType("Test Type");
		category.setShipTonnage(40000);

		Category result = restTemplate.postForObject("http://localhost:" + port + "/categories", category,
				Category.class);
		assertEquals(result.getShipType(), category.getShipType());
		assertEquals(result.getShipTonnage(), category.getShipTonnage());
	}

	@Test
	@Order(5)
	public void integrationTestUpdateCategory() {

		Category updated = new Category();
		updated.setShipType("Updated Test Type");
		updated.setShipTonnage(11111);
		restTemplate.put("http://localhost:" + port + "/categories/{id}", updated, 3);

		Category cat = restTemplate.getForObject("http://localhost:" + port + "/categories/{id}/", Category.class, 3);
		assertEquals(cat.getShipType(), "Updated Test Type");
		assertEquals(cat.getShipTonnage(), 11111);
	}

	@Test
	@Order(6)
	public void integrationTestDeleteCategory() {
		restTemplate.delete("http://localhost:" + port + "/categories/{id}", 3);
		assertTrue(restTemplate.getForObject("http://localhost:" + port + "/categories", List.class).size() == 4);
	}

}
