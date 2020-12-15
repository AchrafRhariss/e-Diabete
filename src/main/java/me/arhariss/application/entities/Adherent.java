package me.arhariss.application.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.AccessType;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


@Entity
@Access(value = AccessType.PROPERTY)
public class Adherent implements Serializable {

	/**
	 * used for serializing
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private StringProperty orderedNumber;
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty address;
	private StringProperty telephone;
	private StringProperty usedDrugs;
	private ObjectProperty<LocalDate> adhesionDate;
	private ObjectProperty<LocalDate> birthDate;
	private IntegerProperty age;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getOrderedNumber() {
		return orderedNumber.get();
	}
	public StringProperty orderedNumberProperty() {
		return orderedNumber;
	}
	public void setOrderedNumber(String orderedNumber) {
		this.orderedNumber = new SimpleStringProperty(orderedNumber);
	}
	
	@Column
	public String getFirstName() {
		return firstName.get();
	}
	public StringProperty firstNameProperty() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = new SimpleStringProperty(firstName);
	}
	
	@Column
	public String getLastName() {
		return lastName.get();
	}
	public StringProperty lastNameProperty() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = new SimpleStringProperty(lastName);
	}
	
	@Column
	public String getAddress() {
		return address.get();
	}
	public StringProperty addressProperty() {
		return address;
	}
	public void setAddress(String address) {
		this.address = new SimpleStringProperty(address);
	}
	
	@Column
	public String getTelephone() {
		return telephone.get();
	}
	public StringProperty telephoneProperty() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = new SimpleStringProperty(telephone);
	}
	
	@Column
	public String getUsedDrugs() {
		return usedDrugs.get();
	}
	public StringProperty usedDrugsProperty() {
		return usedDrugs;
	}
	public void setUsedDrugs(String usedDrugs) {
		this.usedDrugs = new SimpleStringProperty(usedDrugs);
	}
	
	@Column
	public LocalDate getAdhesionDate() {
		return adhesionDate.get();
	}
	public ObjectProperty<LocalDate> adhesionDateProperty() {
		return adhesionDate;
	}
	public void setAdhesionDate(LocalDate adhesionDate) {
		this.adhesionDate = new SimpleObjectProperty<LocalDate>(adhesionDate);
	}
	
	@Column
	public LocalDate getBirthDate() {
		return birthDate.get();
	}
	public ObjectProperty<LocalDate> birthDateProperty() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = new SimpleObjectProperty<LocalDate>(birthDate);
	}
	
	@Column
	public Integer getAge() {
		return age.get();
	}
	public IntegerProperty ageProperty() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = new SimpleIntegerProperty(age);
	}
	
}
