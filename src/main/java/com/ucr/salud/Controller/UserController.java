package com.ucr.salud.Controller;

import com.ucr.salud.model.User;
import com.ucr.salud.model.dto.UserDTO;
import com.ucr.salud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService service;


    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        List<User> users = service.obtenerTodos();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        User user = service.obtenerPorId(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email:.+}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        User user = service.obtenerPorEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            service.add(dto);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo ya está registrado o faltan campos obligatorios");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody User datos) {
        if (service.actualizar(id, datos) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Usuario actualizado exitosamente");
    }

    @PatchMapping("/change/{id}")
    public ResponseEntity<?> agregarPuntos(@PathVariable Integer id, @RequestParam Integer puntos) {
        User actualizado = service.agregarPuntos(id, puntos);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Puntos actualizados exitosamente");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User user = service.obtenerPorId(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Usuario eliminado exitosamente");
    }

    @GetMapping("/existe/{email:.+}")
    public ResponseEntity<?> existePorEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.existePorEmail(email));
    }
}
