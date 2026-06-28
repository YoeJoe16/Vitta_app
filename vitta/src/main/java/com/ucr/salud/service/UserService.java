package com.ucr.salud.service;

import com.ucr.salud.model.Rango;
import com.ucr.salud.model.User;
import com.ucr.salud.model.dto.UserDTO;
import com.ucr.salud.repository.RangoRepository;
import com.ucr.salud.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RangoRepository rangoRepository;

    // Obtener todos los usuarios
    public List<User> obtenerTodos() {
        return userRepository.findAll();
    }

    // Obtener usuario por ID
    public Optional<User> obtenerPorId(Integer id) {
        return userRepository.findById(id);
    }

    // Obtener usuario por email
    public Optional<User> obtenerPorEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Crear nuevo usuario
    public User add(UserDTO dto) throws BadRequestException {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Ya existe un usuario con ese correo");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setTotalPoints(0);
        user.setRange("Bronce");
        return userRepository.save(user);
    }

    // Actualizar usuario
    public User actualizar(Integer id, User datosActualizados) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        user.setName(datosActualizados.getName());
        user.setEmail(datosActualizados.getEmail());
        if (datosActualizados.getPassword() != null && !datosActualizados.getPassword().isBlank()) {
            user.setPassword(datosActualizados.getPassword());
        }
        return userRepository.save(user);
    }

    // Agregar puntos al usuario y recalcular rango
    public User agregarPuntos(Integer id, Integer puntos) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        user.setTotalPoints(user.getTotalPoints() + puntos);
        actualizarRango(user);
        return userRepository.save(user);
    }

    // Actualiza el rango del usuario segun sus puntos totales
    public void actualizarRango(User user) {
        Optional<Rango> rango = rangoRepository.findByPuntos(user.getTotalPoints());
        rango.ifPresent(r -> user.setRange(r.getNombre()));
    }

    // Eliminar usuario
    public void eliminar(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        userRepository.deleteById(id);
    }

    // Verificar si existe por email
    public boolean existePorEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Login simulado (sin JWT): busca por email y compara contrase�a en texto plano
    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword() != null && u.getPassword().equals(password));
    }
}
