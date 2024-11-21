package model.entidades;

import java.util.ArrayList;

import model.entidades.enums.ConsultaStatus;
import view.UI;

public class Funcionario extends Entidade {

    public Funcionario(String name, String cpf, String senha) {
        super(name, cpf, senha);
    }

    @Override
    public void operacoes() {
        int caso = UI.getRequest(new String[] { 
            "Cobrar consulta", 
            "Solicitar consulta", 
            "Buscar histórico de doenças e quadro financeiro",
            "Fazer Cancelamento",  ///
            "Finalizar operações" });

        switch (caso) {
            case 1:
                cobrarConsulta();
                break;
            case 2: 
                solicitarConsulta();
                break;
            case 3: 
                buscarHistorico();
                break;
            case 4: 
                System.out.println("Operações finalizadas.");   // Adicionar aqui! - Fazer update para Concluido ou cancelado (Se tiver agendado pode ter um metodo que pode cancelar)
                break;
            default:
                throw new IllegalArgumentException("Número fora do intervalo");
        }
    }

    public void cobrarConsulta() {
        System.out.println("Digite o CPF do proprietário do pet:");
        String cpf = UI.sc.nextLine();

        //  exemplo
        Pet pet = new Pet(cpf, "Nome do Pet", "Raça", 3); 
        Consulta consulta = new Consulta(pet);     /// Vai te a divida, passar o id do pet
    }

    public void solicitarConsulta() {
        System.out.println("Digite o CPF do proprietário do pet:");
        String cpf = UI.sc.nextLine();
        
        // Nome, Data da consulta

        // exemplo
        Pet pet = new Pet(cpf, "Nome do Pet", "Raça", 3); 
        Consulta consulta = new Consulta(pet, ConsultaStatus.AGENDADO);
        
   // p
        
        consulta.solicitarConsulta();  

        System.out.println("Consulta solicitada com sucesso!");

    }

    public void buscarHistorico() {
        System.out.println("Digite o CPF do proprietário do pet para buscar o histórico:");
        String cpf = UI.sc.nextLine();

        // exemplo
        Pet pet = new Pet(cpf, "Nome do Pet", "Raça", 3); 

        System.out.println("Buscando histórico de doenças e quadro financeiro do pet de CPF: " + cpf);
        
        ArrayList<Consulta> historico = pet.getConsultas(); 
        if (historico.isEmpty()) {
            System.out.println("Não há histórico de consultas.");
        } else {
            for (Consulta c : historico) {
                System.out.println("Consulta de " + c.getDoenca() + " em " + c.getData());
            }
        }
    }
}






/* package model.entidades;

import view.UI;

public class Funcionario extends Entidade {

	public Funcionario(String name, String cpf, String senha) {
		super(name, cpf, senha);
	}

	@Override
	public void operacoes() {
		int caso = UI.getRequest(new String[] { "Registar Pet", "Exibir renda da clínica",
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

*/
