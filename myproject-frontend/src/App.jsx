import {Navigate, Route, Routes} from "react-router";
import Nav from "./components/Nav.jsx";
import ProtectedRoute from "./components/ProtectedRoute.jsx";

import LoginPage from "./pages/LoginPage.jsx";
import RegisterPage from "./pages/RegisterPage.jsx";
import MyPollsPage from "./pages/MyPollsPage.jsx";
import PendingPollsPage from "./pages/PendingPollsPage.jsx";
import CreatePollPage from "./pages/CreatePollPage.jsx";
import PollDetailsPage from "./pages/PollDetailsPage.jsx";
import SubmitPollPage from "./pages/SubmitPollPage.jsx";
import ResultsPage from "./pages/ResultsPage.jsx";
import SubmissionsPage from "./pages/SubmissionsPage.jsx";
import EditPollPage from "./pages/EditPollPage.jsx";
import NotFoundPage from "./pages/NotFoundPage.jsx";
export default function App() {
    return (
        <>
            <Nav />

            <Routes>
                <Route path="/" element={<Navigate to="/polls/my" />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />

                <Route
                    path="/polls/my"
                    element={
                        <ProtectedRoute>
                            <MyPollsPage />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/polls/pending"
                    element={
                        <ProtectedRoute>
                            <PendingPollsPage />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/polls/create"
                    element={
                        <ProtectedRoute>
                            <CreatePollPage />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/polls/:id"
                    element={
                        <ProtectedRoute>
                            <PollDetailsPage />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/polls/:id/submit"
                    element={
                        <ProtectedRoute>
                            <SubmitPollPage />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/polls/:id/results"
                    element={
                        <ProtectedRoute>
                            <ResultsPage />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/polls/:id/submissions"
                    element={
                        <ProtectedRoute>
                            <SubmissionsPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/polls/:id/edit"
                    element={
                        <ProtectedRoute>
                            <EditPollPage />
                        </ProtectedRoute>
                    }
                />

                // path="*" catches every URL that did not match the defined routes.
                <Route path="*" element={<NotFoundPage />} />

            </Routes>
        </>
    );
}