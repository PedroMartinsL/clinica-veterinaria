package model.recursos;

import java.util.HashMap;
import java.util.Map;

import model.entidades.Entidade;

public class Pedido {

	private Entidade responsavel;
	private Map<Medicamento, Integer> pedidos = new HashMap<>();

	public Pedido(Entidade responsavel, Map<Medicamento, Integer> pedidos) {
		super();
		this.responsavel = responsavel;
		this.pedidos = pedidos;
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
