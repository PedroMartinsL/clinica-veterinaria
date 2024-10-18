package controller;

import java.util.ArrayList;

import model.entidades.Entidade;

public class Administrador extends Entidade {
	
	private ArrayList<String> notify = new ArrayList<>();

	public Administrador(String name, String cpf, String senha) {
		super(name, cpf, senha);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void operacoes() {
		// TODO Auto-generated method stub
		
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
	
	public void update(String msg) {
		this.notify.add(msg);
	}

}
