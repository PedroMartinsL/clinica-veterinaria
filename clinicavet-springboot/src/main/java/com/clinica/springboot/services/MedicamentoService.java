package com.clinica.springboot.services;

import java.util.List;
import java.util.Optional;

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
		entity.setContrato(obj.isContrato());
		entity.setPreco(obj.getPreco());
		entity.setNome(obj.getNome());
		entity.setValidade(obj.getValidade());
		entity.setLaboratorio(obj.getLaboratorio());
	}
}
