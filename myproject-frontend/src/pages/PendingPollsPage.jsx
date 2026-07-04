import {useEffect, useState} from "react";
import {Link} from "react-router";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import {fetchPendingPolls} from "../api/pollApi.js";
import ErrorMessage from "../components/ErrorMessage.jsx";
import Loading from "../components/Loading.jsx";

export default function PendingPollsPage() {
    const {auth} = useAuthContext();

    const [polls, setPolls] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    async function loadPendingPolls() {
        setLoading(true);
        setError("");

        try {
            const data = await fetchPendingPolls(auth);
            setPolls(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        loadPendingPolls();
    }, []);

    if (loading) {
        return <Loading />;
    }

    return (
        <main className="container py-4">
            <h1 className="mb-3">Pending Polls</h1>

            <ErrorMessage message={error} />

            {polls.length === 0 && (
                <p className="text-muted">You have no pending polls.</p>
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
                            </div>

                            <div className="card-footer">
                                <Link
                                    className="btn btn-sm btn-primary"
                                    to={`/polls/${poll.id}/submit`}
                                >
                                    Answer Poll
                                </Link>
                            </div>
                        </article>
                    </div>
                ))}
            </div>
        </main>
    );
}