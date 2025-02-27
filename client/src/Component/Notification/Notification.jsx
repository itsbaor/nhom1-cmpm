import React, { useEffect } from "react";
import { useChat } from "../Messages/ChatContext";
import { Card, CardContent, Typography, Divider, Avatar } from "@mui/material";
import NotificationsIcon from '@mui/icons-material/Notifications';
import { api, API_BASE_URL } from "../../config/api";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { formatTimeDifference } from "../../Utils/formatTimeDifferent";

const Notification = () => {
  //Lây thông tin người dùng
  const auth = useSelector((store) => store.auth);
  //Lấy thông tin thông báo
  const { notifications, setNotification } = useChat();
  //Điều hướng
  const navigate = useNavigate();

  //Lấy thông báo từ API
  const getNotify = async () => {
    const { data } = await api.get(
      `${API_BASE_URL}/api/notification/listNotify/${auth?.user?.id}`
    );
    setNotification(data);
  };

  //Lấy thông báo khi component này chạy
  useEffect(() => {
    getNotify();
  }, []);
  return (
    <div className="max-w-lg mx-auto mt-6 p-4 bg-white rounded-lg shadow-md">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-semibold text-gray-800">Notifications</h2>
        <NotificationsIcon fontSize="large" className="text-gray-500" />
      </div>
      <Divider />
      {/* <div className="space-y-4 mt-4">
        {notifications.map((notification) => (
          <Card
            key={notification.id}
            className="cursor-pointer hover:shadow-lg transition-shadow"
            onClick={() => navigate(`/postDetail/${notification.idPosts}`)}
          >
            <CardContent className="flex items-center">
              <Avatar
                className="mr-4 bg-blue-500"
                src={notification.sender.image}
              />
              <div>
                <Typography
                  variant="subtitle1"
                  className="text-gray-900 font-medium"
                >
                  {notification.title}
                </Typography>
                <p className="text-sm text-gray-400">
                  {formatTimeDifference(notification.timestamp)}
                </p>
              </div>
            </CardContent>
          </Card>
          
        ))}
      </div> */}

      {/* Test */}
      <div className="space-y-4 mt-4">
        <Card className="cursor-pointer hover:shadow-lg transition-shadow">
          <CardContent className="flex items-center">
            <Avatar
              className="mr-4 bg-blue-500"
              src="https://plus.unsplash.com/premium_photo-1664474619075-644dd191935f?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aW1hZ2V8ZW58MHx8MHx8fDA%3D"
            />
            <div>
              <Typography
                variant="subtitle1"
                className="text-gray-900 font-medium"
              >
                Chụp ảnh
              </Typography>
              <p className="text-sm text-gray-400">12:00:00</p>
            </div>
          </CardContent>
        </Card>

        <Card className="cursor-pointer hover:shadow-lg transition-shadow">
          <CardContent className="flex items-center">
            <Avatar
              className="mr-4 bg-blue-500"
              src="https://plus.unsplash.com/premium_photo-1664474619075-644dd191935f?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aW1hZ2V8ZW58MHx8MHx8fDA%3D"
            />
            <div>
              <Typography
                variant="subtitle1"
                className="text-gray-900 font-medium"
              >
                Chụp ảnh
              </Typography>
              <p className="text-sm text-gray-400">12:00:00</p>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Notification;
