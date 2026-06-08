package com.ucr.salud.service;

import com.ucr.salud.model.Logro;
import com.ucr.salud.model.LogroUsuario;
import com.ucr.salud.model.User;
import com.ucr.salud.repository.LogroRepository;
import com.ucr.salud.repository.LogroUsuarioRepository;
import com.ucr.salud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LogroService {

    @Autowired
    private LogroRepository logroRepository;

    @Autowired
    private LogroUsuarioRepository logroUsuarioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // ─── Catálogo de logros ───────────────────────────────────────────────────

    public List<Logro> obtenerTodos() {
        return logroRepository.findAll();
    }

    public Optional<Logro> obtenerPorId(Integer id) {
        return logroRepository.findById(id);
    }

    public Optional<Logro> obtenerPorNombre(String nombre) {
        return logroRepository.findByNombre(nombre);
    }

    public Logro crear(Logro logro) {
        if (logroRepository.existsByNombre(logro.getNombre())) {
            throw new RuntimeException("Ya existe un logro con el nombre: " + logro.getNombre());
        }
        return logroRepository.save(logro);
    }

    public Logro actualizar(Integer id, Logro datos) {
        Logro logro = logroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Logro no encontrado con id: " + id));
        if (datos.getNombre()      != null) logro.setNombre(datos.getNombre());
        if (datos.getDescripcion() != null) logro.setDescripcion(datos.getDescripcion());
        if (datos.getCondicion()   != null) logro.setCondicion(datos.getCondicion());
        if (datos.getPuntosBonus() != null) logro.setPuntosBonus(datos.getPuntosBonus());
        if (datos.getIconoUrl()    != null) logro.setIconoUrl(datos.getIconoUrl());
        return logroRepository.save(logro);
    }

    public void eliminar(Integer id) {
        if (!logroRepository.existsById(id)) {
            throw new RuntimeException("Logro no encontrado con id: " + id);
        }
        logroRepository.deleteById(id);
    }

    // ─── Logros de un usuario ─────────────────────────────────────────────────

    public List<LogroUsuario> obtenerLogrosPorUsuario(Integer idUsuario) {
        return logroUsuarioRepository.findByIdUsuario(idUsuario);
    }

    public long contarLogrosPorUsuario(Integer idUsuario) {
        return logroUsuarioRepository.countByIdUsuario(idUsuario);
    }

    // Otorgar un logro a un usuario (idempotente: no duplica si ya lo tiene)
    public LogroUsuario otorgarLogro(Integer idUsuario, Integer idLogro) {
        if (!userRepository.existsById(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado con id: " + idUsuario);
        }

        Logro logro = logroRepository.findById(idLogro)
                .orElseThrow(() -> new RuntimeException("Logro no encontrado con id: " + idLogro));

        if (logroUsuarioRepository.existsByIdUsuarioAndIdLogro(idUsuario, idLogro)) {
            return logroUsuarioRepository.findByIdUsuarioAndIdLogro(idUsuario, idLogro).get();
        }

        LogroUsuario logroUsuario = new LogroUsuario();
        logroUsuario.setIdUsuario(idUsuario);
        logroUsuario.setIdLogro(idLogro);
        logroUsuario.setFechaObtenido(LocalDate.now().toString());

        LogroUsuario guardado = logroUsuarioRepository.save(logroUsuario);

        // Sumar puntos bonus al usuario
        if (logro.getPuntosBonus() != null && logro.getPuntosBonus() > 0) {
            userService.agregarPuntos(idUsuario, logro.getPuntosBonus());
        }

        return guardado;
    }

    // Verificar si un usuario ya posee un logro
    public boolean usuarioTieneLogro(Integer idUsuario, Integer idLogro) {
        return logroUsuarioRepository.existsByIdUsuarioAndIdLogro(idUsuario, idLogro);
    }

    // Evaluar y otorgar logros automáticamente según puntos totales del usuario
    public Logro evaluarLogrosAutomaticos(Integer idUsuario) {
        User user = userRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));

        List<Logro> todos = logroRepository.findAll();
        for (Logro logro : todos) {
            if (logroUsuarioRepository.existsByIdUsuarioAndIdLogro(idUsuario, logro.getId())) {
                continue; // ya tiene este logro
            }
            if (cumpleCondicion(user, logro)) {
                otorgarLogro(idUsuario, logro.getId());
            }
        }
        return null;
    }

    // Evaluación simple de condición basada en puntos totales
    // Condición esperada en formato "puntos>=500" o "puntos>=1000"
    private boolean cumpleCondicion(User user, Logro logro) {
        if (logro.getCondicion() == null || logro.getCondicion().isBlank()) return false;
        try {
            if (logro.getCondicion().startsWith("puntos>=")) {
                int umbral = Integer.parseInt(logro.getCondicion().replace("puntos>=", "").trim());
                return user.getTotalPoints() != null && user.getTotalPoints() >= umbral;
            }
        } catch (NumberFormatException ignored) {
        }
        return false;
    }
}
