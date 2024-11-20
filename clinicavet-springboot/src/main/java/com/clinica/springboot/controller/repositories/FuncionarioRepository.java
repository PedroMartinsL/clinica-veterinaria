package com.clinica.springboot.controller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.springboot.model.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {

}
