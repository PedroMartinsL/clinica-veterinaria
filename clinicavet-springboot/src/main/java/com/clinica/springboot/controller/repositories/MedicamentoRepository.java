package com.clinica.springboot.controller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.springboot.model.entities.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

}
