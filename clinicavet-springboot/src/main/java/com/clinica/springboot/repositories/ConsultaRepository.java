package com.clinica.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.springboot.model.entities.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

}
