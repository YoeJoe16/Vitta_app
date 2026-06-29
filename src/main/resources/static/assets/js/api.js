const API = (() => {
    const BASE = API_BASE;

    const request = async (method, endpoint, data = null) => {
        try {
            showLoading(true);
            const opts = {
                method,
                headers: { 'Content-Type': 'application/json' },
                signal: AbortSignal.timeout(API_TIMEOUT)
            };
            if (data) opts.body = JSON.stringify(data);
            const res = await fetch(`${BASE}${endpoint}`, opts);
            const text = await res.text();
            let result;
            try { result = text ? JSON.parse(text) : null; } catch { result = text; }
            if (!res.ok) {
                const msg = typeof result === 'string' ? result : (result?.message || result?.[0] || `Error ${res.status}`);
                Toast.error(msg);
                return null;
            }
            return result;
        } catch (err) {
            if (err.name === 'AbortError') Toast.error('La solicitud tardó demasiado');
            else if (err instanceof TypeError) Toast.error('Error de conexión con el servidor');
            else Toast.error(err.message || 'Error desconocido');
            return null;
        } finally {
            showLoading(false);
        }
    };

    return {
        get: (e) => request('GET', e),
        post: (e, d) => request('POST', e, d),
        put: (e, d) => request('PUT', e, d),
        patch: (e, d) => request('PATCH', e, d),
        del: (e) => request('DELETE', e)
    };
})();
