package com.ucr.salud.Controller;

import com.ucr.salud.model.Rango;
import com.ucr.salud.service.RangoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/rangos")
public class RangoController {

    @Autowired
    private RangoService service;


    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        List<Rango> rangos = service.obtenerTodos();
        if (rangos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rangos);
    }


    @GetMapping("get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Rango rango = service.obtenerPorId(id).orElse(null);
        if (rango == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rango);
    }


    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable String nombre) {
        Rango rango = service.obtenerPorNombre(nombre).orElse(null);
        if (rango == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rango);
    }


    @GetMapping("/puntos/{puntos}")
    public ResponseEntity<?> getByPuntos(@PathVariable Integer puntos) {
        Rango rango = service.obtenerPorPuntos(puntos).orElse(null);
        if (rango == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rango);
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Rango rango) {
        if (service.crear(rango) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El rango ya está registrado o faltan campos obligatorios");
        }
        return ResponseEntity.ok("Rango registrado exitosamente");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Rango datos) {
        if (service.actualizar(id, datos) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Rango actualizado exitosamente");
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Rango rango = service.obtenerPorId(id).orElse(null);
        if (rango == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Rango eliminado exitosamente");
    }
}
