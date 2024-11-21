package model.entidades;

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
}
