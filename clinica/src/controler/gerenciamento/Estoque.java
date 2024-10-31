package controler.gerenciamento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.entidades.Administrador;
import model.entidades.Entidade;
import model.recursos.Medicamento;
import model.recursos.Pedido;
import view.UI;

public class Estoque {
	static Scanner sc = new Scanner(System.in);

	private static ArrayList<Administrador> admObservers = new ArrayList<>();

	public static void gerenciar() {
		int request = UI.getRequest(1); // corrigir
		switch (request) {
			case 1:
				System.out.println("Digite o id do medicamento que deseja cancelar o contrato: ");
				int id = UI.sc.nextInt();
				UI.sc.nextLine();
				cancelarContrato(id);
			
				break;
			case 2:
				System.out.println("Listar estoque: ");
				listarEstoque();
				System.out.println("Digite enter para sair");
				UI.sc.nextLine();
				break;
			default:
				System.out.println("Saindo do gerenciamento de estoque...");
				break;
		}	
	}

	public static Medicamento removerMedicamento(Medicamento medicamento, Entidade responsavel, int quantidade) {
		// código para remover diretamente do banco de dados
		// tabela de retirada
		checarEstoque(medicamento);
		return medicamento;
	}

	public static void removerMedicamento(Medicamento medicamento) {
		// código para remover diretamente do banco de dados
		// descartar vencidos
	}

	public static void cancelarContrato(int id) {
		//encontrar medicamento por id
		//medicamento.setContrato(false);
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

	private static void checarValidade(Medicamento medicamento) {
		LocalDate ld = LocalDate.now();
		// ler o BD
		ArrayList<Medicamento> remediosAnalise = new ArrayList<>(); // aqui são passados os medicamentos pesquisados
		Iterator<Medicamento> remedio = remediosAnalise.iterator();
		while (remedio.hasNext()) {
			Medicamento atualIterator = remedio.next();
			if (atualIterator.getValidade().isBefore(ld)) {
				removerMedicamento(medicamento);
			}
		}
	}

	public static void reporMedicamento(Medicamento medicamento) {
		if (medicamento.isContrato()) {
			String warning = String.format(" - O medicamento %s está perto de acabar, o estoque será reposto. - %s\n",
					medicamento, LocalDateTime.now().toString());
			notificarAdm(warning);
		}
		// código para incrementar os remédios no BD
	}

	public static void notificarAdm(String msg) {
		// aqui recebemos a lista dos adms pelo BD
		for (Administrador adm : admObservers) {
			adm.update(msg);
		}
	}

	public static void registerAdm(Administrador adm) {
		getAdmObservers().add(adm);
	}

	public static void removeadm(Administrador adm) {
		getAdmObservers().add(adm);
	}

	public static ArrayList<Administrador> getAdmObservers() {
		return admObservers;
	}

	public static void setAdmObservers(ArrayList<Administrador> admObservers) {
		Estoque.admObservers = admObservers;
	}

	public static void listarEstoque() {
		
	}
 }
