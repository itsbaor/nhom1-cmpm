import "./App.css";
import HomePage from "./Component/HomePage/HomePage";
import Authentication from "./Component/Authentication/Authentication";
import { Route, Routes } from "react-router-dom";
import { useSelector } from "react-redux";

function App() {
  const { auth } = useSelector((store) => store);


  return (
    <Routes>
      <Route path="/*" element={auth?.jwt ? <HomePage /> : <Authentication/>} />
    </Routes>
  );
}

export default App;