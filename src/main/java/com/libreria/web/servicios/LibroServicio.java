package com.libreria.web.servicios;

import com.libreria.web.entidades.Autor;
import com.libreria.web.entidades.Editorial;
import com.libreria.web.entidades.Libro;
import com.libreria.web.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio lr;
    @Autowired
    private AutorServicio as;
    @Autowired
    private EditorialServicio es;
    
    @Transactional
    public void registrarLibro(Long isbn, String titulo, Integer anio,
            Integer ejemplares, Integer ejemplaresPrestados,
            String idAutor, String idEditorial)throws Exception{
        Libro libro = new Libro();
        Autor autor = as.buscarAutorPorId(idAutor);
        Editorial editorial = es.buscarEditorialPorId(idEditorial);
        validaciones(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes((libro.getEjemplares())-(libro.getEjemplaresPrestados()));
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        lr.save(libro);
    }
    
    @Transactional
    public void modificar(String id, Long isbn, String titulo, Integer anio,
            Integer ejemplares, Integer ejemplaresPrestados,
            String idAutor, String idEditorial) throws Exception{
       
        Libro libro = lr.buscarPorId(id);
        Autor autor = as.buscarAutorPorId(idAutor);
        Editorial editorial = es.buscarEditorialPorId(idEditorial);
        
        if(libro != null){
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes((libro.getEjemplares())-(libro.getEjemplaresPrestados()));
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            lr.save(libro);
        }else{
            throw new Exception("No existe una editorial con el valor solicitado");
        }
    }
    
    public List<Libro> listarLibros(){
        return lr.findAll();
    }
    
    public void baja(String id){
        Libro libro = buscarLibroPorId(id);
        libro.setAlta(Boolean.FALSE);
        lr.save(libro);
    }
    
    public void alta(String id){
        Libro libro = buscarLibroPorId(id);
        libro.setAlta(Boolean.TRUE);
        lr.save(libro);
    }
    
    public Libro buscarLibroPorId(String id){
        return lr.buscarPorId(id);
    }
    
    public void validaciones(Long isbn, String titulo, Integer anio,
            Integer ejemplares, Integer ejemplaresPrestados,
            Autor autor, Editorial editorial)throws Exception{
        if(isbn == null){
            throw new Exception("El isbn no puede ser nulo");
        }
        if(titulo == null || titulo.isEmpty()){
            throw new Exception("El titulo es nulo o está vacío");
        }
        if(anio == null){
            throw new Exception("El año es nulo");
        }
        if(ejemplares == null || ejemplares < 0){
           throw new Exception("La cantidad de ejemplares es nula o menor a 0"); 
        }
        if(ejemplaresPrestados == null || ejemplaresPrestados < 0 || ejemplaresPrestados > ejemplares){
            throw new Exception("La cantidad de ejemplares prestados es inválida");
        }
        as.validaciones(autor.getNombre());
        es.validaciones(editorial.getNombre());
    }

}
