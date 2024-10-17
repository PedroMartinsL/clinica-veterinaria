package controler.gerenciamento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.entidades.Administrador;
import model.entidades.Entidade;
import model.recursos.Medicamento;
import model.recursos.Pedido;

public class Estoque {
	static Scanner sc = new Scanner(System.in);

	public static Medicamento removerMedicamento(Medicamento medicamento, Entidade responsavel, int quantidade) {
		// código para remover diretamente do banco de dados
		// tabela de retirada
		checarEstoque(medicamento);
		return medicamento;
	}

	public static void cancelarContrato(Medicamento medicamento) {
		medicamento.setContrato(false);
	}

	public static void rendimento() {
		// valores de acordo com o BD
	}

	public static Map<Medicamento, Integer> solicitarMedicamento(Pedido pedido) {
		Map<Medicamento, Integer> solicitacoes = pedido.getPedidos();
		// consulta pelo banco MYSQL

		List<Map.Entry<Medicamento, Integer>> listaMapeada = new ArrayList<>(solicitacoes.entrySet());

		Map<Medicamento, Integer> remedios = new HashMap<>();

		Integer[] disponiveis = new Integer[solicitacoes.size()];
		for (int i = 0; i < solicitacoes.size(); i++) {
			if (disponiveis[i] >= listaMapeada.get(i).getValue()) {
				remedios.put(removerMedicamento(listaMapeada.get(i).getKey(), pedido.getResponsavel(),
						listaMapeada.get(i).getValue()), disponiveis[i]);
				// remedios.put(null, null);
			} else if (disponiveis[i] < listaMapeada.get(i).getValue() && disponiveis[i] > 0) {
				System.out.println("A quantidade encontrada do medicamento está abaixo do esperado!");
				System.out.printf("Medicamento: %s - Quantidade Solicitada: %d - Disponível: %d\n\n",
						listaMapeada.get(i).getKey().getNome(), listaMapeada.get(i).getValue(), disponiveis[i]);

				System.out.printf("1 - Retirar apenas %d\n2 - Cancelar solicitação atual\n", disponiveis[i]);
				String input = sc.nextLine();
				if (input == "1") {
					remedios.put(
							removerMedicamento(listaMapeada.get(i).getKey(), pedido.getResponsavel(), disponiveis[i]),
							disponiveis[i]);
				} else {
					System.out.println("Fim de operação");
				}
			} else {
				System.out.printf("Medicamento %s em falta\n"); // medicamento
			}
		}
		return remedios;
		// entrar com todos os dados de medicamento
	}

	private static void checarEstoque(Medicamento medicamento) {
		boolean statement = true;// checagem de medicamento no estoque no bd

		// TO DO
		if (statement) {
			reporMedicamento(medicamento.clone());
		}
	}
	
	public static void reporMedicamento(Medicamento medicamento) {
		if (medicamento.isContrato()) {
			String warning = String.format(" - O medicamento %s está perto de acabar, o estoque será reposto. - %s\n",
					medicamento, LocalDateTime.now().toString());
			//notificarAdm(warning);
		}
		// código para incrementar os remédios no BD
	}

	

}
