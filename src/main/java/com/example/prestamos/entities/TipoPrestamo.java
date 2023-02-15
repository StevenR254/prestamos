package com.example.prestamos.entities;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

@Entity
@Data
@Table(name="tipoprestamo")
public class TipoPrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="nombre")
    private String nombre;
    @Column(name = "montomaximo")
    private int montoMaximo;
    @Column(name = "edadmaxima")
    private int edadMaxima;
    @Column(name = "cantidadmaximameses")
    private int cantidadMaximaMeses;


}
