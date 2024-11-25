package model.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import db.DB;
import db.DbException;
import model.entidades.enums.ConsultaStatus;

public class Consulta {
    private int id;
    private Pet pet;
    private String doenca;
    private double valor; 
    private Integer consultaStatus;
    private LocalDateTime data; 
    private Connection conn = DB.getConnection();

    
    public Consulta(Pet pet, ConsultaStatus status) {
        this.pet = pet;
        this.data = LocalDateTime.now(); 
        setConsultaStatus(status);
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public ConsultaStatus getConsultaStatus() {
        return ConsultaStatus.valueOf(consultaStatus);
    }

    public void setConsultaStatus(ConsultaStatus consultaStatus) {
        if (consultaStatus != null) {
            this.consultaStatus = consultaStatus.getCode();
        }
    }

    // Método para solicitar uma consulta no banco de dados
    public void solicitarConsulta() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("INSERT INTO Consultas (data, pet_id, status) VALUES (?, ?, ?)", 
                    Statement.RETURN_GENERATED_KEYS);

            // Definindo os parâmetros da consulta
            st.setString(1, LocalDateTime.now().toString()); // Data da consulta
            st.setInt(2, pet.getId()); // ID do pet
            st.setInt(3, 1); // Status da consulta (1 pode representar "agendado")

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    // Método para finalizar a consulta
    public Pet finalizarConsulta() {
        System.out.println("Consulta de " + pet.getAnimal() + " finalizada. Doença tratada: " + doenca);
        return pet;
    }
}
