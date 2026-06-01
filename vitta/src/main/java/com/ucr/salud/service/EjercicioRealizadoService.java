package com.ucr.salud.service;

import com.ucr.salud.model.EjercicioRealizado;
import com.ucr.salud.model.TipoEjercicio;
import com.ucr.salud.repository.EjercicioRealizadoRepository;
import com.ucr.salud.repository.RegistroDiarioRepository;
import com.ucr.salud.repository.TipoEjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EjercicioRealizadoService {

    @Autowired
    private EjercicioRealizadoRepository ejercicioRealizadoRepository;

    @Autowired
    private TipoEjercicioRepository tipoEjercicioRepository;

    @Autowired
    private RegistroDiarioRepository registroDiarioRepository;

    @Autowired
    private RegistroDiarioService registroDiarioService;

    // Obtener todos los ejercicios realizados
    public List<EjercicioRealizado> obtenerTodos() {
        return ejercicioRealizadoRepository.findAll();
    }

    // Obtener por ID
    public Optional<EjercicioRealizado> obtenerPorId(Integer id) {
        return ejercicioRealizadoRepository.findById(id);
    }

    // Obtener ejercicios de un registro diario
    public List<EjercicioRealizado> obtenerPorRegistro(Integer idRegistro) {
        return ejercicioRealizadoRepository.findByIdRegistro(idRegistro);
    }

    // Filtrar por intensidad dentro de un registro
    public List<EjercicioRealizado> obtenerPorRegistroEIntensidad(Integer idRegistro, String intensidad) {
        return ejercicioRealizadoRepository.findByIdRegistroAndIntensidad(idRegistro, intensidad);
    }

    // Registrar un ejercicio; puntos = puntosPorMinuto * minutos
    public EjercicioRealizado registrar(EjercicioRealizado ejercicio) {
        if (!registroDiarioRepository.existsById(ejercicio.getIdRegistro())) {
            throw new RuntimeException("Registro diario no encontrado con id: " + ejercicio.getIdRegistro());
        }

        TipoEjercicio tipo = tipoEjercicioRepository.findById(ejercicio.getIdTipoEjercicio())
                .orElseThrow(() -> new RuntimeException("TipoEjercicio no encontrado con id: " + ejercicio.getIdTipoEjercicio()));

        int minutos = ejercicio.getMinutos() != null ? ejercicio.getMinutos() : 0;
        ejercicio.setPuntosOtorgados(tipo.getPuntosPorMinuto() * minutos);

        EjercicioRealizado guardado = ejercicioRealizadoRepository.save(ejercicio);
        registroDiarioService.recalcularPuntos(ejercicio.getIdRegistro());

        return guardado;
    }

    // Actualizar minutos o intensidad y recalcular puntos
    public EjercicioRealizado actualizar(Integer id, EjercicioRealizado datos) {
        EjercicioRealizado ejercicio = ejercicioRealizadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EjercicioRealizado no encontrado con id: " + id));

        if (datos.getMinutos() != null) {
            ejercicio.setMinutos(datos.getMinutos());
        }
        if (datos.getIntensidad() != null) {
            ejercicio.setIntensidad(datos.getIntensidad());
        }

        TipoEjercicio tipo = tipoEjercicioRepository.findById(ejercicio.getIdTipoEjercicio())
                .orElseThrow(() -> new RuntimeException("TipoEjercicio no encontrado con id: " + ejercicio.getIdTipoEjercicio()));
        ejercicio.setPuntosOtorgados(tipo.getPuntosPorMinuto() * ejercicio.getMinutos());

        EjercicioRealizado actualizado = ejercicioRealizadoRepository.save(ejercicio);
        registroDiarioService.recalcularPuntos(ejercicio.getIdRegistro());

        return actualizado;
    }

    // Eliminar un ejercicio y recalcular puntos del registro
    public void eliminar(Integer id) {
        EjercicioRealizado ejercicio = ejercicioRealizadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EjercicioRealizado no encontrado con id: " + id));
        Integer idRegistro = ejercicio.getIdRegistro();
        ejercicioRealizadoRepository.deleteById(id);
        registroDiarioService.recalcularPuntos(idRegistro);
    }

    // Suma total de puntos de ejercicios en un registro
    public Integer sumaPuntosPorRegistro(Integer idRegistro) {
        return ejercicioRealizadoRepository.sumPuntosByIdRegistro(idRegistro);
    }

    // Total de minutos de ejercicio en un registro
    public Integer sumaMinutosPorRegistro(Integer idRegistro) {
        return ejercicioRealizadoRepository.sumMinutosByIdRegistro(idRegistro);
    }
}
