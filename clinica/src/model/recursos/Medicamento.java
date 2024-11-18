package model.recursos;

import java.time.LocalDate;

public class Medicamento implements Prototype {
	private double preco;
	private String laboratorio;
	private double concentracao;
	private String nome;
	private LocalDate validade;
	private boolean contrato = true;
	
	public Medicamento(double preco, String laboratorio, double concentracao, String nome, LocalDate validade) {
		super();
		this.preco = preco;
		this.laboratorio = laboratorio;
		this.concentracao = concentracao;
		this.nome = nome;
		this.validade = validade;
	}

	public LocalDate getValidade() {
		return validade;
	}

	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}

	public Medicamento(String laboratorio, double concentracao, String nome) {
		this.laboratorio = laboratorio;
		this.concentracao = concentracao;
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public boolean isContrato() {
		return contrato;
	}

	public void setContrato(boolean contrato) {
		this.contrato = contrato;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public double getConcentracao() {
		return concentracao;
	}

	public void setConcentracao(double concentracao) {
		this.concentracao = concentracao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void aplicarMedicamento() {
		System.out.printf("%s %d mg aplicado pelo Auxiliar.\n", getNome(), getConcentracao());
	}
	
	public void estocar() {
		System.out.println("Adicionando medicamento ao estoque");
		//método para adicionar ao estoque
	}

	@Override
	public Medicamento clone() {
		return new Medicamento(preco, laboratorio, concentracao, nome, validade);
	}
}
