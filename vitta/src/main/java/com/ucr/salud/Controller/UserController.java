package com.ucr.salud.Controller;

import com.ucr.salud.model.User;
import com.ucr.salud.model.dto.LoginDTO;
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

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Retorna la lista completa de usuarios registrados.
     * Si no hay usuarios, responde con 204 No Content.
     */
    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        List<User> users = service.obtenerTodos();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    /**
     * Busca un usuario por su ID.
     * Si no existe, responde con 404 Not Found.
     */
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        User user = service.obtenerPorId(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Busca un usuario por su correo electronico.
     * Si no existe, responde con 404 Not Found.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        User user = service.obtenerPorEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Registra un nuevo usuario a partir de un DTO validado.
     * Si hay errores de validacion, retorna 400 con la lista de mensajes de error.
     * Si el correo ya esta en uso o faltan campos, retorna 400 con mensaje descriptivo.
     */
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo ya esta registrado o faltan campos obligatorios");
        }
    }

    /**
     * Actualiza todos los datos de un usuario existente por su ID.
     * Si el usuario no existe, responde con 404 Not Found.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody User datos) {
        if (service.actualizar(id, datos) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Usuario actualizado exitosamente");
    }

    /**
     * Agrega (o modifica) los puntos de un usuario especifico.
     * Recibe la cantidad de puntos como parametro de consulta.
     * Si el usuario no existe, responde con 404 Not Found.
     */
    @PatchMapping("/change/{id}")
    public ResponseEntity<?> agregarPuntos(@PathVariable Integer id, @RequestParam Integer puntos) {
        User actualizado = service.agregarPuntos(id, puntos);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Puntos actualizados exitosamente");
    }

    /**
     * Elimina un usuario por su ID.
     * Primero verifica que exista; si no, responde con 404 Not Found.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User user = service.obtenerPorId(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Usuario eliminado exitosamente");
    }

    /**
     * Verifica si ya existe un usuario registrado con el correo indicado.
     * Retorna true o false segun el resultado.
     */
    @GetMapping("/existe/{email}")
    public ResponseEntity<?> existePorEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.existePorEmail(email));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        User user = service.login(dto.getEmail(), dto.getPassword()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
        }
        return ResponseEntity.ok(user);
    }
}
