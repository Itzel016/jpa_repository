package com.example.jpa_empleos;
import org.springframework.data.domain.Sort;
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
    buscarTodosOrdenados();
}

    /**
 * Método findAll con ordenamiento descendente por nombre.
 */
private void buscarTodosOrdenados() {
    List<Categoria> categorias =
            categoriasJPARepo.findAll(Sort.by("nombre").descending());

    if (categorias.isEmpty()) {
        System.out.println("No hay categorías registradas.");
        return;
    }

    System.out.println("=== CATEGORÍAS ORDENADAS POR NOMBRE (Z-A) ===");

    for (Categoria categoria : categorias) {
        System.out.println(
                categoria.getId() + " " + categoria.getNombre());
    }
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
