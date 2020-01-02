package com.fleet.management.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fleet.management.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
