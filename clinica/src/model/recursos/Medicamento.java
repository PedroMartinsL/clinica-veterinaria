package model.recursos;

import java.time.LocalDate;

public class Medicamento implements Prototype {
	private double preco;
	private String laboratorio;
	private int concentracao;
	private String nome;
	private String[] aplicabilidade;
	private LocalDate validade;
	private boolean contrato = true;
	
	public Medicamento(double preco, String laboratorio, int concentracao, String nome, String[] aplicabilidade,
			LocalDate validade) {
		super();
		this.preco = preco;
		this.laboratorio = laboratorio;
		this.concentracao = concentracao;
		this.nome = nome;
		this.aplicabilidade = aplicabilidade;
		this.validade = validade;
	}

	public LocalDate getValidade() {
		return validade;
	}

	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}

	public Medicamento(String laboratorio, int concentracao, String nome) {
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

	public int getConcentracao() {
		return concentracao;
	}

	public void setConcentracao(int concentracao) {
		this.concentracao = concentracao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String[] getAplicabilidade() {
		return aplicabilidade;
	}

	public void setAplicabilidade(String[] aplicabilidade) {
		this.aplicabilidade = aplicabilidade;
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
		return new Medicamento(preco, laboratorio, concentracao, nome, aplicabilidade, validade);
	}
}
