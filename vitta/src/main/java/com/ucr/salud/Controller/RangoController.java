package com.ucr.salud.Controller;

import com.ucr.salud.model.Rango;
import com.ucr.salud.service.RangoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rangos")
public class RangoController {

    @Autowired
    private RangoService service;

    /**
     * Retorna la lista completa de rangos registrados en el sistema.
     * Si no hay ninguno, responde con 204 No Content.
     */
    @GetMapping("/api/rangos/all")
    public ResponseEntity<List<?>> getAll() {
        List<Rango> rangos = service.obtenerTodos();
        if (rangos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rangos);
    }

    /**
     * Busca un rango por su ID.
     * Si no existe, responde con 404 Not Found.
     */
    @GetMapping("/api/rangos/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Rango rango = service.obtenerPorId(id).orElse(null);
        if (rango == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rango);
    }

    /**
     * Busca un rango por su nombre exacto (por ejemplo: "Bronce", "Plata", "Oro").
     * Si no existe, responde con 404 Not Found.
     */
    @GetMapping("/api/rangos/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable String nombre) {
        Rango rango = service.obtenerPorNombre(nombre).orElse(null);
        if (rango == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rango);
    }

    /**
     * Busca el rango que corresponde a una cantidad de puntos dada.
     * Útil para determinar en qué rango se encuentra un usuario según sus puntos acumulados.
     * Si no existe un rango para esa cantidad, responde con 404 Not Found.
     */
    @GetMapping("/api/rangos/puntos/{puntos}")
    public ResponseEntity<?> getByPuntos(@PathVariable Integer puntos) {
        Rango rango = service.obtenerPorPuntos(puntos).orElse(null);
        if (rango == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rango);
    }

    /**
     * Crea un nuevo rango a partir del cuerpo de la solicitud.
     * Si el rango ya existe o faltan campos obligatorios, responde con 400 Bad Request.
     */
    @PostMapping("/api/rangos/add")
    public ResponseEntity<?> add(@RequestBody Rango rango) {
        if (service.crear(rango) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El rango ya está registrado o faltan campos obligatorios");
        }
        return ResponseEntity.ok("Rango registrado exitosamente");
    }

    /**
     * Actualiza todos los datos de un rango existente por su ID.
     * Si no existe, responde con 404 Not Found.
     */
    @PutMapping("/api/rangos/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Rango datos) {
        if (service.actualizar(id, datos) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Rango actualizado exitosamente");
    }

    /**
     * Elimina un rango por su ID.
     * Primero verifica que exista; si no, responde con 404 Not Found.
     */
    @DeleteMapping("/api/rangos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Rango rango = service.obtenerPorId(id).orElse(null);
        if (rango == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Rango eliminado exitosamente");
    }
}
