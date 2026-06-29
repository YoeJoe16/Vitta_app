const NutritionComponent = (() => {
    let tiposComida = [];
    let registros = [];
    let tipoFilter = '';
    let nivelFilter = '';

    const loadTipos = async () => {
        const res = await API.get('/tipos-comida/all');
        if (res) tiposComida = res;
    };

    const loadRegistros = async () => {
        const u = Auth.getUser();
        if (!u) return;
        const res = await API.get(`/comidas/usuario/${u.id}`);
        if (res) registros = res;
    };

    const renderTiposList = () => {
        let filtered = tiposComida;
        if (tipoFilter) filtered = filtered.filter(t => t.categoria === tipoFilter);
        if (nivelFilter) filtered = filtered.filter(t => t.nivelSaludable === nivelFilter);
        const cats = [...new Set(tiposComida.map(t => t.categoria).filter(Boolean))];
        const nvs = [...new Set(tiposComida.map(t => t.nivelSaludable).filter(Boolean))];

        return `
      <div class="bg-white rounded-2xl shadow-lg p-6 mb-6 animate-slide-in">
        <h2 class="text-xl font-bold text-primary mb-4"><i class="fas fa-utensils mr-2"></i>Tipos de Comida</h2>
        <div class="flex flex-wrap gap-2 mb-4">
          <select id="filterCat" class="px-3 py-2 border rounded-lg text-sm" onchange="NutritionComponent.applyFilter()">
            <option value="">Todas categorías</option>
            ${cats.map(c => `<option value="${c}" ${tipoFilter === c ? 'selected' : ''}>${c}</option>`).join('')}
          </select>
          <select id="filterNivel" class="px-3 py-2 border rounded-lg text-sm" onchange="NutritionComponent.applyFilter()">
            <option value="">Todos niveles</option>
            ${nvs.map(n => `<option value="${n}" ${nivelFilter === n ? 'selected' : ''}>${n}</option>`).join('')}
          </select>
        </div>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
          ${filtered.map(t => `
            <div class="border rounded-xl p-4 hover:shadow-md transition">
              <div class="font-semibold">${t.nombre || '-'}</div>
              <div class="text-sm text-gray-500">${t.categoria || ''} · ${t.nivelSaludable || ''}</div>
              <div class="text-sm mt-1"><span class="font-bold text-primary">${t.puntosBase || 0}</span> pts/base</div>
            </div>
          `).join('')}
        </div>
        ${filtered.length === 0 ? '<p class="text-gray-400 text-center py-4">Sin resultados</p>' : ''}
      </div>`;
    };

    const renderForm = () => `
      <div class="bg-white rounded-2xl shadow-lg p-6 mb-6 animate-slide-in">
        <h2 class="text-xl font-bold text-primary mb-4"><i class="fas fa-plus-circle mr-2"></i>Registrar Comida</h2>
        <form id="comidaForm" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-1">Tipo de comida</label>
            <select id="comidaTipo" required class="w-full px-4 py-3 border rounded-xl">
              <option value="">Seleccionar...</option>
              ${tiposComida.map(t => `<option value="${t.id}">${t.nombre} (${t.puntosBase} pts)</option>`).join('')}
            </select>
          </div>
          <div>
            <label class="block text-sm font-medium mb-1">Porciones</label>
            <input type="number" id="comidaPorciones" value="1" min="1" required
              class="w-full px-4 py-3 border rounded-xl">
          </div>
          <div>
            <label class="block text-sm font-medium mb-1">Momento del día</label>
            <select id="comidaMomento" required class="w-full px-4 py-3 border rounded-xl">
              <option value="desayuno">Desayuno</option>
              <option value="almuerzo">Almuerzo</option>
              <option value="cena">Cena</option>
              <option value="snack">Snack</option>
            </select>
          </div>
          <button type="submit" class="w-full bg-primary text-white py-3 rounded-xl font-semibold hover:bg-opacity-90 transition">
            <i class="fas fa-save mr-2"></i> Registrar
          </button>
        </form>
      </div>`;

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
        <h1 class="text-3xl font-bold text-primary mb-6"><i class="fas fa-apple-alt mr-2"></i>Nutrición</h1>
        ${renderForm()}
        ${renderTiposList()}
      </div>
    </div>`;

    const applyFilter = () => {
        tipoFilter = $('filterCat')?.value || '';
        nivelFilter = $('filterNivel')?.value || '';
        navigateTo('nutrition');
    };

    const mount = async () => {
        await loadTipos();
        const u = Auth.getUser();
        if (!u) return;
        await loadRegistros();

        const appEl = document.getElementById('app');
        if (appEl) appEl.innerHTML = render();

        $('comidaForm')?.addEventListener('submit', async (e) => {
            e.preventDefault();
            const tipoId = parseInt($('comidaTipo').value);
            const porciones = parseInt($('comidaPorciones').value);
            const momento = $('comidaMomento').value;
            if (!tipoId) { Toast.error('Selecciona un tipo de comida'); return; }

            const res = await API.post('/comidas/add', {
                idUsuario: u.id,
                idTipoComida: tipoId,
                cantidadPorciones: porciones,
                momentoDelDia: momento
            });
            if (res) {
                Toast.success('Comida registrada');
                navigateTo('nutrition');
            }
        });
    };

    return { render, mount, applyFilter };
})();
