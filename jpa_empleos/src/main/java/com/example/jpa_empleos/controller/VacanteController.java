
package com.example.jpa_empleos.controller;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.models.Vacante;
import com.example.jpa_empleos.repository.CategoriasJPARepository;
import com.example.jpa_empleos.repository.VacantesRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vacantes")
public class VacanteController {

    private final VacantesRepository vacantesRepo;
    private final CategoriasJPARepository categoriasRepo;

    public VacanteController(
            VacantesRepository vacantesRepo,
            CategoriasJPARepository categoriasRepo) {
        this.vacantesRepo = vacantesRepo;
        this.categoriasRepo = categoriasRepo;
    }

    // Consultar todas las vacantes
    @GetMapping
    public List<Vacante> buscarVacantes() {
        return vacantesRepo.findAll();
    }

    // Guardar una vacante
    @PostMapping
    public ResponseEntity<?> guardarVacante(
            @RequestBody Vacante vacante) {

        if (vacante.getCategoria() == null
                || vacante.getCategoria().getId() == null) {
            return ResponseEntity.badRequest()
                    .body("Debes proporcionar el ID de una categoría.");
        }

        Optional<Categoria> categoriaEncontrada =
                categoriasRepo.findById(
                        vacante.getCategoria().getId());

        if (categoriaEncontrada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La categoría indicada no existe.");
        }

        // Asociar la categoría completa recuperada de MySQL
        vacante.setCategoria(categoriaEncontrada.get());

        // Asegurar que se inserte un registro nuevo
        vacante.setId(null);

        Vacante guardada = vacantesRepo.save(vacante);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(guardada);
    }
}
