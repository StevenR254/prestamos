/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.prestamos.services;

import com.example.prestamos.entities.TipoDocumento;
import com.example.prestamos.repository.ITipoDocumento;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author edwar
 */
@Service
public class TipoDocumentoService {
    
    private ITipoDocumento iTipoDocumento;
    
    private TipoDocumentoService(ITipoDocumento iTipoDocumento){
        this.iTipoDocumento = iTipoDocumento;
    }
    Response response = new Response();
    
    public ArrayList<TipoDocumento> selectAll(){
        return (ArrayList<TipoDocumento>) iTipoDocumento.findAll();
    }
    
    public Response setTipoDocumento(TipoDocumento tipoDocumento){
        //Este Array de tipoDocumento llamado documento hace un llamado al repositorio de tipoDocumento y llama al metodo findByNombre pasandole el nombre del nuevo registro y asi validando si se encuentra repetido
        ArrayList<TipoDocumento> documento = iTipoDocumento.findByNombre(tipoDocumento.getNombre());
        if(documento != null && documento.size() > 0 ){
            response.setCode(500);
            response.setMessage("Tipo Documento ya existe");
            return response;
        }
        iTipoDocumento.save(tipoDocumento);
        response.setCode(200);
        response.setMessage("Tipo Documento guardado con exito");
        return response;
    }
    
    public Response deleteDocumento(int id){
        try{
            this.iTipoDocumento.deleteById(id);
            response.setCode(200);
            response.setMessage("Tipo de documento eliminado con exito");
            return response;
        }catch(Exception e){
            response.setCode(500);
            response.setMessage("Tipo de documento no existe");
            return response;
        }
    }
    
    public TipoDocumento searchTipoDocumento(int id){
        Optional<TipoDocumento> registro = this.iTipoDocumento.findById(id);
        if(registro.isPresent()){
            return registro.get();
        }else{
            return null; //Se podra enviar un mensaje de no existe??
        }
    }
    
    public Response updateTipoDocumento(TipoDocumento tipoDocumento){
        try{
            TipoDocumento registro = searchTipoDocumento(tipoDocumento.getId());
            registro.setNombre(tipoDocumento.getNombre());
            this.iTipoDocumento.save(registro);
            response.setCode(200);
            response.setMessage("Registro Modificado");
            return response;
        }catch(Exception e){
            response.setCode(500);
            response.setMessage("Registro no existe");
            return response;
        }
    }
    
}
