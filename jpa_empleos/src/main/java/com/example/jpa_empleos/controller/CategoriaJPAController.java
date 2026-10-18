package com.example.jpa_empleos.controller;


import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasJPARepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jpa-categorias")
public class CategoriaJPAController {

        private final CategoriasJPARepository categoriasJPARepo;

        // Inyección del repositorio
        public CategoriaJPAController(
                        CategoriasJPARepository categoriasJPARepo) {
                this.categoriasJPARepo = categoriasJPARepo;
        }

        // 1. Obtener todas las categorías
        @GetMapping
        public List<Categoria> obtenerTodas() {
                return categoriasJPARepo.findAll();
        }

        // 2. Obtener categorías ordenadas por nombre descendente
        @GetMapping("/ordenadas")
        public List<Categoria> obtenerOrdenadas() {
                return categoriasJPARepo.findAll(
                                Sort.by("nombre").descending());
        }

        // 3. Obtener categorías con paginación
        @GetMapping("/paginadas")
        public ResponseEntity<?> obtenerPaginadas(
                        @RequestParam(defaultValue = "0") int pagina,
                        @RequestParam(defaultValue = "5") int cantidad) {

                if (pagina < 0 || cantidad <= 0) {
                        return ResponseEntity.badRequest()
                                        .body("La página debe ser >= 0 y la cantidad > 0.");
                }

                Page<Categoria> resultado = categoriasJPARepo.findAll(
                                PageRequest.of(pagina, cantidad));

                return ResponseEntity.ok(resultado);
        }

        // 4. Obtener categorías paginadas y ordenadas por nombre descendente
        @GetMapping("/paginadas/ordenadas")
        public ResponseEntity<?> obtenerPaginadasOrdenadas(
                        @RequestParam(defaultValue = "0") int pagina,
                        @RequestParam(defaultValue = "5") int cantidad) {

                if (pagina < 0 || cantidad <= 0) {
                        return ResponseEntity.badRequest()
                                        .body("La página debe ser >= 0 y la cantidad > 0.");
                }

                Page<Categoria> resultado = categoriasJPARepo.findAll(
                                PageRequest.of(
                                                pagina,
                                                cantidad,
                                                Sort.by("nombre").descending()));

                return ResponseEntity.ok(resultado);
        }

        // 5. Eliminar todas las categorías
        @DeleteMapping("/todas")
        public ResponseEntity<String> eliminarTodas() {
                long total = categoriasJPARepo.count();

                categoriasJPARepo.deleteAllInBatch();

                return ResponseEntity.ok(
                                "Se eliminaron " + total + " categorías.");
        }
}
