package com.clinica.springboot.model.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String animal;
	private String cpf_dono;
	private String raca;
	private int idade;
	private String idVeterinario;

	public Pet() {
	}

	public Pet(int id, String animal, String cpf_dono, String raca, int idade, String idVeterinario) {
		super();
		this.id = id;
		this.animal = animal;
		this.cpf_dono = cpf_dono;
		this.raca = raca;
		this.idade = idade;
		this.idVeterinario = idVeterinario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getIdVeterinario() {
		return idVeterinario;
	}

	public void setIdVeterinario(String idVeterinario) {
		this.idVeterinario = idVeterinario;
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
