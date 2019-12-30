package com.fleet.management;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "owner")
public class Owner {

	@Id
	@Column(name = "id", columnDefinition = "integer")
	private long id;

	@Column(name = "owner_name", columnDefinition = "text")
	private String name;

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

	@Override
	public String toString() {
		return "Owner [id=" + id + ", name=" + name + "]";
	}

}
