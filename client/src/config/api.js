import axios from "axios"


export const API_BASE_URL = "http://localhost:5454"

export const api = axios.create({
    baseURL: API_BASE_URL,
    headers:{
        "Content_Type":"application/json"
    }
})

api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("accessToken");
        console.log("accesstoken:", token) // Lấy JWT mới nhất từ localStorage
        if (token) {
            config.headers["Authorization"] = `Bearer ${token}`; // Cập nhật header Authorization
        }
        return config;
    }, 
    (error) => {
        return Promise.reject(error);
});
