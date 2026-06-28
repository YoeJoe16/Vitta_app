package com.ucr.salud.Controller;

import com.ucr.salud.model.Logro;
import com.ucr.salud.model.LogroUsuario;
import com.ucr.salud.model.User;
import com.ucr.salud.service.LogroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LogroController {
    @Autowired
    private LogroService service;

    @GetMapping("/Logros")
    public ResponseEntity<List<?>> obtenerTodos(){
        List<Logro> Logros=service.obtenerTodos();
        if(Logros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Logros);
    }

    @GetMapping("/Logros/get-by-id/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Logro> logro = service.obtenerPorId(id);
        if (logro.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(logro);
    }

    @GetMapping("/Logros/{nombre}")
    public ResponseEntity<?> obtenerPorNombre(@PathVariable String nombre) {
        Optional<Logro> logro = service.obtenerPorNombre(nombre);
        if (logro.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(logro);
    }
    @PostMapping("/Logros/add")
    public ResponseEntity<?> crear(@Valid @RequestBody Logro logro, BindingResult result){
        if(result.hasErrors()){
            List<String> errors =new ArrayList<>();
            for (ObjectError error: result.getAllErrors()){
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        if (service.crear(logro)==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ya esta registrado el logro");
        }
        return ResponseEntity.ok("Logro registrado exitosamente");
    }

    @GetMapping("/Logros/usuario/{idUsuario}")
    public ResponseEntity<List<?>> obtenerLogrosPorUsuario(@PathVariable Integer idUsuario) {
       List<LogroUsuario> logros =service.obtenerLogrosPorUsuario(idUsuario);
        if(logros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(logros);
    }

    @PutMapping("/Logros/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody Logro logro) {
        if (service.actualizar(id,logro)==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Logro actualizado exitosamente");
    }

    @DeleteMapping("/Logros/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Logro logro=service.obtenerPorId(id).orElse(null);
        if (logro==null){
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Logro eliminado");
    }

    @GetMapping("/Logros/contarLogros/{idUsuario}")
    public ResponseEntity<?> contarLogrosPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.contarLogrosPorUsuario(idUsuario));
    }

    @GetMapping("/Logros/otorgarLogro/{idUsuario}/{idLogro}")
    public ResponseEntity<?> otorgarLogro(@PathVariable Integer idUsuario,@PathVariable Integer idLogro) {
        return ResponseEntity.ok(service.otorgarLogro(idUsuario, idLogro));
    }

    @GetMapping("/Logros/usuarioLogro/{idUsuario}/{idLogro}")
    public ResponseEntity<?> usuarioTieneLogro(@PathVariable Integer idUsuario, @PathVariable Integer idLogro) {
        return ResponseEntity.ok(service.usuarioTieneLogro(idUsuario, idLogro));
    }

    @GetMapping("/Logros/evaluarLogrosAutomaticos/{idUsuario}")
    public ResponseEntity<?> evaluarLogrosAutomaticos(@PathVariable Integer idUsuario) {
        service.evaluarLogrosAutomaticos(idUsuario);
        return ResponseEntity.ok("Logros evaluados exitosamente");
    }




    }


