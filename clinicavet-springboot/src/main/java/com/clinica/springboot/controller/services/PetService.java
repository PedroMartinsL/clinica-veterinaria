package com.clinica.springboot.controller.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.clinica.springboot.controller.exceptions.DatabaseException;
import com.clinica.springboot.controller.exceptions.ResourceNotFoundException;
import com.clinica.springboot.controller.repositories.PetRepository;
import com.clinica.springboot.model.entities.Pet;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PetService {
	
	@Autowired
	private PetRepository repository;

	public List<Pet> findAll() {
		return repository.findAll();
	}

	public Pet findById(Long id) {
		Optional<Pet> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id)); // retorna o objeto do tipo Pet que estiver																	// dentro do optional
	}

	public Pet insert(Pet obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage()); //Passandp exception da própria camada de serviço
		}
	}

	public Pet update(Long id, Pet obj) {
		try {
			Pet entity = repository.getReferenceById(id); //monitorar um obj pelo jpa para depois efetuar uma op no bd
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Pet entity, Pet obj) {
		entity.setAnimal(obj.getAnimal());
		entity.setCpf_dono(obj.getCpf_dono());
		entity.setId(obj.getId());
		entity.setIdade(obj.getIdade());
		entity.setRaca(obj.getRaca());
	}
}
