package com.example.prestamos.repository;

import com.example.prestamos.entities.User;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserRepository extends JpaRepository<User, Integer>{
  
    @Query("SELECT u FROM User u WHERE u.email = ?1 and u.password = ?2")
    ArrayList<User> validarCredenciales(String usuario, String password);
    
        //la sentencia Query nos permite hacer consultas personalizadas en SQL
    @Query("SELECT t FROM User t WHERE t.numDocumento = ?1 or t.email = ?2")
    //Este metodoabstracto de tipo array crea una lista de tipo array con el nombre findByNombre y le pasa el nombre a validar
    ArrayList<User> validarDuplicado(String documento, String correo);
    
}
