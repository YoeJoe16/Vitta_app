package com.ucr.salud.service;

import com.ucr.salud.model.TipoComida;
import com.ucr.salud.repository.TipoComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoComidaService {

    @Autowired
    private TipoComidaRepository tipoComidaRepository;

    public List<TipoComida> obtenerTodos() {
        return tipoComidaRepository.findAll();
    }

    public Optional<TipoComida> obtenerPorId(Integer id) {
        return tipoComidaRepository.findById(id);
    }

    public Optional<TipoComida> obtenerPorNombre(String nombre) {
        return tipoComidaRepository.findByNombre(nombre);
    }

    public List<TipoComida> obtenerPorCategoria(String categoria) {
        return tipoComidaRepository.findByCategoria(categoria);
    }

    public List<TipoComida> obtenerPorNivelSaludable(String nivelSaludable) {
        return tipoComidaRepository.findByNivelSaludable(nivelSaludable);
    }

    public TipoComida crear(TipoComida tipoComida) {
        return tipoComidaRepository.save(tipoComida);
    }

    public TipoComida actualizar(Integer id, TipoComida datos) {
        TipoComida tipo = tipoComidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoComida no encontrado con id: " + id));

        if (datos.getNombre()         != null) tipo.setNombre(datos.getNombre());
        if (datos.getCategoria()      != null) tipo.setCategoria(datos.getCategoria());
        if (datos.getPuntosBase()     != null) tipo.setPuntosBase(datos.getPuntosBase());
        if (datos.getNivelSaludable() != null) tipo.setNivelSaludable(datos.getNivelSaludable());

        return tipoComidaRepository.save(tipo);
    }

    public void eliminar(Integer id) {
        if (!tipoComidaRepository.existsById(id)) {
            throw new RuntimeException("TipoComida no encontrado con id: " + id);
        }
        tipoComidaRepository.deleteById(id);
    }
}
