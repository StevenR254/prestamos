package com.example.prestamos.Controller;

import com.example.prestamos.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("solicitud")
public class SolicitudPrestamoController extends BaseController{

    @GetMapping("solicitud")
    public String solicitud(Model model, @AuthenticationPrincipal OidcUser principal){
        model.addAttribute("titulopagina", "solicitd del prestamo");
        if(userOauth(principal) != null){
            model.addAttribute("usuarioautenticado", userOauth(principal));
            return "/solicitudprestamo/solicitud";
        }
        model.addAttribute("usuarioautenticado", seguridad());
        return "/solicitudprestamo/solicitud";
    }

}
