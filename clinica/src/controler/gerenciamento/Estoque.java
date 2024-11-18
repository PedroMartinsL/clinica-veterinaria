package controler.gerenciamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import db.DB;
import db.DbException;
import model.entidades.Administrador;
import model.entidades.Entidade;
import model.recursos.Medicamento;
import model.recursos.Pedido;
import view.UI;

public class Estoque {
	static Scanner sc = new Scanner(System.in);

	static Connection conn = DB.getConnection();

	private static ArrayList<Administrador> admObservers = new ArrayList<>();

	public static void gerenciar() {
		int request = UI.getRequest(new String[] { "Assinar reposição de medicamento", "Cancelar reposição",
				"Listagem estoque", "Voltar" });
		switch (request) {
		case 1:
			System.out.print("Digite o id do medicamento que deseja cancelar o contrato: ");
			int id = UI.sc.nextInt();
			UI.sc.nextLine();
			cancelarContrato(id);
			break;
		case 2:
			System.out.println("Digite o id do medicamento que deseja cancelar o contrato: ");
			listarEstoque();
			System.out.println("Digite enter para sair");
			UI.sc.nextLine();
			break;

		case 3:
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

	public static Map<Medicamento, Integer> solicitarMedicamento(Pedido pedido) {
		// consulta pelo banco MYSQL
		checarValidade();

		List<Map.Entry<Medicamento, Integer>> listaMapeada = new ArrayList<>(pedido.getPedidos().entrySet());

		Map<Medicamento, Integer> carrinho = new HashMap<>();

		for (int i = 0; i < listaMapeada.size(); i++) {
			Medicamento remedioAdd = addCarrinho(listaMapeada.get(i).getKey(), pedido.getResponsavel(),
					listaMapeada.get(i).getValue());
			if (remedioAdd != null) {
				carrinho.put(remedioAdd, listaMapeada.get(i).getValue());
			}
		}
		return carrinho;
	}

	public static Medicamento addCarrinho(Medicamento medicamento, Entidade responsavel, int quantidade) {
		// teoricamente a validade já foi checada

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT Medicamentos.* from Medicamentos where Medicamentos.nome = ? and Medicamentos.laboratorio = ?  and Medicamentos.concentracao = ?");

			st.setString(1, medicamento.getNome());
			st.setString(2, medicamento.getLaboratorio());
			st.setDouble(3, medicamento.getConcentracao());

			rs = st.executeQuery();
			int reservaEstoque = rs.getInt("quantidade");

			if (quantidade <= reservaEstoque) {
				retirarMedicamento(medicamento, quantidade);
				checarEstoque(medicamento);
				return instanciateMedicamento(rs);
			} else {
				System.out.println("A quantidade encontrada do medicamento está abaixo do esperado!");
				System.out.printf("Medicamento: %s - Quantidade Solicitada: %d - Disponível: %d\n\n",
						medicamento.getNome(), quantidade, reservaEstoque);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	private static Medicamento instanciateMedicamento(ResultSet rs) throws SQLException {
		return new Medicamento(rs.getDouble("preco"), rs.getString("laboratorio"), rs.getDouble("concentracao"),
				rs.getString("nome"), LocalDate.parse((CharSequence) rs.getDate("validade")));
	}

	public static void retirarMedicamento(Medicamento medicamento, int quantidade) throws SQLException {
		PreparedStatement st = null;

		st = conn.prepareStatement("UPDATE Medicamentos "
				+ "SET Quantity = (Select Medicamentos where Medicamentos.nome = ? and Medicamentos.laboratorio = ?  and Medicamentos.concentracao = ?) - ?");

		st.setString(1, medicamento.getNome());
		st.setString(2, medicamento.getLaboratorio());
		st.setDouble(3, medicamento.getConcentracao());
		st.setDouble(4, quantidade);

		// adicionar tabela de retirada com id de cada medicamento e responsável

		st.executeUpdate();

	}

	public static void cancelarContrato(int id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Medicamentos " + "SET contrato = False where id = ?");

			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	private static void checarEstoque(Medicamento medicamento) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT quantidade from Medicamentos where Medicamentos.nome = ? and Medicamentos.laboratorio = ?  and Medicamentos.concentracao = ?");

			st.setString(1, medicamento.getNome());
			st.setString(2, medicamento.getLaboratorio());
			st.setDouble(3, medicamento.getConcentracao());

			rs = st.executeQuery();
			int reservaEstoque = rs.getInt("quantidade");
			if (reservaEstoque < 10) {
				if (medicamento.isContrato()) {
					String warning = String.format(
							" - O medicamento %s está perto de acabar, o estoque será reposto. - %s\n", medicamento,
							LocalDateTime.now().toString());
					notificarAdm(warning);
				}
				reporMedicamento(medicamento);
			}
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	public static void reporMedicamento(Medicamento medicamento) {
		// considerando 10% da carga total
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Medicamentos "
					+ "SET Quantity = (Select Medicamentos where Medicamentos.nome = ? and Medicamentos.laboratorio = ?  and Medicamentos.concentracao = ?) * 10");

			st.setString(1, medicamento.getNome());
			st.setString(2, medicamento.getLaboratorio());
			st.setDouble(3, medicamento.getConcentracao());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	private static void checarValidade() {
		// método que executa antes da retirada do medicamento
		LocalDate ld = LocalDate.now();

		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("DELETE FROM TABELA WHERE (Select Medicamentos.validade from medicamentos) < ?");

			st.setDate(1, java.sql.Date.valueOf(ld));
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
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

	public static void removeAdm(Administrador adm) {
		getAdmObservers().add(adm);
	}

	public static ArrayList<Administrador> getAdmObservers() {
		return admObservers;
	}

	public static void setAdmObservers(ArrayList<Administrador> admObservers) {
		Estoque.admObservers = admObservers;
	}

	public static void listarEstoque() {
	    PreparedStatement st = null;
	    ResultSet rs = null;

	    try {
	        st = conn.prepareStatement("SELECT nome, preco, laborator, validade, concentra, contrato FROM Medicamentos");
	        rs = st.executeQuery();

	        System.out.println("Medicamentos disponíveis:");
	        System.out.printf("%-20s %-10s %-15s %-15s %-10s %-10s%n", 
	                          "Nome", "Preço", "Laboratório", "Validade", "Concentração", "Contrato");
	        System.out.println("-----------------------------------------------------------------------------------");

	        while (rs.next()) {
	            String nome = rs.getString("nome");
	            double preco = rs.getDouble("preco");
	            String laboratorio = rs.getString("laborator");
	            Date validade = rs.getDate("validade");
	            double concentracao = rs.getDouble("concentra");
	            boolean contrato = rs.getBoolean("contrato");

	            System.out.printf("%-20s %-10.2f %-15s %-15s %-10.2f %-10s%n", 
	                              nome, preco, laboratorio, validade, concentracao, contrato ? "Sim" : "Não");
	        }

	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}
	
	public static void listarEstoque(String nomeMedicacao) {
	    PreparedStatement st = null;
	    ResultSet rs = null;

	    try {
	        st = conn.prepareStatement("SELECT nome, preco, laborator, validade, concentra, contrato FROM Medicamentos where nome = ?");
	        
	        st.setString(1, nomeMedicacao);
	        
	        rs = st.executeQuery();

	        System.out.println("Medicamentos disponíveis:");
	        System.out.printf("%-20s %-10s %-15s %-15s %-10s %-10s%n", 
	                          "Nome", "Preço", "Laboratório", "Validade", "Concentração", "Contrato");
	        System.out.println("-----------------------------------------------------------------------------------");

	        while (rs.next()) {
	            String nome = rs.getString("nome");
	            double preco = rs.getDouble("preco");
	            String laboratorio = rs.getString("laborator");
	            Date validade = rs.getDate("validade");
	            double concentracao = rs.getDouble("concentra");
	            boolean contrato = rs.getBoolean("contrato");

	            System.out.printf("%-20s %-10.2f %-15s %-15s %-10.2f %-10s%n", 
	                              nome, preco, laboratorio, validade, concentracao, contrato ? "Sim" : "Não");
	        }

	    } catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}

}
