package model.entidades;

import java.util.ArrayList;
import java.util.Scanner;

import view.Pet;

public class Veterinario extends Entidade {

	public Veterinario(String name, String cpf, String senha) {
		super(name, cpf, senha);
		
	}

	@Override
	public void operacoes() {
	    Scanner sc = new Scanner(System.in);

	        while (true) {
	            System.out.println(" MENU DE OPERAÇÕES (Veterinário)");
	            System.out.println("1. Checar Diagnóstico");
	            System.out.println("2. Prescrever Medicamento");
	            System.out.println("3. Adicionar Sintoma");
	            System.out.println("4. Sair");
	            System.out.print("Escolha uma opção: ");
	            int opcao = sc.nextInt();
	            sc.nextLine(); 

	            switch (opcao) {
	                case 1:
	                    System.out.print("Informe o CPF do dono do pet: ");
	                    String cpfDono = sc.nextLine();
	                    checagemDiagnostico(new Pet(cpfDono, "", "", ""));
	                    break;

	                case 2:
	                    prescreverMedicacao();
	                    break;

	                case 3:
	                    System.out.print("Informe o CPF do dono do pet: ");
	                    String cpfDonoSintoma = sc.nextLine();
	                    Pet pet = new Pet(cpfDonoSintoma, "", "", ""); 
	                    addSintoma(new ArrayList<>(), pet);
	                    break;

	                case 4:
	                    System.out.println("Saindo...");
	                    sc.close(); 
	                    return;

	                default:
	                    System.out.println("Opção inválida! Tente novamente.");
	            }
	        }
    }

	@Override
	public void addUser() {
		
		
	}

	@Override
	public void removerUser() {
		
		
	}

}
