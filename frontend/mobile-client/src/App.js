import { BrowserRouter, Route, Routes } from "react-router-dom";
import './App.css';
import Authenticated from "./pages/Authenticated";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={Authenticated} />
        {/* <Redirect to="/" /> */}
      </Routes>
    </div>
  );
}

export default App;
