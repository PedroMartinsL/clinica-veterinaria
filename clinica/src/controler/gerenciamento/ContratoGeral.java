package controler.gerenciamento;

import model.entidades.AuxiliarVeterinario;
import model.entidades.Entidade;
import model.entidades.Funcionario;
import model.entidades.Veterinario;
import view.UI;

public class ContratoGeral {

	public static void gerenciar() {
		String[] requests = {"Contratar empregado", "Demitir empregado", "Visualizar dados por id", "Voltar"};
		int request = UI.getRequest(requests); // corrigir
		switch (request) {
			case 1:
				System.out.println("Digite o cpf para o contrato: ");
				String cpf = UI.sc.nextLine();
				System.out.println("Digite o nome para o contrato: ");
				String nome = UI.sc.nextLine();
				System.out.println("Digite a senha para o contrato: ");
				String senha = UI.sc.nextLine();
				
				System.out.println("Digite uma entidade a ser contratada: auxiliar, funcionario, veterinario");
				Entidade contribuinte;
				while (true) {
					contribuinte = criarEntidade(UI.sc.nextLine(), nome, cpf, senha);
					if (contribuinte == null)
						System.out.println("Categoria de entidade inválida, tente novamente.");
					else
						break;
				}
				
				contratar(contribuinte);
			
				break;
			case 2:
				System.out.println("Digite o cpf para demitir um contratado: ");
				cpf = UI.sc.nextLine();
				demitir(cpf);
				break;
			case 3:
				System.out.println("Digite o cpf para ver os dados do contratado: ");
				cpf = UI.sc.nextLine();
				dadosUser(cpf);
				break;
			default:
				System.out.println("Saindo das operações...");
				break;
		}	
	}

	private static void contratar(Entidade entidade) {

	}

	private static void demitir(String cpf) {

	}

	private static void dadosUser(String cpf) {

	}

	private static Entidade criarEntidade(String entity, String nome, String cpf, String senha) {
		entity = entity.trim().toLowerCase();
		if (entity.equals("funcionario")) {
			return new Funcionario(nome, cpf, senha);
		} else if (entity.equals("auxiliar")) {
			return new AuxiliarVeterinario(nome, cpf, senha);
		} else if (entity.equals("veterinario")) {
			return new Veterinario(nome, cpf, senha);
		}
		return null;
	}
}
