import { Drawer, Grid2 } from '@mui/material';
import React, { useEffect, useMemo, useState } from 'react';
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
import { getProfileByJwt, updateUserListStatus, updateUserStatus } from '../../Store/Auth/action';
import { initializeWebSocket } from '../../config/websocket';
import BookmarkPost from '../BookmarkPost/BookmarkPost';

const HomePage = () => {
  const location = useLocation()
  const [isNotify, setIsNotify] = useState(false)

  const {addMessageReceiver,addNotification} = useChat()
  const auth = useSelector(store => store.auth)
  const dispatch = useDispatch()

  const isCollapsed = location.pathname === '/messages' || location.pathname.startsWith('/friend');

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
    const onInitialOnlineUsers = (listUser) => {
      const list = JSON.parse(listUser.body)
      dispatch(updateUserListStatus(list))
    }
    initializeWebSocket(
        [
        { channel: `/user/${auth?.user?.id}/queue/messages`, callback: onMessageReceived },
        { channel: `/user/${auth?.user?.id}/queue/status`, callback: onUserOnlineStatus },
        { channel: `/user/${auth?.user?.id}/queue/notification/`, callback: onNotification},
        { channel: `/user/${auth?.user?.id}/queue/onlineUsers`, callback: onInitialOnlineUsers } // Nhận danh sách online
    ],auth?.user)
},[auth?.user])

  useEffect(() => {
      if (auth?.jwt) {
        dispatch(getProfileByJwt())
      }
  },[auth?.jwt,dispatch])

  const notificationStyle = useMemo(() => ({
      position: 'fixed',
      top: 0,
      left: isCollapsed ? '60px' : '420px',
      zIndex: 50,
      backgroundColor: 'white',
      boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
      border: '1px solid #ddd',
      borderRadius: '4px',
      width: '300px',
      height: '100vh',
      overflowY: 'auto',
      padding: '16px',
  }), [isCollapsed]);

    return (
    <div onClick={handleCloseNotify}>
        <Grid2 container xs={12} className={`px-5 justify-between ${isCollapsed ? '' : 'lg:px-24'}`}
        >
            {/* Navigation */}
            <Grid2 size={isCollapsed ? 0.5 : 3} className="w-full relative">
                <Navigation isCollapsed={isCollapsed} isNotify={isNotify} handleNotify={handleNotify}/>
            </Grid2>
    
            {/* Content chính */}
            <Grid2 size={isCollapsed ? 11.5 : 5}  className="w-full relative z-0">
                <Routes>
                    <Route path="/" element={<HomeSection />} />
                    <Route path="/profile/:id" element={<Profile />} />
                    <Route path="/postDetail/:id" element={<PostDetails />} />
                    <Route path="/bookmark" element={<BookmarkPost />} />
                    <Route path="/messages" element={<Message />} />
                    <Route path="/friend/*" element={<Friend />} />
                </Routes>
                {/* Hiển thị container Notification */}
            </Grid2>

            {/* RightPart */}
            <Grid2 size={isCollapsed ? 0 : 3}  className=" w-full relative">
                {!isCollapsed && <RightPart />}
            </Grid2>
        </Grid2>

        {/* Drawer cho Notification */}
        <Drawer
          anchor="right"
          open={isNotify}
          onClose={handleCloseNotify}
          sx={{
            '& .MuiDrawer-paper': {
              width: '300px',
            },
          }}
        >
          <Notification />
        </Drawer>
    </div>
    );
}

export default HomePage;
