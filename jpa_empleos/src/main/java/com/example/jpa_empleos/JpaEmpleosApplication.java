
package com.example.jpa_empleos;

import com.example.jpa_empleos.models.EstatusVacante;
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
    buscarVacantesPorDestacadoEstatus();
}

    /**
     * Query Method: Buscar vacantes por estatus.
     */
    private void buscarVacantesPorEstatus() {

        List<Vacante> vacantesAprobadas =
                vacantesRepo.findByEstatus(EstatusVacante.Aprobada);

        System.out.println(
                "Registros encontrados: " + vacantesAprobadas.size()
        );

        for (Vacante vacante : vacantesAprobadas) {
            System.out.println(
                    vacante.getId() + ": "
                    + vacante.getNombre() + " - "
                    + vacante.getEstatus()
            );
        }
    }
	
/**
 * Query Method: Buscar vacantes por destacado y estatus,
 * ordenadas por ID descendente.
 */
private void buscarVacantesPorDestacadoEstatus() {

    List<Vacante> vacantesAprobadasYDestacadas =
            vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(
                    1,
                    EstatusVacante.Aprobada
            );

    System.out.println(
            "Registros encontrados: "
                    + vacantesAprobadasYDestacadas.size()
    );

    for (Vacante vacante : vacantesAprobadasYDestacadas) {
        System.out.println(
                "ID: " + vacante.getId()
                + " | Nombre: " + vacante.getNombre()
                + " | Estatus: " + vacante.getEstatus()
                + " | Destacada: " + vacante.getDestacado()
        );
    }
}

}
