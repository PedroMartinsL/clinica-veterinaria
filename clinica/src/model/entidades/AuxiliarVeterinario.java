package model.entidades;

import java.sql.Connection;
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


public class AuxiliarVeterinario extends Veterinario {
	
	Scanner sc = new Scanner(System.in);
	
	static Connection conn = DB.getConnection();
	
	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	private Consulta consulta;

	public AuxiliarVeterinario(String name, String cpf, String senha) {
		super(name, cpf, senha);

	}

	@Override
	public boolean operacoes() {
		int request = UI.getRequest(new String[] { "Operações de contratados", "Exibir renda da clínica",
				"Operações de Estoque", "Exibir notificações", "Finalizar operações" });
		
		switch (request) {
		case 1:
				System.out.println("Atendimento ao Pet realizado.");
				break;

			case 2:
				aplicarMedicamento(null, null); // Chama o método para aplicar medicação
				break;

			case 3:
				  System.out.println("Saindo das operações...");
	              break; 
	          default:
	              return false;
	      }
		return true;
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
//recebe e vai aplicando
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

		Pedido pedido = new Pedido(auxVeterinario aux, mapMedicamentos);

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
	
	//realizar atendimento: chama metodo que atualiza status, id, id veterinario, construir veterinsario só com cpf, chama operaão de veterinario
	//Checar diagnostico e prescrever medicaçao, aplicar medicamentos e chaamae o metodo de atualizar status para divida
	
	
}