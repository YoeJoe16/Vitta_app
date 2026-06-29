package com.ucr.salud.service;

import com.ucr.salud.model.ComidaConsumida;
import com.ucr.salud.model.RegistroDiario;
import com.ucr.salud.model.TipoComida;
import com.ucr.salud.model.dto.ComidaConsumidaDTO;
import com.ucr.salud.repository.ComidaConsumidaRepository;
import com.ucr.salud.repository.RegistroDiarioRepository;
import com.ucr.salud.repository.TipoComidaRepository;
import com.ucr.salud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private UserRepository userRepository;

    @Autowired
    private RegistroDiarioService registroDiarioService;

    @Autowired
    private LogroService logroService;

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
        if (!userRepository.existsById(dto.getIdUsuario())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con id: " + dto.getIdUsuario());
        }
        TipoComida tipo = tipoComidaRepository.findById(dto.getIdTipoComida())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de comida no encontrado con id: " + dto.getIdTipoComida()));

        RegistroDiario registro = registroDiarioRepository
                .findByIdUsuarioAndFecha(dto.getIdUsuario(), LocalDate.now().toString())
                .orElseGet(() -> {
                    RegistroDiario nuevo = new RegistroDiario();
                    nuevo.setIdUsuario(dto.getIdUsuario());
                    nuevo.setFecha(LocalDate.now().toString());
                    nuevo.setHoraRegistro(LocalTime.now().toString());
                    nuevo.setPuntosDelDia(0);
                    return registroDiarioRepository.save(nuevo);
                });

        int puntos = tipo.getPuntosBase() * dto.getCantidadPorciones();

        ComidaConsumida comida = new ComidaConsumida();
        comida.setIdRegistro(registro.getId());
        comida.setIdTipoComida(dto.getIdTipoComida());
        comida.setCantidadPorciones(dto.getCantidadPorciones());
        comida.setMomentoDelDia(dto.getMomentoDelDia());
        comida.setPuntosOtorgados(puntos);

        ComidaConsumida guardada = comidaConsumidaRepository.save(comida);
        registroDiarioService.recalcularPuntos(registro.getId());
        logroService.evaluarLogrosAutomaticos(dto.getIdUsuario());
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
    public Optional<ComidaConsumida> registrarCalorias(Integer id, Integer calorias) {
        if (calorias == null || calorias < 0) {
            throw new RuntimeException("El valor de calorías debe ser un número positivo.");
        }

        ComidaConsumida comida = comidaConsumidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ComidaConsumida no encontrada con id: " + id));

        comida.setCaloriasConsumidas(calorias);
        return Optional.of(comidaConsumidaRepository.save(comida));
    }

    // Suma total de calorías consumidas en un registro diario
    public Integer sumaCaloriasPorRegistro(Integer idRegistro) {
        return comidaConsumidaRepository.sumCaloriasByIdRegistro(idRegistro);
    }
}
