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
import com.fleet.management.models.Category;

@RestController
public class CategoryController {

	@Autowired
	CategoryRepository repo;

	/**
	 * Fetch all categories..
	 *
	 * @return list of categories.
	 */
	@GetMapping("/categories")
	public List<Category> getCategories() {
		return repo.findAll();
	}

	/**
	 * Get category by Id.
	 *
	 * @param id - id of the category.
	 * @return the category entity.
	 */
	@RequestMapping("/categories/{id}")
	public Optional<Category> getCategoryById(@PathVariable long id) {
		return repo.findById(id);
	}

	/**
	 * Create a category.
	 *
	 * @param catehory - category to be added.
	 * @return the category entity.
	 */
	@PostMapping("/categories")
	public Category addCategory(@Valid @RequestBody Category category) {
		return repo.save(category);
	}

	/**
	 * Update Category.
	 *
	 * @param categoryId  id of the category to be updated.
	 * @param newCategory the new category.
	 * @return the updated category entity.
	 * @throws Exception
	 */
	@PutMapping("/categories/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryId, @Valid @RequestBody Category newCategory)
			throws Exception {

		Category oldCategory = repo.findById(categoryId).orElseThrow(() ->
		new ResourceNotFoundException("Category " + categoryId + " not found"));
		oldCategory.setShipType(newCategory.getShipType());
		oldCategory.setShipTonnage(newCategory.getShipTonnage());
		oldCategory.setShip(newCategory.getShip());

		return ResponseEntity.ok(repo.save(oldCategory));
	}

	/**
	 * Delete Category.
	 *
	 * @param categoryId id of the category to be deleted.
	 * @throws Exception
	 */
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable(value = "id") Long categoryId) throws ResourceNotFoundException {
		Category category = repo.findById(categoryId).orElseThrow(() -> 
		new ResourceNotFoundException("Category " + categoryId + " not found"));
		repo.deleteById(categoryId);
		return ResponseEntity.ok().build();
	}

}
