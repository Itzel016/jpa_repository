
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
    buscarVacantesVariosEstatus();
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

/**
 * Query Method: Buscar vacantes por rango de salario (Between).
 */
private void buscarVacantesSalario() {

    // Primera consulta: salarios entre 7000 y 14000
    List<Vacante> vacantesSalariosEntre7000y14000 =
            vacantesRepo.findBySalarioBetween(7000.0, 14000.0);

    System.out.println("===== VACANTES POR RANGO DE SALARIO =====");
    System.out.println(
            "Registros encontrados: "
                    + vacantesSalariosEntre7000y14000.size()
    );

    for (Vacante vacante : vacantesSalariosEntre7000y14000) {
        System.out.println(
                vacante.getId() + ": "
                + vacante.getNombre()
                + " - $" + vacante.getSalario()
        );
    }

    System.out.println("-".repeat(100));

    // Segunda consulta: mismo rango, ordenado de mayor a menor
    List<Vacante> vacantesSalariosEntre7000y14000Desc =
            vacantesRepo.findBySalarioBetweenOrderBySalarioDesc(
                    7000.0, 14000.0
            );

    System.out.println("===== VACANTES ORDENADAS POR SALARIO DESCENDENTE =====");
    System.out.println(
            "Registros encontrados: "
                    + vacantesSalariosEntre7000y14000Desc.size()
    );

    for (Vacante vacante : vacantesSalariosEntre7000y14000Desc) {
        System.out.println(
                vacante.getId() + ": "
                + vacante.getNombre()
                + " - $" + vacante.getSalario()
        );
    }
}
/**
 * Query Method: Buscar vacantes por varios estatus (In).
 */
private void buscarVacantesVariosEstatus() {

    EstatusVacante[] listaEstatus = new EstatusVacante[]{
            EstatusVacante.Eliminada,
            EstatusVacante.Creada
    };

    List<Vacante> vacantesVariosEstatus =
            vacantesRepo.findByEstatusIn(listaEstatus);

    System.out.println(
            "Registros encontrados: " + vacantesVariosEstatus.size()
    );

    for (Vacante vacante : vacantesVariosEstatus) {
        System.out.println(
                vacante.getId() + ": "
                + vacante.getNombre() + " - "
                + vacante.getEstatus()
        );
    }
}

}
