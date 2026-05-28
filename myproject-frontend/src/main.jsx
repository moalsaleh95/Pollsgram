import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import {BrowserRouter} from "react-router";
import App from './App.jsx'
import AuthContextProvider from "./context/AuthContextProvider.jsx";
import PollIdContextProvider from "./context/PollIdContextProvider.jsx"


createRoot(document.getElementById('root')).render(
  <StrictMode>
      <AuthContextProvider>
          <PollIdContextProvider>
              <BrowserRouter>
          <App />
              </BrowserRouter>
          </PollIdContextProvider>
      </AuthContextProvider>

  </StrictMode>,
)
