package model.entidades;

import java.util.List;
import java.util.Scanner;

import view.Pet;

import model.recursos.Medicamento;
public class Veterinario extends Entidade {

	private List<Pet> listaDePets;

	public Veterinario(String name, String cpf, String senha) {
		super(name, cpf, senha);
    }

	 Scanner sc = new Scanner(System.in);

	@Override
	public void operacoes() {
  
		 while (true) {
	            System.out.println("Menu de operações (Veterinário)");
	            System.out.println("1. Checar Diagnóstico");
	            System.out.println("2. Prescrever Medicamento");
	            System.out.println("3. Sair");
	            System.out.print("Escolha uma opção: ");
	            int opcao = sc.nextInt();
	            sc.nextLine(); 

	            switch (opcao) {
	                case 1:
	                    checagemDiagnostico(null);
	                    break;

	                case 2:
	                    prescreverMedicacao(new AuxiliarVeterinario(null, null, null)); // Enviando o auxiliar como parâmetro
	                    break;

	                case 3:
	                    System.out.println("Saindo...");
	                    sc.close(); 
	                    return; 
	                default:
	                    System.out.println("Opção inválida! Tente novamente.");
	            }
	        }
	    }
	            public void checagemDiagnostico(Pet pet) {
	                System.out.print("Informe o sintoma do pet: ");
	                String sintoma = sc.nextLine();
	                pet.adicionarDoenca(sintoma);
	                System.out.println("Sintoma '" + sintoma + "' adicionado ao pet.");

	         
	            }

	            public void prescreverMedicacao(AuxiliarVeterinario auxiliar) {
	                System.out.print("Informe o CPF do dono do pet: ");
	                String cpfDono = sc.nextLine();

	                Pet pet = buscarPetPorCpf(cpfDono);
	                if (pet != null) {
	                    System.out.print("Informe o nome do medicamento: ");
	                    String nomeMedicamento = sc.nextLine();

	                    System.out.print("Informe a concentração do medicamento (mg): ");
	                    int concentracao;
	                    try {
	                        concentracao = Integer.parseInt(sc.nextLine()); 
	                    } catch (NumberFormatException e) {
	                        System.out.println("Concentração inválida, operação cancelada.");
	                        return;
	                    }

	                    // Criação do objeto Medicamento
	                    Medicamento medicamento = new Medicamento(0, "Laboratório", concentracao, nomeMedicamento, null, null);

	                    System.out.printf("Medicamento %s de %d mg prescrito para o pet do dono com CPF: %s.\n", 
	                                      medicamento.getNome(), medicamento.getConcentracao(), cpfDono);

	                    // Chamando o auxiliar para aplicar o medicamento
	                    if (auxiliar != null) {
	                        auxiliar.aplicarMedicamento(medicamento, auxiliar);
	                    }
	                } else {
	                    System.out.println("Pet não encontrado.");
	                }
	            }
	        
	@Override
	public void addUser() {
		
		
	}

	@Override
	public void removerUser() {
		
		
	}
	 public Pet buscarPetPorCpf(String cpfDono) {
	        for (Pet pet : listaDePets) {
	            if (pet.getCpfDono().equals(cpfDono)) {
	                return pet; // Retorna o pet se o CPF do dono coincidir
	            }
	        }
	        return null; 
	    }
}
