package com.ucr.salud.service;

import com.ucr.salud.model.RegistroDiario;
import com.ucr.salud.model.dto.RegistroDiarioDTO;
import com.ucr.salud.repository.ComidaConsumidaRepository;
import com.ucr.salud.repository.EjercicioRealizadoRepository;
import com.ucr.salud.repository.HabitoSaludableRepository;
import com.ucr.salud.repository.RegistroDiarioRepository;
import com.ucr.salud.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroDiarioService {

    @Autowired
    private RegistroDiarioRepository registroDiarioRepository;

    @Autowired
    private ComidaConsumidaRepository comidaConsumidaRepository;

    @Autowired
    private EjercicioRealizadoRepository ejercicioRealizadoRepository;

    @Autowired
    private HabitoSaludableRepository habitoSaludableRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // Obtener todos los registros
    public List<RegistroDiario> obtenerTodos() {
        return registroDiarioRepository.findAll();
    }

    // Obtener registro por ID
    public Optional<RegistroDiario> obtenerPorId(Integer id) {
        return registroDiarioRepository.findById(id);
    }

    // Obtener registros de un usuario (ordenados por fecha desc)
    public List<RegistroDiario> obtenerPorUsuario(Integer idUsuario) {
        return registroDiarioRepository.findByIdUsuarioOrderByFechaDesc(idUsuario);
    }

    // Obtener registro de un usuario por fecha específica
    public Optional<RegistroDiario> obtenerPorUsuarioYFecha(Integer idUsuario, String fecha) {
        return registroDiarioRepository.findByIdUsuarioAndFecha(idUsuario, fecha);
    }

    // Crear nuevo registro diario (solo uno por usuario por día)
    public RegistroDiario add(RegistroDiarioDTO dto) {
        if (!userRepository.existsById(dto.getIdUsuario())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con id: " + dto.getIdUsuario());
        }
        registroDiarioRepository.findByIdUsuarioAndFecha(dto.getIdUsuario(), dto.getFecha())
                .ifPresent(r -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un registro para ese día");
                });

        RegistroDiario registro = new RegistroDiario();
        registro.setIdUsuario(dto.getIdUsuario());
        registro.setFecha(dto.getFecha());
        registro.setHoraRegistro(dto.getHoraRegistro());
        registro.setNotas(dto.getNotas());
        registro.setPuntosDelDia(0);
        return registroDiarioRepository.save(registro);
    }

    // Actualizar notas u hora de registro
    public RegistroDiario actualizar(Integer id, RegistroDiario datos) {
        RegistroDiario registro = registroDiarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con id: " + id));

        if (datos.getNotas() != null) {
            registro.setNotas(datos.getNotas());
        }
        if (datos.getHoraRegistro() != null) {
            registro.setHoraRegistro(datos.getHoraRegistro());
        }
        return registroDiarioRepository.save(registro);
    }

    // Recalcula los puntos del día sumando comidas + ejercicios + hábitos
    // y propaga la diferencia al total del usuario
    public RegistroDiario recalcularPuntos(Integer id) {
        RegistroDiario registro = registroDiarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con id: " + id));

        int puntosComida    = comidaConsumidaRepository.sumPuntosByIdRegistro(id);
        int puntosEjercicio = ejercicioRealizadoRepository.sumPuntosByIdRegistro(id);
        int puntosHabito    = habitoSaludableRepository.sumPuntosByIdUsuario(registro.getIdUsuario());

        int puntosAnteriores = registro.getPuntosDelDia() != null ? registro.getPuntosDelDia() : 0;
        int nuevoPuntaje     = puntosComida + puntosEjercicio + puntosHabito;
        int diferencia       = nuevoPuntaje - puntosAnteriores;

        registro.setPuntosDelDia(nuevoPuntaje);
        registroDiarioRepository.save(registro);

        // Propagar diferencia al total de puntos del usuario
        if (diferencia != 0) {
            userService.agregarPuntos(registro.getIdUsuario(), diferencia);
        }

        return registro;
    }

    // Eliminar registro
    public void eliminar(Integer id) {
        if (!registroDiarioRepository.existsById(id)) {
            throw new RuntimeException("Registro no encontrado con id: " + id);
        }
        registroDiarioRepository.deleteById(id);
    }
}
