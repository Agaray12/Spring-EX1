package com.libreria.web.servicios;

import com.libreria.web.entidades.Editorial;
import com.libreria.web.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio er;
    
    @Transactional
    public void registrarEditorial(String nombre)throws Exception{
        Editorial editorial = new Editorial();
        validaciones(nombre);
        editorial.setNombre(nombre);
        er.save(editorial);
    }
    
    @Transactional
    public void modificar(String id, String nombre) throws Exception{
       
        Editorial editorial = er.buscarPorId(id);
        
        if(editorial != null){
            editorial.setNombre(nombre);
            er.save(editorial);
        }else{
            throw new Exception("No existe una editorial con el valor solicitado");
        }
    }
    
    @Transactional
    public void eliminar(String id) throws Exception{
        Editorial editorial = er.buscarPorId(id);
        
        if(editorial.getNombre() != null){
            er.delete(editorial);
            er.save(editorial);
        }else{
            throw new Exception("No existe una editorial con el valor solicitado");
        }
    }
    
    public List<Editorial> listarEditoriales(){
        return er.findAll();
    }
    
    public void baja(String id){
        Editorial editorial = buscarEditorialPorId(id);
        editorial.setAlta(Boolean.FALSE);
        er.save(editorial);
    }
    
    public void alta(String id){
        Editorial editorial = buscarEditorialPorId(id);
        editorial.setAlta(Boolean.TRUE);
        er.save(editorial);
    }
    
    public Editorial buscarEditorialPorNombre(String nombre){
        return er.buscarPorNombre(nombre);
    }
    
    public Editorial buscarEditorialPorId(String id){
        return er.buscarPorId(id);
    }
    
    public void validaciones(String nombreEditorial) throws Exception{
        if(nombreEditorial == null || nombreEditorial.isEmpty()){
            throw new Exception("El nombre ingresado es nulo o vacío");
        }
//        if(editorial.getNombre().equals(buscarEditorialPorNombre(editorial.getNombre()).getNombre())){
//            throw new Exception("El nombre de la editorial no puede repetirse");
//        }
    }
}
