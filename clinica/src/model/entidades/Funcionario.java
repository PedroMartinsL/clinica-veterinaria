package model.entidades;

import view.Pet;

public class Funcionario extends Entidade {

	public Funcionario(String name, String cpf, String senha) {
		super(name, cpf, senha);
	}

	@Override
	public void operacoes() {

	}

	@Override
	public void addUser() {

	}

	@Override
	public void removerUser() {

	}

	public void cobrarConsulta(Pet pet) {
		System.out.println("Cobrando consulta para o pet: " + pet.getAnimal());
	}

	public void solicitarConsulta(Pet pet) {
		System.out.println("Solicitando consulta para o pet: " + pet.getAnimal());
	}
}
