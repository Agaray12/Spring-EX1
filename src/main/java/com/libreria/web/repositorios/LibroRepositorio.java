package com.libreria.web.repositorios;

import com.libreria.web.entidades.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long>{
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarLibroPorTitulo(@Param("titulo") String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :autor")
    public List<Libro> buscarLibroPorAutor(@Param("autor") String autor);
    
    @Query("SELECT l FROM Libro l WHERE l.editorial.nombre = :editorial")
    public List<Libro> buscarLibroPorEditorial(@Param("editorial") String editorial);
    
    @Query("SELECT l FROM Libro l WHERE l.id = :id")
    public Libro buscarPorId(@Param("id") String id);
        
}
