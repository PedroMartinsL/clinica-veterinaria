package com.clinica.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.springboot.model.entities.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
