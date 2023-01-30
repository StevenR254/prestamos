package com.example.prestamos.Controller;

import com.example.prestamos.entities.TipoDocumento;
import com.example.prestamos.entities.User;
import com.example.prestamos.services.Response;
import com.example.prestamos.services.TipoDocumentoService;
import com.example.prestamos.services.UserService;
import dto.RegistroDTO;
import dto.UpdateUserDTO;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("admin")
public class UserAdminController {
    
    //inyeccion de dependencias
    UserService userService;
    TipoDocumentoService tipoDocumentoService;
    
    public UserAdminController(UserService userService, TipoDocumentoService tipoDocumentoService){
        this.userService = userService;
        this.tipoDocumentoService = tipoDocumentoService;
    }
    
    //Con este metodo y la vista se pueden ver todos los usuarios registrados
    @GetMapping("usuarios")
    public String usuariosRegistrados(Model usuarios){
        ArrayList<User> usersDB = this.userService.selectAll();
        usuarios.addAttribute("usuarios", usersDB);
        return "/useradmin/usuariosregistrados";
    }
    
    
    //PathVariable dice que recibe un parametro en la url y lo mapea en el parametro
    //cuando abro la vista de editar usuario con este metodo puedo traer la informacion que tiene este usuario
    @GetMapping("edituser/{id}")
    public String edituser(@PathVariable int id, Model data){
        User userinfo = this.userService.searchUser(id);
        data.addAttribute("user", userinfo);
        ArrayList<TipoDocumento> documentos = this.tipoDocumentoService.selectAll();
        data.addAttribute("misdocumentos", documentos);
        return "useradmin/edituser";
    }
    
    //Con este metodo estando en la vista editar puedo modificar la informacion almacenada
    @PostMapping("edituserpost")
    public RedirectView edituserpost(UpdateUserDTO updateUserDTO){
        User user = new User();
        user.setId(updateUserDTO.getId());
        user.setTipoDocumento(updateUserDTO.getTipoDocumento());
        user.setNombres(updateUserDTO.getNombres());
        user.setApellidos(updateUserDTO.getApellidos());
        Response response = this.userService.updateUserName(user);
        System.out.println(response.getCode());
        System.out.println(response.getMessage());
        return new RedirectView("/admin/usuarios");
    }
    
    //Con este metodo podremos eliminar un usuario registrado
    @GetMapping("deleteuser/{id}")
    public RedirectView deleteuser(@PathVariable int id){
        Response response = this.userService.deleteUser(id);
        return new RedirectView("/admin/usuarios");
    }
    
    @GetMapping("newuser")
    public String newuser(Model data){
        ArrayList<TipoDocumento> tipoDocumento = this.tipoDocumentoService.selectAll();
        data.addAttribute("misdocumentos", tipoDocumento);
        return "useradmin/newuser";
    }
    
    @PostMapping("newusercreate")
    public RedirectView newusercreate(RegistroDTO registroDTO){
        System.out.println(registroDTO.getPassword());
        System.out.println(registroDTO.getValidarPassword());
        if(!registroDTO.getPassword().equals(registroDTO.getValidarPassword())){
            System.out.println("La contraseña no coincide");
            return new RedirectView("/login/error");
        }
        User user = new User();
        user.setNombres(registroDTO.getNombres());
        user.setApellidos(registroDTO.getApellidos());
        user.setEmail(registroDTO.getEmail());
        user.setTipoDocumento(registroDTO.getTipoDocumento());
        user.setNumDocumento(registroDTO.getNumDocumento());
        user.setPassword(registroDTO.getPassword());
        Response response = this.userService.createUser(user);
        return new RedirectView("useradmin/usuarios");
    }
}
