package model.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DB;
import db.DbException;
import model.entidades.enums.ConsultaStatus;
import view.UI;

public class Funcionario extends Entidade {

	public Funcionario(String name, String cpf, String senha) {
		super(name, cpf, senha);
	}

	@Override
	public boolean operacoes() {
		int caso = UI.getRequest(new String[] { "Registrar Pet", "Cobrar consulta", "Solicitar consulta",
				"Buscar histórico de doenças e quadro financeiro", "Fazer Cancelamento", "Finalizar operações" });

		switch (caso) {
		case 1:
			registrarPet();
			break;
		case 2:
			cobrarConsulta();
			break;
		case 3:
			solicitarConsulta();
			break;
		case 4:
			buscarHistorico();
			break;
		case 5:
			cancelarConsulta();
			break;
		case 6:
			System.out.println("Operações finalizadas.");
			break;
		default:
			return false;
		}
		return true;
	}

	public void registrarPet() {
		System.out.println("Digite o CPF do proprietário:");
		String cpf = UI.sc.nextLine();
		System.out.println("Digite o nome do animal:");
		String animal = UI.sc.nextLine();
		System.out.println("Digite a raça do animal:");
		String raca = UI.sc.nextLine();
		System.out.println("Digite a idade do animal:");
		int idade = UI.sc.nextInt();

		// Criar o pet
		Pet pet = new Pet(cpf, animal, raca, idade);

		Consulta consulta = new Consulta(pet, ConsultaStatus.AGENDADO);

		pet.registrarPet();
		consulta.solicitarConsulta();
		System.out.println("Animal registrado com sucesso e consulta agendada!");
	}

	public void cobrarConsulta() { // id_pet
		System.out.println("Digite o ID do pet:");
		String cpf = UI.sc.nextLine();
		Pet pet = new Pet(cpf, "Nome do Pet", "Raça", 3);
		Consulta consulta = new Consulta(pet); // Vai associar a consulta ao pet

		// Lógica de cobrança (pode ser expandida conforme necessário)
		System.out.println("Cobrando consulta para o pet: " + pet.getAnimal());
	}

	public void solicitarConsulta() {

		System.out.println("Digite o CPF do proprietário do pet:");
		String cpf = UI.sc.nextLine();
		System.out.println("Digite o nome do animal:");
		String animal = UI.sc.nextLine();
		System.out.println("Digite a raça do animal:");
		String raca = UI.sc.nextLine();
		System.out.println("Digite a idade do animal:");
		int idade = UI.sc.nextInt();
		UI.sc.nextLine();

		Pet pet = new Pet(cpf, animal, raca, idade);

		Consulta consulta = new Consulta(pet, ConsultaStatus.AGENDADO);

		consulta.solicitarConsulta();

		System.out.println("Consulta solicitada com sucesso para o pet: " + animal);
	}

	public void buscarHistorico() {
		System.out.println("Digite o CPF do proprietário do pet para buscar o histórico:");
		String cpf = UI.sc.nextLine();

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

	public static void cancelarConsulta() {

		PreparedStatement st = null;
		Connection conn = DB.getConnection();

		System.out.println("Digite o ID do Pet:");
		int id = UI.sc.nextInt();

		try {
			st = conn.prepareStatement("UPDATE Consultas " + "SET Status = 4 where id = ?");

			st.setInt(1, id);
			st.executeUpdate();
			System.out.println("Consulta cancelada com sucesso para o pet de ID: " + id);

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}
}
