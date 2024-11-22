package controller.gerenciamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;
import model.entidades.Administrador;
import model.entidades.AuxiliarVeterinario;
import model.entidades.Entidade;
import model.entidades.Funcionario;
import model.entidades.Veterinario;
import view.UI;

public class ContratoGeral {

	static Connection conn = DB.getConnection();

	public static void gerenciar() {
		int request = UI.getRequest(
				new String[] { "Contratar empregado", "Demitir empregado", "Visualizar dados por id", "Voltar" });
		switch (request) {
		case 1:
			System.out.println("Digite o cpf para o contrato: ");
			String cpf = UI.sc.nextLine();
			System.out.println("Digite o nome para o contrato: ");
			String nome = UI.sc.nextLine();
			System.out.println("Digite a senha para o contrato: ");
			String senha = UI.sc.nextLine();

			System.out.println("Digite uma entidade a ser contratada: auxiliar, funcionario, veterinario, administrador");
			Entidade contribuinte;
			while (true) {
				contribuinte = criarEntidade(UI.sc.nextLine(), nome, cpf, senha);
				if (contribuinte == null)
					System.out.println("Categoria de entidade inválida, tente novamente.");
				else
					break;
			}
			contratar(contribuinte);
			break;
		case 2:
			System.out.print("Digite o cpf para demitir um contratado: ");
			cpf = UI.sc.nextLine();
			System.out.print("\nDigite o cargo: ");
			String cargo = UI.sc.nextLine();
			demitir(cpf, cargo);
			break;
		case 3:
			System.out.println("Digite o cpf para ver os dados do contratado: ");
			cpf = UI.sc.nextLine();
			System.out.print("\nDigite o cargo: ");
			cargo = UI.sc.nextLine();
			dadosUser(cpf, cargo);
			break;
		default:
			System.out.println("Saindo das operações...");
			break;
		}
	}

	private static void contratar(Entidade entidade) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO " + tableSimpleName(entidade.getClass().getSimpleName()) + "(nome, cpf, senha) " + "VALUES " + "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, entidade.getName());
			st.setString(2, entidade.getCpf());
			st.setString(3, entidade.getSenha());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1); // recuperar o valor da primeira coluna
					entidade.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	private static void demitir(String cpf, String funcao) {
		PreparedStatement st = null;
		try {

			String tableName = tableSimpleName(funcao);

			st = conn.prepareStatement("DELETE FROM " + tableName + "WHERE cpf = (?)");

			st.setString(1, cpf);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				System.out.printf("Funcionario de cpf: %s demititdo%n", cpf);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	private static void dadosUser(String cpf, String funcao) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			String tableName = tableSimpleName(funcao);

			st = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE cpf = ? ");

			st.setString(1, cpf);

			rs = st.executeQuery(); // Faz com que o comando seja executado e caindo no rs

			if (rs.next()) {
				String.format("[CPF: %s, Nome: %s, Email: %s]", rs.getString("cpf"), rs.getString("nome"),
						rs.getString("email"));
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private static String tableSimpleName(String simpleName) {
		if (simpleName.equals("Administrador")) {
			return "Administradores";
		} else if (simpleName.equals("AuxiliarVeterinario")) {
			return "AuxiliaresVeterinarios";
		} else if (simpleName.equals("Veterinario")) {
			return "Veterinarios";
		} else {
			throw new IllegalArgumentException("Entidade não instanciada.");
		}
	}

	public static Entidade confirmarUser(String cpf, String senha) {
	    String[] tabelas = {"Funcionarios", "Administradores","AuxiliaresVeterinarios", "Veterinarios"};
	    PreparedStatement st = null;
	    ResultSet rs = null;

	    try {
	        for (String tabela : tabelas) {
	            String query = "SELECT * FROM " + tabela + " WHERE cpf = ? AND senha = ?";
	            st = conn.prepareStatement(query);
	            st.setString(1, cpf);
	            st.setString(2, senha);
	           

	            rs = st.executeQuery();

	            if (rs.next()) {
	                return criarEntidade(tabela, rs.getString("nome"), rs.getString("cpf"), rs.getString("senha"));
	            }
	        }

	        // Se nenhum registro for encontrado
	        System.out.println("CPF ou senha incorretos");
	        return null;

	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}
	
	private static Entidade criarEntidade(String entity, String nome, String cpf, String senha) {
		entity = entity.trim();
		if (entity.equals("Funcionarios")) {
			return new Funcionario(nome, cpf, senha);
		} else if (entity.equals("AuxiliaresVeterinarios")) {
			return new AuxiliarVeterinario(nome, cpf, senha);
		} else if (entity.equals("Veterinarios")) {
			return new Veterinario(nome, cpf, senha);
		} else if (entity.equals("Administradores")) {
			return new Administrador(nome, cpf, senha);
		}
		return null;
	}
}
