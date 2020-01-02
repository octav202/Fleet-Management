package com.fleet.management.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class representing the Owner object.
 * The Owner entity is mapped to the owner table.
 *
 * @author Octav
 *
 */
@Entity
@Table(name = "owner")
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "owner_name", columnDefinition = "text")
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ship_owner", joinColumns = { @JoinColumn(name = "owner_id") }, inverseJoinColumns = {
			@JoinColumn(name = "ship_id") })
	@JsonIgnoreProperties(value = "owners")
	Set<Ship> ships = new HashSet<>();

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
	 * @param id the id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get owner name.
	 *
	 * @return owner name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set owner name.
	 *
	 * @param name the owner name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the list of ships owned by this owner.
	 *
	 * @return list of ships.
	 */
	public Set<Ship> getShips() {
		return ships;
	}

	/**
	 * Set the list of ships owned by this owner.
	 *
	 * @param ships list of ships.
	 */
	public void setShips(Set<Ship> ships) {
		this.ships = ships;
	}

	@Override
	public String toString() {
		return "Owner [id=" + id + ", name=" + name + ", ships=" + ships + "]";
	}
}
