package com.example.prestamos.Controller;

import com.example.prestamos.entities.TipoDocumento;
import com.example.prestamos.services.Response;
import com.example.prestamos.services.TipoDocumentoService;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("tipodocumento")
public class DocumentoController extends BaseController {
    
    private TipoDocumentoService tipoDocumentoService;
    
    public DocumentoController(TipoDocumentoService tipoDocumentoService){
        this.tipoDocumentoService = tipoDocumentoService;
    }
    
    @GetMapping("index")
    public String index(Model documentos){
        //Consulto los documentos a la base de datos por medio de la logica del negocio
        ArrayList<TipoDocumento> documentosBD = this.tipoDocumentoService.selectAll();
        //Armo el modelo que se le pasa a Thymeleaf
        documentos.addAttribute("misdocumentos", documentosBD);
        documentos.addAttribute("usuarioautenticado", seguridad());
        return "documento/index";
    }
    
    @GetMapping("create")
    public String create(){
        return "documento/create";
    }
    
    @PostMapping("createdoc")
    //la clase RedirectView me permite devolver una vista segun el parametro del return
    public RedirectView create(TipoDocumento tipoDocumento){
        //en este caso seguimos usando la entidad response guardando dentro el resultado del metodo setTipoDocumento en el response.
        Response response = this.tipoDocumentoService.setTipoDocumento(tipoDocumento);
        return new RedirectView("/tipodocumento/index");
    }
    
}
