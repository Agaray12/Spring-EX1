package com.libreria.web.controladores;

import com.libreria.web.entidades.Autor;
import com.libreria.web.entidades.Editorial;
import com.libreria.web.entidades.Libro;
import com.libreria.web.servicios.AutorServicio;
import com.libreria.web.servicios.EditorialServicio;
import com.libreria.web.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroServicio ls;
    @Autowired
    private AutorServicio as;
    @Autowired
    private EditorialServicio es;

    @GetMapping("/crear")
    public String formulario(ModelMap modelo) {
        List<Autor> autores = as.listarAutores();
        List<Editorial> editoriales = es.listarEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "crearLibro";
    }

    @PostMapping("/crear")
    public String guardar(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados,
            @RequestParam String autor, @RequestParam String editorial) {
        try {
            ls.registrarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
            modelo.put("exito", "Registro exitoso");
            return "crearLibro";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "crearLibro";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Libro> libros = ls.listarLibros();

        modelo.addAttribute("libros", libros);

        return "listaLibros";
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            ls.baja(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {
        try {
            ls.alta(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        List<Autor> autores = as.listarAutores();
        List<Editorial> editoriales = es.listarEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        
        modelo.put("libro", ls.buscarLibroPorId(id));
        
        return "modificarLibro";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados,
            @RequestParam String autor, @RequestParam String editorial){
        
        try {
            ls.modificar(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "modificarLibro";
        }
        
        
    }
}
