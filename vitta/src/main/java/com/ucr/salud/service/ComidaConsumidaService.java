package com.ucr.salud.service;

import com.ucr.salud.model.ComidaConsumida;
import com.ucr.salud.model.TipoComida;
import com.ucr.salud.repository.ComidaConsumidaRepository;
import com.ucr.salud.repository.RegistroDiarioRepository;
import com.ucr.salud.repository.TipoComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ComidaConsumida registrar(ComidaConsumida comida) {
        if (!registroDiarioRepository.existsById(comida.getIdRegistro())) {
            throw new RuntimeException("Registro diario no encontrado con id: " + comida.getIdRegistro());
        }

        TipoComida tipo = tipoComidaRepository.findById(comida.getIdTipoComida())
                .orElseThrow(() -> new RuntimeException("TipoComida no encontrado con id: " + comida.getIdTipoComida()));

        // Puntos = puntosBase del tipo * cantidadPorciones
        int porciones = comida.getCantidadPorciones() != null ? comida.getCantidadPorciones() : 1;
        comida.setPuntosOtorgados(tipo.getPuntosBase() * porciones);

        ComidaConsumida guardada = comidaConsumidaRepository.save(comida);

        // Recalcular puntos del día
        registroDiarioService.recalcularPuntos(comida.getIdRegistro());

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

    // Registrar o actualizar las calorías de una comida consumida
    public ComidaConsumida registrarCalorias(Integer id, Integer calorias) {
        if (calorias == null || calorias < 0) {
            throw new RuntimeException("El valor de calorías debe ser un número positivo.");
        }

        ComidaConsumida comida = comidaConsumidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ComidaConsumida no encontrada con id: " + id));

        comida.setCaloriasConsumidas(calorias);
        return comidaConsumidaRepository.save(comida);
    }

    // Suma total de calorías consumidas en un registro diario
    public Integer sumaCaloriasPorRegistro(Integer idRegistro) {
        return comidaConsumidaRepository.sumCaloriasByIdRegistro(idRegistro);
    }
}
