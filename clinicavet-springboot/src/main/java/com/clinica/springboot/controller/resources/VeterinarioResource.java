package com.clinica.springboot.controller.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.clinica.springboot.controller.services.VeterinarioService;
import com.clinica.springboot.model.entities.Veterinario;

@RestController
@RequestMapping(value = "/vets")
public class VeterinarioResource {
	
	@Autowired
	private VeterinarioService service;
		
	@GetMapping 
	public ResponseEntity<List<Veterinario>> findAll() {
		List<Veterinario> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}") 
	public ResponseEntity<Veterinario> findById(@PathVariable String id) {
		Veterinario obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Veterinario> insert(@RequestBody Veterinario obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getCpf()).toUri();
		return ResponseEntity.created(uri).body(obj); 
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id)  {
		service.delete(id);
		return ResponseEntity.noContent().build();
		//código 204, quando não temos conteúdo
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Veterinario> update(@PathVariable String id, @RequestBody Veterinario obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}