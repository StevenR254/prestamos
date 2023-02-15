package com.example.prestamos.Controller;

import com.example.prestamos.dto.RegistroDTO;
import com.example.prestamos.dto.UsuariosDTO;
import com.example.prestamos.entities.User;
import com.example.prestamos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public class BaseController {

    @Autowired
    protected UserService userService;

    protected User seguridad(){
        //Ingreso a la información de spring security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Tomo el correo electronico que nos guarda spring security
        String currentPrincipalName = authentication.getName();
        User user = this.userService.selectByUserName(currentPrincipalName);
        return user;
    }
    public User userOauth(@AuthenticationPrincipal OidcUser principal){
        User user = new User();
        Map<String,Object> Auth0Data = principal.getClaims();
        String username = (String) Auth0Data.get("name");
        user = this.userService.selectByUserName(username);
        return user;
    }


}
