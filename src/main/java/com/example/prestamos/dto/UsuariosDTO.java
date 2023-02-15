package com.example.prestamos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuariosDTO {

    private int id;
    private String nombres;
    private String apellidos;
    private String email;
    private String tipoDocumento;
    private String numDocumento;


}
