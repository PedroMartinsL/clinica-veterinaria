package controler.gerenciamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
				"Listagem estoque", "Adicionar linha de Medicamento", "Voltar" });
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
		case 5:
			System.out.println("Adicionar medicamentos: ");
			adicionarMedicamento();
			System.out.println("Digite enter para sair");
			UI.sc.nextLine();
			break;

		default:
			System.out.println("Saindo do gerenciamento de estoque...");
			break;
		}
	}

	public static Map<Medicamento, Integer> solicitarMedicamento(Pedido pedido) {

		List<Map.Entry<Medicamento, Integer>> listaMapeada = new ArrayList<>(pedido.getPedidos().entrySet());

		Map<Medicamento, Integer> carrinho = new HashMap<>();

		for (int i = 0; i < listaMapeada.size(); i++) {
			Medicamento remedioAdd = addCarrinho(listaMapeada.get(i).getKey(), pedido.getResponsavel(),
					listaMapeada.get(i).getValue(), pedido.getIdConsulta());
			if (remedioAdd != null) {
				carrinho.put(remedioAdd, listaMapeada.get(i).getValue());
			}
		}
		return carrinho;
	}
	
	public static Medicamento addCarrinho(Medicamento medicamento, Entidade responsavel, int quantidade, int idConsulta) {
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
			if (rs.next()) {
				int reservaEstoque = rs.getInt("quantidade");
				
				if (quantidade <= reservaEstoque) {
					retirarMedicamento(rs.getInt("id_medicamento"), quantidade, responsavel, idConsulta); // alterar lógica aqui
					checarEstoque(medicamento);
									
					return instanciateMedicamento(rs);
				} else {
					System.out.println("A quantidade encontrada do medicamento está abaixo do esperado!");
					System.out.printf("Medicamento: %s - Quantidade Solicitada: %d - Disponível: %d\n\n",
							medicamento.getNome(), quantidade, reservaEstoque);
				}
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
		return new Medicamento(rs.getInt("id"), rs.getDouble("preco"), rs.getString("laboratorio"),
				rs.getDouble("concentracao"), rs.getString("nome"));
	}

	public static void retirarMedicamento(int idMedicamento, int quantidade,
			Entidade responsavel, int idConsulta) throws SQLException {
		PreparedStatement st = null;

		st = conn.prepareStatement(
				"UPDATE Medicamentos SET quantitidade = quantitidade - ? WHERE id = ?");

		st.setDouble(1, quantidade);
		st.setInt(2, idMedicamento);
		
		st.executeUpdate();
		
		st = conn.prepareStatement(
				"INSERT INTO Pedidos (id_consulta, medicamento_id, quantidade, responsavel) " + "values (?,?,?,?)");

		st.setInt(1, idConsulta);
		st.setInt(2, idMedicamento);
		st.setInt(3, quantidade);
		st.setString(4, responsavel.getCpf());

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
					"SELECT quantidade from Medicamentos where nome = ? and laboratorio = ?  and concentracao = ?");

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
			st = conn.prepareStatement("UPDATE Medicamentos where nome = ? and laboratorio = ?  and concentracao = ?"
					+ "SET quantidade = quantidade * 10");

			st.setString(1, medicamento.getNome());
			st.setString(2, medicamento.getLaboratorio());
			st.setDouble(3, medicamento.getConcentracao());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		// depois adicionar a tabela de despesa
	}

	public static void notificarAdm(String msg) {
		//modificar e adicionar em um bloco de notas para os adms
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
			st = conn
					.prepareStatement("SELECT nome, preco, laboratorio, concentracao, quantidade, contrato FROM Medicamentos");
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

	public static void listarEstoque(String nomeMedicacao) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT nome, preco, laboratorio, concentracao, quantidade, contrato FROM Medicamentos where nome = ?");

			st.setString(1, nomeMedicacao);

			rs = st.executeQuery();

			System.out.println("Medicamentos disponíveis:");
			System.out.printf("%-20s %-10s %-15s %-15s %-10s %-10s%n", "Nome", "Preço", "Laboratório", "Quantidade",
					"Concentração", "Contrato");
			System.out.println("-----------------------------------------------------------------------------------");

			while (rs.next()) {
				String nome = rs.getString("nome");
				double preco = rs.getDouble("preco");
				String laboratorio = rs.getString("laborator");
				double concentracao = rs.getDouble("concentracao");
				int quantidade = rs.getInt("quantidade");
				boolean contrato = rs.getBoolean("contrato");

				System.out.printf("%-20s %-10.2f %-15s %-15s %-10.2f %-10s%n", nome, preco, laboratorio,
						quantidade, concentracao, contrato ? "Sim" : "Não");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private static void adicionarMedicamento() {
		//corrigir futuramente se for um mesmo medicamento adicionado
		PreparedStatement st = null;
		try {
			System.out.println("Digite o nome do medicamento: ");
			String nome = UI.sc.nextLine();
			System.out.println("Digite o laboratório: ");
			String laboratorio = UI.sc.nextLine();
			System.out.println("Digite o preço: ");
			double preco = UI.sc.nextDouble();
			System.out.println("Digite a quantidade: ");
			int quantidade = UI.sc.nextInt();
			System.out.println("Digite a concentração: ");
			double concentracao = UI.sc.nextDouble();
			UI.sc.nextLine();

			Medicamento medicamento = new Medicamento(preco, laboratorio, concentracao, nome);

			st = conn.prepareStatement(
					"INSERT INTO Medicamentos " + "(preco, laboratorio, concentracao, nome, quantidade, contrato) "
							+ "VALUES " + "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setDouble(1, medicamento.getPreco());
			st.setString(2, medicamento.getLaboratorio());
			st.setDouble(3, medicamento.getConcentracao());
			st.setString(4, medicamento.getNome());
			st.setInt(5, quantidade);
			st.setBoolean(6, true);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1); // recuperar o valor da primeira coluna
					medicamento.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("Argumento inválido, tente novamente");
			UI.sc.nextLine();
		} finally {
			DB.closeStatement(st);
		}
	}

}
