package com.example.prestamos.services;

import com.example.prestamos.entities.User;
import com.example.prestamos.repository.IUserRepository;
import java.util.ArrayList;
import java.util.Optional;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.springframework.stereotype.Service;

@Service 
public class UserService {
    
    private IUserRepository iUserRepository;
    
    public UserService(IUserRepository rep){
        this.iUserRepository = rep;
    }
    
    Response response = new Response();
    
    public ArrayList<User> selectAll(){
        return (ArrayList<User>) this.iUserRepository.findAll();
    }
    
    public Response createUser(User user){
        ArrayList<User> validarExistente = iUserRepository.validarDuplicado(user.getNumDocumento(), user.getEmail());
        if(validarExistente != null  && validarExistente.size()>0){
            response.setCode(500);
            response.setMessage("Usuario ya existe");
            return response;
        }
        if(user.getPassword().equals(null) || user.getPassword().equals("")){
            response.setCode(500);
            response.setMessage("Contraseña Incorrecta");
            return response;
        }
        this.iUserRepository.save(user);
        response.setCode(200);
        response.setMessage("Usuario Registrado exitosamente");
        return response;
    }
    
    public User searchUser(int id){
        Optional<User> buscar = this.iUserRepository.findById(id);
        if(buscar.isPresent()){
            return buscar.get();
        }
        return null;
    }
    
    public Response deleteUser(int id){
        try{
            this.iUserRepository.deleteById(id);
            response.setCode(200);
            response.setMessage("Usuario Eliminado exitosamente");
            return response;
        }catch(Exception e){
            response.setCode(500);
            response.setMessage("Error " + e.getMessage());
            return response;
        }
    }
    /*tenemos dos update,Este updateUser me sirve para mis pruebas Rest se hizo la prueba en aplicacion y este no funciono, esto debido a que recibe
      Parametros que no aplican en nuestra vista, por lo general me arrojaba un error 500.*/
    public Response updateUser(User id){
        if(id.getId() == 0 ){
            response.setCode(500);
            response.setMessage("Error el id del usuario no es valido");
            return response;
        }
        User exists = searchUser(id.getId());
        if(exists == null){
            response.setCode(500);
            response.setMessage("Error el id del usuario no es valido");
            return response;
        }
        
        /*Este metodo va ligado al isValidEmail, trata de verificar si el correo tiene la estructura adecuada, en la condicion if
        recordemos que se ejecuta la condicion si es true pero con el cambio de condicion la validamos como false intercambiando su valor con el 
        signo ! y que sea true*/
        if(!isValidEmail(id.getEmail())){
            response.setCode(500);
            response.setMessage("Error el correo electronico no tiene el formato adecuado");
            return response;
        }
        
        exists.setEdad(id.getEdad());
        exists.setEmail(id.getEmail());
        this.iUserRepository.save(exists);
        response.setCode(200);
        response.setMessage("Usuario modificado exitosamente");
        return response;
    }
    
    public Response updateUserName(User id){
        if(id.getId() == 0 ){
            response.setCode(500);
            response.setMessage("Error el id del usuario no es valido");
            return response;
        }
        User exists = searchUser(id.getId());
        if(exists == null){
            response.setCode(500);
            response.setMessage("Error el id del usuario no es valido");
            return response;
        }
        
        /*Este metodo va ligado al isValidEmail, trata de verificar si el correo tiene la estructura adecuada, en la condicion if
        recordemos que se ejecuta la condicion si es true pero con el cambio de condicion la validamos como false intercambiando su valor con el 
        signo ! y que sea true*/

        exists.setTipoDocumento(id.getTipoDocumento());
        exists.setNombres(id.getNombres());
        exists.setApellidos(id.getApellidos());
        this.iUserRepository.save(exists);
        response.setCode(200);
        response.setMessage("Usuario modificado exitosamente");
        return response;
    }
    
    public Response loginUser(User user){
        
        if(!isValidEmail(user.getEmail())){
            response.setCode(500);
            response.setMessage("El correo electronico no es valido");
            return response;
        }
        
        if(user.getPassword().equals(null) || user.getPassword().equals("")){
            response.setCode(500);
            response.setMessage("La contraseña no es valida");
            return response;
        }
        
        ArrayList<User> existe = iUserRepository.validarCredenciales(user.getEmail(), user.getPassword());
        if(existe != null && existe.size() > 0){
            response.setCode(200);
            response.setMessage("Usuario Logueado exitosamente");
            return response;
        }
        response.setCode(500);
        response.setMessage("Datos de ingreso no validos");
        return response;
        
    }
    
    public static boolean isValidEmail(String email){
        boolean result = true;
        try{
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        }catch(AddressException ex){
            result = false;
        }
        return result;
    }
    
}
