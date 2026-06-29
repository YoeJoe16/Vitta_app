const RegisterComponent = (() => {
    const render = () => `
    <div class="min-h-screen bg-gradient-to-br from-primary to-secondary flex items-center justify-center p-4">
      <div class="bg-white rounded-2xl shadow-2xl p-8 w-full max-w-md animate-slide-in">
        <div class="text-center mb-8">
          <i class="fas fa-user-plus text-5xl text-primary mb-2"></i>
          <h1 class="text-4xl font-bold text-primary">Vitta</h1>
          <p class="text-gray-500">Crea tu cuenta</p>
        </div>
        <form id="registerForm" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Nombre completo</label>
            <input type="text" id="registerName" required
              class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition"
              placeholder="Tu nombre">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
            <input type="email" id="registerEmail" required data-validate="email"
              class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition"
              placeholder="tu@email.com">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Contraseña</label>
            <input type="password" id="registerPassword" required data-validate="password"
              class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition"
              placeholder="Mín. 8 caracteres, 1 mayúscula, 1 número, 1 especial">
          </div>
          <button type="submit"
            class="w-full bg-primary text-white py-3 rounded-xl font-semibold hover:bg-opacity-90 transition shadow-lg">
            <i class="fas fa-check mr-2"></i> Registrarse
          </button>
        </form>
        <p class="text-center mt-6 text-gray-600">
          ¿Ya tienes cuenta?
          <a href="#login" class="text-primary font-semibold hover:underline">Inicia sesión</a>
        </p>
      </div>
    </div>`;

    const mount = () => {
        $('registerForm')?.addEventListener('submit', async (e) => {
            e.preventDefault();
            await Auth.register({
                name: $('registerName').value,
                email: $('registerEmail').value,
                password: $('registerPassword').value
            });
        });
    };

    return { render, mount };
})();
