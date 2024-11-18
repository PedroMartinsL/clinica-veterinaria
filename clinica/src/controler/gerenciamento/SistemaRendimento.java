package controler.gerenciamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import db.DB;
import model.recursos.Medicamento;
import view.UI;

public class SistemaRendimento {

	static Connection conn = DB.getConnection();

	public static void gerenciar() {
		int request = UI.getRequest(new String[] { "Rendimento", "Gastos", "Voltar" }); // corrigir
		switch (request) {
		case 1:
			System.out.println("Mostrando rendimento");
			//rendimento();

			System.out.println("Digite enter para sair");
			UI.sc.nextLine();
			break;
		case 2:
			System.out.println("Listando gastos com medicamentos: ");
			System.out.println("Digite um medicamento específico para mostrar ou nada para listar todos");
			String medicamento = UI.sc.nextLine();
			if (medicamento == null) {
				//gastosRemedio();
			} else {
				// checar a existência do remédio
				//gastosRemedio(null);
			}
			System.out.println("Digite enter para sair");
			UI.sc.nextLine();
			break;
		default:
			System.out.println("Saindo do gerenciamento de estoque...");
			break;
		}
	}

	public static void rendimento() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
            java.sql.Date sqlDate = java.sql.Date.valueOf(thirtyDaysAgo);
			
			st = conn.prepareStatement(
					"SELECT SUM(valor_total) as despesa_medicamento from Despesas where data > ?");
			st.setDate(1, sqlDate);
			double gastosFixos = 3000;
			rs = st.executeQuery();
			double rendimento = lucroMensal() - gastosFixos - rs.getDouble("despesa_medicamento");
			System.out.printf("Gastos fixos: %.2f, Despesa com medicamentos: %.2f, Rendimento: %.2f, Arrecadado com consultas: %.2f", gastosFixos, rs.getDouble("despesa_medicamento"), rendimento, lucroMensal());
			
			
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

			st = conn.prepareStatement(
					"SELECT SUM(valor_consulta) as total_dividas from Consultas where data > ?");
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
				st = conn.prepareStatement("SELECT * from Despesas");
			} else if (ld != null && medicamento == null) {
				st = conn.prepareStatement("SELECT * from Despesas where data > ?");
				st.setDate(1, java.sql.Date.valueOf(ld));
			} else if (ld == null && medicamento != null) {
				st = conn.prepareStatement("SELECT * from Despesas where id_medicamento = ?");
				// st.setString(1, medicamento.getId());
			} else {
				st = conn.prepareStatement("SELECT * from Despesas where id_medicamento = ? and data > ?");
				st.setDate(2, java.sql.Date.valueOf(ld));
				// st.setString(1, medicamento.getId());
			}

			rs = st.executeQuery();
			while (rs.next()) {
				// mostrar tabelado o id, id_medicamento, valor, data
			}

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
