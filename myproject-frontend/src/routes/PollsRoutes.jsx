import {Route, Routes} from "react-router";
import LoginPage from "../pages/LoginPage.jsx";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import {usePollIdContext} from "../context/PollIdContextProvider.jsx";
import PollsPage from "../pages/PollsPage.jsx";

export default function PollsRoutes() {
    const { auth } = useAuthContext();
    const { pollId } = usePollIdContext();

    if (!auth.loggedIn) {
        return <LoginPage/>;
    } else if (pollId === undefined) {
        return <PollsPage/>;
    }

    return <Routes>
        <Route index path={"schools"} element={<PollsPage/>}/>
    </Routes>;
}