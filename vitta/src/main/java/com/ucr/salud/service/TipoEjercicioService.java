package com.ucr.salud.service;

import com.ucr.salud.model.TipoEjercicio;
import com.ucr.salud.repository.TipoEjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoEjercicioService {

    @Autowired
    private TipoEjercicioRepository tipoEjercicioRepository;

    public List<TipoEjercicio> obtenerTodos() {
        return tipoEjercicioRepository.findAll();
    }

    public Optional<TipoEjercicio> obtenerPorId(Integer id) {
        return tipoEjercicioRepository.findById(id);
    }

    public Optional<TipoEjercicio> obtenerPorNombre(String nombre) {
        return tipoEjercicioRepository.findByNombre(nombre);
    }

    public List<TipoEjercicio> obtenerPorCategoria(String categoria) {
        return tipoEjercicioRepository.findByCategoria(categoria);
    }

    public TipoEjercicio crear(TipoEjercicio tipoEjercicio) {
        return tipoEjercicioRepository.save(tipoEjercicio);
    }

    public TipoEjercicio actualizar(Integer id, TipoEjercicio datos) {
        TipoEjercicio tipo = tipoEjercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoEjercicio no encontrado con id: " + id));

        if (datos.getNombre()          != null) tipo.setNombre(datos.getNombre());
        if (datos.getCategoria()       != null) tipo.setCategoria(datos.getCategoria());
        if (datos.getPuntosPorMinuto() != null) tipo.setPuntosPorMinuto(datos.getPuntosPorMinuto());
        if (datos.getDescripcion()     != null) tipo.setDescripcion(datos.getDescripcion());

        return tipoEjercicioRepository.save(tipo);
    }

    public void eliminar(Integer id) {
        if (!tipoEjercicioRepository.existsById(id)) {
            throw new RuntimeException("TipoEjercicio no encontrado con id: " + id);
        }
        tipoEjercicioRepository.deleteById(id);
    }
}
