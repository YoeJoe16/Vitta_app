package com.ucr.salud.Controller;

import com.ucr.salud.model.ComidaConsumida;
import com.ucr.salud.model.dto.ComidaConsumidaDTO;
import com.ucr.salud.service.ComidaConsumidaService;
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
public class ComidaConsumidaController {
    @Autowired
    private ComidaConsumidaService service;

    @GetMapping("/comidas/all")
    public ResponseEntity<List<?>> obtenerTodas(){
        List<ComidaConsumida> comidaConsumidas=service.obtenerTodas();
        if(comidaConsumidas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comidaConsumidas);
    }

    @GetMapping("/comidas/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<ComidaConsumida> comidaConsumida = service.obtenerPorId(id);
        if (comidaConsumida.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comidaConsumida);
    }
    @GetMapping("/comidas/registro/{idRegistro}")
    public ResponseEntity<List<?>> obtenerPorRegistro(@PathVariable Integer idRegistro){
        List<ComidaConsumida> comidaConsumidas=service.obtenerPorRegistro(idRegistro);
        if(comidaConsumidas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comidaConsumidas);
    }

    @GetMapping("/comidas/registro/{idRegistro}/{momentoDelDia}")
    public ResponseEntity<List<?>> obtenerPorRegistroYMomento(@PathVariable Integer idRegistro, @PathVariable String momentoDelDia){
        List<ComidaConsumida> comidaConsumidas=service.obtenerPorRegistroYMomento(idRegistro,momentoDelDia);
        if(comidaConsumidas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comidaConsumidas);
    }


    @PostMapping("/comidas/add")
    public ResponseEntity<?> registrar(@Valid @RequestBody ComidaConsumidaDTO comidaConsumida , BindingResult result){
        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();
            for (ObjectError error: result.getAllErrors()){
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        var resultadoService = service.registrar(comidaConsumida);

        if (resultadoService == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ya esta registrado el id o no cumple los campos obligatorios");
        }

        return ResponseEntity.ok("Comida registrada exitosamente");
    }

    @PutMapping("/comidas/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody ComidaConsumida comida) {
        if (service.actualizar(id,comida)==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Comida actualizada exitosamente");
    }

    @DeleteMapping("/comidas/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        ComidaConsumida comidaConsumida=service.obtenerPorId(id).orElse(null);
         if (comidaConsumida==null){
            return ResponseEntity.notFound().build();
         }
        service.eliminar(id);
         return ResponseEntity.ok("Comida eliminada");
    }

    @GetMapping("/comidas/sumaPuntosPorRegistro")
    public Integer sumaPuntosPorRegistro(@RequestParam Integer idRegistro){
        return service.sumaPuntosPorRegistro(idRegistro);
    }



    @PatchMapping("/comidas/registrarCalorias/{id}/{calorias}")
    public ResponseEntity<?> registrarCalorias(@PathVariable Integer id,@PathVariable Integer calorias) {
        Optional<ComidaConsumida> comidaConsumida = service.registrarCalorias(id,calorias);
        if (comidaConsumida.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comidaConsumida);
    }

    @GetMapping("/comidas/sumaCaloriasPorRegistro")
    public Integer sumaCaloriasPorRegistro(@RequestParam Integer idRegistro){
        return service.sumaCaloriasPorRegistro(idRegistro);
    }
}
