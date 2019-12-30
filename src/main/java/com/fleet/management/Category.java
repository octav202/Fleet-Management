package com.fleet.management;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "category")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", columnDefinition = "integer")
	private long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ship_id")
	@JsonIgnore
	private Ship ship;

	@Column(name = "ship_type", columnDefinition = "text")
	private String shipType;

	@Column(name = "ship_tonnage", columnDefinition = "integer")
	private long shipTonnage;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public long getShipTonnage() {
		return shipTonnage;
	}

	public void setShipTonnage(long shipTonnage) {
		this.shipTonnage = shipTonnage;
	}

}
