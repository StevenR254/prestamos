/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.example.prestamos.entities.TipoDocumento;

/*Esta clase se crea como parte de logica de negocio donde necesitare interactuar con campos que no necesariamente necesito en mi base de datos
el otro punto es que nunca debo poner a viajar a mis entidades directamente desde el cliente esto para evitar ataques contra información sensible*/
/*Todo la informacion que encontremos en la capa DTO corresponde a la informacion con la que interactua el cliente en este caso la informacion que ponemos aqui 
es la misma que tiene el formulario en la vista registro*/

public class RegistroDTO {
    private String nombres;
    private String apellidos;
    private String numDocumento;
    private String email;
    private TipoDocumento tipoDocumento;
    private String password;
    private String validarPassword;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidarPassword() {
        return validarPassword;
    }

    public void setValidarPassword(String validarPassword) {
        this.validarPassword = validarPassword;
    }
    
    
    
}
