package model.recursos;

import java.time.LocalDate;

public class Medicamento implements Prototype {
	private int id;
	private double preco;
	private String laboratorio;
	private double concentracao;
	private String nome;
	private boolean contrato;

	public Medicamento(int id, double preco, String laboratorio, double concentracao, String nome) {
		this.id = id;
		this.preco = preco;
		this.laboratorio = laboratorio;
		this.concentracao = concentracao;
		this.nome = nome;
	}
	
	public Medicamento(double preco, String laboratorio, double concentracao, String nome) {
		this.preco = preco;
		this.laboratorio = laboratorio;
		this.concentracao = concentracao;
		this.nome = nome;
	}

	public Medicamento(String laboratorio, double concentracao, String nome) {
		this.laboratorio = laboratorio;
		this.concentracao = concentracao;
		this.nome = nome;
	}

	public Medicamento(int id) {
		super();
		this.id = id;
	}

	public Medicamento() {
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
		// método para adicionar ao estoque
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public Medicamento clone() {
		return new Medicamento(id, preco, laboratorio, concentracao, nome, validade);
	}
}
