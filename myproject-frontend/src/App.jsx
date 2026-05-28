import Nav from "./components/Nav.jsx";
import './App.css'
import PollsRoutes from "./routes/PollsRoutes.jsx";

function App() {

  return (
      <div className="bg-primary bg-opacity-10">
      <div className="bg-primary">
        <header className="container">
          <Nav/>
        </header>
      </div>
      <div className="bg-white">
        <main className="container pt-3 pb-4 bg-white">
          <PollsRoutes/>

        </main>
      </div>
      <div className="border-top">
        <footer className="container pt-2">
          <p className="text-muted small">Summer 2026, Web Technology Project (ICS), OTH Regensburg<br/>
            Created with Spring Boot, React, Bootstrap, MariaDB, and Docker.<br/>
            Project repository: <a className="text-muted" href="https://gitlab.oth-regensburg.de">OTH GitLab</a></p>
        </footer>
      </div>
      </div>
  )
}

export default App
