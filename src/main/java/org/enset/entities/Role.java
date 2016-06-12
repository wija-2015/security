package org.enset.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Role implements Serializable {
@Id
private String role;
private String description;
// Getters, Setters et Constructeurs
public Role() {
	super();
	// TODO Auto-generated constructor stub
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

}