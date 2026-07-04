export function validateRequired(value, fieldName) {
    if (!value || value.trim() === "") {
        return `${fieldName} is required.`;
    }

    return "";
}

export function validateFutureDate(date) {
    if (!date) {
        return "Due date is required.";
    }

    const today = new Date().toISOString().split("T")[0];

    if (date < today) {
        return "Due date cannot be in the past.";
    }

    return "";
}