package com.ucr.salud.Controller;

import com.ucr.salud.model.RegistroDiario;
import com.ucr.salud.model.dto.RegistroDiarioDTO;
import com.ucr.salud.service.RegistroDiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros-diarios")
public class RegistroDiarioController {

    @Autowired
    private RegistroDiarioService service;

    /**
     * Retorna la lista completa de registros diarios almacenados.
     * Si no hay ninguno, responde con 204 No Content.
     */
    @GetMapping("/api/registros-diarios/all")
    public ResponseEntity<List<?>> getAll() {
        List<RegistroDiario> registros = service.obtenerTodos();
        if (registros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registros);
    }

    /**
     * Busca un registro diario por su ID.
     * Si no existe, responde con 404 Not Found.
     */
    @GetMapping("/api/registros-diarios/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        RegistroDiario registro = service.obtenerPorId(id).orElse(null);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registro);
    }

    /**
     * Retorna todos los registros diarios asociados a un usuario específico.
     * Si el usuario no tiene registros, responde con 204 No Content.
     */
    @GetMapping("/api/registros-diarios/usuario/{idUsuario}")
    public ResponseEntity<List<?>> getByUsuario(@PathVariable Integer idUsuario) {
        List<RegistroDiario> registros = service.obtenerPorUsuario(idUsuario);
        if (registros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registros);
    }

    /**
     * Busca el registro diario de un usuario en una fecha específica.
     * Útil para verificar si ya existe un registro para ese día.
     * Si no existe, responde con 404 Not Found.
     */
    @GetMapping("/api/registros-diarios/usuario/{idUsuario}/fecha/{fecha}")
    public ResponseEntity<?> getByUsuarioYFecha(
            @PathVariable Integer idUsuario,
            @PathVariable String fecha) {
        RegistroDiario registro = service.obtenerPorUsuarioYFecha(idUsuario, fecha).orElse(null);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registro);
    }

    /**
     * Crea un nuevo registro diario a partir de un DTO.
     * Si ya existe un registro para ese día o faltan campos obligatorios,
     * responde con 400 Bad Request.
     */
    @PostMapping("/api/registros-diarios/add")
    public ResponseEntity<?> add(@RequestBody RegistroDiarioDTO dto) {
        RegistroDiario nuevo = service.add(dto);
        if (nuevo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un registro para ese día o faltan campos obligatorios");
        }
        return ResponseEntity.ok("Registro creado exitosamente");
    }

    /**
     * Actualiza todos los datos de un registro diario existente por su ID.
     * Si no existe, responde con 404 Not Found.
     */
    @PutMapping("/api/registros-diarios/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RegistroDiario datos) {
        if (service.actualizar(id, datos) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Registro actualizado exitosamente");
    }

    /**
     * Recalcula los puntos acumulados de un registro diario específico,
     * tomando en cuenta los ejercicios y comidas registradas en ese día.
     * Si el registro no existe, responde con 404 Not Found.
     */
    @PatchMapping("/api/registros-diarios/recalcular/{id}")
    public ResponseEntity<?> recalcularPuntos(@PathVariable Integer id) {
        if (service.recalcularPuntos(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Puntos recalculados exitosamente");
    }

    /**
     * Elimina un registro diario por su ID.
     * Primero verifica que exista; si no, responde con 404 Not Found.
     */
    @DeleteMapping("/api/registros-diarios/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        RegistroDiario registro = service.obtenerPorId(id).orElse(null);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Registro eliminado exitosamente");
    }
}
