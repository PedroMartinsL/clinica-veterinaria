package model.entidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AuxiliarVeterinario extends Entidade {

	public AuxiliarVeterinario(String name, String cpf, String senha) {
		super(name, cpf, senha);
		
	}

	@Override
	public void operacoes(Veterinario veterinario) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n MENU DE OPERAÇÕES (Auxiliar Veterinário)");
            System.out.println("1. Atender Pet");
            System.out.println("2. Aplicar Medicamento");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Atendimento ao Pet realizado.");
                    break;

                case 2:
                    // Aplicar medicamento
                    aplicarMedicamento(veterinario);
                    break;

                case 3:
                    System.out.println("Saindo...");
                    return;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void aplicarMedicamento(Veterinario veterinario) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o CPF do dono do pet: ");
        String cpfDono = scanner.nextLine();

        System.out.print("Informe a quantidade de medicamentos: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe o nome do medicamento: ");
        String nomeMedicamento = scanner.nextLine();

        //construtor do medicamento
        Medicamento medicamento = new Medicamento(nomeMedicamento);

        //armazena o medicamento e sua quantidade
        Map<Medicamento, Integer> mapMedicamentos = new HashMap<>();
        mapMedicamentos.put(medicamento, quantidade);

        
        Pedido pedido = new Pedido(veterinario, mapMedicamentos);

        //solicitar medicamento
        solicitarMedicamento(pedido);

        System.out.println("Aplicando " + quantidade + " doses de " + nomeMedicamento + " para o pet do dono com CPF: " + cpfDono + ".");
    }
    
    private void solicitarMedicamento(Pedido pedido) {
        System.out.println("Medicamento solicitado: " + pedido);
    }
	@Override
	public void addUser() {
		
		
	}

	@Override
	public void removerUser() {
		
		
	}

}
