const Validators = {
    email: (v) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v),

    password: (v) => v.length >= 8,

    required: (v) => v && v.trim() !== '',

    number: (v) => !isNaN(v) && v !== '',

    validateForm: (formId) => {
        const form = document.getElementById(formId);
        if (!form) return true;
        const inputs = form.querySelectorAll('[required]');
        let isValid = true;
        inputs.forEach(input => {
            const rules = (input.dataset.validate || '').split(',').map(s => s.trim()).filter(Boolean);
            let fieldValid = true;
            for (const rule of rules) {
                if (Validators[rule] && !Validators[rule](input.value)) {
                    fieldValid = false;
                    break;
                }
            }
            if (!fieldValid) {
                input.classList.add('border-red-500');
                isValid = false;
            } else {
                input.classList.remove('border-red-500');
            }
        });
        return isValid;
    }
};
