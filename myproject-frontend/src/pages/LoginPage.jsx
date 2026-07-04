import {useState} from "react";
import {useNavigate} from "react-router";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import ErrorMessage from "../components/ErrorMessage.jsx";

export default function LoginPage() {
    const navigate = useNavigate();
    const {login} = useAuthContext();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    async function handleSubmit(event) {
        event.preventDefault();
        setError("");

        try {
            await login(username, password);
            navigate("/polls/my");
        } catch (err) {
            setError("Invalid username or password");
        }
    }

    return (
        <main className="container py-4">
            <h1 className="mb-3">Login</h1>

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
                    Login
                </button>
            </form>
        </main>
    );
}