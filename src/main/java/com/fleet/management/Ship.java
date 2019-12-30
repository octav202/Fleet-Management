package com.fleet.management;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ship")
public class Ship implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", columnDefinition = "integer")
	private long id;

	@Column(name = "ship_name", columnDefinition = "text")
	private String name;

	@Column(name = "imo_number", columnDefinition = "text")
	private String imoNumber;

	@OneToOne(mappedBy = "ship", fetch = FetchType.EAGER)
	private Category category;

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

}
