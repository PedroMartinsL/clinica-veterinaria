package model.entidades;

public class Pet {
	private String cpfDono;
	private String animal;
	private String raca;
	private String idade;

	public Pet(String cpfDono, String animal, String raca, String idade) {
		super();
		this.cpfDono = cpfDono;
		this.animal = animal;
		this.raca = raca;
		this.idade = idade;
	}

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

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

}
