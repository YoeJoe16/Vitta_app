package com.ucr.salud.Controller;

import com.ucr.salud.model.TipoEjercicio;
import com.ucr.salud.service.TipoEjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tipos-ejercicio")
public class TipoEjercicioController {

    @Autowired
    private TipoEjercicioService service;

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        List<TipoEjercicio> ejercicios = service.obtenerTodos();
        if (ejercicios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ejercicios);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        TipoEjercicio ejercicio = service.obtenerPorId(id).orElse(null);
        if (ejercicio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ejercicio);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable String nombre) {
        TipoEjercicio ejercicio = service.obtenerPorNombre(nombre).orElse(null);
        if (ejercicio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ejercicio);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<?>> getByCategoria(@PathVariable String categoria) {
        List<TipoEjercicio> ejercicios = service.obtenerPorCategoria(categoria);
        if (ejercicios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ejercicios);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody TipoEjercicio tipoEjercicio) {
        if (service.crear(tipoEjercicio) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ejercicio ya está registrado o faltan campos obligatorios");
        }
        return ResponseEntity.ok("Tipo de ejercicio registrado exitosamente");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TipoEjercicio datos) {
        if (service.actualizar(id, datos) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Tipo de ejercicio actualizado exitosamente");
    }

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
