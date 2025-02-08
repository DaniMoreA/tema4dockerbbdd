package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID único generado automáticamente
    private String nombre;
    private String telefono;
    private String correo;
    private String asunto;
    private String contenido;
    private double precio;
    
 // Constructor sin parámetros (default constructor)
    public Anuncio() {
        // Puede dejarse vacío o agregar valores predeterminados si es necesario
    }

    // Constructor
    public Anuncio(String nombre, String telefono, String correo, String asunto, String contenido, double precio) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.asunto = asunto;
        this.contenido = contenido;
        this.precio = precio;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
