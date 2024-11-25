package com.clinica.springboot.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.clinica.springboot.controller.exceptions.DatabaseException;
import com.clinica.springboot.controller.exceptions.ResourceNotFoundException;
import com.clinica.springboot.model.entities.Medicamento;
import com.clinica.springboot.repositories.MedicamentoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MedicamentoService {
	
	@Autowired
	private MedicamentoRepository repository;

	public List<Medicamento> findAll() {
		return repository.findAll();
	}

	public Medicamento findById(Long id) {
		Optional<Medicamento> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id)); // retorna o objeto do tipo Medicamento que estiver																	// dentro do optional
	}

	public Medicamento insert(Medicamento obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage()); //Passando exception da própria camada de serviço
		}
	}

	public Medicamento update(Long id, Medicamento obj) {
		try {
			Medicamento entity = repository.getReferenceById(id); //monitorar um obj pelo jpa para depois efetuar uma op no bd
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Medicamento entity, Medicamento obj) {
		entity.setConcentracao(obj.getConcentracao());
		entity.setPreco(obj.getPreco());
		entity.setNome(obj.getNome());
		entity.setQuantidadeAtual(obj.getQuantidadeAtual());
		entity.setQuantidadeMinima(obj.getQuantidadeMinima());
		
		entity.setLaboratorio(obj.getLaboratorio());
	}
	
	public Medicamento updatePatch(Long id, Medicamento obj) {
	    try {
	        Medicamento entity = repository.getReferenceById(id);
	        partialUpdateData(entity, obj); 
	        return repository.save(entity); 
	    } catch (EntityNotFoundException e) {
	        throw new ResourceNotFoundException(id); 
	    }
	}

	private void partialUpdateData(Medicamento entity, Medicamento obj) {
	    if (obj.getConcentracao() != null) {
	        entity.setConcentracao(obj.getConcentracao());
	    }
	    if (obj.getPreco() != null) {
	        entity.setPreco(obj.getPreco());
	    }
	    if (obj.getNome() != null) {
	        entity.setNome(obj.getNome());
	    }
	    if (obj.getQuantidadeAtual() != null) {
	        entity.setQuantidadeAtual(obj.getQuantidadeAtual());
	    }
	    if (obj.getQuantidadeMinima() != null) {
	    	entity.setQuantidadeMinima(obj.getQuantidadeMinima());
	    }
	    if (obj.getLaboratorio() != null) {
	        entity.setLaboratorio(obj.getLaboratorio());
	    }
	}
	
	 // Verifica necessidade de reposição
    public List<Medicamento> verificarReposicao() {
        return repository.findAll()
                .stream()
                .filter(item -> item.getQuantidadeAtual() <= item.getQuantidadeMinima())
                .collect(Collectors.toList());
    }

    // Atualiza quantidade do estoque
    public Medicamento atualizarQuantidade(Long id, Integer novaQuantidade) {
    	Medicamento estoque = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado!"));
        estoque.setQuantidadeAtual(novaQuantidade);
        return repository.save(estoque);
    }

}
