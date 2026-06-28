-- ============================================================
-- Seed data for catalog tables
-- ============================================================

-- Tipos de Comida
INSERT INTO "tb-tipoComida" (nombre, categoria, puntos_base, nivel_saludable, id_tipo_comida) VALUES
('Desayuno', 'Desayunos', 10, 'Saludable', 1),
('Almuerzo', 'Comidas principales', 15, 'Saludable', 2),
('Cena', 'Comidas principales', 12, 'Saludable', 3),
('Snack', 'Entre comidas', 5, 'Moderado', 4),
('Fruta', 'Snacks saludables', 8, 'Muy Saludable', 5),
('Bebida', 'Bebidas', 3, 'Neutro', 6),
('Postre', 'Dulces', 4, 'Ocasional', 7),
('Ensalada', 'Comidas ligeras', 9, 'Muy Saludable', 8);

-- Tipos de Ejercicio
INSERT INTO "tb-tipoEjercicio" (nombre, categoria, puntos_por_minuto, descripcion, id_tipo_ejercicio) VALUES
('Caminata', 'Cardiovascular', 2, 'Caminata ligera', 1),
('Correr', 'Cardiovascular', 5, 'Trote o carrera', 2),
('Yoga', 'Flexibilidad', 3, 'Ejercicios de estiramiento y relajacion', 3),
('Pesas', 'Fuerza', 4, 'Entrenamiento con pesas', 4),
('Natacion', 'Cardiovascular', 6, 'Natacion en piscina', 5),
('Ciclismo', 'Cardiovascular', 5, 'Andar en bicicleta', 6),
('Baile', 'Cardiovascular', 4, 'Baile aerobico', 7),
('CrossFit', 'Alta intensidad', 7, 'Entrenamiento funcional de alta intensidad', 8);

-- Rangos
INSERT INTO "tb-rango" (nombre, puntos_minimos, puntos_maximos, icono_url, color_hex) VALUES
('Bronce', 0, 999, 'shield-alt', '#CD7F32'),
('Plata', 1000, 2999, 'star', '#C0C0C0'),
('Oro', 3000, 5999, 'crown', '#FFD700'),
('Diamante', 6000, 999999, 'gem', '#B9F2FF');

-- Logros
INSERT INTO "tb-logro" (nombre, descripcion, condicion, puntos_bonus, icono_url, id_logro) VALUES
('Primer Registro', 'Completaste tu primer registro diario', 'registros >= 1', 50, 'clipboard-check', 1),
('Racha de 3 Dias', 'Registros 3 dias consecutivos', 'racha >= 3', 100, 'fire', 2),
('Racha de 7 Dias', 'Registros 7 dias consecutivos', 'racha >= 7', 200, 'fire', 3),
('Explorador de Comidas', 'Registrar 5 comidas', 'comidas >= 5', 75, 'utensils', 4),
('Deportista', 'Registrar 5 ejercicios', 'ejercicios >= 5', 75, 'dumbbell', 5),
('Estrella Fitness', 'Acumular 1000 puntos', 'puntos >= 1000', 150, 'star', 6),
('Maestro de Habitos', 'Completar 10 habitos', 'habitos >= 10', 100, 'check-double', 7),
('Leyenda', 'Alcanzar 5000 puntos', 'puntos >= 5000', 500, 'trophy', 8);
