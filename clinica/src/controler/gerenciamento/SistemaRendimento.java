package controler.gerenciamento;

import model.recursos.Medicamento;
import view.UI;

public class SistemaRendimento {

	public static void gerenciar() {
		int request = UI.getRequest(new String[] {"Rendimento", "Gastos", "Voltar"}); // corrigir
		switch (request) {
			case 1:
				System.out.println("Mostrando rendimento");
				rendimento();
				
				System.out.println("Digite enter para sair");
				UI.sc.nextLine();
				break;
			case 2:
				System.out.println("Listando gastos com medicamentos: ");
				System.out.println("Digite um medicamento específico para mostrar ou nada para listar todos");
				String medicamento = UI.sc.nextLine();
				if (medicamento == null) {
					gastosRemedio();
				} else {
					//checar a existência do remédio
					gastosRemedio(null);
				}
				System.out.println("Digite enter para sair");
				UI.sc.nextLine();
				break;
			default:
				System.out.println("Saindo do gerenciamento de estoque...");
				break;
		}	
	}

	public static void rendimento() {

	}

	public static void gastosRemedio() {

	}

	public static void gastosRemedio(Medicamento medicamento) {

	}

}
