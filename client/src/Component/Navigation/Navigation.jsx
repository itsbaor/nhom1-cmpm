import React, { useEffect } from 'react';
import logoSocial from '../../image/logoSocial.png';
import { NavigationMenu } from './NavigationMenu'
import { useState } from 'react';
import  LogoutOutlinedIcon  from '@mui/icons-material/LogoutOutlined';
import { useDispatch, useSelector } from 'react-redux';
import { logOut } from '../../Store/Auth/action';
import { useNavigate } from 'react-router-dom';
import { disconnectWebSocket } from '../../config/websocket';
import { useChat } from '../Messages/ChatContext';
import Notification from '../Notification/Notification';
import { api, API_BASE_URL } from '../../config/api';

const Navigation = ({ isCollapsed, handleNotify }) => {
    const [selectedIconId, setSelectedIcon] = useState(null)
    const {resetChatContext,notifications} = useChat()
    const [countUnreadNotify, setCountUnreadNotify] = useState('')
    
    const navigate = useNavigate()
    const auth = useSelector(store => store.auth)
    const dispatch = useDispatch()

    const getUnreadNotify = async () => {
        const {data} = await api.get(`${API_BASE_URL}/api/notification/unread-notify`)
        setCountUnreadNotify(data)
    }
    
    useEffect(() => {
        getUnreadNotify() 
    },[notifications])

    const handleChangeIconColor = (id) => {
        setSelectedIcon(prevSelected => (prevSelected === id && selectedIconId === id) ? id : id);
    }

    const handleChangePath = (path, title) => {
        title === "Profile" ?  navigate(`/profile/${auth?.user?.id}`) : navigate(path);
        title === "Notifications" ? handleNotify(true) : handleNotify(false)  ;
    }
    const handleLogout = () => {
        console.log("🔴 Bắt đầu logout...");
        dispatch(logOut()); // Xóa Redux state
    
        console.log("🟡 Đang disconnect WebSocket...");
        disconnectWebSocket(auth?.user); 
    
        console.log("🔵 Đang reset Chat Context...");
        resetChatContext();
    
        console.log("✅ Logout hoàn tất!");
    };
    

    return (
        <div
            className={`h-screen sticky top-0 transition-all duration-300 bg-gray-100 ${(isCollapsed)? 'w-[60px]' : 'w-[300px]'
                }`}
        >
            {/* Logo */}
            <div className={`py-4 flex items-center justify-center`}>
                <img src={logoSocial} alt="Logo Social" className="w-16" />
            </div>

            {/* Navigation Menu */}
            <div className={`py-6 space-y-4 ${(isCollapsed) ? '' : 'px-4'}`}>
                {NavigationMenu.map((item) => (
                    <div
                        key={item.id}
                        className={`flex items-center space-x-3 px-4 py-2 cursor-pointer rounded-lg transition-all duration-200 
                            ${selectedIconId === item.id
                                ? 'bg-blue-100 text-blue-500'
                                : 'hover:bg-gray-100 text-gray-700'
                            }`}
                        onClick={(e) => {
                            e.stopPropagation();
                            handleChangeIconColor(item.id);
                            handleChangePath(item.path, item.title);
                        }}
                    >
                        {/* Icon */}
                        <div className="text-2xl relative">
                            {selectedIconId === item.id ? item.iconTouched : item.icon}
                            {(countUnreadNotify > 0 && item.title === "Notifications") && (
                                    <span className="absolute top-0 right-[-3px] w-3 h-3 text-sm font-bold text-red-500">{countUnreadNotify}</span>
                                )}
                        </div>

                        {/* Title */}
                        {(!isCollapsed) && (
                            <p className="text-base font-medium">{item.title}</p>
                        )}
                    </div>
                ))}
            </div>

            {/* Divider */}
            <hr className={`my-8 border-gray-300 ${(isCollapsed)? 'hidden' : ''}`} />

            {/* User Info */}
            {(!isCollapsed) && (
                <div
                    className={`flex items-center px-4 py-4 cursor-pointer ${(isCollapsed)? 'justify-center' : 'space-x-4'
                        }`}
                    onClick={() => navigate(`/profile/${auth?.user?.id}`)}
                >
                    {/* Avatar */}
                    <img
                        src={auth?.user?.image}
                        alt="User Avatar"
                        className="w-10 h-10 rounded-full"
                    />
                    {/* User Info */}
                    {(!isCollapsed) && (
                        <div className="flex flex-col">
                            <p className="font-semibold text-gray-800">
                                {auth?.user?.fullName}
                            </p>
                            <p className="text-sm text-gray-500">{auth?.user?.email}</p>
                        </div>
                    )}
                    {/* Logout Icon */}
                    <LogoutOutlinedIcon
                        onClick={handleLogout}
                        className={`text-gray-500 hover:text-red-500 transition-colors duration-200`}
                    />
                </div>
            )}
        </div>
    );
}

export default Navigation;
