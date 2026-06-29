const Auth = (() => {
    const login = async (email) => {
        const user = await API.get(`/usuarios/email/${encodeURIComponent(email)}`);
        if (!user) {
            Toast.error('Usuario no encontrado con ese email');
            return false;
        }
        Storage.setUser(user);
        Toast.success(`¡Bienvenido, ${user.name || 'Usuario'}!`);
        navigateTo('dashboard');
        return true;
    };

    const register = async (data) => {
        if (!Validators.email(data.email)) { Toast.error('Email inválido'); return false; }
        if (!Validators.password(data.password)) { Toast.error('Mínimo 8 caracteres'); return false; }

        const exists = await API.get(`/usuarios/existe/${encodeURIComponent(data.email)}`);
        if (exists === true) { Toast.error('Ese email ya está registrado'); return false; }

        const res = await API.post('/usuarios/add', {
            name: data.name,
            email: data.email,
            password: data.password
        });
        if (!res) return false;
        Toast.success('¡Cuenta creada! Inicia sesión');
        navigateTo('login');
        return true;
    };

    const logout = () => {
        Storage.clear();
        Toast.info('Sesión cerrada');
        navigateTo('login');
    };

    const verifySession = async () => {
        const u = Storage.getUser();
        if (!u) return false;
        const res = await API.get(`/usuarios/existe/${encodeURIComponent(u.email)}`);
        if (res !== true) {
            Storage.clear();
            return false;
        }
        return true;
    };

    return {
        login, register, logout,
        isAuthenticated: () => !!Storage.getUser(),
        getUser: () => Storage.getUser(),
        verifySession
    };
})();
