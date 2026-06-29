const Toast = (() => {
    const create = (msg, type) => {
        const c = document.getElementById('toastContainer');
        if (!c) return;
        const el = document.createElement('div');
        const bg = ({ success: 'bg-green-500', error: 'bg-red-500', info: 'bg-blue-500', warning: 'bg-yellow-500' })[type] || 'bg-blue-500';
        el.className = `${bg} text-white px-6 py-3 rounded-lg shadow-lg animate-slide-in text-sm`;
        el.textContent = msg;
        c.appendChild(el);
        setTimeout(() => el.remove(), TOAST_DURATION);
    };
    return {
        success: (m) => create(m, 'success'),
        error: (m) => create(m, 'error'),
        info: (m) => create(m, 'info'),
        warning: (m) => create(m, 'warning')
    };
})();

const showLoading = (show) => {
    const el = document.getElementById('loadingOverlay');
    if (el) el.classList.toggle('hidden', !show);
};

const navigateTo = (page, params = {}) => {
    window.history.pushState(params, '', `#${page}`);
    window.dispatchEvent(new HashChangeEvent('hashchange'));
};

const render = (html) => {
    const app = document.getElementById('app');
    if (app) app.innerHTML = html;
};

const $ = (id) => document.getElementById(id);
