package model.entidades;

import view.UI;

public class Funcionario extends Entidade {

	public Funcionario(String name, String cpf, String senha) {
		super(name, cpf, senha);
	}

	@Override
	public void operacoes() {
		int caso = UI.getRequest(3);
		switch (caso) {
		case 1: // Metodo Registar Pet
			Pet pet = new Pet("12345678901","Baleia","Jubarte",10);
			Consulta consulta = new Consulta(pet, "Afogada");
			consulta.registrarPet(pet);
			consulta.solicitarConsulta(pet);
			System.out.println("Tudo Adicionado Com Sucesso!");
				
		}
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
