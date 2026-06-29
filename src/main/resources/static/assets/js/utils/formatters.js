const Formatters = {
    date: (d) => d ? new Date(d).toLocaleDateString('es-ES') : '',
    time: (d) => d ? new Date(d).toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' }) : '',
    capitalize: (s) => s ? s.charAt(0).toUpperCase() + s.slice(1).toLowerCase() : '',
    number: (n) => n != null ? n.toLocaleString('es-CR') : '0'
};
