package model.entidades;

import view.UI;

public class Funcionario extends Entidade {

	public Funcionario(String name, String cpf, String senha) {
		super(name, cpf, senha);
	}

	@Override
	public void operacoes() {
		int caso = UI.getRequest(new String[] { "Operações de contratados", "Exibir renda da clínica",
				"Operações de Estoque", "Exibir notificações", "Finalizar operações"}); // Botar Operacoes
		switch (caso) {
		case 1: // Metodo Registar Pet
			System.out.println("Digite o cpf:");
			String cpf = UI.sc.nextLine();
			System.out.println("Digite o animal:");
			String animal = UI.sc.nextLine();
			System.out.println("Digite a raca:");
			String raca = UI.sc.nextLine();
			System.out.println("Digite a idade:");
			int idade = UI.sc.nextInt();
			Pet pet = new Pet(cpf,animal,raca,idade);
			Consulta consulta = new Consulta(pet);
			consulta.registrarPet();
			consulta.solicitarConsulta();
			System.out.println("Animal registrado Com Sucesso!");
			break;
		default:
			throw new IllegalArgumentException("Number out of range");
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
