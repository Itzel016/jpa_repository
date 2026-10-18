package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasJPARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JpaEmpleosApplication implements CommandLineRunner {

    private final CategoriasJPARepository categoriasJPARepo;

    public JpaEmpleosApplication(
            CategoriasJPARepository categoriasJPARepo) {
        this.categoriasJPARepo = categoriasJPARepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaEmpleosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        borrarTodasEnBloque();
        System.out.println("Todas las categorías fueron eliminadas.");
        buscarTodasJPA();
    }

    /**
     * Método deleteAllInBatch - Interfaz JpaRepository.
     * Usar con precaución: elimina todos los registros.
     */
    private void borrarTodasEnBloque() {
        categoriasJPARepo.deleteAllInBatch();
    }

    /** Método findAll - Interfaz JpaRepository */
    private void buscarTodasJPA() {
        List<Categoria> categorias = categoriasJPARepo.findAll();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorías registradas.");
            return;
        }

        for (Categoria categoria : categorias) {
            System.out.println(
                    categoria.getId() + " " + categoria.getNombre());
        }
    }
}
