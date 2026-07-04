import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router";
import {fetchPollById, submitPoll} from "../api/pollApi.js";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import ErrorMessage from "../components/ErrorMessage.jsx";
import Loading from "../components/Loading.jsx";
export default function SubmitPollPage() {
    const {id} = useParams();
    const navigate = useNavigate();
    const {auth} = useAuthContext();

    const [poll, setPoll] = useState(null);
    const [answers, setAnswers] = useState({});
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
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

        loadPoll();
    }, [auth, id]);

    function updateAnswer(questionId, field, value) {
        setAnswers(previous => ({
            ...previous,
            [questionId]: {
                questionId,
                [field]: value
            }
        }));
    }

    function renderInput(question) {
        const currentAnswer = answers[question.id] || {};

        if (question.type === "BOOLEAN") {
            return (
                <select
                    className="form-select"
                    value={
                        currentAnswer.booleanValue === undefined
                            ? ""
                            : String(currentAnswer.booleanValue)
                    }
                    onChange={event =>
                        updateAnswer(
                            question.id,
                            "booleanValue",
                            event.target.value === "true"
                        )
                    }
                >
                    <option value="">Choose...</option>
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>
            );
        }

        if (question.type === "NUMBER") {
            return (
                <input
                    type="number"
                    min="1"
                    max="5"
                    className="form-control"
                    value={currentAnswer.numericValue || ""}
                    onChange={event =>
                        updateAnswer(
                            question.id,
                            "numericValue",
                            Number(event.target.value)
                        )
                    }
                />
            );
        }

        return (
            <textarea
                className="form-control"
                rows="3"
                value={currentAnswer.textValue || ""}
                onChange={event =>
                    updateAnswer(question.id, "textValue", event.target.value)
                }
            />
        );
    }

    async function handleSubmit(event) {
        event.preventDefault();
        setError("");

        const answerList = Object.values(answers);

        try {
            await submitPoll(auth, id, answerList);
            navigate("/polls/pending");
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
            <h1 className="mb-2">{poll.title}</h1>
            <p className="text-muted">{poll.description}</p>

            <ErrorMessage message={error} />

            <form onSubmit={handleSubmit} className="card p-3">
                {poll.questions.map(question => (
                    <div className="mb-4" key={question.id}>
                        <label className="form-label fw-semibold">
                            {question.text}
                        </label>

                        {renderInput(question)}
                    </div>
                ))}

                <button className="btn btn-primary" type="submit">
                    Submit Answers
                </button>
            </form>
        </main>
    );
}