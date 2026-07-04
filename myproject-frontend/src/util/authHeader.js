export function createBasicAuthHeader(username, password) {
    const token = btoa(`${username}:${password}`);

    return {
        Authorization: `Basic ${token}`
    };
}