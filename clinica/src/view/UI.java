package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UI {

	public static Scanner sc = new Scanner(System.in);

	public static void setSc(Scanner sc) {
		UI.sc = sc;
	}

	public static int getRequest(int vetorAtual) {
        List<String> opcoes = null;
        switch (vetorAtual) {
        case 1:
            // Menu para opções de usuário
            opcoes = Arrays.asList("Operações de contratados", "Exibir renda da clínica", "Operações de Estoque","Exibir notificações", "Finalizar operações");
            break;
        case 2:
            // Menu para operações de rastreamento, cadastro, login, etc.

            opcoes = Arrays.asList("Atender o pet", "Aplicar medicação", "Finalizar Consulta");
            break;
        case 3:
            // Menu para operações de rastreamento, cadastro, login, etc.

            opcoes = Arrays.asList("Solicitar Consulta", "Cobrar Consulta","Exibir Historico" ,"Finalizar Consulta");
            break;
            
        default:
            // Lança uma exceção se a opção for inválida
            throw new IllegalArgumentException("Opção inválida, tente novamente");
        }

        boolean entrada = false;
        int request = 4;
        short contador;

        do {
            contador = 1;
            for (String opcao : opcoes) {
                // Exibe cada opção numerada no menu
                System.out.printf("%d - %s\n", contador++, opcao);
            }
            try {
                 // Insere a solicitação de usuário
                    System.out.print("\n-> ");
                    String digito = UI.sc.nextLine().trim();
                    if (digito.isEmpty()) {
                        throw new IllegalArgumentException("Valor nulo lançado");
                    }
                    request = Integer.parseInt(digito);
                    clearScreen();
                    if (request < 1 || request > opcoes.size()) {
                        System.out.println("Valor fora dos parâmetros, digite entre 1 e " + opcoes.size());
                    } else {
                        entrada = true;
                    }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, por favor insira um número. Digite enter para tentar novamente.");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.err.printf("Entrada inválida, %s, digite enter para tentar novamente.\n", e.getMessage());
                sc.nextLine();
            }
        } while (!entrada || (request < 1) || (request > opcoes.size()));
        clearScreen();
        return request; // Retorna o valor enum da opção
    }

	// Retorna o valor enum da opção
	// pesquisar como pegar 2 tipos de erros em um código entre parÊnteses()

	public static void clearScreen() { // Limpar a tela
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}
}
