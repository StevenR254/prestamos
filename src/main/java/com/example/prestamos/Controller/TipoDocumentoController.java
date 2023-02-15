/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.prestamos.Controller;

import com.example.prestamos.entities.TipoDocumento;
import com.example.prestamos.services.Response;
import com.example.prestamos.services.TipoDocumentoService;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tipodocumento")
public class TipoDocumentoController extends BaseController{
    
    private TipoDocumentoService tipoDocumentoService;
    
    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService){
        this.tipoDocumentoService = tipoDocumentoService;
    }
    
    @RequestMapping("gettipodocumento")
    public ArrayList<TipoDocumento> getAllTipoDocumento(){
        return tipoDocumentoService.selectAll();
    }
    
    @PostMapping("settipodocumento")
    public Response CrearTipoDocumento(@RequestBody TipoDocumento tipoDocumento){
        return tipoDocumentoService.setTipoDocumento(tipoDocumento);
    }
    
    @DeleteMapping("deletedocumento/{id}")
    public Response eliminarDocumento(@PathVariable int id){
        return this.tipoDocumentoService.deleteDocumento(id);
    }
    
    @RequestMapping("searchdocumento/{id}")
    public TipoDocumento buscarDocumento(@PathVariable int id){
        return this.tipoDocumentoService.searchTipoDocumento(id);
    }
    
    @PutMapping("update")
    public Response modificarDocumento(@RequestBody TipoDocumento tipoDocumento){
        return this.tipoDocumentoService.updateTipoDocumento(tipoDocumento);
    }
}

