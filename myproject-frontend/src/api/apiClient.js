const API_BASE_URL = (
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8080"
).replace(/\/+$/, "");

export async function apiRequest(path, options = {}) {
  const headers = {
    ...(options.body != null ? { "Content-Type": "application/json" } : {}),
    ...(options.headers || {}),
  };

  const response = await fetch(`${API_BASE_URL}/api${path}`, {
    ...options,
    headers,
  });

  const text = await response.text();

  if (!response.ok) {
    let message = `Request failed (${response.status})`;

    if (text) {
      try {
        const errorBody = JSON.parse(text);
        message = errorBody.message || errorBody.error || message;
      } catch {
        message = text;
      }
    }

    throw new Error(message);
  }

  if (!text.trim()) {
    return null;
  }

  return JSON.parse(text);
}
