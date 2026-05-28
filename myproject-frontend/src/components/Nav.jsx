import {useAuthContext} from "../context/AuthContextProvider.jsx";
import {NavLink} from "react-router";
import * as DateUtil from "../util/DateUtil.js";
import { usePollIdContext } from "../context/PollIdContextProvider.jsx";

export default function Nav() {
    const { auth } = useAuthContext();
    const { pollId } = usePollIdContext();

    return <nav className="container navbar navbar-dark navbar-expand-sm pt-2 pb-2">
        <span className="col navbar-brand">
            <strong>Polls App</strong>
        </span>

        <div id="mainnav" className="col navbar-collapse collapse">
            <div className="navbar-nav ms-auto text-end">
                <NavLink className="nav-link" to="/polls">Polls</NavLink>
                {pollId !== undefined && auth.roles.includes("USER") &&
                    <NavLink className="nav-link" to={"/menu/" + DateUtil.today()}>Menu</NavLink>}
                {pollId !== undefined && auth.roles.includes("USER") &&
                    <NavLink className="nav-link" to="/dishes">Dishes</NavLink>}
                {auth.roles.includes("ADMIN") &&
                    <NavLink className="nav-link" to="/users">Users</NavLink>}
                <NavLink className="nav-link" to="/login">{auth.loggedIn ? "Log out" : "Log in"}</NavLink>
            </div>
        </div>
    </nav>;
}