const LogrosComponent = (() => {
    let logros = [];
    let userLogros = [];

    const load = async () => {
        const res = await API.get('/Logros');
        if (res) logros = res;
        const u = Auth.getUser();
        if (!u) return;
        const res2 = await API.get(`/Logros/idUsuario/${u.id}`);
        if (res2) userLogros = res2;
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
        <h1 class="text-3xl font-bold text-primary mb-6"><i class="fas fa-medal mr-2"></i>Logros</h1>

        <div class="bg-white rounded-2xl shadow-lg p-6 mb-6 animate-slide-in">
          <h2 class="text-xl font-bold text-primary mb-4"><i class="fas fa-star mr-2 text-yellow-500"></i>Mis Logros</h2>
          ${userLogros.length === 0 ? '<p class="text-gray-400 text-center py-4">Aún no tienes logros. ¡Sigue acumulando puntos!</p>' : `
          <div class="grid grid-cols-2 md:grid-cols-3 gap-4">
            ${userLogros.map(ul => {
              const l = logros.find(x => x.id === ul.idLogro);
              return `
                <div class="border rounded-xl p-4 text-center bg-yellow-50 border-yellow-200 hover:shadow-md transition">
                  <i class="fas fa-trophy text-3xl text-yellow-500 mb-2"></i>
                  <div class="font-semibold text-sm">${l?.nombre || 'Logro'}</div>
                  <div class="text-xs text-gray-500">${l?.descripcion || ''}</div>
                  <div class="text-xs text-gray-400 mt-1">${ul.fechaObtenido || ''}</div>
                </div>
              `;
            }).join('')}
          </div>`}
        </div>

        <div class="bg-white rounded-2xl shadow-lg p-6 animate-slide-in">
          <h2 class="text-xl font-bold text-primary mb-4"><i class="fas fa-list mr-2"></i>Todos los Logros</h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
            ${logros.map(l => {
              const earned = userLogros.some(ul => ul.idLogro === l.id);
              return `
                <div class="border rounded-xl p-4 hover:shadow-md transition ${earned ? 'bg-green-50 border-green-300' : 'opacity-70'}">
                  <div class="flex items-center gap-2">
                    <i class="fas ${earned ? 'fa-check-circle text-green-500' : 'fa-lock text-gray-400'}"></i>
                    <div class="font-semibold ${earned ? 'text-green-700' : ''}">${l.nombre || '-'}</div>
                  </div>
                  <div class="text-sm text-gray-500 mt-1">${l.descripcion || ''}</div>
                  <div class="text-xs text-primary mt-1">Condición: ${l.condicion || '-'}</div>
                  ${l.puntosBonus ? `<div class="text-xs text-yellow-600 font-semibold">+${l.puntosBonus} pts bonus</div>` : ''}
                </div>
              `;
            }).join('')}
          </div>
          ${logros.length === 0 ? '<p class="text-gray-400 text-center py-4">No hay logros configurados</p>' : ''}
        </div>

        <div class="text-center mt-6">
          <button onclick="LogrosComponent.evaluate()" class="px-6 py-3 bg-primary text-white rounded-xl font-semibold hover:bg-opacity-90 transition shadow-lg">
            <i class="fas fa-sync mr-2"></i> Evaluar Logros
          </button>
        </div>
      </div>
    </div>`;

    const evaluate = async () => {
        const u = Auth.getUser();
        if (!u) return;
        await API.get(`/Logros/evaluarLogrosAutomaticos/${u.id}`);
        Toast.success('Logros evaluados');
        navigateTo('logros');
    };

    const mount = async () => {
        await load();
        const appEl = document.getElementById('app');
        if (appEl) appEl.innerHTML = render();
    };

    return { render, mount, evaluate };
})();
