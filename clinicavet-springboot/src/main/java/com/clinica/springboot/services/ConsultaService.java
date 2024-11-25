package com.clinica.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.clinica.springboot.controller.exceptions.DatabaseException;
import com.clinica.springboot.controller.exceptions.ResourceNotFoundException;
import com.clinica.springboot.model.entities.Consulta;
import com.clinica.springboot.repositories.ConsultaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ConsultaService {
	
	@Autowired
	private ConsultaRepository repository;

	public List<Consulta> findAll() {
		return repository.findAll();
	}
	
	public Consulta findById(Long id) {
		Optional<Consulta> obj = repository.findById(id);
		return obj.get();
	}
	
	public Consulta insert(Consulta obj) {
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

	public Consulta update(Long id, Consulta obj) {
		try {
			Consulta entity = repository.getReferenceById(id); //monitorar um obj pelo jpa para depois efetuar uma op no bd
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Consulta entity, Consulta obj) {
		entity.setConsultaStatus(obj.getConsultaStatus());
		entity.setDoenca(obj.getDoenca());
		entity.setVeterinario(obj.getVeterinario());
		entity.setAuxVeterinario(obj.getAuxVeterinario());
		entity.setData(obj.getData());
	}
	
	public Consulta updatePatch(Long id, Consulta obj) {
	    try {
	        Consulta entity = repository.getReferenceById(id);
	        partialUpdateData(entity, obj); 
	        return repository.save(entity); 
	    } catch (EntityNotFoundException e) {
	        throw new ResourceNotFoundException(id); 
	    }
	}

	private void partialUpdateData(Consulta entity, Consulta obj) {
	    if (obj.getConsultaStatus() != null) {
	        entity.setConsultaStatus(obj.getConsultaStatus());
	    }
	    if (obj.getDoenca() != null) {
	        entity.setDoenca(obj.getDoenca());
	    }
	    if (obj.getAuxVeterinario() != null) {
	    	entity.setAuxVeterinario(obj.getAuxVeterinario());
	    }
	    if (obj.getVeterinario() != null) {
	    	entity.setVeterinario(obj.getVeterinario());
	    }
	    if (obj.getData() != null) {
	    	entity.setData(obj.getData());
	    }
	}
}
