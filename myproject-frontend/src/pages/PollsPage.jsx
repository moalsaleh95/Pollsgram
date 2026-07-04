import {useEffect, useRef, useState} from "react";
import {useAuthContext} from "../context/AuthContextProvider.jsx";
import * as PollApi from "../api/pollApi.js";
import PollItem from "../components/PollItem.jsx";

export default function PollsPage() {
    const { auth } = useAuthContext();
    const [polls, setPolls] = useState([]);
    const newPollName = useRef(undefined);

    useEffect(() => {
        PollsApi.getPolls().then(result => {
            setPolls(result);
        });
    }, [auth]);

    async function createPoll(name) {
        const newPoll = {name: name};
        const result = await PollsApi.createPoll(newPoll);
        setPolls([...polls, result]);
    }

    async function deletePoll(id) {
        await PollsApi.deletePoll(id);
        setPolls(polls.filter(s => s.id !== id));
    }

    return <div className="row mt-2">
        {polls.map(s =>
            <PollItem key={s.id} poll={s} deletePoll={deletePoll}/>
        )}
        {auth.roles.includes("ADMIN") && <>
            <div className="col-sm-9">
                <input className="form-control" ref={newPollName} defaultValue="New Poll"/>
            </div>
            <div className="col-sm-3 d-grid mt-2 mt-sm-0">
                <button className="btn btn-primary" onClick={() => createPoll(newPollName.current.value)}>Create</button>
            </div>
        </>}
        {!auth.loggedIn && <p><em>To see polls, please log in.</em></p>}
    </div>;
}