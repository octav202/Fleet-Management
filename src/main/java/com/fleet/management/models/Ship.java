package com.fleet.management.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class representing the Ship object.
 * The Ship entity is mapped to the ship table.
 *
 * @author Octav
 *
 */
@Entity
@Table(name = "ship")
public class Ship implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", columnDefinition = "integer")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "ship_name", columnDefinition = "text")
	private String name;

	@Column(name = "imo_number", columnDefinition = "text")
	private String imoNumber;

	@OneToOne(mappedBy = "ship", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Category category;

	// @ManyToMany(mappedBy = "ships")
	// @JsonIgnoreProperties(value="ships")

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ship_owner", joinColumns = { @JoinColumn(name = "ship_id") }, inverseJoinColumns = {
			@JoinColumn(name = "owner_id") })
	@JsonIgnoreProperties(value = "owners")
	private Set<Owner> owners = new HashSet<>();

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
	 * Get ship name.
	 *
	 * @return ship name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set ship name,
	 *
	 * @param name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get ship's IMO number.
	 *
	 * @return IMO Number.
	 */
	public String getImoNumber() {
		return imoNumber;
	}

	/**
	 * Set ship's IMO number.
	 *
	 * @param imoNumber
	 */
	public void setImoNumber(String imoNumber) {
		this.imoNumber = imoNumber;
	}

	/**
	 * Get the ship's category.
	 *
	 * @return the @{Category} object.
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Set the ship's category object.
	 *
	 * @param category the @{Category} object
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Get this ship's owners.
	 *
	 * @return the list of owners.
	 */
	public Set<Owner> getOwners() {
		return owners;
	}

	/**
	 * Set the owners of this ship.
	 *
	 * @param owners list of owners.
	 */
	public void setOwners(Set<Owner> owners) {
		this.owners = owners;
	}

	@Override
	public String toString() {
		return "Ship [id=" + id + ", name=" + name + ", imoNumber=" + imoNumber + ", category=" + category + ", owners="
				+ this.owners + "]";
	}

}
