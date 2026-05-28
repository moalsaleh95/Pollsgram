export function today() {
    return new Date().toISOString().slice(0, 10);
}

export function render(date) {
    return new Date(date).toLocaleString();
}