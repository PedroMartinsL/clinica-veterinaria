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
		int request = UI.getRequest(new String[] { "Listar Consulta", "Aplicar Medicamento", "Exibir notificações", "Finalizar operações" });
		
		switch (request) {
		case 1:
            listarConsultas();
            break;
        case 2:
            aplicarMedicamento(null, null); // Aqui, passe os parâmetros reais conforme o fluxo.
            break;
        case 3:
            System.out.println("Saindo das operações...");
            return false;
        default:
            System.out.println("Opção inválida!");
    }
    return true;
	}

	public static void listarConsultas() {
	    PreparedStatement st = null;
	    ResultSet rs = null;

	    try {
	        st = conn.prepareStatement("SELECT id, nome_pet, status FROM Consultas");
	        rs = st.executeQuery();

	        System.out.printf("%-10s %-20s %-15s%n", 
	            "ID", "Nome do Pet", "Data da Consulta", "Status");
	        System.out.println("---------------------------------------------------------------");

	        while (rs.next()) {
	            String id = rs.getString("id");
	            String nomePet = rs.getString("nome_pet");
	            String dataConsulta = rs.getString("data_consulta");
	            String status = rs.getString("status");

	            System.out.printf("%-10s %-20s %-20s %-15s%n", 
	                id, nomePet, dataConsulta, status);
	        }

	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}

	void aplicarMedicamento(Map<Medicamento, Integer> mapMedicamentos, Pet pet, Entidade veterinario) {
	    System.out.print("Informe o nome do medicamento: ");
	    String nomeMedicamento = sc.nextLine();

	    System.out.print("Informe a quantidade do medicamento: ");
	    int quantidadeMedicamento = sc.nextInt();
	    sc.nextLine();

	    // Cria o objeto Medicamento
	    Medicamento medicamento = new Medicamento();

	    // Cria e adiciona o medicamento e a quantidade ao mapa
	    mapMedicamentos = new HashMap<>();
	    mapMedicamentos.put(medicamento, quantidadeMedicamento);

	    // Cria o pedido
	    Pedido pedido = new Pedido(veterinario, mapMedicamentos, quantidadeMedicamento);

	    // Chama o método de solicitar medicamento
	    solicitarMedicamento(pedido);

	    // Exibe a aplicação do medicamento
	    System.out.println("Aplicando " + quantidadeMedicamento + " doses de " + nomeMedicamento);

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