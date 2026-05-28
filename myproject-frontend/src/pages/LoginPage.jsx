import {useRef, useState} from "react";
import {loggedOut, useAuthContext} from "../context/AuthContextProvider.jsx";
import {usePollIdContext} from "../context/PollIdContextProvider.jsx";
import * as PollApi from "../api/PollApi.js";

export default function LoginPage() {
    const { auth, setAuth } = useAuthContext();
    const { setPollId } = usePollIdContext();
    const [createAccount, setCreateAccount] = useState(false);
    const name = useRef(undefined);
    const password = useRef(undefined);

    function logOut() {
        setAuth(loggedOut);
        setPollId(undefined);
    }

    async function logIn() {
        const newAuth = {name: name.current.value, password: password.current.value}
        const result = await PollApi.logIn(newAuth);
        newAuth.roles = result.roles;
        newAuth.loggedIn = true;
        setAuth(newAuth);
    }

    async function register() {
        const newAuth = {name: name.current.value, password: password.current.value, roles: ["USER"]};
        if (role.current.value === "MANAGER" || role.current.value === "ADMIN") newAuth.roles.push("MANAGER");
        if (role.current.value === "ADMIN") newAuth.roles.push("ADMIN");
        const result = await PollApi.registerUser(newAuth);
        newAuth.roles = result.roles;
        newAuth.loggedIn = true;
        setAuth(newAuth)
    }

    if (auth.loggedIn) {
        return <>
            <p><em>Currently logged in as: <strong>{auth.name}</strong></em></p>
            <button className="btn btn-outline-danger col-12" onClick={logOut}>Log out</button>
        </>;
    } else {
        return <>
            <p><em>Currently not logged in.</em></p>
            <div className="form-check mb-sm-3">
                <input id="new-account" className="form-check-input" type="checkbox" defaultValue={""+createAccount}
                       onChange={e => setCreateAccount(e.target.checked)}/>
                <label className="form-check-label" htmlFor="new-account">I want to create a new account.</label>
            </div>
            <div className="row mb-sm-3">
                <label className="col-sm-3 col-form-label text-end" htmlFor="name">User name:</label>
                <div className="col-sm-9">
                    <input id="name" className="form-control" placeholder="Your name" ref={name}/>
                </div>
            </div>
            <div className="row mb-sm-3">
                <label className="col-sm-3 col-form-label text-end" htmlFor="password">Password:</label>
                <div className="col-sm-9">
                    <input type="password" id="password" className="form-control" placeholder="***" ref={password}/>
                </div>
            </div>
            {createAccount ?
                <>
                <div className="row mb-3">
                    <label className="col-sm-3 col-form-label text-end" htmlFor="roles">Role:</label>
                    <div className="col-sm-9">
                        <select id="role" className="form-select" ref={role}>
                            <option value="USER">User</option>
                            <option value="MANAGER">Manager</option>
                            <option value="ADMIN">Admin</option>
                        </select>
                    </div>
                </div>
                <button className="btn btn-primary col-12 mt-3 mt-sm-0" onClick={register}>Register</button>
            </>
                : <button className="btn btn-primary col-12 mt-3 mt-sm-0" onClick={logIn}>Log in</button>
            }
        </>;
    }
}