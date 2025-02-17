import "./App.css";
import HomePage from "./components/screen/HomePage.js";
import Login from "./components/FromAcc/login/Login";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import Signup from "./components/FromAcc/signup/Signup.js";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/home" element={<HomePage />} />
          <Route path="/" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
