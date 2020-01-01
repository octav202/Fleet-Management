package com.fleet.management;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "owner")
public class Owner {

	@Id
	@Column(name = "id", columnDefinition = "integer")
	private long id;

	@Column(name = "owner_name", columnDefinition = "text")
	private String name;

	@ManyToMany()
    @JoinTable(
        name = "ship_owner",
        joinColumns = { @JoinColumn(name = "owner_id") },
        inverseJoinColumns = { @JoinColumn(name = "ship_id") }
    )
	@JsonIgnoreProperties(value = "owners")
	Set<Ship> ships = new HashSet<>();

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

	public Set<Ship> getShips() {
		return ships;
	}

	public void setShips(Set<Ship> ships) {
		this.ships = ships;
	}

	@Override
	public String toString() {
		return "Owner [id=" + id + ", name=" + name + "]";
	}

}
