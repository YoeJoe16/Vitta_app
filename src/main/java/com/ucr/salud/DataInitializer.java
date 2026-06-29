package com.ucr.salud;

import com.ucr.salud.model.Logro;
import com.ucr.salud.model.Rango;
import com.ucr.salud.model.TipoComida;
import com.ucr.salud.model.TipoEjercicio;
import com.ucr.salud.repository.LogroRepository;
import com.ucr.salud.repository.RangoRepository;
import com.ucr.salud.repository.TipoComidaRepository;
import com.ucr.salud.repository.TipoEjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private RangoRepository rangoRepository;
    @Autowired private TipoEjercicioRepository tipoEjercicioRepository;
    @Autowired private TipoComidaRepository tipoComidaRepository;
    @Autowired private LogroRepository logroRepository;

    @Override
    public void run(String... args) {
        if (rangoRepository.count() > 0) return;

        // --- Rangos ---
        rangoRepository.save(new Rango(null, "Bronce", 0, 99, null, "#CD7F32"));
        rangoRepository.save(new Rango(null, "Plata", 100, 299, null, "#C0C0C0"));
        rangoRepository.save(new Rango(null, "Oro", 300, 599, null, "#FFD700"));
        rangoRepository.save(new Rango(null, "Diamante", 600, 999, null, "#B9F2FF"));
        rangoRepository.save(new Rango(null, "Elite", 1000, 9999, null, "#FF00FF"));

        // --- Tipos de ejercicio ---
        tipoEjercicioRepository.save(new TipoEjercicio(null, "Correr", "Cardio", 5, "Running al aire libre o en cinta"));
        tipoEjercicioRepository.save(new TipoEjercicio(null, "Caminar", "Cardio", 3, "Caminata a paso moderado"));
        tipoEjercicioRepository.save(new TipoEjercicio(null, "Ciclismo", "Cardio", 6, "Andar en bicicleta"));
        tipoEjercicioRepository.save(new TipoEjercicio(null, "Natación", "Cardio", 7, "Nadar en piscina"));
        tipoEjercicioRepository.save(new TipoEjercicio(null, "Yoga", "Flexibilidad", 2, "Posturas y respiración"));
        tipoEjercicioRepository.save(new TipoEjercicio(null, "Pesas", "Fuerza", 4, "Entrenamiento con pesas"));
        tipoEjercicioRepository.save(new TipoEjercicio(null, "CrossFit", "Alta intensidad", 8, "Entrenamiento funcional intenso"));
        tipoEjercicioRepository.save(new TipoEjercicio(null, "Baile", "Cardio", 4, "Bailar cualquier ritmo"));

        // --- Tipos de comida ---
        tipoComidaRepository.save(new TipoComida(null, "Ensalada", "Saludable", 10, "Alto"));
        tipoComidaRepository.save(new TipoComida(null, "Fruta", "Saludable", 8, "Alto"));
        tipoComidaRepository.save(new TipoComida(null, "Verduras cocidas", "Saludable", 7, "Alto"));
        tipoComidaRepository.save(new TipoComida(null, "Pollo a la plancha", "Proteína", 9, "Alto"));
        tipoComidaRepository.save(new TipoComida(null, "Pescado", "Proteína", 10, "Alto"));
        tipoComidaRepository.save(new TipoComida(null, "Huevos", "Proteína", 6, "Medio"));
        tipoComidaRepository.save(new TipoComida(null, "Arroz integral", "Carbohidrato", 5, "Medio"));
        tipoComidaRepository.save(new TipoComida(null, "Pasta integral", "Carbohidrato", 4, "Medio"));
        tipoComidaRepository.save(new TipoComida(null, "Pan blanco", "Carbohidrato", 2, "Bajo"));
        tipoComidaRepository.save(new TipoComida(null, "Pizza", "Comida rápida", 1, "Bajo"));
        tipoComidaRepository.save(new TipoComida(null, "Hamburguesa", "Comida rápida", 1, "Bajo"));
        tipoComidaRepository.save(new TipoComida(null, "Refresco", "Bebida", 0, "Bajo"));
        tipoComidaRepository.save(new TipoComida(null, "Agua", "Bebida", 5, "Alto"));

        // --- Logros ---
        logroRepository.save(new Logro(null, "Primeros pasos", "Registra tu primer día", "puntos>=1", 10, null));
        logroRepository.save(new Logro(null, "Punto coleccionista", "Alcanza 100 puntos", "puntos>=100", 20, null));
        logroRepository.save(new Logro(null, "Mitad de camino", "Alcanza 500 puntos", "puntos>=500", 50, null));
        logroRepository.save(new Logro(null, "1000 puntos", "Alcanza 1000 puntos", "puntos>=1000", 100, null));
        logroRepository.save(new Logro(null, "Dedicado", "Completa 7 días seguidos", null, 30, null));
        logroRepository.save(new Logro(null, "Rutina saludable", "Completa 30 días seguidos", null, 100, null));
    }
}
