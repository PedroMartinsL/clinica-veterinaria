package model.entidades;

import java.util.ArrayList;

import controler.gerenciamento.ContratoGeral;
import controler.gerenciamento.Estoque;
import controler.gerenciamento.SistemaRendimento;
import view.UI;

public class Administrador extends Entidade {
	
	private ArrayList<String> notificacoes = new ArrayList<>();

	public Administrador(String name, String cpf, String senha) {
		super(name, cpf, senha);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void operacoes() {
		int request = UI.getRequest(1);
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
				for (String notificacao: notificacoes) {
					System.out.println("° " + notificacao);
				}
				break;
			default:
				System.out.println("Saindo das operações...");
				break;
		}	
	}

	@Override
	public void addUser() {
		// TODO Auto-generated method stub
	}

	@Override
	public void removerUser() {
		// TODO Auto-generated method stub
		
	}
	
	private void contratar(Entidade entidade) {
		
	}
	
	private void demitir(Entidade entidade) {
		
	}
	
	private void rendaClinica() {
		
	}
	
	private void dadosUser() {
		
	}
	
	public void update(String msg) {
		this.notificacoes.add(msg);
	}

}
