package com.ucr.salud.Controller;

import com.ucr.salud.model.ComidaConsumida;
import com.ucr.salud.model.EjercicioRealizado;
import com.ucr.salud.model.dto.ComidaConsumidaDTO;
import com.ucr.salud.model.dto.EjercicioRealizadoDTO;
import com.ucr.salud.service.EjercicioRealizadoService;
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
public class EjercicioRealizadoController {
    @Autowired
    private EjercicioRealizadoService service;

    //registro
    @PostMapping("/ejercicios/add")
    public ResponseEntity<?> registrar(@Valid @RequestBody EjercicioRealizadoDTO ejercicioRealizadoDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errors =new ArrayList<>();
            for (ObjectError error: result.getAllErrors()){
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        if (service.registrar(ejercicioRealizadoDTO)==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ya esta registrado el id o no cumple los campos obligatorios");
        }
        return ResponseEntity.ok("Ejercicio registrada exitosamente");
    }
    //findAll
    @GetMapping("/ejercicios/all")
    public ResponseEntity<List<?>> getAll(){
        List<EjercicioRealizado> ejercicioRealizados=service.obtenerTodos();
        if(ejercicioRealizados.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ejercicioRealizados);
    }

    @GetMapping("/ejercicios/get-by-id/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<EjercicioRealizado> ejercicioRealizado = service.obtenerPorId(id);
        if (ejercicioRealizado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ejercicioRealizado);
    }

    @GetMapping("/ejercicios/registro/{idRegistro}")
    public ResponseEntity<List<?>> obtenerPorRegistro(@PathVariable Integer idRegistro){
        List<EjercicioRealizado> ejercicioRealizado=service.obtenerPorRegistro(idRegistro);
        if(ejercicioRealizado.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ejercicioRealizado);
    }

    @GetMapping("/ejercicios/registro/{idRegistro}/{intensidad}")
    public ResponseEntity<List<?>> obtenerPorRegistroYIntesidad(@PathVariable Integer idRegistro, @PathVariable String intensidad){
        List<EjercicioRealizado> EjercicioRealizado=service.obtenerPorRegistroEIntensidad(idRegistro,intensidad);
        if(EjercicioRealizado.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(EjercicioRealizado);
    }

    @PutMapping("/ejercicios/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody EjercicioRealizado ejercicioRealizado) {
        if (service.actualizar(id,ejercicioRealizado)==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Ejercicio actualizado exitosamente");
    }

    @DeleteMapping("/ejercicios/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        EjercicioRealizado ejercicioRealizado=service.obtenerPorId(id).orElse(null);
        if (ejercicioRealizado==null){
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Ejercicio eliminado");
    }






}
