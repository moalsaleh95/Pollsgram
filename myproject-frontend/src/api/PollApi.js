import {currentAuth} from "../context/AuthContextProvider.jsx";

const ROOT = window.location.protocol + "//" + window.location.hostname + ":8080";

let errorHandler = null;

export function registerErrorHandler(handler) {
    errorHandler = handler;
}

export async function logIn(credentials) {
    return apiFetch(ROOT + "/users/current",
        {headers: authorizedAs(credentials)});
}

export async function registerUser(user) {
    return apiFetch(ROOT + "/users",
        {method: "POST", headers: json(), body: JSON.stringify(user)});
}

export async function getUsers() {
    return apiFetch(ROOT + "/users",
        {headers: authorized()});
}

export async function deleteUser(name) {
    return apiFetch(ROOT + "/users/" + name,
        {method: "DELETE", headers: authorized()});
}

export async function getSchools() {
    return apiFetch(ROOT + "/schools",
        {headers: authorized()});
}

export async function createSchool(school) {
    return apiFetch(ROOT + "/schools",
        {method: "POST", headers: authorizedJson(), body: JSON.stringify(school)});
}

export async function deleteSchool(id) {
    return apiFetch(ROOT + "/schools/" + id,
        {method: "DELETE", headers: authorized()});
}

export async function getDishes(schoolId) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/dishes",
        {headers: authorized()});
}

export async function createDish(schoolId, dish) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/dishes",
        {method: "POST", headers: authorizedJson(), body: JSON.stringify(dish)});
}

export async function updateDish(schoolId, dishId, dish) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/dishes/" + dishId,
        {method: "PUT", headers: authorizedJson(), body: JSON.stringify(dish)})
}

export async function deleteDish(schoolId, dishId) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/dishes/" + dishId,
        {method: "DELETE", headers: authorized()})
}

export async function getFavorite(schoolId, dishId) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/dishes/" + dishId + "/favorite",
        {headers: authorized()});
}

export async function updateFavorite(schoolId, dishId, status) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/dishes/" + dishId + "/favorite",
        {method: status ? "POST" : "DELETE", headers: authorized()})
}

export async function getMenu(schoolId, day) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/menu?day=" + day,
        {headers: authorized()});
}

export async function updateMenu(schoolId, day, menu) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/menu?day=" + day,
        {method: "PUT", headers: authorizedJson(), body: JSON.stringify(menu)});
}

export async function getComments(schoolId, day) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/menu/comments?day=" + day,
        {headers: authorized()});
}

export async function addComment(schoolId, day, comment) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/menu/comments?day=" + day,
        {method: "POST", headers: authorizedJson(), body: JSON.stringify(comment)});
}

export async function deleteComment(schoolId, commentId) {
    return apiFetch(ROOT + "/schools/" + schoolId + "/menu/comments/" + commentId,
        {method: "DELETE", headers: authorized()});
}

function json() {
    return {"Content-Type": "application/json"}
}

function authorized() {
    return authorizedAs(currentAuth());
}

function authorizedJson() {
    return {...authorized(), ...json()};
}

function authorizedAs(auth) {
    return {"Authorization": "Basic " + btoa(auth.name + ":" + auth.password)};
}

async function apiFetch(url, options) {
    try {
        const response = await fetch(url, options);
        if (!response.ok) {
            await extractError(response);
        }
        try {
            return await response.json();
        } catch {
            return {};
        }
    } catch (e) {
        console.error(e);
        if (errorHandler) {
            errorHandler(e.message);
        }
        throw e;
    }
}

async function extractError(response) {
    let message = response.status + " " + response.message;
    try {
        const serverError = await response.json();
        message = serverError.message;
    } catch {
    }
    throw new Error(message);
}

