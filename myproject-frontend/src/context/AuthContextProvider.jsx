import {createContext, useContext, useState} from "react";
import {fetchCurrentUser} from "../api/authApi.js";

const loggedOutUser = {
    username: null,
    password: null,
    roles: [],
    loggedIn: false
};

const AuthContext = createContext(undefined);

export function useAuthContext() {
    return useContext(AuthContext);
}

export default function AuthContextProvider({children}) {
    const [auth, setAuth] = useState(loggedOutUser);

    async function login(username, password) {
        const user = await fetchCurrentUser(username, password);

        setAuth({
            username: user.username,
            password,
            roles: user.roles,
            loggedIn: true
        });
    }

    function logout() {
        setAuth(loggedOutUser);
    }

    return (
        <AuthContext.Provider value={{auth, login, logout}}>
            {children}
        </AuthContext.Provider>
    );
}