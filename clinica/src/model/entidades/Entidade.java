package model.entidades;

public abstract class Entidade implements Operacoes {

	private String name;
	private String cpf;
	private String senha;

	public Entidade(String name, String cpf, String senha) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.senha = senha;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
