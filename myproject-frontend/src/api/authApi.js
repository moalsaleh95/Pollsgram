import {apiRequest} from "./apiClient.js";
import {createBasicAuthHeader} from "../util/authHeader.js";

export function registerUser(username, password) {
    return apiRequest("/users/register", {
        method: "POST",
        body: JSON.stringify({username, password})
    });
}

export function fetchCurrentUser(username, password) {
    return apiRequest("/auth/me", {
        method: "GET",
        headers: createBasicAuthHeader(username, password)
    });
}