import {createContext, useContext, useState} from "react";

export const loggedOut = {name: null, password: null, roles: [], loggedIn: false};

const AuthContext = createContext(undefined);

let _currentAuth = loggedOut;

export function useAuthContext() {
    return useContext(AuthContext);
}

export default function AuthContextProvider({children}) {
    const [auth, internalSetAuth] = useState(loggedOut);

    function setAuth(newAuth) {
        _currentAuth = newAuth;
        internalSetAuth(newAuth);
    }

    return <AuthContext value={{auth, setAuth}}>
        {children}
    </AuthContext>;
}

export function currentAuth() {
    return _currentAuth;
}