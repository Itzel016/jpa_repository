
package com.example.jpa_empleos.controller;

import com.example.jpa_empleos.models.EstatusVacante;
import com.example.jpa_empleos.models.Vacante;
import com.example.jpa_empleos.repository.VacantesRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/jpa-vacante")
public class VacantesQueryController {

    private final VacantesRepository vacantesRepo;

    // Inyección del repositorio
    public VacantesQueryController(VacantesRepository vacantesRepo) {
        this.vacantesRepo = vacantesRepo;
    }

    // 1. Buscar vacantes por varios estatus
    @GetMapping("/estatus-varios")
    public ResponseEntity<?> buscarPorVariosEstatus(
            @RequestParam String estatus) {

        try {
            EstatusVacante[] estados = Arrays.stream(estatus.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(EstatusVacante::valueOf)
                    .toArray(EstatusVacante[]::new);

            if (estados.length == 0) {
                return ResponseEntity.badRequest()
                        .body("Debes indicar al menos un estatus.");
            }

            List<Vacante> vacantes =
                    vacantesRepo.findByEstatusIn(estados);

            return ResponseEntity.ok(vacantes);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Estatus inválido. Revisa los valores de EstatusVacante.");
        }
    }

    // 2. Buscar vacantes por rango de salario
    @GetMapping("/salario")
    public ResponseEntity<?> buscarPorSalario(
            @RequestParam Double minimo,
            @RequestParam Double maximo) {

        if (minimo > maximo) {
            return ResponseEntity.badRequest()
                    .body("El salario mínimo no puede ser mayor que el máximo.");
        }

        List<Vacante> vacantes =
                vacantesRepo.findBySalarioBetween(minimo, maximo);

        return ResponseEntity.ok(vacantes);
    }

    // 3. Buscar vacantes por rango de salario, descendente
    @GetMapping("/salario/desc")
    public ResponseEntity<?> buscarPorSalarioDesc(
            @RequestParam Double minimo,
            @RequestParam Double maximo) {

        if (minimo > maximo) {
            return ResponseEntity.badRequest()
                    .body("El salario mínimo no puede ser mayor que el máximo.");
        }

        List<Vacante> vacantes =
                vacantesRepo.findBySalarioBetweenOrderBySalarioDesc(
                        minimo, maximo);

        return ResponseEntity.ok(vacantes);
    }

    // 4. Buscar vacantes destacadas por estatus, ordenadas por ID descendente
    @GetMapping("/destacadas")
    public List<Vacante> buscarDestacadas(
            @RequestParam int destacado,
            @RequestParam EstatusVacante estatus) {

        return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(
                destacado, estatus);
    }

    // 5. Buscar vacantes por un estatus específico
    @GetMapping("/estatus")
    public List<Vacante> buscarPorEstatus(
            @RequestParam EstatusVacante estatus) {

        return vacantesRepo.findByEstatus(estatus);
    }
}
