package com.libreria.web.controladores;

import com.libreria.web.entidades.Editorial;
import com.libreria.web.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialController {
    
    @Autowired
    private EditorialServicio es;
    
    @GetMapping("/crear")
    public String formulario(){
        return "crearEditorial";
    }
    
    @PostMapping("/crear")
    public String guardar(ModelMap modelo, @RequestParam String nombre){
        try{
            es.registrarEditorial(nombre);
            modelo.put("exito", "Registro exitoso");
            return "crearEditorial";
        }catch(Exception e){
            modelo.put("error", "Falto algun dato");
            return "crearEditorial";
        }
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Editorial> editoriales = es.listarEditoriales();
        
        modelo.addAttribute("editoriales", editoriales);
        
        return "listaEditoriales";
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        try{
            es.baja(id);
            return "redirect:/editorial/lista";
        }catch(Exception e){
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id){
        try{
            es.alta(id);
            return "redirect:/editorial/lista";
        }catch(Exception e){
            return "redirect:/";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        
        modelo.put("editorial", es.buscarEditorialPorId(id));
        
        return "modificarEditorial";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombre){
        
        try {
            es.modificar(id, nombre);
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "modificarEditorial";
        }
        
        
    }
    
}
