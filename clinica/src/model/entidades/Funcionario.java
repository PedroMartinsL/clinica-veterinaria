package model.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	public void cobrarConsulta() {
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
	            int status = rs.getInt("status");

	            if (status == 3) {
	                System.out.println("Consulta encontrada! Status: Em Dívida.");
	                
	                System.out.println("Deseja pagar a consulta? (1 - Sim, 2 - Não)");
	                int opcao = UI.sc.nextInt();
	                
	                if (opcao == 1) {
	                    String sqlAtualizarStatus = "UPDATE Consultas SET status = 2 WHERE id = ?";
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
