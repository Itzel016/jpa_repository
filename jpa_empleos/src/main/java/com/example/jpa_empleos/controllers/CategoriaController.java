package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriasRepository categoriasRepo;

    // Inyección del repositorio
    public CategoriaController(CategoriasRepository categoriasRepo) {
        this.categoriasRepo = categoriasRepo;
    }

    // 1. Consultar todas las categorías
    @GetMapping
    public ResponseEntity<Iterable<Categoria>> listarTodas() {
        return ResponseEntity.ok(categoriasRepo.findAll());
    }

    // 2. Buscar una categoría por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(
            @PathVariable Integer id) {

        Optional<Categoria> categoria =
                categoriasRepo.findById(id);

        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        }

        return ResponseEntity.notFound().build();
    }

    // 3. Crear una nueva categoría
    @PostMapping
    public ResponseEntity<Categoria> crear(
            @RequestBody Categoria categoria) {

        // El ID se genera automáticamente
        categoria.setId(null);

        Categoria nuevaCategoria =
                categoriasRepo.save(categoria);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevaCategoria);
    }

    // 4. Actualizar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(
            @PathVariable Integer id,
            @RequestBody Categoria datos) {

        Optional<Categoria> encontrada =
                categoriasRepo.findById(id);

        if (encontrada.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Categoria categoria = encontrada.get();
        categoria.setNombre(datos.getNombre());
        categoria.setDescripcion(datos.getDescripcion());

        Categoria actualizada =
                categoriasRepo.save(categoria);

        return ResponseEntity.ok(actualizada);
    }

    // 5. Eliminar una categoría por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {

        Optional<Categoria> encontrada =
                categoriasRepo.findById(id);

        if (encontrada.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categoriasRepo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
