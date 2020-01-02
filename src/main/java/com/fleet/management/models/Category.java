package com.fleet.management.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class representing the Category object.
 * The Category entity is mapped to the category table.
 * This class provides details about a specific ship.
 *
 * @author Octav
 *
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ship_id")
	@JsonIgnore
	private Ship ship;

	@Column(name = "ship_type", columnDefinition = "text")
	private String shipType;

	@Column(name = "ship_tonnage", columnDefinition = "integer")
	private long shipTonnage;

	/**
	 * Get object id.
	 *
	 * @return the id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set object id.
	 * This should not be used explicitly, since the id's are auto-generated
	 *
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get the ship associated with this category.
	 *
	 * @return the @{Ship} instance
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * Set the ship associated with this category.
	 *
	 * @param ship the @{Ship} instance
	 */
	public void setShip(Ship ship) {
		this.ship = ship;
	}

	/**
	 * Get the ship type.
	 *
	 * @return ship type.
	 */
	public String getShipType() {
		return shipType;
	}

	/**
	 * Set ship type.
	 *
	 * @param shipType the ship type.
	 */
	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	/**
	 * Get ship tonnage.
	 *
	 * @return ship tonnage.
	 */
	public long getShipTonnage() {
		return shipTonnage;
	}

	/**
	 * Set ship tonnage.
	 *
	 * @param shipTonnage.
	 */
	public void setShipTonnage(long shipTonnage) {
		this.shipTonnage = shipTonnage;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", ship=" + ship + ", shipType=" + shipType + ", shipTonnage=" + shipTonnage
				+ "]";
	}
}
