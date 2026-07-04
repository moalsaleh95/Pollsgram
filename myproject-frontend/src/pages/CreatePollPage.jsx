import {useState} from "react";
import {useNavigate} from "react-router";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import {createPoll} from "../api/pollApi.js";
import ErrorMessage from "../components/ErrorMessage.jsx";
import {
    validateRequired,
    validateFutureDate
} from "../util/validation.js";

const emptyQuestion = {
    text: "",
    type: "BOOLEAN"
};

export default function CreatePollPage() {
    const navigate = useNavigate();
    const {auth} = useAuthContext();

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [dueDate, setDueDate] = useState("");
    const [questions, setQuestions] = useState([{...emptyQuestion}]);
    const [error, setError] = useState("");

    function updateQuestion(index, field, value) {
        const updatedQuestions = questions.map((question, currentIndex) => {
            if (currentIndex !== index) {
                return question;
            }

            return {
                ...question,
                [field]: value
            };
        });

        setQuestions(updatedQuestions);
    }

    function addQuestion() {
        setQuestions([...questions, {...emptyQuestion}]);
    }

    function removeQuestion(index) {
        if (questions.length === 1) {
            setError("A poll must contain at least one question");
            return;
        }

        setQuestions(questions.filter((_, currentIndex) => currentIndex !== index));
    }

    async function handleSubmit(event) {
        event.preventDefault();
        setError("");

        const titleError = validateRequired(title, "Title");
        if (titleError) {
            setError(titleError);
            return;
        }

        const descriptionError = validateRequired(description, "Description");
        if (descriptionError) {
            setError(descriptionError);
            return;
        }

        const dateError = validateFutureDate(dueDate);
        if (dateError) {
            setError(dateError);
            return;
        }

        for (const question of questions) {
            const questionError = validateRequired(question.text, "Question");
            if (questionError) {
                setError(questionError);
                return;
            }
        }

        const poll = {
            title,
            description,
            dueDate,
            questions
        };

        try {
            await createPoll(auth, poll);
            navigate("/polls/my");
        } catch (err) {
            setError(err.message);
        }
    }

    return (
        <main className="container py-4">
            <h1 className="mb-3">Create Poll</h1>

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

                <section className="mb-3">
                    <div className="d-flex justify-content-between align-items-center mb-2">
                        <h2 className="h5 mb-0">Questions</h2>

                        <button
                            type="button"
                            className="btn btn-sm btn-outline-primary"
                            onClick={addQuestion}
                        >
                            Add question
                        </button>
                    </div>

                    {questions.map((question, index) => (
                        <div className="border rounded p-3 mb-3" key={index}>
                            <div className="mb-3">
                                <label className="form-label">
                                    Question text
                                </label>
                                <input
                                    className="form-control"
                                    value={question.text}
                                    onChange={event =>
                                        updateQuestion(index, "text", event.target.value)
                                    }
                                />
                            </div>

                            <div className="mb-3">
                                <label className="form-label">
                                    Question type
                                </label>
                                <select
                                    className="form-select"
                                    value={question.type}
                                    onChange={event =>
                                        updateQuestion(index, "type", event.target.value)
                                    }
                                >
                                    <option value="BOOLEAN">Yes / No</option>
                                    <option value="NUMBER">Number 1-5</option>
                                    <option value="TEXT">Text</option>
                                </select>
                            </div>

                            <button
                                type="button"
                                className="btn btn-sm btn-outline-danger"
                                onClick={() => removeQuestion(index)}
                            >
                                Remove question
                            </button>
                        </div>
                    ))}
                </section>

                <button className="btn btn-primary" type="submit">
                    Create Poll
                </button>
            </form>
        </main>
    );
}