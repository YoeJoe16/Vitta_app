const LoginComponent = (() => {
    const render = () => `
    <div class="min-h-screen bg-gradient-to-br from-primary to-secondary flex items-center justify-center p-4">
      <div class="bg-white rounded-2xl shadow-2xl p-8 w-full max-w-md animate-slide-in">
        <div class="text-center mb-8">
          <i class="fas fa-leaf text-5xl text-primary mb-2"></i>
          <h1 class="text-4xl font-bold text-primary">Vitta</h1>
          <p class="text-gray-500">Tu salud en tus manos</p>
        </div>
        <form id="loginForm" class="space-y-5">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
            <input type="email" id="loginEmail" required data-validate="email"
              class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition"
              placeholder="tu@email.com">
          </div>
          <button type="submit"
            class="w-full bg-primary text-white py-3 rounded-xl font-semibold hover:bg-opacity-90 transition shadow-lg">
            <i class="fas fa-arrow-right mr-2"></i> Entrar
          </button>
        </form>
        <p class="text-center mt-6 text-gray-600">
          ¿No tienes cuenta?
          <a href="#register" class="text-primary font-semibold hover:underline">Regístrate</a>
        </p>
      </div>
    </div>`;

    const mount = () => {
        $('loginForm')?.addEventListener('submit', async (e) => {
            e.preventDefault();
            if (!Validators.validateForm('loginForm')) { Toast.error('Corrige los campos'); return; }
            await Auth.login($('loginEmail').value);
        });
    };

    return { render, mount };
})();
