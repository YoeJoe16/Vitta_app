const HabitsComponent = (() => {
    let habits = [];

    const loadHabits = async () => {
        const u = Auth.getUser();
        if (!u) return;
        const res = await API.get(`/Habitos/usuario/${u.id}`);
        if (res) habits = res;
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
        <h1 class="text-3xl font-bold text-primary mb-6"><i class="fas fa-check-double mr-2"></i>Hábitos Saludables</h1>

        <div class="bg-white rounded-2xl shadow-lg p-6 mb-6 animate-slide-in">
          <h2 class="text-xl font-bold text-primary mb-4"><i class="fas fa-plus-circle mr-2"></i>Nuevo Hábito</h2>
          <form id="habitoForm" class="space-y-4">
            <div>
              <label class="block text-sm font-medium mb-1">Tipo de hábito</label>
              <input type="text" id="habitoTipo" required placeholder="Ej: Beber 2L agua"
                class="w-full px-4 py-3 border rounded-xl focus:outline-none focus:ring-2 focus:ring-primary">
            </div>
            <div class="flex items-center gap-3">
              <input type="checkbox" id="habitoCompletado" class="w-5 h-5 text-primary">
              <label for="habitoCompletado" class="text-sm">Marcar como completado</label>
            </div>
            <button type="submit" class="w-full bg-primary text-white py-3 rounded-xl font-semibold hover:bg-opacity-90 transition">
              <i class="fas fa-save mr-2"></i> Crear Hábito
            </button>
          </form>
        </div>

        <div class="bg-white rounded-2xl shadow-lg p-6 animate-slide-in">
          <h2 class="text-xl font-bold text-primary mb-4">
            <i class="fas fa-list mr-2"></i>Mis Hábitos
            <span class="text-sm font-normal text-gray-500 ml-2">(${habits.length})</span>
          </h2>
          ${habits.length === 0 ? '<p class="text-gray-400 text-center py-8">No hay hábitos registrados</p>' : `
          <div class="space-y-3">
            ${habits.map(h => `
              <div class="flex items-center justify-between border rounded-xl p-4 hover:shadow-md transition ${h.completado ? 'border-green-300 bg-green-50' : ''}">
                <div>
                  <div class="font-semibold ${h.completado ? 'text-green-700' : ''}">${h.tipoHabito || 'Sin nombre'}</div>
                  <div class="text-sm text-gray-500">+${h.puntosOtorgados || 0} pts</div>
                </div>
                <div class="flex items-center gap-2">
                  ${h.completado
                    ? '<span class="text-green-600 font-semibold text-sm"><i class="fas fa-check-circle"></i> Completado</span>'
                    : `<button class="text-sm px-3 py-1 bg-primary text-white rounded-lg hover:bg-opacity-90" onclick="HabitsComponent.toggle(${h.id})">Completar</button>`
                  }
                </div>
              </div>
            `).join('')}
          </div>`}
        </div>
      </div>
    </div>`;

    const toggle = async (id) => {
        const res = await API.put(`/Habitos/actualizarCompletado/${id}`, { completado: true });
        if (res) {
            Toast.success('¡Hábito completado!');
            navigateTo('habits');
        }
    };

    const mount = async () => {
        await loadHabits();

        const appEl = document.getElementById('app');
        if (appEl) appEl.innerHTML = render();

        $('habitoForm')?.addEventListener('submit', async (e) => {
            e.preventDefault();
            const u = Auth.getUser();
            if (!u) return;
            const tipo = $('habitoTipo').value;
            const completado = $('habitoCompletado').checked;

            const res = await API.post('/Habitos/add', {
                idUsuario: u.id,
                tipoHabito: tipo,
                completado
            });
            if (res) {
                Toast.success('Hábito creado');
                navigateTo('habits');
            }
        });
    };

    return { render, mount, toggle };
})();
