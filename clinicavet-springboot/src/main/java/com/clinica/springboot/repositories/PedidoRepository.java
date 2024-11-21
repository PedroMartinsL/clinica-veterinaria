package com.clinica.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.springboot.model.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
