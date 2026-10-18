package com.example.jpa_empleos;


import com.example.jpa_empleos.models.Perfil;
import com.example.jpa_empleos.repository.PerfilesRepository;
import com.example.jpa_empleos.repository.UsuarioRepository;
import com.example.jpa_empleos.repository.VacantesRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class JpaEmpleosApplication implements CommandLineRunner {

    private final VacantesRepository vacantesRepo;
    private final PerfilesRepository perfilesRepo;
    private final UsuarioRepository usuarioRepo;

    // Inyección de los repositorios
    public JpaEmpleosApplication(
            VacantesRepository vacantesRepo,
            PerfilesRepository perfilesRepo,
            UsuarioRepository usuarioRepo) {

        this.vacantesRepo = vacantesRepo;
        this.perfilesRepo = perfilesRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaEmpleosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        crearPerfiles();
    }

    /**
     * Guarda los perfiles en la base de datos.
     */
    private void crearPerfiles() {
        List<Perfil> perfiles = obtenerPerfiles();

        // Evitar insertar perfiles que ya existen
        List<Perfil> existentes = perfilesRepo.findAll();
        List<Perfil> nuevos = new LinkedList<>();

        for (Perfil perfil : perfiles) {
            boolean existe = existentes.stream()
                    .anyMatch(p -> perfil.getPerfil().equals(p.getPerfil()));

            if (!existe) {
                nuevos.add(perfil);
            }
        }

        if (!nuevos.isEmpty()) {
            perfilesRepo.saveAll(nuevos);
            System.out.println("Perfiles guardados correctamente:");
            for (Perfil perfil : nuevos) {
                System.out.println(perfil.getPerfil());
            }
        } else {
            System.out.println("Los tres perfiles ya están registrados.");
        }
    }

    /**
     * Crea la lista de perfiles de la aplicación.
     */
    private List<Perfil> obtenerPerfiles() {
        List<Perfil> perfiles = new LinkedList<>();

        Perfil perfil1 = new Perfil();
        perfil1.setPerfil("SUPERVISOR");

        Perfil perfil2 = new Perfil();
        perfil2.setPerfil("ADMINISTRADOR");

        Perfil perfil3 = new Perfil();
        perfil3.setPerfil("USUARIO");

        perfiles.add(perfil1);
        perfiles.add(perfil2);
        perfiles.add(perfil3);

        return perfiles;
    }
}
