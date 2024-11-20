package com.clinica.springboot.model.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AuxVeterinario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String cpf;
	
	private String nome;
	private String senha;

	public AuxVeterinario() {
	}

	public AuxVeterinario(String nome, String cpf, String senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuxVeterinario other = (AuxVeterinario) obj;
		return Objects.equals(cpf, other.cpf);
	}
	
	
}
