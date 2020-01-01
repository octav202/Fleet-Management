package com.fleet.management;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImoNumber() {
		return imoNumber;
	}

	public void setImoNumber(String imoNumber) {
		this.imoNumber = imoNumber;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Owner> getOwners() {
		return owners;
	}

	public void setOwners(Set<Owner> owners) {
		this.owners = owners;
	}

	@Override
	public String toString() {
		return "Ship [id=" + id + ", name=" + name + ", imoNumber=" + imoNumber + ", category=" + category + ", owners="
				+ this.owners + "]";
	}

}
