
package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Perfil;
import com.example.jpa_empleos.models.Usuario;

import com.example.jpa_empleos.repository.CategoriasJPARepository;
import com.example.jpa_empleos.repository.VacantesRepository;
import com.example.jpa_empleos.repository.PerfilesRepository;
import com.example.jpa_empleos.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class JpaEmpleosApplication implements CommandLineRunner {

    private final CategoriasJPARepository categoriasJPARepo;
    private final VacantesRepository vacantesRepo;
    private final PerfilesRepository perfilesRepo;
    private final UsuarioRepository usuarioRepo;

    public JpaEmpleosApplication(
            CategoriasJPARepository categoriasJPARepo,
            VacantesRepository vacantesRepo,
            PerfilesRepository perfilesRepo,
            UsuarioRepository usuarioRepo) {

        this.categoriasJPARepo = categoriasJPARepo;
        this.vacantesRepo = vacantesRepo;
        this.perfilesRepo = perfilesRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaEmpleosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        crearUsuarioConPerfiles();
    }

    /**
     * Crear usuario con los perfiles ADMINISTRADOR y USUARIO.
     */
    private void crearUsuarioConPerfiles() {

        // Evitar duplicar el usuario si se reinicia la aplicación.
        boolean existeUsuario = usuarioRepo.findAll()
                .stream()
                .anyMatch(u -> "joseg".equalsIgnoreCase(u.getUsername()));

        if (existeUsuario) {
            System.out.println("El usuario joseg ya está registrado.");
            return;
        }

        // Recuperar los perfiles existentes por su nombre.
        List<Perfil> perfiles = perfilesRepo.findAll();

        Perfil administrador = perfiles.stream()
                .filter(p -> "ADMINISTRADOR".equalsIgnoreCase(p.getPerfil()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No existe el perfil ADMINISTRADOR. "
                        + "Regístralo primero en la base de datos."
                ));

        Perfil usuarioPerfil = perfiles.stream()
                .filter(p -> "USUARIO".equalsIgnoreCase(p.getPerfil()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No existe el perfil USUARIO. "
                        + "Regístralo primero en la base de datos."
                ));

        // Crear el usuario.
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("José García");
        nuevoUsuario.setEmail("jose@gmail.com");
        nuevoUsuario.setUsername("joseg");
        nuevoUsuario.setPassword("12345");
        nuevoUsuario.setEstatus(1);
        nuevoUsuario.setFechaRegistro(LocalDate.now());

        // Asignar los dos perfiles.
        Set<Perfil> perfilesUsuario = new HashSet<>();
        perfilesUsuario.add(administrador);
        perfilesUsuario.add(usuarioPerfil);

        nuevoUsuario.setPerfiles(perfilesUsuario);

        // Guardar el usuario y su relación con los perfiles.
        Usuario guardado = usuarioRepo.save(nuevoUsuario);

        System.out.println("Usuario creado correctamente.");
        System.out.println("ID: " + guardado.getId());
        System.out.println("Nombre: " + guardado.getNombre());
        System.out.println("Username: " + guardado.getUsername());
        System.out.println("Perfiles asignados:");

        for (Perfil perfil : guardado.getPerfiles()) {
            System.out.println("- " + perfil.getPerfil());
        }
    }
}
