
package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Vacante;
import com.example.jpa_empleos.repository.VacantesRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JpaEmpleosApplication implements CommandLineRunner {

    private final VacantesRepository vacantesRepo;

    // Inyección del repositorio
    public JpaEmpleosApplication(VacantesRepository vacantesRepo) {
        this.vacantesRepo = vacantesRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaEmpleosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        buscarVacantes();
    }

    /**
     * Recuperar todas las vacantes y sus categorías.
     */
    private void buscarVacantes() {
        List<Vacante> vacantes = vacantesRepo.findAll();

        if (vacantes.isEmpty()) {
            System.out.println("No hay vacantes registradas.");
            return;
        }

        System.out.println("===== VACANTES Y SUS CATEGORÍAS =====");

        for (Vacante vacante : vacantes) {
            String nombreCategoria =
                    vacante.getCategoria() != null
                            ? vacante.getCategoria().getNombre()
                            : "Sin categoría";

            System.out.println(
                    vacante.getId() + ". "
                    + vacante.getNombre()
                    + " -> " + nombreCategoria
            );
        }
    }
}
