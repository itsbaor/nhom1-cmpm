import { createContext, useContext, useEffect, useState } from "react";
import { sendMessage } from "../../config/websocket";

const ChatContext = createContext()

export const ChatProvider = ({ children }) => {
    const [messages, setMessages] = useState([])
    const [activeUser, setActiveUser] = useState(null)
    const [notifications, setNotifications] = useState([])
    
    const addMessageSender = (message) => {
        setMessages((prev) => [...prev, message]);
        console.log("list messages: ",messages)
        sendMessage(message);
    };

    const addMessageReceiver = (message) => {
        setMessages((prev) => [...prev, message]);
        console.log("list messages receiver: ",messages)
    };

    const setMessage = (listMessage) => {
        setMessages(listMessage)
    }

    const selectUser = (user) => {
        setActiveUser(user);
    };

    const resetChatContext = () => {
        setMessages([])
        setActiveUser(null)
    }

    const setNotification = (listNotification) => {
        setNotifications(listNotification)
        console.log("List notification: ", listNotification)
    }

    const addNotification = (notification) => {
        setNotifications((prev) => [...prev, notification])
        console.log("Notification add: ", notification)
    }

    return (
        <ChatContext.Provider value={{ messages, activeUser, notifications,
         addMessageSender,addMessageReceiver, setMessage, selectUser, resetChatContext, setNotification, addNotification }}>
            {children}
        </ChatContext.Provider>
    );
};

export const useChat = () => {
    return useContext(ChatContext);
};
