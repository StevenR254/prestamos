package com.example.prestamos.Controller;

import com.example.prestamos.dto.CrearDocumentoDTO;
import com.example.prestamos.dto.UsuariosDTO;
import com.example.prestamos.entities.TipoDocumento;
import com.example.prestamos.entities.User;
import com.example.prestamos.services.Response;
import com.example.prestamos.services.TipoDocumentoService;
import com.example.prestamos.services.UserService;
import com.example.prestamos.dto.RegistroDTO;
import com.example.prestamos.dto.UpdateUserDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("admin")
public class UserAdminController extends BaseController{
    
    //inyeccion de dependencias
    //UserService userService;
    TipoDocumentoService tipoDocumentoService;
    
    public UserAdminController(TipoDocumentoService tipoDocumentoService){
        //this.userService = userService;
        this.tipoDocumentoService = tipoDocumentoService;
    }
    
    //Con este metodo y la vista se pueden ver todos los usuarios registrados
    @GetMapping("usuarios")
    public String usuariosRegistrados(Model usuarios, @AuthenticationPrincipal OidcUser principal){
        List<UsuariosDTO> usersDB = this.userService.selectAll();
        if(userOauth(principal) != null){
            usuarios.addAttribute("usuarioautenticado", userOauth(principal));
            usuarios.addAttribute("usuarios", usersDB);
            return "/useradmin/usuariosregistrados";
        }
        usuarios.addAttribute("usuarios", usersDB);
        usuarios.addAttribute("usuarioautenticado" , seguridad());
        return "/useradmin/usuariosregistrados";
    }
    
    
    //PathVariable dice que recibe un parametro en la url y lo mapea en el parametro
    //cuando abro la vista de editar usuario con este metodo puedo traer la informacion que tiene este usuario
    @GetMapping("edituser/{id}")
    public String edituser(@PathVariable int id, Model data, @AuthenticationPrincipal OidcUser principal){
        User userinfo = this.userService.searchUser(id);
        data.addAttribute("user", userinfo);
        ArrayList<TipoDocumento> documentos = this.tipoDocumentoService.selectAll();
        if(userOauth(principal) != null){
            data.addAttribute("misdocumentos", documentos);
            data.addAttribute("usuarioautenticado", userOauth(principal));
            return "useradmin/edituser";
        }
        data.addAttribute("misdocumentos", documentos);
        data.addAttribute("usuarioautenticado", seguridad());
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
    public String newuser(Model data, @AuthenticationPrincipal OidcUser principal){
        ArrayList<TipoDocumento> tipoDocumento = this.tipoDocumentoService.selectAll();
        if(userOauth(principal) != null){
            data.addAttribute("usuarioautenticado", userOauth(principal));
            data.addAttribute("misdocumentos", tipoDocumento);
            return "useradmin/newuser";
        }
        data.addAttribute("misdocumentos", tipoDocumento);
        data.addAttribute("usuarioautenticado", seguridad());
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
        if(response.getCode() == 500){
            System.out.println(response.getMessage());
        }
        return new RedirectView("/admin/usuarios");
    }

    @GetMapping("tiposdocumento")
    public String tiposdocumento(Model model, @AuthenticationPrincipal OidcUser principal){
        ArrayList<TipoDocumento> td = this.tipoDocumentoService.selectAll();
        if(userOauth(principal) != null){
            model.addAttribute("usuarioautenticado", userOauth(principal));
            model.addAttribute("misdocumentos", td);
            return "/useradmin/documentos";
        }
        model.addAttribute("misdocumentos", td);
        model.addAttribute("usuarioautenticado", seguridad());
        return "/useradmin/documentos";
    }

    @GetMapping("createdoc")
    public String createdoc(Model data, @AuthenticationPrincipal OidcUser principal){
        if(userOauth(principal) != null){
            data.addAttribute("usuarioautenticado", userOauth(principal));
            return "/useradmin/createdoc";
        }
        data.addAttribute("usuarioautenticado", seguridad());
        return "/useradmin/createdoc";
    }

    @PostMapping("postcreatedoc")
    public RedirectView postcreatedoc(CrearDocumentoDTO crearDocumentoDTO){
        TipoDocumento tipoDocumento = new TipoDocumento();
        //tipoDocumento.setId(crearDocumentoDTO.getId());
        tipoDocumento.setNombre(crearDocumentoDTO.getNombre());
        Response response = this.tipoDocumentoService.setTipoDocumento(tipoDocumento);
        if(response.getCode() == 500){
            return new RedirectView("/login/error");
        }
        return new RedirectView("/admin/tiposdocumento");
    }

    @GetMapping("editdoc/{id}")
    public String editdoc(@PathVariable int id, Model model, @AuthenticationPrincipal OidcUser principal){
        TipoDocumento tipoDocumento = this.tipoDocumentoService.searchTipoDocumento(id);
        if(userOauth(principal) != null){
            model.addAttribute("usuarioautenticado", userOauth(principal));
            model.addAttribute("misdocumentos", tipoDocumento);
            return ("/useradmin/editardoc");
        }
        model.addAttribute("misdocumentos", tipoDocumento);
        model.addAttribute("usuarioautenticado", seguridad());
        return ("/useradmin/editardoc");
    }

    @PostMapping("posteditdoc")
    public RedirectView posteditdoc(CrearDocumentoDTO crearDocumentoDTO){
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setId(crearDocumentoDTO.getId());
        tipoDocumento.setNombre(crearDocumentoDTO.getNombre());
        Response response = this.tipoDocumentoService.updateTipoDocumento(tipoDocumento);
        if(response.getCode() == 500){
            System.out.println(response.getMessage());
            return new RedirectView("/login/error");
        }
        return new RedirectView("/admin/tiposdocumento");
    }

    @GetMapping("deletedoc/{id}")
    public RedirectView deletedoc(@PathVariable int id){
        Response response = this.tipoDocumentoService.deleteDocumento(id);
        if(response.getCode() == 500){
            System.out.println(response.getMessage());
            return new RedirectView("/login/error");
        }
        return new RedirectView("/admin/tiposdocumento");
    }
}
