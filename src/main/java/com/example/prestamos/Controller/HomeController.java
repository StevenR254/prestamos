package com.example.prestamos.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    
    @GetMapping("inicio")
    public String inicio(){
        return "home/inicio";
    }

    
}
