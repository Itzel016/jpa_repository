package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasJPARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class JpaEmpleosApplication implements CommandLineRunner {
	private final CategoriasJPARepository categoriasJPARepo;

	public JpaEmpleosApplication(CategoriasJPARepository categoriasJPARepo) {
		this.categoriasJPARepo = categoriasJPARepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(JpaEmpleosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		buscarTodasJPA();
	}

	/** * Método findAll - Interfaz JPARepository */
	private void buscarTodasJPA() {
		List<Categoria> categorias = categoriasJPARepo.findAll();
		for (Categoria categoria : categorias) {
			System.out.println(categoria.getId() + " " + categoria.getNombre());
		}
	}
}