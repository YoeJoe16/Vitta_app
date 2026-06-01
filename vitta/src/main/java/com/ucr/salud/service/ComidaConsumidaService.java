package com.ucr.salud.service;

import com.ucr.salud.model.ComidaConsumida;
import com.ucr.salud.model.TipoComida;
import com.ucr.salud.model.dto.ComidaConsumidaDTO;
import com.ucr.salud.repository.ComidaConsumidaRepository;
import com.ucr.salud.repository.RegistroDiarioRepository;
import com.ucr.salud.repository.TipoComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ComidaConsumidaService {

    @Autowired
    private ComidaConsumidaRepository comidaConsumidaRepository;

    @Autowired
    private TipoComidaRepository tipoComidaRepository;

    @Autowired
    private RegistroDiarioRepository registroDiarioRepository;

    @Autowired
    private RegistroDiarioService registroDiarioService;

    // Obtener todas las comidas consumidas
    public List<ComidaConsumida> obtenerTodas() {
        return comidaConsumidaRepository.findAll();
    }

    // Obtener por ID
    public Optional<ComidaConsumida> obtenerPorId(Integer id) {
        return comidaConsumidaRepository.findById(id);
    }

    // Obtener todas las comidas de un registro diario
    public List<ComidaConsumida> obtenerPorRegistro(Integer idRegistro) {
        return comidaConsumidaRepository.findByIdRegistro(idRegistro);
    }

    // Obtener comidas de un registro filtradas por momento del día
    public List<ComidaConsumida> obtenerPorRegistroYMomento(Integer idRegistro, String momentoDelDia) {
        return comidaConsumidaRepository.findByIdRegistroAndMomentoDelDia(idRegistro, momentoDelDia);
    }

    // Registrar una comida consumida; calcula puntos automáticamente
    public ComidaConsumida registrar(ComidaConsumidaDTO dto) {
        if (!registroDiarioRepository.existsById(dto.getIdRegistro())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado con id: " + dto.getIdRegistro());
        }
        TipoComida tipo = tipoComidaRepository.findById(dto.getIdTipoComida())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de comida no encontrado con id: " + dto.getIdTipoComida()));

        int puntos = tipo.getPuntosBase() * dto.getCantidadPorciones();

        ComidaConsumida comida = new ComidaConsumida();
        comida.setIdRegistro(dto.getIdRegistro());
        comida.setIdTipoComida(dto.getIdTipoComida());
        comida.setCantidadPorciones(dto.getCantidadPorciones());
        comida.setMomentoDelDia(dto.getMomentoDelDia());
        comida.setPuntosOtorgados(puntos);

        ComidaConsumida guardada = comidaConsumidaRepository.save(comida);
        registroDiarioService.recalcularPuntos(dto.getIdRegistro());
        return guardada;
    }

    // Actualizar porciones o momento del día y recalcular puntos
    public ComidaConsumida actualizar(Integer id, ComidaConsumida datos) {
        ComidaConsumida comida = comidaConsumidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ComidaConsumida no encontrada con id: " + id));

        if (datos.getCantidadPorciones() != null) {
            comida.setCantidadPorciones(datos.getCantidadPorciones());
        }
        if (datos.getMomentoDelDia() != null) {
            comida.setMomentoDelDia(datos.getMomentoDelDia());
        }

        // Recalcular puntos con el nuevo número de porciones
        TipoComida tipo = tipoComidaRepository.findById(comida.getIdTipoComida())
                .orElseThrow(() -> new RuntimeException("TipoComida no encontrado con id: " + comida.getIdTipoComida()));
        comida.setPuntosOtorgados(tipo.getPuntosBase() * comida.getCantidadPorciones());

        ComidaConsumida actualizada = comidaConsumidaRepository.save(comida);
        registroDiarioService.recalcularPuntos(comida.getIdRegistro());

        return actualizada;
    }

    // Eliminar una comida consumida y recalcular puntos del registro
    public void eliminar(Integer id) {
        ComidaConsumida comida = comidaConsumidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ComidaConsumida no encontrada con id: " + id));
        Integer idRegistro = comida.getIdRegistro();
        comidaConsumidaRepository.deleteById(id);
        registroDiarioService.recalcularPuntos(idRegistro);
    }

    // Suma total de puntos de comidas en un registro
    public Integer sumaPuntosPorRegistro(Integer idRegistro) {
        return comidaConsumidaRepository.sumPuntosByIdRegistro(idRegistro);
    }
}
