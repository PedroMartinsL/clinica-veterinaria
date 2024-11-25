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
public class Veterinario extends Entidade {

	 Scanner sc = new Scanner(System.in);
	 
	 static Connection conn = DB.getConnection();

		public Consulta getConsulta() {
			return consulta;
		}

		public void setConsulta(Consulta consulta) {
			this.consulta = consulta;
		}

		private Consulta consulta;

		public Veterinario(String name, String cpf, String senha) {
			super(name, cpf, senha);
	    }
		
	@Override
	public boolean operacoes() {
		int request = UI.getRequest(new String[] {"Listar Consultas", "Checagem de diagnóstico", "Prescrever Medicação" });
		switch (request) {
		case 1:
			listarConsultas();
			break;
		case 2:
			  checagemDiagnostico(consulta.getId()); 
              break;
          case 3:
        	  prescreverMedicacao(request, new AuxiliarVeterinario(null, null, null), null); //auxiliar como parâmetro
              break;
          case 4: finalizarConsulta(request); 
          break;
          default: 
        	  System.out.println("Saindo das operações...");
      } 
		return true;
	}

    public Map<Medicamento, Integer> prescreverMedicacao(int idConsulta ,AuxiliarVeterinario auxiliar, Pet pet) {
    	Map<Medicamento, Integer> medicamentosPrescritos = new HashMap<>();


        if (pet != null) {
            boolean adicionar = true;

            while (adicionar) {
                System.out.print("Informe o nome do medicamento: ");
                String nomeMedicamento = sc.nextLine();

                System.out.print("Informe o laboratório do medicamento: ");
                String nomeLaboratorio = sc.nextLine();

                System.out.print("Informe a concentração do medicamento (mg): ");
                int concentracao;
                try {
                    concentracao = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Concentração inválida, operação cancelada.");
                    break;
                }
                
                System.out.print("Informe a quantidade do medicamento: ");
                int quantidade = Integer.parseInt(sc.nextLine());


            // Criação do objeto Medicamento
            Medicamento medicamento = new Medicamento(nomeLaboratorio, concentracao, nomeMedicamento);
            medicamentosPrescritos.put(medicamento, quantidade);

            //fazer o put
            System.out.printf("Medicamento %s (%d mg) prescrito para o pet com CPF do dono: %s.\n",
                    medicamento.getNome(), medicamento.getConcentracao(), pet.getCpfDono());

            System.out.print("Deseja adicionar outro medicamento? (S/N): ");
            adicionar = sc.nextLine().equalsIgnoreCase("S");
        }

            
            //Deseja cadastrar outro medicamento?
//termina while
            //Construir pedido passando map, com id da consulta, e a entidade
            // estoque.solicitar medicamento passa o pedido como parametro 
            //estoque retorna map de remedios e recebe medicamento

            Pedido pedido = new Pedido(this, medicamentosPrescritos, idConsulta);
            solicitarMedicamento(pedido);
        } else {
            System.out.println("Pet não encontrado.");
        }

        return medicamentosPrescritos;
    }
    

	private void checagemDiagnostico(int idConsulta) {
        System.out.print("Informe o sintoma do pet: ");
        String sintoma = sc.nextLine();
        // Fazer update da consulta na coluna doença pegando id da consulta
        String sql = "UPDATE Consulta SET diagnostico = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, sintoma);
            st.setInt(2, idConsulta);
            st.executeUpdate();
            System.out.println("Sintoma '" + sintoma + "' registrado para a consulta.");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
	}

	public static void listarConsultas() {
		PreparedStatement st = null;
		ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT status, nome_pet, nome_veterinario FROM Consulta");
            rs = st.executeQuery();

            System.out.println("Consultas:");
            System.out.printf("%-20s %-20s %-20s%n", "Status", "Pet", "Veterinário");
            System.out.println("-----------------------------------------------------------");

            while (rs.next()) {
                String status = rs.getString("status");
                String nomePet = rs.getString("nome_pet");
                String nomeVeterinario = rs.getString("nome_veterinario");

                System.out.printf("%-20s %-20s %-20s%n", status, nomePet, nomeVeterinario);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
	}
	
	void aplicarMedicamento(Map<Medicamento, Integer> mapMedicamentos, Pet pet) {
	    if (mapMedicamentos == null || mapMedicamentos.isEmpty()) {
	        System.out.println("Nenhum medicamento disponível para aplicação.");
	        return;
	    }
	    System.out.printf("Medicamentos disponíveis para o pet '%s':%n", pet.getCpfDono());
	    for (Map.Entry<Medicamento, Integer> entry : mapMedicamentos.entrySet()) {
	        Medicamento medicamento = entry.getKey();
	        int quantidade = entry.getValue();
	        System.out.printf("- %s (%d mg), Quantidade: %d%n",
	                medicamento.getNome(), medicamento.getConcentracao(), quantidade);
	    }

	    System.out.print("Informe o nome do medicamento a ser aplicado: ");
	    String nomeMedicamento = sc.nextLine();

	    Medicamento medicamentoSelecionado = null;
	    for (Medicamento medicamento : mapMedicamentos.keySet()) {
	        if (medicamento.getNome().equalsIgnoreCase(nomeMedicamento)) {
	            medicamentoSelecionado = medicamento;
	            break;
	        }
	    }
	    if (medicamentoSelecionado == null) {
	        System.out.println("Medicamento não encontrado na lista.");
	        return;
	    }

	    System.out.print("Informe a quantidade a ser aplicada: ");
	    int quantidadeAplicada;
	    try {
	        quantidadeAplicada = Integer.parseInt(sc.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("Quantidade inválida. Operação cancelada.");
	        return;
	    }

	    int quantidadeDisponivel = mapMedicamentos.get(medicamentoSelecionado);
	    if (quantidadeAplicada > quantidadeDisponivel) {
	        System.out.printf("Quantidade insuficiente. Disponível: %d.%n", quantidadeDisponivel);
	        return;
	    }
	    // Atualiza a quantidade restante
	    mapMedicamentos.put(medicamentoSelecionado, quantidadeDisponivel - quantidadeAplicada);

	    System.out.printf("Aplicado %d de %s (%d mg) para o pet '%s'.%n",
	            quantidadeAplicada, medicamentoSelecionado.getNome(),
	            medicamentoSelecionado.getConcentracao(), pet.getCpfDono());
	}
	
	private void solicitarMedicamento(Pedido pedido) {
	    // Validação para evitar NullPointerException
	    if (pedido == null || pedido.getPedidos() == null || pedido.getPedidos().isEmpty()) {
	        System.out.println("Nenhum medicamento foi solicitado.");
	        return;
	    }
	    System.out.println("Medicamento(s) solicitado(s):");
	    for (Map.Entry<Medicamento, Integer> entry : pedido.getPedidos().entrySet()) {
	        Medicamento medicamento = entry.getKey();
	        int quantidade = entry.getValue();
	        System.out.printf("- %s (%d mg), Quantidade: %d, Laboratório: %s%n",
	                medicamento.getNome(),
	                medicamento.getConcentracao(),
	                quantidade,
	                medicamento.getLaboratorio());
	    }
	    System.out.println("Solicitação registrada com sucesso.");
	}
	
	public void realizarAtendimento(int idConsulta, AuxiliarVeterinario auxiliar, Pet pet) {
	    System.out.println("Iniciando atendimento...");


	    // Checar diagnóstico
	    checagemDiagnostico(idConsulta);

	    // Prescrever medicação
	    Map<Medicamento, Integer> medicamentosPrescritos = prescreverMedicacao(idConsulta, auxiliar, pet);

	    // Aplicar medicamentos (somente se prescritos)
	    if (!medicamentosPrescritos.isEmpty()) {
	        aplicarMedicamento(medicamentosPrescritos, pet);
	    }
	    System.out.println("Atendimento concluído.");
	}
	

	private void finalizarConsulta(int idConsulta) {
	    String sql = "UPDATE Consulta SET status = 'FINALIZADA' WHERE id = ?";
	    try (PreparedStatement st = conn.prepareStatement(sql)) {
	        st.setInt(1, idConsulta); // Define o parâmetro ID
	        st.executeUpdate();
	        System.out.println("Consulta " + idConsulta + " finalizada com sucesso.");
	    } catch (SQLException e) {
	        System.err.println("Erro ao finalizar a consulta: " + e.getMessage());
	        e.printStackTrace();
	    }

	// atualizar o status da consulta para numero 3, em dívida
	//atualizar e colocar no banco de dados
	
	
	//realizar atendimento: chama metodo que atualiza status, id, id aux veterinario, construir veterinsario só com cpf, chama operaão de veterinario
		//Checar diagnostico e prescrever medicaçao, aplicar medicamentos e chaamae o metodo de atualizar status para divida
	}
}

