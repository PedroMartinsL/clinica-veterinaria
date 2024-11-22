package model.entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import db.DB;
import db.DbException;
import model.recursos.Medicamento;
import model.recursos.Pedido;
import view.UI;
public class Veterinario extends Entidade {


	public Veterinario(String name, String cpf, String senha) {
		super(name, cpf, senha);
    }

	 Scanner sc = new Scanner(System.in);

	@Override
	public boolean operacoes() {
		int request = UI.getRequest(new String[] { "Operações de contratados", "Exibir renda da clínica",
				"Operações de Estoque", "Exibir notificações", "Finalizar operações" });
		switch (request) {
		case 1:
			  checagemDiagnostico(null); 
              break;
          case 2:
              prescreverMedicacao(new AuxiliarVeterinario(null, null, null), null); //auxiliar como parâmetro
              break;
          default: 
        	  System.out.println("Saindo das operações...");
      } 
		return true;
	}

    public Map<Medicamento, Integer> prescreverMedicacao(int idConsulta ,AuxiliarVeterinario auxiliar, Pet pet) {
        //instanciar map 
        if (pet != null) {
        	//while
            System.out.print("Informe o nome do medicamento: ");
            String nomeMedicamento = sc.nextLine();
            
            System.out.print("Informe o nome do medicamento: ");
            String nomeLaboratorio = sc.nextLine();

            System.out.print("Informe a concentração do medicamento (mg): ");
            int concentracao;
            try {
                concentracao = Integer.parseInt(sc.nextLine()); 
            } catch (NumberFormatException e) {
                System.out.println("Concentração inválida, operação cancelada.");
                return;
            }

            // Criação do objeto Medicamento
            Medicamento medicamento = new Medicamento(nomeLaboratorio, concentracao, nomeMedicamento);

            //fazer o put
            System.out.printf("Medicamento %s de %d mg prescrito para o pet do dono com CPF: %s.\n", 
                              medicamento.getNome(), medicamento.getConcentracao());
            
            //Deseja cadastrar outro medicamento?
//termina while
            //Construir pedido passando map, com id da consulta, e a entidade
            // estoque.solicitar medicamento passa o pedido como parametro 
            //estoque retorna map de remedios e recebe medicamento

        } else {
            System.out.println("Pet não encontrado.");
        }
    }

	private void checagemDiagnostico(int idConsulta) {
        System.out.print("Informe o sintoma do pet: ");
        String sintoma = sc.nextLine();
        // Fazer update da consulta na coluna doença pegando id da consulta
        System.out.println("Sintoma '" + sintoma + "' adicionado ao pet.");


	}
	

	public static void listarConsultas() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn
					.prepareStatement("SELECT * FROM Consultas");
			rs = st.executeQuery();

			System.out.println("Medicamentos disponíveis:");
			System.out.printf("%-20s %-10s %-15s %-15s %-10s %-10s%n", "Nome", "Preço", "Laboratório", "Quantidade",
					"Concentração", "Contrato");
			System.out.println("-----------------------------------------------------------------------------------");

			while (rs.next()) {
				String nome = rs.getString("nome");
				double preco = rs.getDouble("preco");
				String laboratorio = rs.getString("laboratorio");
				int quantidade = rs.getInt("quantidade");
				double concentracao = rs.getDouble("concentracao");
				boolean contrato = rs.getBoolean("contrato");

				System.out.printf("%-20s %-10.2f %-15s %-15s %-10.2f %-10s%n", nome, preco, laboratorio, quantidade,
						concentracao, contrato ? "Sim" : "Não");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	void aplicarMedicamento(Map<Medicamento, Integer> mapMedicamentos, Pet pet) {

		System.out.print("Informe o nome do medicamento: ");
		String nomeMedicamento = sc.nextLine();

		System.out.print("Informe a quantidade do medicamento: ");
		int quantidadeMedicamento = sc.nextInt();
		sc.nextLine();

		// construtor do medicamento
		Medicamento medicamento = new Medicamento(0, nomeMedicamento, 0, null, null, null);

		// armazena o medicamento e sua quantidade
		Map<Medicamento, Integer> mapMedicamentos = new HashMap<>();
		mapMedicamentos.put(medicamento, quantidadeMedicamento);

		Pedido pedido = new Pedido(auxVeterinario, mapMedicamentos);

		// solicitar medicamento
		solicitarMedicamento(pedido);

		System.out.println("Aplicando " + quantidadeMedicamento + " doses de " + nomeMedicamento
				);
	}

	private void solicitarMedicamento(Pedido pedido) {
		System.out.println("Medicamento solicitado: " + pedido);
	

	}
	private static void finalizarConsulta() {
		
	}
	// atualizar o status da consulta para numero 3, em dívida
	//atualizar e colocar no banco de dados
	
	
	//realizar atendimento: chama metodo que atualiza status, id, id aux veterinario, construir veterinsario só com cpf, chama operaão de veterinario
		//Checar diagnostico e prescrever medicaçao, aplicar medicamentos e chaamae o metodo de atualizar status para divida

}

