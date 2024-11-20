package com.clinica.springboot.controller.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.springboot.model.entities.Pet;

@RestController
@RequestMapping(value = "/pets")
public class PetResource {
	
	@GetMapping
	public ResponseEntity<Pet> findAll() {
		Pet u = new Pet(2, "Pato", "0009990291", "MallardDuck", 3, "13233341412");
		return ResponseEntity.ok().body(u);
	}
}
