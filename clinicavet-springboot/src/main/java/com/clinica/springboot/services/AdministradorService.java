package com.clinica.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.clinica.springboot.controller.exceptions.DatabaseException;
import com.clinica.springboot.controller.exceptions.ResourceNotFoundException;
import com.clinica.springboot.model.entities.Administrador;
import com.clinica.springboot.repositories.AdministradorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdministradorService {

	@Autowired
	private AdministradorRepository repository;

	public List<Administrador> findAll() {
		return repository.findAll();
	}

	public Administrador findById(String id) {
		Optional<Administrador> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id)); // retorna o objeto do tipo Administrador que
																			// estiver // dentro do optional
	}

	public Administrador insert(Administrador obj) {
		return repository.save(obj);
	}

	public void delete(String id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage()); // Passando exception da própria camada de serviço
		}
	}

	public Administrador update(String id, Administrador obj) {
		try {
			Administrador entity = repository.getReferenceById(id); // monitorar um obj pelo jpa para depois efetuar uma
																	// op no bd
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Administrador entity, Administrador obj) {
		entity.setNome(obj.getNome());
		entity.setSenha(obj.getSenha());
	}
}
