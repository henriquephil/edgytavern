import { Route, Routes, useNavigate } from "react-router-dom";
import './App.css';
import Authenticated from "./pages/Authenticated";
import SecurityService from "./services/SecurityService";

function App() {
  if (SecurityService.isAuthenticated()) {
    return <Authenticated/>
  } else {
    return (
      <div className="App">
        Not authenticated
      </div>
    );
  }
}

export default App;
