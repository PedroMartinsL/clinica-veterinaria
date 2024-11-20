package com.clinica.springboot.controller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.springboot.model.entities.AuxVeterinario;

public interface AuxVeterinarioRepository extends JpaRepository<AuxVeterinario, String> {

}