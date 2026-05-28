import {createContext, useContext, useState} from "react";

const PollIdContext = createContext(undefined);

export function usePollIdContext() {
    return useContext(PollIdContext);
}

export default function PollIdProvider({children}) {
    const [pollId, setPollId] = useState(undefined);

    return <PollIdContext.Provider value={{pollId, setPollId}}>
        {children}
    </PollIdContext.Provider>;
}