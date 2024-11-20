package com.clinica.springboot.controller.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.clinica.springboot.controller.exceptions.DatabaseException;
import com.clinica.springboot.controller.exceptions.ResourceNotFoundException;
import com.clinica.springboot.controller.repositories.FuncionarioRepository;
import com.clinica.springboot.model.entities.Funcionario;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FuncionarioService {
	
	@Autowired
	private FuncionarioRepository repository;

	public List<Funcionario> findAll() {
		return repository.findAll();
	}

	public Funcionario findById(String id) {
		Optional<Funcionario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id)); // retorna o objeto do tipo Funcionario que estiver																	// dentro do optional
	}

	public Funcionario insert(Funcionario obj) {
		return repository.save(obj);
	}

	public void delete(String id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage()); //Passandp exception da própria camada de serviço
		}
	}

	public Funcionario update(String id, Funcionario obj) {
		try {
			Funcionario entity = repository.getReferenceById(id); //monitorar um obj pelo jpa para depois efetuar uma op no bd
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Funcionario entity, Funcionario obj) {
		entity.setNome(obj.getNome());
		entity.setSenha(obj.getSenha());
		
	}
}
