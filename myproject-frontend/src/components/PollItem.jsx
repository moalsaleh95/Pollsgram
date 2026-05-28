import {useAuthContext} from "../context/AuthContextProvider.jsx";
import {usePollIdContext} from "../context/PollIdContextProvider.jsx";

export default function SchoolItem({poll, deletePoll}) {
    const { auth } = useAuthContext();
    const { pollId, setPollId } = usePollIdContext();

    const selected = poll.id === pollId;

    return <div className="col-sm-6 mb-3">
        <div className={"card p-2 " + (selected ? "bg-primary bg-opacity-10" : "bg-light")}>
            <h5 className="card-title">{poll.name + " "}</h5>
            <div className="row">
                <div className="col-md-6 d-grid">
                    {selected ?
                        <button className="btn btn-primary mb-2 mb-md-0" disabled>Selected</button> :
                        <button className="btn btn-outline-primary mb-2 mb-md-0" onClick={() => setPollId(poll.id)}>Select</button>
                    }
                </div>
                {auth.roles.includes("ADMIN") &&
                    <div className="col-md-6 d-grid">
                        <button className="btn btn-danger" disabled={selected} onClick={() => deletePoll(poll.id)}>Delete</button>
                    </div>
                }
            </div>
        </div>
    </div>
}