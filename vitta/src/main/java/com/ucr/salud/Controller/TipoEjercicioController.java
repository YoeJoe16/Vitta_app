package com.ucr.salud.Controller;

import com.ucr.salud.model.TipoEjercicio;
import com.ucr.salud.service.TipoEjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-ejercicio")
public class TipoEjercicioController {

    @Autowired
    private TipoEjercicioService service;

    /**
     * Retorna la lista completa de tipos de ejercicio registrados.
     * Si no hay ninguno, responde con 204 No Content.
     */
    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        List<TipoEjercicio> ejercicios = service.obtenerTodos();
        if (ejercicios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ejercicios);
    }

    /**
     * Busca un tipo de ejercicio por su ID.
     * Si no existe, responde con 404 Not Found.
     */
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        TipoEjercicio ejercicio = service.obtenerPorId(id).orElse(null);
        if (ejercicio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ejercicio);
    }

    /**
     * Busca un tipo de ejercicio por su nombre exacto.
     * Si no existe, responde con 404 Not Found.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable String nombre) {
        TipoEjercicio ejercicio = service.obtenerPorNombre(nombre).orElse(null);
        if (ejercicio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ejercicio);
    }

    /**
     * Retorna todos los tipos de ejercicio que pertenecen a una categoría dada.
     * Si no hay resultados para esa categoría, responde con 204 No Content.
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<?>> getByCategoria(@PathVariable String categoria) {
        List<TipoEjercicio> ejercicios = service.obtenerPorCategoria(categoria);
        if (ejercicios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ejercicios);
    }

    /**
     * Crea un nuevo tipo de ejercicio a partir del cuerpo de la solicitud.
     * Si el ejercicio ya existe o faltan campos obligatorios, responde con 400 Bad Request.
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody TipoEjercicio tipoEjercicio) {
        if (service.crear(tipoEjercicio) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ejercicio ya está registrado o faltan campos obligatorios");
        }
        return ResponseEntity.ok("Tipo de ejercicio registrado exitosamente");
    }

    /**
     * Actualiza todos los datos de un tipo de ejercicio existente por su ID.
     * Si el tipo de ejercicio no existe, responde con 404 Not Found.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TipoEjercicio datos) {
        if (service.actualizar(id, datos) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Tipo de ejercicio actualizado exitosamente");
    }

    /**
     * Elimina un tipo de ejercicio por su ID.
     * Primero verifica que exista; si no, responde con 404 Not Found.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        TipoEjercicio ejercicio = service.obtenerPorId(id).orElse(null);
        if (ejercicio == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Tipo de ejercicio eliminado exitosamente");
    }
}
