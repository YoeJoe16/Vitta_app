const App = (() => {
    const PAGES = {
        login: LoginComponent,
        register: RegisterComponent,
        dashboard: DashboardComponent,
        profile: ProfileComponent,
        nutrition: NutritionComponent,
        exercise: ExerciseComponent,
        habits: HabitsComponent,
        logros: LogrosComponent
    };

    const PUBLIC_PAGES = ['login', 'register'];

    const renderPage = (page, params = {}) => {
        if (!PUBLIC_PAGES.includes(page) && !Auth.isAuthenticated()) {
            navigateTo('login');
            return;
        }

        const component = PAGES[page];
        if (!component) {
            navigateTo(Auth.isAuthenticated() ? 'dashboard' : 'login');
            return;
        }

        render(component.render(params));
        component.mount(params);
    };

    const handleRouting = () => {
        const page = window.location.hash.slice(1) || (Auth.isAuthenticated() ? 'dashboard' : 'login');
        renderPage(page);
    };

    window.addEventListener('hashchange', handleRouting);
    window.addEventListener('popstate', handleRouting);

    const init = async () => {
        if (Auth.isAuthenticated()) {
            const valid = await Auth.verifySession();
            if (!valid) {
                navigateTo('login');
                return;
            }
        }
        handleRouting();
    };

    return { init };
})();

document.addEventListener('DOMContentLoaded', () => App.init());
