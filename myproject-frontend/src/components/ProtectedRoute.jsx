import {Navigate} from "react-router";
import {useAuthContext} from "../context/AuthContextProvider.jsx";

export default function ProtectedRoute({children}) {
    const {auth} = useAuthContext();

    if (!auth.loggedIn) {
        return <Navigate to="/login" />;
    }

    return children;
}