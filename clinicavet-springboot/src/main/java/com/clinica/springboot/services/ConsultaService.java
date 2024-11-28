package com.clinica.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinica.springboot.controller.exceptions.ResourceNotFoundException;
import com.clinica.springboot.model.entities.AuxVeterinario;
import com.clinica.springboot.model.entities.Consulta;
import com.clinica.springboot.model.entities.Veterinario;
import com.clinica.springboot.repositories.AuxVeterinarioRepository;
import com.clinica.springboot.repositories.ConsultaRepository;
import com.clinica.springboot.repositories.VeterinarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private AuxVeterinarioRepository auxRepository;

    @Autowired
    private VeterinarioRepository vetRepository;

    public List<Consulta> findAll() {
        return repository.findAll();
    }

    public Consulta findById(Long id) {
        Optional<Consulta> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public Consulta insert(Consulta obj) {
        if (obj.getVeterinario() != null) {
            Veterinario veterinario = vetRepository.findById(obj.getVeterinario().getCpf())
                    .orElseThrow(() -> new IllegalArgumentException("Veterinario não encontrado"));
            veterinario.getConsultas().add(obj);
            vetRepository.save(veterinario);
            if (obj.getAuxVeterinario() != null) {
                AuxVeterinario aux = auxRepository.findById(obj.getAuxVeterinario().getCpf())
                        .orElseThrow(() -> new IllegalArgumentException("Auxiliar veterinario não encontrado"));
                aux.getConsultas().add(obj);
                auxRepository.save(aux);
            }
        }
        return repository.save(obj);
    }

    @Transactional
    public Consulta update(Long id, Consulta obj) {
        try {
            Consulta entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Consulta entity, Consulta obj) {
        entity.setConsultaStatus(obj.getConsultaStatus());
        entity.setDoenca(obj.getDoenca());
        entity.setData(obj.getData());
    }


    @Transactional
    public Consulta updatePatch(Long id, Consulta obj) {
        try {
            Consulta entity = repository.getReferenceById(id);
            partialUpdateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void partialUpdateData(Consulta entity, Consulta obj) {
        if (obj.getConsultaStatus() != null) {
            entity.setConsultaStatus(obj.getConsultaStatus());
        }
        if (obj.getDoenca() != null) {
            entity.setDoenca(obj.getDoenca());
        }
        if (obj.getData() != null) {
            entity.setData(obj.getData());
        }
    }
}
