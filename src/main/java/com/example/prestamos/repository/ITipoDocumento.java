package com.example.prestamos.repository;
import com.example.prestamos.entities.TipoDocumento;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoDocumento extends JpaRepository<TipoDocumento, Integer>{
 
    //la sentencia Query nos permite hacer consultas personalizadas en SQL
    @Query("SELECT t FROM TipoDocumento t WHERE t.nombre = ?1")
    //Este metodoabstracto de tipo array crea una lista de tipo array con el nombre findByNombre y le pasa el nombre a validar
    ArrayList<TipoDocumento> findByNombre(String nombre);

}