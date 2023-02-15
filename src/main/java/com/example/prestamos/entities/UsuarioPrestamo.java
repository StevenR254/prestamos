package com.example.prestamos.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="usuarioprestamo")
public class UsuarioPrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="tipoprestamoid")
    private TipoPrestamo prestamo;
    @ManyToOne
    @JoinColumn(name="usuarioid")
    private User usuario;
    @Column(name = "montosolicitado")
    private int montoSolicitado;
    @Column(name="cantidadcuotas")
    private int cantidadCuotas;
    @Column(name="codeudor")
    private String codeudor;
    @Column(name = "celularcodeudor")
    private String celularCodeudor;

}

