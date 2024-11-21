package model.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Pet {
	private int id;
	private String animal;
	private String cpfDono;
	private String raca;
	private int idade;

	public Pet() {

	}

	public Pet(int id) {
		super();
		this.id = id;
	}

	public Pet(String animal, String cpfDono, String raca, int idade) {
		this.animal = animal;
		this.cpfDono = cpfDono;
		this.raca = raca;
		this.idade = idade;
	}

	public Pet(int id, String animal, String cpfDono, String raca, int idade) {
		super();
		this.id = id;
		this.animal = animal;
		this.cpfDono = cpfDono;
		this.raca = raca;
		this.idade = idade;
	}

	// Getters e Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public String getCpfDono() {
		return cpfDono;
	}

	public void setCpfDono(String cpfDono) {
		this.cpfDono = cpfDono;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	// Método para exibir informações do pet
	public void exibirInformacoes() {
		System.out.println("Pet: " + animal);
		System.out.println("CPF do dono: " + cpfDono);
		System.out.println("Raça: " + raca);
		System.out.println("Idade: " + idade);
	}
	
	
	public void registrarPet() {
        PreparedStatement st = null;
        Connection conn = DB.getConnection();
        try {
            // Inserindo dados no banco de dados
            st = conn.prepareStatement("INSERT INTO Pet (animal, cpf_dono, raca, idade) VALUES (?, ?, ?, ?)", 
                    Statement.RETURN_GENERATED_KEYS);

            // Passando os atributos para a consulta
            st.setString(1, getAnimal()); 
            st.setString(2, getCpfDono() );         
            st.setString(3, getRaca());     
            st.setInt(4, getIdade());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1); 
                }
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
}
