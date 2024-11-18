package model.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controler.gerenciamento.ContratoGeral;
import db.DB;
import db.DbException;
import view.UI;

public abstract class Entidade implements Operacoes {

	private String name;
	private String cpf;
	private String senha;
	private int id;
	private Connection conn = DB.getConnection();

	public Entidade(String name, String cpf, String senha) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.senha = senha;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void registerUser(Entidade obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO department " + "(Name) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1); // recuperar o valor da primeira coluna
					obj.setId(id);
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

	public void removeUser() {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department " + "WHERE Id = ?");

			st.setInt(1, id);

			int rows = st.executeUpdate();

			if (rows == 0) {
				throw new DbException("Index out of range");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	public void updateUser(Entidade obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE department " + "SET Name = ? " + "WHERE Id = ?");

			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			int rowsAffected = st.executeUpdate(); // Executa a atualização

			if (rowsAffected == 0) {
				throw new DbException("No rows affected! Department ID might not exist.");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	protected String[] loginUser() {
		String[] dados = new String[2];
		short contador = 3;
		try {
			System.out.println("- Login -:");
			do {
				System.out.print("CPF: ");
				String cpf = UI.sc.next();
				System.out.print("Senha: ");
				String senha = UI.sc.next();
				UI.sc.nextLine();
				if (ContratoGeral.confirmarUser(cpf, senha) != null) {
					break;
				} else {
					System.out.println("E-mail/Senha errados, tente novamente!");
					contador--;
				}
			} while (contador > 0);
			if (dados[0] == null || dados[1] == null) {
				throw new IllegalArgumentException(
						"Seus dados não correnspondem ao sistema. Tente novamente mais tarde.");
			} else {
				System.out.println("Bem-vindo ao Sistema Clínica Veterinária.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Erro por um argumento ilegal: " + e.getMessage());
		}
		return dados;
	}

}
