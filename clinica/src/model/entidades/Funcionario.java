package model.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		int caso = UI.getRequest(new String[] { "Cobrar consulta", "Solicitar consulta",
				"Buscar histórico de doenças e quadro financeiro", "Fazer Cancelamento", "Finalizar operações" });

		switch (caso) {
		case 1:
			cobrarConsulta();
			break;
		case 2:
			registrarConsulta();
			break;
		case 3:
			buscarHistorico();
			break;
		case 4:
			cancelarConsulta();
			break;
		case 5:
			System.out.println("Operações finalizadas.");
			break;
		default:
			return false;
		}
		return true;
	}

	public void registrarConsulta() {
		System.out.println("Digite o CPF do proprietário:");
		String cpf = UI.sc.nextLine();
		System.out.println("Digite o nome do animal:");
		String animal = UI.sc.nextLine();
		System.out.println("Digite a raça do animal:");
		String raca = UI.sc.nextLine();
		System.out.println("Digite a idade do animal:");
		int idade = UI.sc.nextInt();
		UI.sc.nextLine();

		// Criar o pet
		Pet pet = new Pet(animal, cpf, raca, idade);

		Consulta consulta = new Consulta(pet, ConsultaStatus.AGENDADO);

		pet.registrarPet();
		consulta.solicitarConsulta();
		System.out.println("Animal registrado com sucesso e consulta agendada!");
	}

	public void cobrarConsulta() { // id de consulta
		PreparedStatement st = null;
		Connection conn = DB.getConnection();

		System.out.println("Digite o ID da Consulta para cobrança:");
		int idConsulta = UI.sc.nextInt();

		try {
			String sqlStatus = "SELECT status FROM Consultas WHERE id = ?";
			st = conn.prepareStatement(sqlStatus);
			st.setInt(1, idConsulta);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				int status = rs.getInt("status"); // tem que ser igual ao bd

				if (status == 3) {
					System.out.println("Consulta encontrada! Status: Em Dívida.");

					System.out.println("Deseja pagar a consulta? (1 - Sim, 2 - Não)");
					int opcao = UI.sc.nextInt();

					if (opcao == 1) {
						String sqlAtualizarStatus = "UPDATE Consultas SET status = 5 WHERE id = ?";
						st = conn.prepareStatement(sqlAtualizarStatus);
						st.setInt(1, idConsulta);
						st.executeUpdate();

						System.out.println("Consulta cobrada com sucesso para o ID da consulta: " + idConsulta);
					} else {
						System.out.println("Cobrança não realizada.");
					}
				} else {
					System.out.println("O status da consulta não é 'Em Dívida'. Não é possível cobrar.");
				}
			} else {
				System.out.println("Nenhuma consulta encontrada com o ID: " + idConsulta);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
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
		PreparedStatement st = null;
		Connection conn = DB.getConnection();

		System.out.println("Digite o ID do Pet para buscar o histórico de consultas:");
		int idPet = UI.sc.nextInt();
		UI.sc.nextLine();

		try {
			// pegar histórico de consultas do pet
			String sql = "SELECT id, data, doenca, status FROM Consultas WHERE pet_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, idPet);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				System.out.println("Histórico de Consultas do Pet ID: " + idPet);
				do {
					System.out.println("ID Consulta: " + rs.getInt("id") + " | Data: " + rs.getDate("data")
							+ " | Doença: " + rs.getString("doenca") + " | Status: " + rs.getInt("status"));
				} while (rs.next());
			} else {
				System.out.println("Nenhuma consulta encontrada para o pet com ID: " + idPet);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	public void buscarHistoricoDoencas() {
		PreparedStatement st = null;
		Connection conn = DB.getConnection();

		try {
			// pegar todas as doenças registradas nas consultas
			String sql = "SELECT doenca, id, pet_id FROM Consultas";
			st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				System.out.println("Histórico de Doenças:");
				do {
					System.out.println("Doença: " + rs.getString("doenca"));
				} while (rs.next());
			} else {
				System.out.println("Nenhuma doença registrada no histórico.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	public void buscarHistoricoDividas() {
		PreparedStatement st = null;
		Connection conn = DB.getConnection();

		try {
			// pegar as consultas com status "Em Dívida"
			String sql = "SELECT id, pet_id, data, doenca FROM Consultas WHERE status = 3";
			st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				System.out.println("Histórico de Dívidas (Consultas em Dívida):");
				do {
					System.out.println("ID Consulta: " + rs.getInt("id") + " | Pet ID: " + rs.getInt("pet_id")
							+ " | Data: " + rs.getDate("data") + " | Doença: " + rs.getString("doenca"));
				} while (rs.next());
			} else {
				System.out.println("Não há consultas em dívida no sistema.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	public static void cancelarConsulta() {

		PreparedStatement st = null;
		Connection conn = DB.getConnection();

		System.out.println("Digite o ID da Consulta:");
		int id = UI.sc.nextInt();
		UI.sc.nextLine();

		try {
			st = conn.prepareStatement("UPDATE Consultas " + "SET Status = 4 where id = ?");

			st.setInt(1, id);
			st.executeUpdate();
			System.out.println("Consulta cancelada com sucesso para a consulta de ID: " + id);

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}
}
