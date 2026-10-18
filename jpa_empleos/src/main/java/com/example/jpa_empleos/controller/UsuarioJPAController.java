package com.example.jpa_empleos.controller;


import com.example.jpa_empleos.models.Perfil;
import com.example.jpa_empleos.models.Usuario;
import com.example.jpa_empleos.repository.PerfilesRepository;
import com.example.jpa_empleos.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/jpa-usuario")
public class UsuarioJPAController {

    private final UsuarioRepository usuarioRepo;
    private final PerfilesRepository perfilesRepo;

    // Inyección de repositorios
    public UsuarioJPAController(
            UsuarioRepository usuarioRepo,
            PerfilesRepository perfilesRepo) {
        this.usuarioRepo = usuarioRepo;
        this.perfilesRepo = perfilesRepo;
    }

    // 1. Buscar un usuario por ID y mostrar sus perfiles
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable Integer id) {

        Optional<Usuario> usuarioOptional = usuarioRepo.findById(id);

        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado.");
    }

    // 2. Crear un usuario con perfiles existentes
    @PostMapping
    public ResponseEntity<?> crearUsuario(
            @RequestBody Usuario usuario) {

        if (usuario.getPerfiles() == null
                || usuario.getPerfiles().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Debes asignar al menos un perfil.");
        }

        Set<Perfil> perfilesExistentes = new HashSet<>();

        // Recuperar los perfiles reales desde la base de datos
        for (Perfil perfilRecibido : usuario.getPerfiles()) {

            if (perfilRecibido.getId() == null) {
                return ResponseEntity.badRequest()
                        .body("Cada perfil debe incluir su ID.");
            }

            Optional<Perfil> perfilOptional =
                    perfilesRepo.findById(perfilRecibido.getId());

            if (perfilOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe el perfil con ID "
                                + perfilRecibido.getId());
            }

            perfilesExistentes.add(perfilOptional.get());
        }

        // Asegurar que se inserte un usuario nuevo
        usuario.setId(null);
        usuario.setPerfiles(perfilesExistentes);

        Usuario guardado = usuarioRepo.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(guardado);
    }

    // 3. Crear varios perfiles
    @PostMapping("/perfiles")
    public ResponseEntity<?> crearPerfiles(
            @RequestBody List<Perfil> perfiles) {

        if (perfiles == null || perfiles.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Debes enviar al menos un perfil.");
        }

        // Crear perfiles nuevos, sin reutilizar IDs recibidos
        for (Perfil perfil : perfiles) {
            if (perfil.getPerfil() == null
                    || perfil.getPerfil().isBlank()) {
                return ResponseEntity.badRequest()
                        .body("Cada perfil debe tener un nombre.");
            }

            perfil.setId(null);
        }

        List<Perfil> guardados =
                perfilesRepo.saveAll(new ArrayList<>(perfiles));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(guardados);
    }
}
