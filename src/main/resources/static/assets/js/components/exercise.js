const ExerciseComponent = (() => {
    let tipos = [];
    let ejercicios = [];

    const loadTipos = async () => {
        const res = await API.get('/tipos-ejercicio/all');
        if (res) tipos = res;
    };

    const loadEjercicios = async () => {
        const u = Auth.getUser();
        if (!u) return;
        const res = await API.get(`/ejercicios/usuario/${u.id}`);
        if (res) ejercicios = res;
    };

    const render = () => `
    <div class="min-h-screen bg-light">
      <nav class="bg-primary text-white shadow-lg">
        <div class="max-w-6xl mx-auto px-4 py-3 flex items-center justify-between">
          <div class="flex items-center gap-2">
            <i class="fas fa-leaf text-2xl"></i>
            <span class="text-xl font-bold">Vitta</span>
          </div>
          <button onclick="navigateTo('dashboard')" class="hover:text-accent transition"><i class="fas fa-arrow-left mr-1"></i> Volver</button>
        </div>
      </nav>
      <div class="max-w-4xl mx-auto px-4 py-8">
        <h1 class="text-3xl font-bold text-primary mb-6"><i class="fas fa-running mr-2"></i>Ejercicio</h1>

        <div class="bg-white rounded-2xl shadow-lg p-6 mb-6 animate-slide-in">
          <h2 class="text-xl font-bold text-primary mb-4"><i class="fas fa-plus-circle mr-2"></i>Registrar Ejercicio</h2>
          <form id="ejercicioForm" class="space-y-4">
            <div>
              <label class="block text-sm font-medium mb-1">Tipo de ejercicio</label>
              <select id="ejTipo" required class="w-full px-4 py-3 border rounded-xl">
                <option value="">Seleccionar...</option>
                ${tipos.map(t => `<option value="${t.id}">${t.nombre} (${t.puntosPorMinuto} pts/min)</option>`).join('')}
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium mb-1">Minutos</label>
              <input type="number" id="ejMinutos" value="30" min="1" required class="w-full px-4 py-3 border rounded-xl">
            </div>
            <div>
              <label class="block text-sm font-medium mb-1">Intensidad</label>
              <select id="ejIntensidad" required class="w-full px-4 py-3 border rounded-xl">
                <option value="baja">Baja</option>
                <option value="media" selected>Media</option>
                <option value="alta">Alta</option>
              </select>
            </div>
            <button type="submit" class="w-full bg-primary text-white py-3 rounded-xl font-semibold hover:bg-opacity-90 transition">
              <i class="fas fa-save mr-2"></i> Registrar
            </button>
          </form>
        </div>

        <div class="bg-white rounded-2xl shadow-lg p-6 mb-6 animate-slide-in">
          <h2 class="text-xl font-bold text-primary mb-4"><i class="fas fa-history mr-2"></i>Ejercicios Registrados
            <span class="text-sm font-normal text-gray-500 ml-2">(${ejercicios.length})</span>
          </h2>
          ${ejercicios.length === 0 ? '<p class="text-gray-400 text-center py-8">No hay ejercicios registrados hoy</p>' : `
          <div class="space-y-3">
            ${ejercicios.map(e => {
              const t = tipos.find(x => x.id === e.idTipoEjercicio);
              return `
              <div class="flex items-center justify-between border rounded-xl p-4 hover:shadow-md transition">
                <div>
                  <div class="font-semibold">${t?.nombre || 'Ejercicio #' + e.idTipoEjercicio}</div>
                  <div class="text-sm text-gray-500">${e.minutos} min · ${e.intensidad} · +${e.puntosOtorgados} pts</div>
                </div>
                <span class="text-xs text-gray-400">${e.fecha || ''}</span>
              </div>`;
            }).join('')}
          </div>`}
        </div>

        <div class="bg-white rounded-2xl shadow-lg p-6 animate-slide-in">
          <h2 class="text-xl font-bold text-primary mb-4"><i class="fas fa-list mr-2"></i>Tipos de Ejercicio</h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
            ${tipos.map(t => `
              <div class="border rounded-xl p-4 hover:shadow-md transition">
                <div class="font-semibold">${t.nombre || '-'}</div>
                <div class="text-sm text-gray-500">${t.categoria || ''}</div>
                <div class="text-sm mt-1"><span class="font-bold text-primary">${t.puntosPorMinuto || 0}</span> pts/min</div>
              </div>
            `).join('')}
          </div>
          ${tipos.length === 0 ? '<p class="text-gray-400 text-center py-4">Sin tipos de ejercicio</p>' : ''}
        </div>
      </div>
    </div>`;

    const mount = async () => {
        await loadTipos();
        const u = Auth.getUser();
        if (!u) return;
        await loadEjercicios();

        const appEl = document.getElementById('app');
        if (appEl) appEl.innerHTML = render();

        $('ejercicioForm')?.addEventListener('submit', async (e) => {
            e.preventDefault();
            const tipoId = parseInt($('ejTipo').value);
            const mins = parseInt($('ejMinutos').value);
            const intensidad = $('ejIntensidad').value;
            if (!tipoId) { Toast.error('Selecciona un tipo'); return; }

            const res = await API.post('/ejercicios/add', {
                idUsuario: u.id,
                idTipoEjercicio: tipoId,
                minutos: mins,
                intensidad
            });
            if (res) {
                Toast.success('Ejercicio registrado');
                navigateTo('exercise');
            }
        });
    };

    return { render, mount };
})();
