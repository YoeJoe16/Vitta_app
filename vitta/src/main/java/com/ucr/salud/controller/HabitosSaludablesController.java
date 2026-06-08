package com.ucr.salud.Controller;

import com.ucr.salud.model.EjercicioRealizado;
import com.ucr.salud.model.HabitoSaludable;
import com.ucr.salud.service.HabitoSaludableService;
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
public class HabitosSaludablesController {
    @Autowired
    private HabitoSaludableService service;

    //add
    @PostMapping("/Habitos/add")
    public ResponseEntity<?> registro(@Valid @RequestBody HabitoSaludable habitoSaludable, BindingResult result){
        if(result.hasErrors()){
            List<String> errors =new ArrayList<>();
            for (ObjectError error: result.getAllErrors()){
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        if (service.registrar(habitoSaludable)==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ya esta registrado el id o no cumple los campos obligatorios");
        }
        return ResponseEntity.ok("Ejercicio registrada exitosamente");
    }
    //findAll
    @GetMapping("/Habitos/all")
    public ResponseEntity<List<?>> obtenerTodos(){
        List<HabitoSaludable> habitosSaludables=service.obtenerTodos();
        if(habitosSaludables.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(habitosSaludables);
    }

    @GetMapping("/Habitos/get-by-id/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<HabitoSaludable> habitoSaludable = service.obtenerPorId(id);
        if (habitoSaludable.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(habitoSaludable);
    }

    @GetMapping("/Habitos/registro/{idUsuario}")
    public ResponseEntity<List<?>> obtenerPorIDUsario(@PathVariable Integer idUsuario){
        List<HabitoSaludable> habitoSaludable=service.obtenerPorIdUsario(idUsuario);
        if(habitoSaludable.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(habitoSaludable);
    }

    @GetMapping("/Habitos//completado/{idUsuario}")
    public ResponseEntity<List<?>> obtenerCompletadosPorUsuario(@PathVariable Integer idUsuario){
        List<HabitoSaludable> habitoSaludable=service.obtenerCompletadosPorUsuario(idUsuario);
        if(habitoSaludable.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(habitoSaludable);
    }

    @PutMapping("/Habitos/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody HabitoSaludable habitoSaludable) {
        if (service.actualizar(id,habitoSaludable)==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Habito actualizado exitosamente");
    }

    @PutMapping("/Habitos/actualizarCompletado/{id}")
    public ResponseEntity<?> actualizarCompletado(@PathVariable Integer id, HabitoSaludable completado) {
        if (service.actualizar(id,completado)==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Habito completado exitosamente");
    }
    @DeleteMapping("/Habitos/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        HabitoSaludable habitoSaludable=service.obtenerPorId(id).orElse(null);
        if (habitoSaludable==null){
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.ok("Habito eliminada");
    }

    @GetMapping("/Habito/sumaPuntosPorRegistro")
    public Integer sumaPuntosPorRegistro(Integer idRegistro){
        return service.sumaPuntosPorRegistro(idRegistro);
    }
}
