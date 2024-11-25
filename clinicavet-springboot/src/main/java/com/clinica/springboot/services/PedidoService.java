package com.clinica.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.clinica.springboot.controller.exceptions.DatabaseException;
import com.clinica.springboot.controller.exceptions.ResourceNotFoundException;
import com.clinica.springboot.model.entities.Pedido;
import com.clinica.springboot.repositories.PedidoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;

	public List<Pedido> findAll() {
		return repository.findAll();
	}
	
	public Pedido findById(Long id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.get();
	}
	
	public Pedido insert(Pedido obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage()); 
		}
	}

	public Pedido update(Long id, Pedido obj) {
		try {
			Pedido entity = repository.getReferenceById(id); 
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Pedido entity, Pedido obj) {
		entity.setMedicamento(obj.getMedicamento());
		entity.setQuantidade(obj.getQuantidade());
	}
	
	public Pedido updatePatch(Long id, Pedido obj) {
	    try {
	    	Pedido entity = repository.getReferenceById(id);
	        partialUpdateData(entity, obj); 
	        return repository.save(entity); 
	    } catch (EntityNotFoundException e) {
	        throw new ResourceNotFoundException(id); 
	    }
	}

	private void partialUpdateData(Pedido entity, Pedido obj) {
		if (obj.getMedicamento() != null) {
			entity.setMedicamento(obj.getMedicamento());
		}
		if (obj.getQuantidade() != null) {
			entity.setQuantidade(obj.getQuantidade());
		}
	}
}
