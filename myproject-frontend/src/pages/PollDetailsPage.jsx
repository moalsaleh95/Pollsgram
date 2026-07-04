import {useEffect, useState} from "react";
import {useParams} from "react-router";
import {fetchPollById, inviteUser} from "../api/pollApi.js";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import ErrorMessage from "../components/ErrorMessage.jsx";
import Loading from "../components/Loading.jsx";

export default function PollDetailsPage() {
    const {id} = useParams();
    const {auth} = useAuthContext();

    const [poll, setPoll] = useState(null);
    const [inviteUsername, setInviteUsername] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [loading, setLoading] = useState(true);

    async function loadPoll() {
        setLoading(true);

        try {
            const data = await fetchPollById(auth, id);
            setPoll(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        loadPoll();
    }, [id]);

    async function handleInvite(event) {
        event.preventDefault();
        setError("");
        setSuccess("");

        try {
            await inviteUser(auth, id, inviteUsername);
            setInviteUsername("");
            setSuccess("User invited successfully.");
        } catch (err) {
            setError(err.message);
        }
    }

    if (!poll) {
        return (
            <main className="container py-4">
                <ErrorMessage message={error} />
                <p>Loading...</p>
            </main>
        );
    }

    if (loading) {
        return <Loading />;
    }

    return (
        <main className="container py-4">
            <h1>{poll.title}</h1>
            <p>{poll.description}</p>

            <p className="text-muted">
                Due date: {poll.dueDate}
            </p>

            <span className={`badge ${poll.finished ? "text-bg-secondary" : "text-bg-success"}`}>
                {poll.finished ? "Finished" : "Open"}
            </span>

            <hr />

            <section className="mb-4">
                <h2 className="h4">Questions</h2>

                <ul className="list-group">
                    {poll.questions.map(question => (
                        <li className="list-group-item" key={question.id}>
                            <strong>{question.text}</strong>
                            <br />
                            <span className="text-muted small">
                                Type: {question.type}
                            </span>
                        </li>
                    ))}
                </ul>
            </section>

            {!poll.finished && (
                <section className="card p-3">
                    <h2 className="h4">Invite user</h2>

                    <ErrorMessage message={error} />

                    {success && (
                        <div className="alert alert-success" role="alert">
                            {success}
                        </div>
                    )}

                    <form onSubmit={handleInvite}>
                        <div className="mb-3">
                            <label htmlFor="inviteUsername" className="form-label">
                                Username
                            </label>
                            <input
                                id="inviteUsername"
                                className="form-control"
                                value={inviteUsername}
                                onChange={event => setInviteUsername(event.target.value)}
                            />
                        </div>

                        <button className="btn btn-primary" type="submit">
                            Invite
                        </button>
                    </form>
                </section>
            )}
        </main>
    );
}