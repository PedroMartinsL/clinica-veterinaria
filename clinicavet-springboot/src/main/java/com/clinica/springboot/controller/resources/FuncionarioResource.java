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

import com.clinica.springboot.controller.services.FuncionarioService;
import com.clinica.springboot.model.entities.Funcionario;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioResource {
	
	@Autowired
	private FuncionarioService service;
		
	@GetMapping 
	public ResponseEntity<List<Funcionario>> findAll() {
		List<Funcionario> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}") 
	public ResponseEntity<Funcionario> findById(@PathVariable String id) {
		Funcionario obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Funcionario> insert(@RequestBody Funcionario obj) {
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
	public ResponseEntity<Funcionario> update(@PathVariable String id, @RequestBody Funcionario obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
