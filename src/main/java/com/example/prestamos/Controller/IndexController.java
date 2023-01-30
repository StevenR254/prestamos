package com.example.prestamos.Controller;


import com.example.prestamos.entities.TipoDocumento;
import com.example.prestamos.entities.User;
import com.example.prestamos.services.Response;
import com.example.prestamos.services.UserService;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("index")
public class IndexController {
    
    /*Este es el manejo de las capas*/
    /*Quien atiende al cliente - R - Los controladores */
    /*Quien maneja la logica de negocio - R Los Servicios*/
    /*Quien se comunica con la base de datos - R los repositorios*/
    
    private UserService userService;
    
    public IndexController(UserService service){
        this.userService = service;
    }
    
    
    @RequestMapping("getusuarios")
    public ArrayList<User> getUsuarios(){
        return this.userService.selectAll();
    }
    
    @RequestMapping("getusuario/{id}")
    public User getUsuario(@PathVariable int id){
        return this.userService.searchUser(id);
    }
    
    @PostMapping("createUsuario")
    //@Request Body, sirve para entregar respuentas Json
    public Response createUsuarios(@RequestBody User user){
        return this.userService.createUser(user);
    }
    
    @DeleteMapping("borrarusuario/{id}")
    public Response deleteUser(@PathVariable int id){
        return this.userService.deleteUser(id);
    }
    
    @PutMapping("update")
    public Response updateUser(@RequestBody User user){
        return this.userService.updateUser(user);
    }
    
    @PostMapping("auth")
    public Response auth(@RequestBody User user){
        return this.userService.loginUser(user);
    }
}
