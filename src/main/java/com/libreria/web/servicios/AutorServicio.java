package com.libreria.web.servicios;

import com.libreria.web.entidades.Autor;
import com.libreria.web.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio ar;
    
    @Transactional
    public void registrarAutor(String nombre)throws Exception{
        Autor autor = new Autor();
        autor.setNombre(nombre);
        validaciones(nombre);
        ar.save(autor);
    }
    
    @Transactional
    public void modificar(String id, String nombre) throws Exception{
       
        Autor autor = ar.buscarPorId(id);
        
        if(autor != null){
            autor.setNombre(nombre);
            ar.save(autor);
        }else{
            throw new Exception("No existe un autor con el valor solicitado");
        }
    }
    
    @Transactional
    public void eliminar(String id) throws Exception{
        Autor autor = ar.buscarPorId(id);
        
        if(autor.getNombre() != null){
            ar.delete(autor);
            ar.save(autor);
        }else{
            throw new Exception("No existe un autor con el valor solicitado");
        }
    }
    
    public List<Autor> listarAutores(){
        return ar.findAll();
    }
    
    @Transactional
    public void baja(String id){
        Autor autor = buscarAutorPorId(id);
        autor.setAlta(Boolean.FALSE);
        ar.save(autor);
    }
    
    @Transactional
    public void alta(String id){
        Autor autor = buscarAutorPorId(id);
        autor.setAlta(Boolean.TRUE);
        ar.save(autor);
    }
    
    @Transactional
    public Autor buscarAutorPorNombre(String nombre){
        return ar.buscarPorNombre(nombre);
    }
    
    @Transactional
    public Autor buscarAutorPorId(String id){
        return ar.buscarPorId(id);
    }
    
    public void validaciones(String nombre) throws Exception{
        if(nombre == null || nombre.isEmpty()){
            throw new Exception("El nombre ingresado es nulo o vacío");
        }
//        if(nombre.equals(buscarAutorPorNombre(nombre).getNombre())){
//            throw new Exception("El nombre del autor no puede repetirse");
//        }
    }
}
