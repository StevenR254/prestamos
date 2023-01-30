package com.example.prestamos.Controller;

import com.example.prestamos.entities.TipoDocumento;
import com.example.prestamos.entities.User;
import com.example.prestamos.services.Response;
import com.example.prestamos.services.TipoDocumentoService;
import com.example.prestamos.services.UserService;
import dto.RegistroDTO;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("login")
public class LoginController {
    
    UserService userService;
    TipoDocumentoService tipoDocumentoService;
    
    public LoginController(UserService userService, TipoDocumentoService tipoDocumentoService){
        this.userService = userService;
        this.tipoDocumentoService = tipoDocumentoService;
    }
    
    @GetMapping("login")
    public String login(){
        return "login/login";
    }
    
    @GetMapping("registro")
    public String registro(Model tiposdocumento){
        // la idea aqui en esta parte es pasarle los tipos de documento a la vista, lo primero es acceder a los tipos de documentos ya creados y compartirlos con Thymeleaf
        ArrayList<TipoDocumento> tiposDocumento = this.tipoDocumentoService.selectAll();
        // Ojo ya usado con esto pasamos la informacion al model de Thymeleaf, funciona a base llave valor
        tiposdocumento.addAttribute("misdocumentos", tiposDocumento);
        
        return "login/registro";
    }
    
    //Este metodo es el que valida el logueo de usuario en base de datos
    @PostMapping("postlogin")
    public RedirectView postlogin(RegistroDTO registroDTO){
        
        User user = new User();
        user.setEmail(registroDTO.getEmail());
        user.setPassword(registroDTO.getPassword());
        
        Response response = this.userService.loginUser(user);
        System.out.println(response.getMessage());
        if(response.getCode() == 200){
            return new RedirectView("/inicio");
        }
        return new RedirectView("/login/error");
    }
    
    @PostMapping("postregistro")
    public RedirectView postRegistro(RegistroDTO registroDTO){
        
        if(!registroDTO.getPassword().equals(registroDTO.getValidarPassword())){
            System.out.println("La contraseña no coincide");
            return new RedirectView("/login/error");
        }
        
        //en este caso mi UserService recibe un parametro de tipo User, al hacer el cambio necesitamos un mapeo
        //tenemos metodos que hacen el mapeo mas facil
        //o la otra es editar el service para que me reciba datos de tipo RegistroDTO
        User user = new User();
        
        user.setApellidos(registroDTO.getApellidos());
        user.setEmail(registroDTO.getEmail());
        user.setNombres(registroDTO.getNombres());
        user.setNumDocumento(registroDTO.getNumDocumento());
        user.setPassword(registroDTO.getPassword());
        user.setTipoDocumento(registroDTO.getTipoDocumento());
        
        Response response = this.userService.createUser(user);
        System.out.println(response.getMessage());
        if(response.getCode() == 200){
            System.out.println(response.getCode());
            return new RedirectView("/inicio");
        }
        return new RedirectView("/login/error");
    }
    
    @GetMapping("error")
    public String error(){
        return "login/error";
    }
    
}
