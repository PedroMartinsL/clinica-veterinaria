package com.clinica.springboot.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Pet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String animal;
	private String cpf_dono;
	private String raca;
	private Integer idade;
	
	@JsonIgnore
	@OneToMany(mappedBy = "pet")
	private List<Consulta> consultas = new ArrayList<>();

	public Pet() {
	}

	public Pet(Long id, String animal, String cpf_dono, String raca, Integer idade) {
		super();
		this.id = id;
		this.animal = animal;
		this.cpf_dono = cpf_dono;
		this.raca = raca;
		this.idade = idade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public String getCpf_dono() {
		return cpf_dono;
	}

	public void setCpf_dono(String cpf_dono) {
		this.cpf_dono = cpf_dono;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		return id == other.id;
	}

}
