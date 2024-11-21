package model.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import db.DB;
import db.DbException;
import model.entidades.enums.ConsultaStatus;
import model.recursos.Medicamento;

public class Consulta {
    private int id; // id da consulta
    private Pet pet;
    private String doenca;
    private double valor; // valor da consulta
    private Integer consultaStatus;
    private LocalDateTime data; // data da consulta
    private ArrayList<Medicamento> medicamentosAplicados;
    private Connection conn = DB.getConnection();

    // Construtor que aceita um Pet
    public Consulta(Pet pet, ConsultaStatus status) {
        this.pet = pet;
        this.data = LocalDateTime.now(); // inicializando com a data atual
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

    public ArrayList<Medicamento> getMedicamentosAplicados() {
        return medicamentosAplicados;
    }

    public void setMedicamentosAplicados(ArrayList<Medicamento> medicamentosAplicados) {
        this.medicamentosAplicados = medicamentosAplicados;
    }
    
    public ConsultaStatus getConsultaStatus() {
        return ConsultaStatus.valueOf(consultaStatus);
    }

    public void setConsultaStatus(ConsultaStatus orderStatus) {
        if (orderStatus != null)
            this.consultaStatus = orderStatus.getCode(); 
    }

    // Método para registrar o pet no banco de dados (não alterado)
    public void registrarPet() {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "INSERT INTO pet " + "(animal, cpf_dono, raca, idade) " + "VALUES " + "(?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

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

    // Método para solicitar uma nova consulta no banco de dados
    public void solicitarConsulta() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "INSERT INTO Consulta (data, idPet, idStatus) " + "VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS //
            );

            // Definindo os parâmetros da consulta
            st.setString(1, LocalDateTime.now().toString()); // Data da consulta, com formato de LocalDateTime
            st.setInt(2, pet.getId()); // ID do pet associado à consulta
            st.setInt(3, 1); // Status da consulta, 1 pode representar "em andamento" - Mas e o status?

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
            // Tratamento de erro em caso de falha na execução do SQL
            throw new DbException(e.getMessage());
        } finally {
            // Fechando os recursos após a execução do comando
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    public void calcularValor() { //Deve ter alguma melhoria
        this.valor = 100.0 + (medicamentosAplicados.size() * 10.0); // valor base + 10 por medicamento
    }

    // Método para finalizar a consulta
    public Pet finalizarConsulta() {
        System.out.println("Consulta de " + pet.getAnimal() + " finalizada. Doença tratada: " + doenca);
        return pet; 
    }

    // Adicionar medicamentos à consulta (caso necessário)
    public void adicionarMedicamento(Medicamento medicamento) {
        if (medicamentosAplicados == null) {
            medicamentosAplicados = new ArrayList<>();
        }
        medicamentosAplicados.add(medicamento);
    }
}

