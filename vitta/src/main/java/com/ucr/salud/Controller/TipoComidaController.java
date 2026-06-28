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

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        List<TipoComida> comidas = service.obtenerTodos();
        if (comidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comidas);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        TipoComida comida = service.obtenerPorId(id).orElse(null);
        if (comida == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comida);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable String nombre) {
        TipoComida comida = service.obtenerPorNombre(nombre).orElse(null);
        if (comida == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comida);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<?>> getByCategoria(@PathVariable String categoria) {
        List<TipoComida> comidas = service.obtenerPorCategoria(categoria);
        if (comidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comidas);
    }

    @GetMapping("/nivel-saludable/{nivelSaludable}")
    public ResponseEntity<List<?>> getByNivelSaludable(@PathVariable String nivelSaludable) {
        List<TipoComida> comidas = service.obtenerPorNivelSaludable(nivelSaludable);
        if (comidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comidas);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody TipoComida tipoComida) {
        if (service.crear(tipoComida) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La comida ya esta registrada o faltan campos obligatorios");
        }
        return ResponseEntity.ok("Tipo de comida registrado exitosamente");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TipoComida datos) {
        if (service.actualizar(id, datos) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Tipo de comida actualizado exitosamente");
    }

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
