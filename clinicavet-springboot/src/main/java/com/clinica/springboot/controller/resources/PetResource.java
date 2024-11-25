package com.clinica.springboot.controller.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.clinica.springboot.model.entities.Pet;
import com.clinica.springboot.services.PetService;

@RestController
@RequestMapping(value = "/pets")
public class PetResource {
	

	@Autowired
	private PetService service;
		
	@GetMapping 
	public ResponseEntity<List<Pet>> findAll() {
		List<Pet> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}") 
	public ResponseEntity<Pet> findById(@PathVariable Long id) {
		Pet obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Pet> insert(@RequestBody Pet obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj); 
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)  {
		service.delete(id);
		return ResponseEntity.noContent().build();
		//código 204, quando não temos conteúdo
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Pet> update(@PathVariable Long id, @RequestBody Pet obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Pet> partialUpdate(@PathVariable Long id, @RequestBody Pet obj) {
		obj = service.updatePatch(id, obj);
	    return ResponseEntity.ok().body(obj);
	}
}
