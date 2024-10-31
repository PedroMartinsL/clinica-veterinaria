package model.entidades;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.DB;
import db.DbException;
import model.recursos.Medicamento;

public class Consulta {
	private Pet pet;
	private String doenca;
	private Date data;
	private ArrayList<Medicamento> medicamentosAplicados;
	private int consulta;

	public int getConsulta() {
		return consulta;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	private Connection conn = DB.getConnection();

	public Consulta(Pet pet, String doenca) {
		this.pet = pet;
		this.doenca = doenca;
		// this.data ; // Define a data atual
	}

	// Getters e Setters
	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public String getDoenca() {
		return doenca;
	}

	public void setDoenca(String doenca) {
		this.doenca = doenca;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void registrarPet(Pet pet) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO pet " + "(Animal, cpf_dono, idade, raça) " + "VALUES " + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, pet.getAnimal());
			st.setString(2, pet.getCpfDono());
			st.setInt(3, pet.getIdade());
			st.setString(4, pet.getRaca());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1); // recuperar o valor da primeira coluna
					pet.setId(id);
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

	public void solicitarConsulta() {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO pet " + "(Animal, doenca, data) " + "VALUES " + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, getPet().getId());
			st.setString(2, getDoenca());
			st.setDate(3, getData());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1); // recuperar o valor da primeira coluna
					pet.setId(id);
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

	public ArrayList<Medicamento> getMedicamentosAplicados() {
		return medicamentosAplicados;
	}

	public void setMedicamentosAplicados(ArrayList<Medicamento> medicamentosAplicados) {
		this.medicamentosAplicados = medicamentosAplicados;
	}

}
