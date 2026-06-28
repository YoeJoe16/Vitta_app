package com.ucr.salud.Controller;

import com.ucr.salud.model.RegistroDiario;
import com.ucr.salud.model.dto.RegistroDiarioDTO;
import com.ucr.salud.service.RegistroDiarioService;
import jakarta.validation.Valid;
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

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        List<RegistroDiario> registros = service.obtenerTodos();
        if (registros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        RegistroDiario registro = service.obtenerPorId(id).orElse(null);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registro);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<?>> getByUsuario(@PathVariable Integer idUsuario) {
        List<RegistroDiario> registros = service.obtenerPorUsuario(idUsuario);
        if (registros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/usuario/{idUsuario}/fecha/{fecha}")
    public ResponseEntity<?> getByUsuarioYFecha(
            @PathVariable Integer idUsuario,
            @PathVariable String fecha) {
        RegistroDiario registro = service.obtenerPorUsuarioYFecha(idUsuario, fecha).orElse(null);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registro);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody RegistroDiarioDTO dto) {
        RegistroDiario nuevo = service.add(dto);
        if (nuevo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un registro para ese dia o faltan campos obligatorios");
        }
        return ResponseEntity.ok("Registro creado exitosamente");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RegistroDiario datos) {
        if (service.actualizar(id, datos) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Registro actualizado exitosamente");
    }

    @PatchMapping("/recalcular/{id}")
    public ResponseEntity<?> recalcularPuntos(@PathVariable Integer id) {
        if (service.recalcularPuntos(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Puntos recalculados exitosamente");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        RegistroDiario registro = service.obtenerPorId(id).orElse(null);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Registro eliminado exitosamente");
    }
}
