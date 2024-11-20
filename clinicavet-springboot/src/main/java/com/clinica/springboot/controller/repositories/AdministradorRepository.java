package com.clinica.springboot.controller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.springboot.model.entities.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, String> {

}
