package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class Controlador {

    @Autowired
    private AnuncioRepository anuncioRepository; // Repositorio para acceder a los anuncios

    @GetMapping("/") // Página principal
    public String inicio(Model model) {
        List<Anuncio> anuncios = anuncioRepository.findAll(); // Obtener todos los anuncios de la base de datos
        model.addAttribute("anuncios", anuncios);
        model.addAttribute("mostrarMisAnuncios", !anuncios.isEmpty());
        return "inicio";
    }

    @GetMapping("/nuevo")
    public String formularioNuevoAnuncio(Model model) {
        return "nuevo";
    }

    @PostMapping("/nuevo")
    public String crearAnuncio(@RequestParam String nombre,
                                @RequestParam String telefono,
                                @RequestParam String correo,
                                @RequestParam String asunto,
                                @RequestParam String contenido,
                                @RequestParam double precio,
                                Model model) {

        if (!validarTelefono(telefono)) {
            model.addAttribute("mensaje", "El teléfono debe contener exactamente 9 dígitos.");
            return "nuevo";
        }

        if (!validarCorreo(correo)) {
            model.addAttribute("mensaje", "El correo electrónico no tiene un formato válido.");
            return "nuevo";
        }

        Anuncio anuncio = new Anuncio(nombre, telefono, correo, asunto, contenido, precio);
        anuncioRepository.save(anuncio); // Guardar el anuncio en la base de datos

        model.addAttribute("mensaje", "Anuncio creado correctamente.");
        return "creado";
    }

    @GetMapping("/mis-anuncios")
    public String verMisAnuncios(Model model) {
        List<Anuncio> misAnuncios = anuncioRepository.findAll();
        model.addAttribute("misAnuncios", misAnuncios);
        return "misAnuncios";
    }

    @PostMapping("/eliminar")
    public String eliminarAnuncio(@RequestParam Long id) {
        anuncioRepository.deleteById(id); // Eliminar el anuncio por su ID
        return "redirect:/";
    }

    @GetMapping("/modificar/{id}")
    public String modificarAnuncio(@PathVariable Long id, Model model) {
        Anuncio anuncio = anuncioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid anuncio Id:" + id));
        model.addAttribute("anuncio", anuncio);
        return "modificar";  // Retorna la vista modificar.html
    }

    @PostMapping("/modificar")
    public String guardarCambios(@RequestParam Long id,
                                  @RequestParam String nombre,
                                  @RequestParam String telefono,
                                  @RequestParam String correo,
                                  @RequestParam String asunto,
                                  @RequestParam String contenido,
                                  @RequestParam double precio,
                                  Model model) {
        // Buscar el anuncio en la base de datos con el id
        Anuncio anuncio = anuncioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid anuncio Id:" + id));

        if (!validarTelefono(telefono)) {
            model.addAttribute("mensaje", "El teléfono debe contener exactamente 9 dígitos.");
            return "modificar"; // Volver a la página de modificación
        }

        if (!validarCorreo(correo)) {
            model.addAttribute("mensaje", "El correo electrónico no tiene un formato válido.");
            return "modificar"; // Volver a la página de modificación
        }

        // Actualizar los campos del anuncio
        anuncio.setNombre(nombre);
        anuncio.setTelefono(telefono);
        anuncio.setCorreo(correo);
        anuncio.setAsunto(asunto);
        anuncio.setContenido(contenido);
        anuncio.setPrecio(precio);
        
        // Guardar los cambios en la base de datos
        anuncioRepository.save(anuncio); 
        
        // Agregar mensaje de éxito
        model.addAttribute("mensaje", "Anuncio actualizado correctamente.");

        // Redirigir a la página de mis anuncios
        return "redirect:/mis-anuncios";
    }


    private boolean validarTelefono(String telefono) {
        return telefono != null && telefono.length() == 9 && telefono.matches("\\d{9}");
    }

    private boolean validarCorreo(String correo) {
        return correo != null && correo.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$");
    }
}

