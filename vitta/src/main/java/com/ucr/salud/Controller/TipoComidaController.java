package com.ucr.salud.Controller;

import com.ucr.salud.model.TipoComida;
import com.ucr.salud.service.TipoComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-comida")
public class TipoComidaController {

    @Autowired
    private TipoComidaService service;

    /**
     * Retorna la lista completa de tipos de comida registrados.
     * Si no hay ninguno, responde con 204 No Content.
     */
    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        List<TipoComida> comidas = service.obtenerTodos();
        if (comidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comidas);
    }

    /**
     * Busca un tipo de comida por su ID.
     * Si no existe, responde con 404 Not Found.
     */
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        TipoComida comida = service.obtenerPorId(id).orElse(null);
        if (comida == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comida);
    }

    /**
     * Busca un tipo de comida por su nombre exacto.
     * Si no existe, responde con 404 Not Found.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable String nombre) {
        TipoComida comida = service.obtenerPorNombre(nombre).orElse(null);
        if (comida == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comida);
    }

    /**
     * Retorna todos los tipos de comida que pertenecen a una categoría dada
     * (por ejemplo: proteína, carbohidrato, grasa).
     * Si no hay resultados, responde con 204 No Content.
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<?>> getByCategoria(@PathVariable String categoria) {
        List<TipoComida> comidas = service.obtenerPorCategoria(categoria);
        if (comidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comidas);
    }

    /**
     * Retorna todos los tipos de comida filtrados por su nivel de salud
     * (por ejemplo: alto, medio, bajo).
     * Si no hay resultados para ese nivel, responde con 204 No Content.
     */
    @GetMapping("/nivel-saludable/{nivelSaludable}")
    public ResponseEntity<List<?>> getByNivelSaludable(@PathVariable String nivelSaludable) {
        List<TipoComida> comidas = service.obtenerPorNivelSaludable(nivelSaludable);
        if (comidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comidas);
    }

    /**
     * Crea un nuevo tipo de comida a partir del cuerpo de la solicitud.
     * Si la comida ya existe o faltan campos obligatorios, responde con 400 Bad Request.
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody TipoComida tipoComida) {
        if (service.crear(tipoComida) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La comida ya está registrada o faltan campos obligatorios");
        }
        return ResponseEntity.ok("Tipo de comida registrado exitosamente");
    }

    /**
     * Actualiza todos los datos de un tipo de comida existente por su ID.
     * Si no existe, responde con 404 Not Found.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TipoComida datos) {
        if (service.actualizar(id, datos) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Tipo de comida actualizado exitosamente");
    }

    /**
     * Elimina un tipo de comida por su ID.
     * Primero verifica que exista; si no, responde con 404 Not Found.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        TipoComida comida = service.obtenerPorId(id).orElse(null);
        if (comida == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Tipo de comida eliminado exitosamente");
    }
}
