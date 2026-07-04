import {useEffect, useState} from "react";
import {useParams} from "react-router";
import {fetchPollById, fetchPollParticipations} from "../api/pollApi.js";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import ErrorMessage from "../components/ErrorMessage.jsx";
import Loading from "../components/Loading.jsx";

export default function SubmissionsPage() {
    const {id} = useParams();
    const {auth} = useAuthContext();

    const [poll, setPoll] = useState(null);
    const [submissions, setSubmissions] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function loadData() {
            setError("");
            setLoading(true);

            try {
                const pollData = await fetchPollById(auth, id);
                const submissionData = await fetchPollParticipations(auth, id);

                setPoll(pollData);
                setSubmissions(submissionData);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        }

        loadData();
    }, [auth, id]);

    function renderAnswer(answer) {
        if (answer.booleanValue !== null) {
            return answer.booleanValue ? "Yes" : "No";
        }

        if (answer.numericValue !== null) {
            return answer.numericValue;
        }

        return answer.textValue;
    }

    if (loading) {
        return <Loading />;
    }

    return (
        <main className="container py-4">
            <h1 className="mb-3">Submissions</h1>

            <ErrorMessage message={error} />

            {poll && (
                <section className="mb-4">
                    <h2 className="h4">{poll.title}</h2>
                    <p className="text-muted">{poll.description}</p>
                </section>
            )}

            {submissions.length === 0 && !error && (
                <p className="text-muted">No submissions yet.</p>
            )}

            <div className="row g-3">
                {submissions.map(submission => (
                    <div className="col-12 col-lg-6" key={submission.id}>
                        <article className="card h-100">
                            <div className="card-body">
                                <h2 className="h5">
                                    {submission.participantUsername}
                                </h2>

                                <p className="small text-muted">
                                    Submitted at: {submission.submittedAt}
                                </p>

                                <ul className="list-group">
                                    {submission.answers.map(answer => (
                                        <li
                                            className="list-group-item"
                                            key={answer.questionId}
                                        >
                                            <strong>{answer.questionText}</strong>
                                            <br />
                                            {renderAnswer(answer)}
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </article>
                    </div>
                ))}
            </div>
        </main>
    );
}