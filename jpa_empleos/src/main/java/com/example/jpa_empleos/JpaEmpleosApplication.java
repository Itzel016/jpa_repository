
package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Vacante;
import com.example.jpa_empleos.repository.VacantesRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.models.Vacante;
import com.example.jpa_empleos.models.EstatusVacante;
import com.example.jpa_empleos.repository.VacantesRepository;

import java.util.Date;
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
    guardarVacante();
}

/**
 * Guardar una nueva vacante.
 */
private void guardarVacante() {
    Vacante vacante = new Vacante();

    vacante.setNombre("Desarrollador Java PRO");
    vacante.setDescripcion(
            "Se busca desarrollador con experiencia en Spring Boot y JPA."
    );
    vacante.setFecha(new Date());
    vacante.setSalario(25000.0);
    vacante.setEstatus(EstatusVacante.Creada);
    vacante.setDestacado(1);
    vacante.setImagen("logo_empresa.png");
    vacante.setDetalles(
            "Trabajo remoto con horario flexible. " +
            "Beneficios y capacitación incluidos."
    );

    // Asociar una categoría que ya exista en la base de datos.
    Categoria categoria = new Categoria();
    categoria.setId(28);

    vacante.setCategoria(categoria);

    Vacante guardada = vacantesRepo.save(vacante);

    System.out.println("Vacante guardada correctamente.");
    System.out.println("ID: " + guardada.getId());
    System.out.println("Nombre: " + guardada.getNombre());
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
