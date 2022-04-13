package com.libreria.web.controladores;

import com.libreria.web.entidades.Autor;
import com.libreria.web.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorController {
    
    @Autowired
    private AutorServicio as;
    
    @GetMapping("/crear")
    public String formulario(){
        return "crearAutor";
    }
    
    @PostMapping("/crear")
    public String guardar(ModelMap modelo, @RequestParam String nombre){
        try{
            as.registrarAutor(nombre);
            modelo.put("exito", "Registro exitoso");
            return "crearAutor";
        }catch(Exception e){
            modelo.put("error", "Falto algun dato");
            return "crearAutor";
        }
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Autor> autores = as.listarAutores();
        
        modelo.addAttribute("autores", autores);
        
        return "listaAutores";
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        try{
            as.baja(id);
            return "redirect:/autor/lista";
        }catch(Exception e){
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id){
        try{
            as.alta(id);
            return "redirect:/autor/lista";
        }catch(Exception e){
            return "redirect:/";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        
        modelo.put("autor", as.buscarAutorPorId(id));
        
        return "modificarAutor";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombre){
        try {
            as.modificar(id, nombre);
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "modificarAutor";
        }
    }
}
