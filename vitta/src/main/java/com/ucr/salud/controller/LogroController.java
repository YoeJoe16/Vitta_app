package com.ucr.salud.controller;

import com.ucr.salud.model.Logro;
import com.ucr.salud.model.LogroUsuario;
import com.ucr.salud.model.User;
import com.ucr.salud.service.LogroService;
import jakarta.validation.Valid;
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
    private LogroService service;

    @GetMapping("/Logros")
    public ResponseEntity<List<?>> obtenerTodos(){
        List<Logro> Logros=service.obtenerTodos();
        if(Logros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Logros);
    }

    @GetMapping("/Logros/{id}")
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
        return ResponseEntity.ok("logro registrada exitosamente");
    }

    @GetMapping("/Logros/{idUsuario}")
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

    @DeleteMapping("/Habitos/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Logro logro=service.obtenerPorId(id).orElse(null);
        if (logro==null){
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Habito eliminada");
    }

    @GetMapping("/Logros/contarLogros/{idUsuario}")
    public long contarLogrosPorUsuario(@PathVariable Integer idUsuario) {
        return contarLogrosPorUsuario(idUsuario);
    }

    @GetMapping("/Logros/otorgarLogro/{idUsuario}/{idLogro}")
    public LogroUsuario otorgarLogro(@PathVariable Integer idUsuario,@PathVariable Integer idLogro) {
        LogroUsuario logro = service.otorgarLogro(idUsuario, idLogro);
        return logro;
    }

    @GetMapping("/Logros/otorgarLogro/{idUsuario}/{idLogro}")
    public boolean usuarioTieneLogro(@PathVariable Integer idUsuario, @PathVariable Integer idLogro) {
        boolean logro = service.usuarioTieneLogro(idUsuario, idLogro);
        return logro;
    }

    @GetMapping("Logros/evaluarLogrosAutomaticos/{idUsuario}")
    public ResponseEntity<?> evaluarLogrosAutomaticos(@PathVariable Integer idUsuario) {
        Logro logro= service.evaluarLogrosAutomaticos(idUsuario);
        if (logro==null){
            return ResponseEntity.notFound().build();
        }
        service.evaluarLogrosAutomaticos(idUsuario);
        return ResponseEntity.ok("logros evaluados");
    }




    }


