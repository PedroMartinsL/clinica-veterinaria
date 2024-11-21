package controller.gerenciamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import db.DB;
import db.DbException;
import model.recursos.Medicamento;
import view.UI;

public class SistemaRendimento {

	static Connection conn = DB.getConnection();

	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static void gerenciar() {
		try {
			int request = UI.getRequest(new String[] { "Rendimento", "Gastos", "Voltar" }); // corrigir
			switch (request) {
			case 1:
				System.out.println("Mostrando rendimento");
				rendimento();

				System.out.println("Digite enter para sair");
				UI.sc.nextLine();
				break;
			case 2:
				System.out.println("Listando gastos com medicamentos: ");
				System.out.println("Digite um id do medicamento específico para mostrar ou nada para listar todos");
				String id = UI.sc.nextLine();
				LocalDate ld = LocalDate.parse(UI.sc.nextLine(), dtf);

				if (id.equals(" ")) {
					gastosRemedio(new Medicamento(), ld);
				} else {
					try {
						gastosRemedio(new Medicamento(Integer.parseInt(id)), ld);
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}
				}
				System.out.println("Digite enter para sair");
				UI.sc.nextLine();
				break;
			default:
				System.out.println("Saindo do gerenciamento de estoque...");
				break;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	public static void rendimento() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
			java.sql.Date sqlDate = java.sql.Date.valueOf(thirtyDaysAgo);

			st = conn.prepareStatement("SELECT SUM(valor_total) as despesa_medicamento from Reabastecimentos where data > ?");
			st.setDate(1, sqlDate);
			double gastosFixos = 3000;
			rs = st.executeQuery();
			double rendimento = lucroMensal() - gastosFixos - rs.getDouble("despesa_medicamento");
			System.out.printf(
					"Gastos fixos: %.2f, Despesa com medicamentos: %.2f, Rendimento: %.2f, Arrecadado com consultas: %.2f",
					gastosFixos, rs.getDouble("despesa_medicamento"), rendimento, lucroMensal());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private static double lucroMensal() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
			java.sql.Date sqlDate = java.sql.Date.valueOf(thirtyDaysAgo);

			st = conn.prepareStatement("SELECT SUM(valor) as total_dividas from Consultas where data > ?");
			st.setDate(1, sqlDate);
			rs = st.executeQuery();
			return rs.getDouble("total_dividas");

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	public static void gastosRemedio(Medicamento medicamento, LocalDate ld) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			if (medicamento == null && ld == null) {
				st = conn.prepareStatement("SELECT * from Reabastecimentos");
			} else if (ld != null && medicamento == null) {
				st = conn.prepareStatement("SELECT * from Reabastecimentos where data_compra > ?");
				st.setDate(1, java.sql.Date.valueOf(ld));
			} else if (ld == null && medicamento != null) {
				st = conn.prepareStatement("SELECT * from Reabastecimentos where medicamento_id = ?");
				st.setInt(1, medicamento.getId());
			} else {
				st = conn.prepareStatement("SELECT * from Reabastecimentos where medicamento_id = ? and data_compra > ?");
				st.setInt(1, medicamento.getId());
				st.setDate(2, java.sql.Date.valueOf(ld));
			}

			rs = st.executeQuery();

			System.out.printf("%-10s %-15s %-10s %-15s %-15s%n", "ID", "MEDICAMENTO_ID", "VALOR", "DATA", "QUANTIDADE");
			System.out.println("--------------------------------------------------------------");

			while (rs.next()) {
				int id = rs.getInt("id");
				int idMedicamento = rs.getInt("medicamento_id");
				double valor = rs.getDouble("valor");
				java.sql.Date data = rs.getDate("data_compra");
				int quantidade = rs.getInt("quantidade");
				System.out.printf("%-10d %-15d %-10.2f %-15s %-15s%n", id, idMedicamento, valor, data.toString(), quantidade);
			}

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
