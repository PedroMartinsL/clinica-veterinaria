package model.entidades;

import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String cpfDono;
    private String animal;
    private String raca;
    private Integer idade;
    private List<String> doencas; // Lista para armazenar doenças do pet
    private int id;

    public Pet(String cpfDono, String animal, String raca, Integer idade) {
        this.cpfDono = cpfDono;
        this.animal = animal;
        this.raca = raca;
        this.idade = idade;
        this.doencas = new ArrayList<>(); // Inicializa a lista de doenças
    }

    // Getters e Setters
    public String getCpfDono() {
        return cpfDono;
    }

    public void setCpfDono(String cpfDono) {
        this.cpfDono = cpfDono;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
    
    

    //public Consulta solicitarAtendimento(String doenca) {
    	
        
       
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void financiaCliente(String cpf) {
        System.out.println("Financiando cliente com CPF: " + cpf);
    }

    public void listarDoencasPet() {
        System.out.println("Doenças do pet: " + doencas);
    }

    public void adicionarDoenca(String doenca) {
        this.doencas.add(doenca);
    }
}

