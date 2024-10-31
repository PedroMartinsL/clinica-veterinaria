package model.entidades;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
					"INSERT INTO pet " + "(animal, cpf_dono, raça, idade) " + "VALUES " + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, pet.getAnimal());
			st.setString(2, pet.getCpfDono());
			st.setString(3, pet.getRaca());
			st.setInt(4, pet.getIdade());

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

	public void solicitarConsulta(Pet pet) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO Consulta " + "(data, doenca, idPet, idStatus) " + "VALUES " + "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, LocalDate.now().toString());
			st.setString(2, getDoenca());
			st.setInt(3, pet.getId());
			st.setInt(4, 1);

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
