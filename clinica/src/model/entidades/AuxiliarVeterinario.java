package model.entidades;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.recursos.Medicamento;
import model.recursos.Pedido;
public class AuxiliarVeterinario extends Entidade {

	public AuxiliarVeterinario(String name, String cpf, String senha) {
		super(name, cpf, senha);
		
	}
	  Scanner sc = new Scanner(System.in);
	@Override
	public void operacoes() {
        
        while (true) {
            System.out.println("Menu de operações (Auxiliar Veterinário)");
            System.out.println("1. Atender Pet");
            System.out.println("2. Aplicar Medicamento");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.println("Atendimento ao Pet realizado.");
                    break;

                case 2:
                    aplicarMedicamento(null, null); // Chama o método para aplicar medicação
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
            
            
    void aplicarMedicamento(Medicamento nome, Entidade auxVeterinario) {
        
        System.out.print("Informe o nome do medicamento: ");
        String nomeMedicamento = sc.nextLine();

        System.out.print("Informe a quantidade do medicamento: ");
        int quantidadeMedicamento = sc.nextInt();
        sc.nextLine();

        //construtor do medicamento
        Medicamento medicamento = new Medicamento(0, nomeMedicamento, 0, null, null, null);

        //armazena o medicamento e sua quantidade
        Map<Medicamento, Integer> mapMedicamentos = new HashMap<>();
        mapMedicamentos.put(medicamento, quantidadeMedicamento);

        
        Pedido pedido = new Pedido(auxVeterinario, mapMedicamentos);

        //solicitar medicamento
        solicitarMedicamento(pedido);

        System.out.println("Aplicando " + quantidadeMedicamento + " doses de " + nomeMedicamento + " para o pet do dono com CPF: ");
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
