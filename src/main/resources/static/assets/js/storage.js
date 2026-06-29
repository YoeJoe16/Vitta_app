const Storage = (() => {
    const USER_KEY = 'vitta_user';

    return {
        setUser: (user) => localStorage.setItem(USER_KEY, JSON.stringify(user)),

        getUser: () => {
            const raw = localStorage.getItem(USER_KEY);
            return raw ? JSON.parse(raw) : null;
        },

        clear: () => {
            localStorage.removeItem(USER_KEY);
        }
    };
})();
