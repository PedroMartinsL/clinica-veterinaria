package com.clinica.springboot.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.clinica.springboot.model.entities.Administrador;
import com.clinica.springboot.model.entities.AuxVeterinario;
import com.clinica.springboot.model.entities.Consulta;
import com.clinica.springboot.model.entities.Funcionario;
import com.clinica.springboot.model.entities.Medicamento;
import com.clinica.springboot.model.entities.Pet;
import com.clinica.springboot.model.entities.Veterinario;
import com.clinica.springboot.model.entities.enums.ConsultaStatus;
import com.clinica.springboot.repositories.AdministradorRepository;
import com.clinica.springboot.repositories.AuxVeterinarioRepository;
import com.clinica.springboot.repositories.ConsultaRepository;
import com.clinica.springboot.repositories.FuncionarioRepository;
import com.clinica.springboot.repositories.MedicamentoRepository;
import com.clinica.springboot.repositories.PetRepository;
import com.clinica.springboot.repositories.VeterinarioRepository;

@Configuration
@Profile("test")
public class ClinicaConfig implements CommandLineRunner {
	@Autowired
	private AdministradorRepository admRepository;

	@Autowired
	private FuncionarioRepository funcRepository;

	@Autowired
	private MedicamentoRepository medRepository;

	@Autowired
	private AuxVeterinarioRepository auxRepository;

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private VeterinarioRepository vetRepository;

	@Autowired
	private PetRepository petRepository;

	@Override
	public void run(String... args) throws Exception {

		Administrador adm = new Administrador("Rubens Paiva", "00998786521", "filme24");
		Administrador adm2 = new Administrador("Fausto", "00923785529", "margarida4");

		Funcionario func = new Funcionario("Maria Renata", "01112386521", "galaxia2");
		Funcionario func2 = new Funcionario("João Mario", "01712300921", "andromeda1");

		AuxVeterinario aux = new AuxVeterinario("Ana Beatriz", "01712312322", "biolove1");
		AuxVeterinario aux2 = new AuxVeterinario("Alecrim Francisco", "03412310022", "gatinhofofo6");

		Veterinario vet = new Veterinario("Tayssa Lira", "14382202124", "coalas12");
		Veterinario vet2 = new Veterinario("Rapunzel Torres", "07789002124", "pascoal");

		admRepository.saveAll(Arrays.asList(adm, adm2));
		funcRepository.saveAll(Arrays.asList(func, func2));
		auxRepository.saveAll(Arrays.asList(aux, aux2));
		vetRepository.saveAll(Arrays.asList(vet, vet2));

		Medicamento dipirona = new Medicamento(null, "Dipirona", "Cimed", 25.0, 15.50,
				Instant.parse("2025-06-30T09:30:20Z"), true);
		Medicamento vonal = new Medicamento(null, "Vonal", "Avert", 5.0, 12.00, Instant.parse("2027-01-02T19:53:00Z"),
				true);
		Medicamento cefalexina = new Medicamento(null, "Cefalexina", "Cimed", 500.0, 40.00,
				Instant.parse("2024-08-17T20:30:00Z"), true);
		Medicamento probiotico = new Medicamento(null, "Probiótico", "Biofarma", 25.0, 8.20,
				Instant.parse("2025-01-15T10:23:00Z"), true);
		Medicamento creatina = new Medicamento(null, "Creatina Equina", "VetUP", 25.0, 80.00,
				Instant.parse("2026-10-21T18:10:00Z"), true);

		medRepository.saveAll(Arrays.asList(dipirona, vonal, cefalexina, probiotico, creatina));

		Pet pet = new Pet(null, "Coelho", "08912425602", "French Lop", 1);
		Pet pet1 = new Pet(null, "Cachorro", "02198765432", "Labrador Retriever", 3);
		Pet pet2 = new Pet(null, "Gato", "09987654321", "Siamês", 2);
		Pet pet3 = new Pet(null, "Pássaro", "01234567890", "Calopsita", 4);
		Pet pet4 = new Pet(null, "Hamster", "07865432109", "Sírio", 1);
		Pet pet5 = new Pet(null, "Tartaruga", "05432198765", "Tigre d'Água", 10);

		petRepository.saveAll(Arrays.asList(pet, pet1, pet2, pet3, pet4, pet5));

		Consulta consulta = new Consulta(null, Instant.parse("2024-11-20T19:15:07Z"), "Febre", vet,
				ConsultaStatus.CONCLUIDA, pet);
		Consulta consulta1 = new Consulta(null, Instant.parse("2024-12-11T15:45:00Z"), "Vômito", vet2,
				ConsultaStatus.AGENDADO, pet1);
		Consulta consulta2 = new Consulta(null, Instant.parse("2024-11-23T10:30:00Z"), "Infecção na pele", vet,
				ConsultaStatus.DIVIDA, pet2);
		Consulta consulta3 = new Consulta(null, Instant.parse("2024-11-25T16:00:00Z"), "Diarreia", vet2,
				ConsultaStatus.CANCELADA, pet3);
		Consulta consulta4 = new Consulta(null, Instant.parse("2024-12-01T09:15:00Z"), "Dificuldade para andar", vet,
				ConsultaStatus.ANDAMENTO, pet4);
		Consulta consulta5 = new Consulta(null, Instant.parse("2024-12-05T11:00:00Z"), "Check-up geral", vet2,
				ConsultaStatus.CONCLUIDA, pet5);
		
		consultaRepository.saveAll(Arrays.asList(consulta, consulta1, consulta2, consulta3, consulta4, consulta5));
	}

}
