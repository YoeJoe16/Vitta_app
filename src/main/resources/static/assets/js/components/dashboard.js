const DashboardComponent = (() => {
    const render = () => {
        const u = Auth.getUser() || {};
        return `
    <div class="min-h-screen bg-light">
      <nav class="bg-primary text-white shadow-lg">
        <div class="max-w-6xl mx-auto px-4 py-3 flex items-center justify-between">
          <div class="flex items-center gap-2">
            <i class="fas fa-leaf text-2xl"></i>
            <span class="text-xl font-bold">Vitta</span>
          </div>
          <div class="flex items-center gap-4">
            <span class="hidden sm:inline text-sm">${u.name || ''}</span>
            <a href="#profile" class="hover:text-accent transition"><i class="fas fa-user-circle text-xl"></i></a>
            <button onclick="Auth.logout()" class="hover:text-red-300 transition"><i class="fas fa-sign-out-alt"></i></button>
          </div>
        </div>
      </nav>

      <div class="max-w-6xl mx-auto px-4 py-8">
        <h1 class="text-3xl font-bold text-primary mb-6 animate-slide-in">
          <i class="fas fa-home mr-2"></i>Dashboard
        </h1>

        <div class="bg-white rounded-2xl shadow-lg p-6 mb-8 animate-slide-in" style="animation-delay:0.1s">
          <h2 class="text-xl font-bold text-primary mb-4"><i class="fas fa-trophy mr-2 text-yellow-500"></i>Gamificación</h2>
          <div class="grid grid-cols-2 md:grid-cols-4 gap-4 text-center">
            <div class="bg-light rounded-xl p-4">
              <div class="text-3xl font-bold text-primary" id="totalPoints">${u.totalPoints || 0}</div>
              <div class="text-sm text-gray-500">Puntos</div>
            </div>
            <div class="bg-light rounded-xl p-4">
              <div class="text-3xl font-bold text-secondary" id="userRange">${u.range || 'Bronce'}</div>
              <div class="text-sm text-gray-500">Rango</div>
            </div>
            <div class="bg-light rounded-xl p-4">
              <div class="text-3xl font-bold text-accent" id="logrosCount">-</div>
              <div class="text-sm text-gray-500">Logros</div>
            </div>
            <div class="bg-light rounded-xl p-4">
              <div class="text-3xl font-bold text-yellow-600" id="streakDays"><i class="fas fa-fire"></i> -</div>
              <div class="text-sm text-gray-500">Racha</div>
            </div>
          </div>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          <a href="#nutrition" class="bg-white rounded-2xl shadow-lg p-6 hover:shadow-xl transition animate-slide-in" style="animation-delay:0.2s">
            <div class="w-12 h-12 bg-green-100 rounded-xl flex items-center justify-center mb-4">
              <i class="fas fa-apple-alt text-2xl text-green-600"></i>
            </div>
            <h3 class="text-lg font-bold text-gray-800">Nutrición</h3>
            <p class="text-sm text-gray-500 mt-1">Registra tus comidas</p>
          </a>
          <a href="#exercise" class="bg-white rounded-2xl shadow-lg p-6 hover:shadow-xl transition animate-slide-in" style="animation-delay:0.3s">
            <div class="w-12 h-12 bg-blue-100 rounded-xl flex items-center justify-center mb-4">
              <i class="fas fa-running text-2xl text-blue-600"></i>
            </div>
            <h3 class="text-lg font-bold text-gray-800">Ejercicio</h3>
            <p class="text-sm text-gray-500 mt-1">Registra tus rutinas</p>
          </a>
          <a href="#habits" class="bg-white rounded-2xl shadow-lg p-6 hover:shadow-xl transition animate-slide-in" style="animation-delay:0.4s">
            <div class="w-12 h-12 bg-purple-100 rounded-xl flex items-center justify-center mb-4">
              <i class="fas fa-check-double text-2xl text-purple-600"></i>
            </div>
            <h3 class="text-lg font-bold text-gray-800">Hábitos</h3>
            <p class="text-sm text-gray-500 mt-1">Crea buenos hábitos</p>
          </a>
          <a href="#logros" class="bg-white rounded-2xl shadow-lg p-6 hover:shadow-xl transition animate-slide-in" style="animation-delay:0.5s">
            <div class="w-12 h-12 bg-yellow-100 rounded-xl flex items-center justify-center mb-4">
              <i class="fas fa-medal text-2xl text-yellow-600"></i>
            </div>
            <h3 class="text-lg font-bold text-gray-800">Logros</h3>
            <p class="text-sm text-gray-500 mt-1">Tus medallas</p>
          </a>
        </div>
      </div>
    </div>`;
    };

    const mount = async () => {
        const u = Auth.getUser();
        if (!u) return;

        const logrosRes = await API.get(`/Logros/contarLogros/${u.id}`);
        if (logrosRes != null) {
            const el = $('logrosCount');
            if (el) el.textContent = logrosRes;
        }
    };

    return { render, mount };
})();
