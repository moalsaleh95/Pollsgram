import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router";
import {fetchPollById, updatePoll} from "../api/pollApi.js";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import ErrorMessage from "../components/ErrorMessage.jsx";
import Loading from "../components/Loading.jsx";
export default function EditPollPage() {
    const {id} = useParams();
    const navigate = useNavigate();
    const {auth} = useAuthContext();

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [dueDate, setDueDate] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function loadPoll() {
            setLoading(true);

            try {
                const poll = await fetchPollById(auth, id);

                setTitle(poll.title);
                setDescription(poll.description);
                setDueDate(poll.dueDate);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        }

        loadPoll();
    }, [auth, id]);

    async function handleSubmit(event) {
        event.preventDefault();
        setError("");

        try {
            await updatePoll(auth, id, {
                title,
                description,
                dueDate
            });

            navigate("/polls/my");
        } catch (err) {
            setError(err.message);
        }
    }

    if (loading) {
        return <Loading />;
    }

    return (
        <main className="container py-4">
            <h1 className="mb-3">Edit Poll</h1>

            <ErrorMessage message={error} />

            <form onSubmit={handleSubmit} className="card p-3">
                <div className="mb-3">
                    <label htmlFor="title" className="form-label">
                        Title
                    </label>
                    <input
                        id="title"
                        className="form-control"
                        value={title}
                        onChange={event => setTitle(event.target.value)}
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="description" className="form-label">
                        Description
                    </label>
                    <textarea
                        id="description"
                        className="form-control"
                        rows="3"
                        value={description}
                        onChange={event => setDescription(event.target.value)}
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="dueDate" className="form-label">
                        Due date
                    </label>
                    <input
                        id="dueDate"
                        type="date"
                        className="form-control"
                        value={dueDate}
                        onChange={event => setDueDate(event.target.value)}
                    />
                </div>

                <button className="btn btn-primary" type="submit">
                    Save Changes
                </button>
            </form>
        </main>
    );
}