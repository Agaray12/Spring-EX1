package com.libreria.web.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") //localhost:8080/
public class MainController {
    
    @GetMapping("/")
    public String home(){
        return "index";
    }
}
