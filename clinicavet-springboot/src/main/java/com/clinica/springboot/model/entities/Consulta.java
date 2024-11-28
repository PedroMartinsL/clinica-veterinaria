package com.clinica.springboot.model.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.clinica.springboot.model.entities.enums.ConsultaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultas")
public class Consulta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant data;

	@ManyToOne
	@JoinColumn(name = "veterinario_id")
	private Veterinario veterinario;

	@ManyToOne
	@JoinColumn(name = "auxiliar_id")
	private AuxVeterinario auxVeterinario;

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	@JsonIgnore
	@OneToMany(mappedBy = "consulta")
	private List<Pedido> pedidos;

	private String doenca;
	private Integer consultaStatus;

	public Consulta() {
	}

	public Consulta(Long id, Instant data, String doenca, Veterinario veterinario, AuxVeterinario auxVeterinario,
			ConsultaStatus consultaStatus, Pet pet) {
		super();
		this.id = id;
		this.data = data;
		this.doenca = doenca;
		this.veterinario = veterinario;
		this.auxVeterinario = auxVeterinario;
		setConsultaStatus(consultaStatus);
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

	public void setAuxVeterinario(AuxVeterinario auxVeterinario) {
		this.auxVeterinario = auxVeterinario;
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

	public AuxVeterinario getAuxVeterinario() {
		return auxVeterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public ConsultaStatus getConsultaStatus() {
	    if (consultaStatus == null) {
	        return null;
	    }
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