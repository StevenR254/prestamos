package com.example.prestamos.Controller;

import com.example.prestamos.dto.RegistroDTO;
import com.example.prestamos.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HomeController extends BaseController{

    /*Tenemos que cuadrar el controlador para que reciba la respuesta segun el metodo de autenticacion que haya usado
    * en este caso Spring Security o OAUTH*/
    @GetMapping("inicio")
    public String inicio(Model model, @AuthenticationPrincipal OidcUser principal){
        if(userOauth(principal) != null){
            model.addAttribute("usuarioautenticado", userOauth(principal));
            return "home/inicio";
        }
        model.addAttribute("usuarioautenticado", seguridad());
        return "home/inicio";
    }



    
}
