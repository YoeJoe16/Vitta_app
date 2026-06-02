package com.ucr.salud.service;

import com.ucr.salud.model.Rango;
import com.ucr.salud.repository.RangoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RangoService {

    @Autowired
    private RangoRepository rangoRepository;

    // Obtener todos los rangos
    public List<Rango> obtenerTodos() {
        return rangoRepository.findAll();
    }

    // Obtener rango por ID
    public Optional<Rango> obtenerPorId(Integer id) {
        return rangoRepository.findById(id);
    }

    // Obtener rango por nombre
    public Optional<Rango> obtenerPorNombre(String nombre) {
        return rangoRepository.findByNombre(nombre);
    }

    // Obtener el rango que corresponde a una cantidad de puntos
    public Optional<Rango> obtenerPorPuntos(Integer puntos) {
        return rangoRepository.findByPuntos(puntos);
    }

    // Crear nuevo rango
    public Rango crear(Rango rango) {
        return rangoRepository.save(rango);
    }

    // Actualizar rango existente
    public Rango actualizar(Integer id, Rango datos) {
        Rango rango = rangoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rango no encontrado con id: " + id));

        if (datos.getNombre()        != null) rango.setNombre(datos.getNombre());
        if (datos.getPuntosMinimos() != null) rango.setPuntosMinimos(datos.getPuntosMinimos());
        if (datos.getPuntosMaximos() != null) rango.setPuntosMaximos(datos.getPuntosMaximos());
        if (datos.getIconoUrl()      != null) rango.setIconoUrl(datos.getIconoUrl());
        if (datos.getColorHex()      != null) rango.setColorHex(datos.getColorHex());

        return rangoRepository.save(rango);
    }

    // Eliminar rango
    public void eliminar(Integer id) {
        if (!rangoRepository.existsById(id)) {
            throw new RuntimeException("Rango no encontrado con id: " + id);
        }
        rangoRepository.deleteById(id);
    }
}
