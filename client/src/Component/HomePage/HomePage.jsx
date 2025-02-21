import { Grid2 } from '@mui/material';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';
import HomeSection from '../HomeSection/HomeSection';
import Navigation from '../Navigation/Navigation';
import RightPart from '../RightPart/RightPart'
import Profile from '../Profile/Profile';
import PostDetails from '../PostDetails/PostDetails';
import Message from '../Messages/Message';
import Notification from '../Notification/Notification';
import Friend from '../Friends/Friend';
import { useDispatch, useSelector } from 'react-redux';
import { useChat } from '../Messages/ChatContext';
import { getProfileByJwt, updateUserStatus } from '../../Store/Auth/action';
import { initializeWebSocket } from '../../config/websocket';

const HomePage = () => {
    const [isCollapsed, setIsCollapsed] = useState(false)
    const location = useLocation()
    const [isNotify, setIsNotify] = useState(false)

    const {addMessageReceiver,addNotification} = useChat()
    const auth = useSelector(store => store.auth)
    const dispatch = useDispatch()

    const handleNotify = (status) => {
        setIsNotify(status)
    }

    const handleCloseNotify = () => {
        if(isNotify){
            setIsNotify(false)
        }
    }

    useEffect(() => {
        const onMessageReceived = (msg) => {
            const message = JSON.parse(msg.body)
            addMessageReceiver(message)
        }
        const onUserOnlineStatus = (req) => {
            const userStatus = JSON.parse(req.body)
            dispatch(updateUserStatus(userStatus))
        }
        const onNotification = (noti) => {
            const notice = JSON.parse(noti.body)
            addNotification(notice)
        }
        initializeWebSocket(
            [
            { channel: `/user/${auth?.user?.id}/queue/messages`, callback: onMessageReceived },
            { channel: `/topic/public`, callback: onUserOnlineStatus },
            { channel: `/user/${auth?.user?.id}/queue/notification/`, callback: onNotification}
        ],auth?.user)
    },[auth?.user])

    useEffect(() => {
        if(location.pathname === '/messages' || location.pathname.startsWith('/friend')){
            setIsCollapsed(true)
        } else {
            setIsCollapsed(false)
        }
    }, [location.pathname])

    useEffect(() => {
        dispatch(getProfileByJwt())
    },[])

    return (
        <div onClick={handleCloseNotify}>
            <Grid2 container xs={12} className={`px-5 justify-between ${isCollapsed ? '' : 'lg:px-24'}`}
            >
                {/* Navigation */}
                <Grid2 size={isCollapsed ? 0.5 : 3} xs={0} className="hidden lg:block w-full relative">
                    <Navigation isCollapsed={isCollapsed} isNotify={isNotify} handleNotify={handleNotify}/>
                </Grid2>
        
                {/* Content chính */}
                <Grid2 size={isCollapsed ? 11.5 : 5} xs={12} className="hidden lg:block w-full relative z-0">
                    <Routes>
                        <Route path="/" element={<HomeSection />} />
                        <Route path="/profile/:id" element={<Profile />} />
                        <Route path="/postDetail/:id" element={<PostDetails />} />
                        <Route path="/messages" element={<Message />} />
                        <Route path="/friend/*" element={<Friend />} />
                    </Routes>
                    {/* Hiển thị container Notification */}
                </Grid2>

                {/* RightPart */}
                <Grid2 size={isCollapsed ? 0 : 3} xs={0} className="hidden lg:block w-full relative">
                    {!isCollapsed && <RightPart />}
                </Grid2>
            </Grid2>
            {isNotify && (
                <div className="fixed top-0 left-[60px] lg:left-[420px] z-[50] bg-white shadow-lg border rounded-md w-[300px] h-screen overflow-y-auto p-4 notification-container"
                onClick={(e) => e.stopPropagation()}>
                    <Notification />
                </div>
            )}
        </div>
    );
}

export default HomePage;
