import {NavLink, useNavigate} from "react-router";
import {useAuthContext} from "../context/AuthContextProvider.jsx";

export default function Nav() {
    const {auth, logout} = useAuthContext();
    const navigate = useNavigate();

    function handleLogout() {
        logout();
        navigate("/login");
    }

    function navClass({isActive}) {
        return isActive ? "nav-link active fw-semibold" : "nav-link";
    }

    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-primary">
            <div className="container">
                <NavLink className="navbar-brand fw-bold" to="/">
                    Pollsgram
                </NavLink>

                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#mainNav"
                    aria-controls="mainNav"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <span className="navbar-toggler-icon" />
                </button>

                <div id="mainNav" className="collapse navbar-collapse">
                    <div className="navbar-nav ms-auto align-items-md-center">
                        {auth.loggedIn ? (
                            <>
                                <span className="navbar-text text-white-50 me-md-3">
                                    {auth.username}
                                </span>

                                <NavLink className={navClass} to="/polls/my">
                                    My Polls
                                </NavLink>

                                <NavLink className={navClass} to="/polls/pending">
                                    Pending Polls
                                </NavLink>

                                <NavLink className={navClass} to="/polls/create">
                                    Create Poll
                                </NavLink>

                                <button
                                    className="btn btn-link nav-link text-start"
                                    onClick={handleLogout}
                                >
                                    Logout
                                </button>
                            </>
                        ) : (
                            <>
                                <NavLink className={navClass} to="/login">
                                    Login
                                </NavLink>

                                <NavLink className={navClass} to="/register">
                                    Register
                                </NavLink>
                            </>
                        )}
                    </div>
                </div>
            </div>
        </nav>
    );
}