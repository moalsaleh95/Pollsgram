import {apiRequest} from "./apiClient.js";
import {createBasicAuthHeader} from "../util/authHeader.js";

function authHeaders(auth) {
    return createBasicAuthHeader(auth.username, auth.password);
}

export function fetchMyPolls(auth) {
    return apiRequest("/polls/my", {
        method: "GET",
        headers: authHeaders(auth)
    });
}

export function fetchPendingPolls(auth) {
    return apiRequest("/polls/pending", {
        method: "GET",
        headers: authHeaders(auth)
    });
}

export function createPoll(auth, poll) {
    return apiRequest("/polls", {
        method: "POST",
        headers: authHeaders(auth),
        body: JSON.stringify(poll)
    });
}

export function finishPoll(auth, pollId) {
    return apiRequest(`/polls/${pollId}/finish`, {
        method: "POST",
        headers: authHeaders(auth)
    });
}

export function deletePoll(auth, pollId) {
    return apiRequest(`/polls/${pollId}`, {
        method: "DELETE",
        headers: authHeaders(auth)
    });
}

export function fetchPollById(auth, pollId) {
    return apiRequest(`/polls/${pollId}`, {
        method: "GET",
        headers: authHeaders(auth)
    });
}

export function inviteUser(auth, pollId, username) {
    return apiRequest(`/polls/${pollId}/invitations`, {
        method: "POST",
        headers: authHeaders(auth),
        body: JSON.stringify({username})
    });
}

export function submitPoll(auth, pollId, answers) {
    return apiRequest(`/polls/${pollId}/submit`, {
        method: "POST",
        headers: authHeaders(auth),
        body: JSON.stringify({answers})
    });
}

export function fetchPollResults(auth, pollId) {
    return apiRequest(`/polls/${pollId}/results`, {
        method: "GET",
        headers: authHeaders(auth)
    });
}

export function fetchPollParticipations(auth, pollId) {
    return apiRequest(`/polls/${pollId}/participations`, {
        method: "GET",
        headers: authHeaders(auth)
    });
}

export function updatePoll(auth, pollId, poll) {
    return apiRequest(`/polls/${pollId}`, {
        method: "PUT",
        headers: authHeaders(auth),
        body: JSON.stringify(poll)
    });
}