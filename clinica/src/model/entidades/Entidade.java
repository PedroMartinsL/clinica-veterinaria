package model.entidades;

import controller.gerenciamento.ContratoGeral;
import view.UI;

public abstract class Entidade implements Operacoes {

	private String name;
	private String cpf;
	private String senha;
	private int id;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static Entidade loginUser() {
		short contador = 3;
		Entidade entidade = null;
		try {
			System.out.println("- Login -:");
			do {
				System.out.print("CPF: ");
				String cpf = UI.sc.next();
				System.out.print("Senha: ");
				String senha = UI.sc.next();
				UI.sc.nextLine();
				entidade = ContratoGeral.confirmarUser(cpf, senha);
				if (entidade != null) {
					break;
				} else {
					System.out.println("E-mail/Senha errados, tente novamente!");
					contador--;
				}
			} while (contador > 0);
		} catch (IllegalArgumentException e) {
			System.out.println("Erro por um argumento ilegal: " + e.getMessage());
		}
		return entidade;
	}

}
