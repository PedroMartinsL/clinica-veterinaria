package com.clinica.springboot.model.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "veterinario_id")
	private Veterinario veterinario;

	@OneToOne
	@JoinColumn(name = "medicamento_id")
	private Medicamento medicamento;

	@ManyToOne
	@JoinColumn(name = "consulta_id")
	private Consulta consulta;

	private Integer quantidade;

	public Pedido() {
	}

	public Pedido(Long id, Veterinario veterinario, Medicamento medicamento, Consulta consulta,
			Integer quantidade) {
		super();
		this.id = id;
		this.veterinario = veterinario;
		this.medicamento = medicamento;
		this.consulta = consulta;
		this.quantidade = quantidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(consulta, id, medicamento, quantidade, veterinario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(consulta, other.consulta) && Objects.equals(id, other.id)
				&& Objects.equals(medicamento, other.medicamento) && Objects.equals(quantidade, other.quantidade)
				&& Objects.equals(veterinario, other.veterinario);
	}
}