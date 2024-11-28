package com.clinica.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.springboot.model.entities.Consulta;
import com.clinica.springboot.model.entities.Medicamento;
import com.clinica.springboot.model.entities.Pedido;
import com.clinica.springboot.repositories.ConsultaRepository;
import com.clinica.springboot.repositories.MedicamentoRepository;
import com.clinica.springboot.repositories.PedidoRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
    private MedicamentoRepository medicamentoRepository;
	
	@Autowired
    private ConsultaRepository consultaRepository;

	public List<Pedido> findAll() {
		return repository.findAll();
	}
	
	public Pedido findById(Long id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.get();
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
        Medicamento medicamento = medicamentoRepository.findById(obj.getMedicamento().getId())
                .orElseThrow(() -> new IllegalArgumentException("Medicamento não encontrado"));

        if (medicamento.getQuantidadeAtual() < obj.getQuantidade()) {
            throw new IllegalArgumentException("Quantidade insuficiente no Estoque para o medicamento");
        }
        
        medicamento.setQuantidadeAtual(medicamento.getQuantidadeAtual() - obj.getQuantidade());
        
        medicamentoRepository.save(medicamento);
        obj.setMedicamento(medicamento);
   
        
        Consulta consulta = consultaRepository.findById(obj.getConsulta().getId())
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
        
        consultaRepository.save(consulta);
        obj.setConsulta(consulta);
        
        return repository.save(obj);
    }
}
