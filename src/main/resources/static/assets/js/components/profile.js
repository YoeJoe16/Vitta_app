const ProfileComponent = (() => {
    const render = () => {
        const u = Auth.getUser() || {};
        return `
    <div class="min-h-screen bg-light">
      <nav class="bg-primary text-white shadow-lg">
        <div class="max-w-4xl mx-auto px-4 py-3 flex items-center justify-between">
          <div class="flex items-center gap-2">
            <i class="fas fa-leaf text-2xl"></i>
            <span class="text-xl font-bold">Vitta</span>
          </div>
          <button onclick="navigateTo('dashboard')" class="hover:text-accent transition">
            <i class="fas fa-arrow-left mr-1"></i> Volver
          </button>
        </div>
      </nav>

      <div class="max-w-2xl mx-auto px-4 py-8">
        <h1 class="text-3xl font-bold text-primary mb-6 animate-slide-in">
          <i class="fas fa-user-circle mr-2"></i>Mi Perfil
        </h1>

        <div class="bg-white rounded-2xl shadow-lg p-8 animate-slide-in">
          <form id="profileForm" class="space-y-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Nombre</label>
              <input type="text" id="profileName" value="${u.name || ''}" required
                class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <input type="email" id="profileEmail" value="${u.email || ''}" disabled
                class="w-full px-4 py-3 border border-gray-300 rounded-xl bg-gray-100 cursor-not-allowed">
            </div>
            <div class="bg-light rounded-xl p-4 grid grid-cols-2 gap-4 text-center">
              <div>
                <div class="text-2xl font-bold text-primary">${u.totalPoints || 0}</div>
                <div class="text-sm text-gray-500">Puntos totales</div>
              </div>
              <div>
                <div class="text-2xl font-bold text-secondary">${u.range || 'Bronce'}</div>
                <div class="text-sm text-gray-500">Rango</div>
              </div>
            </div>
            <button type="submit"
              class="w-full bg-primary text-white py-3 rounded-xl font-semibold hover:bg-opacity-90 transition shadow-lg">
              <i class="fas fa-save mr-2"></i> Guardar Cambios
            </button>
          </form>
        </div>
      </div>
    </div>`;
    };

    const mount = () => {
        $('profileForm')?.addEventListener('submit', async (e) => {
            e.preventDefault();
            const name = $('profileName').value;
            const user = Auth.getUser();
            if (!user) return;
            const res = await API.put(`/usuarios/update/${user.id}`, {
                ...user,
                name: name,
                password: user.password
            });
            if (res) {
                Storage.setUser({ ...user, name });
                Toast.success('Perfil actualizado');
                navigateTo('dashboard');
            }
        });
    };

    return { render, mount };
})();
