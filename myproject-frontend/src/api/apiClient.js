const API_BASE_URL = `${import.meta.env.VITE_API_URL}/api`;

export async function apiRequest(path, options = {}) {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
    },
  });

  if (!response.ok) {
    let message = "Request failed";

    try {
      const errorBody = await response.json();
      message = errorBody.message || errorBody.error || message;
    } catch {
      // ignore
    }

    throw new Error(message);
  }

  // Read the body as text first
  const text = await response.text();

  // Empty body?
  if (!text) {
    return null;
  }

  // Otherwise parse JSON
  return JSON.parse(text);
}
