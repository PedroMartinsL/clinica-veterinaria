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

import com.clinica.springboot.model.entities.Consulta;
import com.clinica.springboot.services.ConsultaService;

@RestController
@RequestMapping(value = "/consultas")
public class ConsultaResource {

	@Autowired
	private ConsultaService service;

	@GetMapping
	public ResponseEntity<List<Consulta>> findAll() {
		List<Consulta> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Consulta> findById(@PathVariable Long id) {
		Consulta obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Consulta> insert(@RequestBody Consulta obj) {
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
	public ResponseEntity<Consulta> update(@PathVariable Long id, @RequestBody Consulta obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Consulta> partialUpdate(@PathVariable Long id, @RequestBody Consulta obj) {
	    Consulta updatedPet = service.updatePatch(id, obj);
	    return ResponseEntity.ok().body(updatedPet);
	}
}
