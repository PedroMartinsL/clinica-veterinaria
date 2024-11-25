package model.entidades;

import java.util.ArrayList;

import controller.gerenciamento.ContratoGeral;
import controller.gerenciamento.Estoque;
import controller.gerenciamento.SistemaRendimento;
import view.UI;

public class Administrador extends Entidade {

	private ArrayList<String> notificacoes = new ArrayList<>();

	public Administrador(String name, String cpf, String senha) {
		super(name, cpf, senha);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean operacoes() {
		int request = UI.getRequest(new String[] { "Operações de contratados", "Exibir renda da clínica",
				"Operações de Estoque", "Exibir notificações", "Finalizar operações" });
		switch (request) {
		case 1:
			ContratoGeral.gerenciar();
			break;
		case 2:
			SistemaRendimento.gerenciar();
			break;
		case 3:
			Estoque.gerenciar();
			break;
		case 4:
			for (String notificacao : notificacoes) {
				System.out.println("° " + notificacao);
			}
			break;
		default:
			System.out.println("Saindo das operações...");
			return false;
		}
		return true;
	}

	public void update(String msg) {
		this.notificacoes.add(msg);
	}

}
