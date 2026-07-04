import {useState} from "react";
import {useNavigate} from "react-router";
import {registerUser} from "../api/authApi.js";
import ErrorMessage from "../components/ErrorMessage.jsx";

export default function RegisterPage() {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    async function handleSubmit(event) {
        event.preventDefault();
        setError("");

        try {
            await registerUser(username, password);
            navigate("/login");
        } catch (err) {
            setError(err.message);
        }
    }

    return (
        <main className="container py-4">
            <h1 className="mb-3">Register</h1>

            <ErrorMessage message={error} />

            <form onSubmit={handleSubmit} className="card p-3">
                <div className="mb-3">
                    <label htmlFor="username" className="form-label">
                        Username
                    </label>
                    <input
                        id="username"
                        className="form-control"
                        value={username}
                        onChange={event => setUsername(event.target.value)}
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="password" className="form-label">
                        Password
                    </label>
                    <input
                        id="password"
                        type="password"
                        className="form-control"
                        value={password}
                        onChange={event => setPassword(event.target.value)}
                    />
                </div>

                <button className="btn btn-primary" type="submit">
                    Register
                </button>
            </form>
        </main>
    );
}