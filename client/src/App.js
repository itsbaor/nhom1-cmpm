import "./App.css";
import HomePage from "./Component/HomePage/HomePage";
import Authentication from "./Component/Authentication/Authentication";
import { Route, Routes } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { getProfileByJwt } from "./Store/Auth/action";
import { jwtDecode } from "jwt-decode";
import { api } from "./config/api";

// Kiểm tra tính hợp lệ của token
const isTokenValid = (token) => {
  if (!token) return false;

  try {
    const decoded = jwtDecode(token);
    const currentTime = Date.now() / 1000; // Chuyển đổi sang giây
    return decoded.exp > currentTime;
  } catch (error) {
    console.error("Failed to decode token", error);
    return false;
  }
};

let isRefreshing = false; // Biến cờ để kiểm soát việc refresh token

const refreshToken = async () => {
  try {
    const { data } = await api.post("/api/auth/refresh-token", {}, {
      withCredentials: true,
    });

    localStorage.setItem("accessToken", data.accessToken);
    return data.accessToken;
  } catch (error) {
    console.error("Refresh Token failed", error);
    window.location.href = "/signin";
    return null;
  }
};

const checkAndRefreshToken = async () => {
  if (isRefreshing) return; // Nếu đang refresh, không làm gì cả

  const accessToken = localStorage.getItem("accessToken");

  if (!accessToken || !isTokenValid(accessToken)) {
    isRefreshing = true; // Đánh dấu đang refresh token
    await refreshToken();
    isRefreshing = false; // Đánh dấu đã refresh xong
  }
};

function App() {
  const { auth } = useSelector((store) => store);


  return (
    <Routes>
      {/* <Route path="/*" element={auth?.jwt ? <HomePage /> : <Authentication/>} /> */}
      <Route path="/*" element={<HomePage />} />
    </Routes>
  );
}

export default App;