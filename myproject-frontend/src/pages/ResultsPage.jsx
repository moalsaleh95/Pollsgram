import {useEffect, useState} from "react";
import {useParams} from "react-router";
import {fetchPollById, fetchPollResults} from "../api/pollApi.js";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import ErrorMessage from "../components/ErrorMessage.jsx";
import Loading from "../components/Loading.jsx";
export default function ResultsPage() {
    const {id} = useParams();
    const {auth} = useAuthContext();

    const [poll, setPoll] = useState(null);
    const [results, setResults] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function loadData() {
            setError("");
            setLoading(true);

            try {
                const pollData = await fetchPollById(auth, id);
                const resultData = await fetchPollResults(auth, id);

                setPoll(pollData);
                setResults(resultData);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        }

        loadData();
    }, [auth, id]);

    function renderResult(result) {
        if (result.questionType === "BOOLEAN") {
            return (
                <ul className="list-group">
                    <li className="list-group-item">
                        Yes: {result.trueCount}
                    </li>
                    <li className="list-group-item">
                        No: {result.falseCount}
                    </li>
                </ul>
            );
        }

        if (result.questionType === "NUMBER") {
            return (
                <p className="mb-0">
                    Average rating:{" "}
                    <strong>{result.averageValue.toFixed(2)}</strong>
                </p>
            );
        }

        return (
            <ul className="list-group">
                {result.textAnswers.length === 0 && (
                    <li className="list-group-item text-muted">
                        No text answers.
                    </li>
                )}

                {result.textAnswers.map((answer, index) => (
                    <li className="list-group-item" key={index}>
                        {answer}
                    </li>
                ))}
            </ul>
        );
    }

    if (loading) {
        return <Loading />;
    }

    return (
        <main className="container py-4">
            <h1 className="mb-3">Poll Results</h1>

            <ErrorMessage message={error} />

            {poll && (
                <section className="mb-4">
                    <h2 className="h4">{poll.title}</h2>
                    <p className="text-muted">{poll.description}</p>
                </section>
            )}

            <div className="row g-3">
                {results.map(result => (
                    <div className="col-12 col-md-6" key={result.questionId}>
                        <article className="card h-100">
                            <div className="card-body">
                                <h3 className="h5">{result.questionText}</h3>
                                <p className="small text-muted">
                                    Type: {result.questionType}
                                </p>

                                {renderResult(result)}
                            </div>
                        </article>
                    </div>
                ))}
            </div>
        </main>
    );
}