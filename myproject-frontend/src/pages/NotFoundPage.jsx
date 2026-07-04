import {Link} from "react-router";

export default function NotFoundPage() {
    return (
        <main className="container py-5 text-center">
            <h1 className="display-5">Page not found</h1>

            <p className="text-muted">
                The page you are looking for does not exist.
            </p>

            <Link className="btn btn-primary" to="/polls/my">
                Back to My Polls
            </Link>
        </main>
    );
}