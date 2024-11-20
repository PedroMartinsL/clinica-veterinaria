package com.clinica.springboot.model.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.clinica.springboot.model.entities.enums.ConsultaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Consulta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant data;

	private String doenca;
	
	@ManyToOne
	@JoinColumn(name = "veterinario_id")
	private Veterinario veterinario;
	private Integer consultaStatus;

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	public Consulta() {
	}

	public Consulta(Long id, Instant data, String doenca, Veterinario veterinario, Integer consultaStatus, Pet pet) {
		super();
		this.id = id;
		this.data = data;
		this.doenca = doenca;
		this.veterinario = veterinario;
		this.consultaStatus = consultaStatus;
		this.pet = pet;
	}

	public String getDoenca() {
		return doenca;
	}

	public void setDoenca(String doenca) {
		this.doenca = doenca;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setId_veterinario(Veterinario Veterinario) {
		this.veterinario = Veterinario;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getData() {
		return data;
	}

	public void setData(Instant data) {
		this.data = data;
	}

	public ConsultaStatus getConsultaStatus() {
		return ConsultaStatus.valueOf(consultaStatus);
	}

	public void setConsultaStatus(ConsultaStatus orderStatus) {
		if (orderStatus != null)
			this.consultaStatus = orderStatus.getCode();
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
		Consulta other = (Consulta) obj;
		return Objects.equals(id, other.id);
	}

}