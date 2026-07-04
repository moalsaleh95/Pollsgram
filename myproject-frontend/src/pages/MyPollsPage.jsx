import {useEffect, useState} from "react";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import {deletePoll, fetchMyPolls, finishPoll} from "../api/pollApi.js";
import ErrorMessage from "../components/ErrorMessage.jsx";
import {Link} from "react-router";
import Loading from "../components/Loading.jsx";
export default function MyPollsPage() {
    const {auth} = useAuthContext();

    const [polls, setPolls] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    async function loadPolls() {
        setLoading(true);
        setError("");

        try {
            const data = await fetchMyPolls(auth);
            setPolls(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        loadPolls();
    }, []);

    async function handleFinish(pollId) {
        try {
            await finishPoll(auth, pollId);
            await loadPolls();
        } catch (err) {
            setError(err.message);
        }
    }

    async function handleDelete(pollId) {
        try {
            await deletePoll(auth, pollId);
            await loadPolls();
        } catch (err) {
            setError(err.message);
        }
    }

    if (loading) {
        return <Loading />;
    }

    return (

        <main className="container py-4">
            <h1 className="mb-3">My Polls</h1>

            <ErrorMessage message={error} />

            {polls.length === 0 && (
                <p className="text-muted">You have not created any polls yet.</p>
            )}

            <div className="row g-3">
                {polls.map(poll => (
                    <div className="col-12 col-md-6 col-xl-4" key={poll.id}>
                        <article className="card h-100">
                            <div className="card-body">
                                <h2 className="h5">{poll.title}</h2>
                                <p>{poll.description}</p>

                                <p className="small text-muted mb-1">
                                    Due date: {poll.dueDate}
                                </p>

                                <p className="small text-muted">
                                    Questions: {poll.questions.length}
                                </p>

                                <span className={`badge ${poll.finished ? "text-bg-secondary" : "text-bg-success"}`}>
                                    {poll.finished ? "Finished" : "Open"}
                                </span>
                            </div>

                            <div className="card-footer d-flex gap-2 flex-wrap">
                                <Link className="btn btn-sm btn-outline-secondary" to={`/polls/${poll.id}`}>
                                    Details
                                </Link>

                                {poll.finished && (
                                    <Link className="btn btn-sm btn-outline-success" to={`/polls/${poll.id}/results`}>
                                        Results
                                    </Link>
                                )}

                                {poll.finished && (
                                    <Link className="btn btn-sm btn-outline-info" to={`/polls/${poll.id}/submissions`}>
                                        Submissions
                                    </Link>
                                )}

                                {!poll.finished && (
                                    <button
                                        className="btn btn-sm btn-outline-primary"
                                        onClick={() => handleFinish(poll.id)}
                                    >
                                        Finish
                                    </button>
                                )}

                                {!poll.finished && (
                                    <Link className="btn btn-sm btn-outline-warning" to={`/polls/${poll.id}/edit`}>
                                        Edit
                                    </Link>
                                )}

                                <button
                                    className="btn btn-sm btn-outline-danger"
                                    onClick={() => handleDelete(poll.id)}
                                >
                                    Delete
                                </button>
                            </div>
                        </article>
                    </div>
                ))}
            </div>
        </main>
    );
}