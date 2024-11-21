package model.recursos;

import java.util.HashMap;
import java.util.Map;

import model.entidades.Entidade;

public class Pedido {

	private Entidade responsavel;
	private Map<Medicamento, Integer> pedidos = new HashMap<>();
	private int idConsulta;

	public Pedido(Entidade responsavel, Map<Medicamento, Integer> pedidos, int idConsulta) {
		super();
		this.responsavel = responsavel;
		this.pedidos = pedidos;
		this.idConsulta = idConsulta;
	}

	public int getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(int idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Entidade getResponsavel() {
		return responsavel;
	}

	public Map<Medicamento, Integer> getPedidos() {
		return pedidos;
	}

	public void setResponsavel(Entidade responsavel) {
		this.responsavel = responsavel;
	}

	public void setPedidos(Map<Medicamento, Integer> pedidos) {
		this.pedidos = pedidos;
	}

}
