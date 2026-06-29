package com.ucr.salud.service;

import com.ucr.salud.model.HabitoSaludable;
import com.ucr.salud.repository.HabitoSaludableRepository;
import com.ucr.salud.repository.RegistroDiarioRepository;
import com.ucr.salud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitoSaludableService {

    // Puntos fijos por completar un hábito saludable
    private static final int PUNTOS_POR_HABITO = 10;

    @Autowired
    private HabitoSaludableRepository habitoSaludableRepository;

    @Autowired
    private RegistroDiarioRepository registroDiarioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistroDiarioService registroDiarioService;

    @Autowired
    private LogroService logroService;

    // Obtener todos los hábitos
    public List<HabitoSaludable> obtenerTodos() {
        return habitoSaludableRepository.findAll();
    }

    // Obtener por ID
    public Optional<HabitoSaludable> obtenerPorId(Integer id) {
        return habitoSaludableRepository.findById(id);
    }

    // Obtener hábitos de un registro diario
    public List<HabitoSaludable> obtenerPorusuario(Integer idUsuario) {
        return habitoSaludableRepository.findByIdUsuario(idUsuario);
    }

    // Obtener sólo los hábitos completados de un registro
    public List<HabitoSaludable> obtenerCompletadosPorUsuario(Integer idUsuario) {
        return habitoSaludableRepository.findByIdUsuarioAndCompletado(idUsuario, true);
    }

    // Registrar un hábito; asigna puntos si está completado
    public HabitoSaludable registrar(HabitoSaludable habito) {
        if (!userRepository.existsById(habito.getIdUsuario())) {
            throw new RuntimeException("Usuario no encontrado con id: " + habito.getIdUsuario());
        }

        habito.setPuntosOtorgados(Boolean.TRUE.equals(habito.getCompletado()) ? PUNTOS_POR_HABITO : 0);

        HabitoSaludable guardado = habitoSaludableRepository.save(habito);
        registroDiarioService.recalcularPuntos(habito.getIdUsuario());
        logroService.evaluarLogrosAutomaticos(habito.getIdUsuario());

        return guardado;
    }

    // Marcar hábito como completado o no completado
    public HabitoSaludable actualizarCompletado(Integer id, Boolean completado) {
        HabitoSaludable habito = habitoSaludableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HabitoSaludable no encontrado con id: " + id));

        habito.setCompletado(completado);
        habito.setPuntosOtorgados(Boolean.TRUE.equals(completado) ? PUNTOS_POR_HABITO : 0);

        HabitoSaludable actualizado = habitoSaludableRepository.save(habito);
        registroDiarioService.recalcularPuntos(habito.getIdUsuario());
        logroService.evaluarLogrosAutomaticos(habito.getIdUsuario());

        return actualizado;
    }

    // Actualizar tipo de hábito
    public HabitoSaludable actualizar(Integer id, HabitoSaludable datos) {
        HabitoSaludable habito = habitoSaludableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HabitoSaludable no encontrado con id: " + id));

        if (datos.getTipoHabito() != null) {
            habito.setTipoHabito(datos.getTipoHabito());
        }
        if (datos.getCompletado() != null) {
            habito.setCompletado(datos.getCompletado());
            habito.setPuntosOtorgados(Boolean.TRUE.equals(datos.getCompletado()) ? PUNTOS_POR_HABITO : 0);
        }

        HabitoSaludable actualizado = habitoSaludableRepository.save(habito);
        registroDiarioService.recalcularPuntos(habito.getIdUsuario());

        return actualizado;
    }

    // Eliminar hábito y recalcular puntos del registro
    public void eliminar(Integer id) {
        HabitoSaludable habito = habitoSaludableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HabitoSaludable no encontrado con id: " + id));
        Integer idRegistro = habito.getIdUsuario();
        habitoSaludableRepository.deleteById(id);
        registroDiarioService.recalcularPuntos(idRegistro);
    }

    // Suma de puntos de hábitos de un usuario
    public Integer sumaPuntosPorUsuario(Integer idUsuario) {
        return habitoSaludableRepository.sumPuntosByIdUsuario(idUsuario);
    }
}
