import { createContext, useContext, useState, useCallback, useMemo } from "react";
import { sendMessage } from "../../config/websocket";

const ChatContext = createContext();

export const ChatProvider = ({ children }) => {
    const [messages, setMessages] = useState([]);
    const [activeUser, setActiveUser] = useState(null);
    const [notifications, setNotifications] = useState([]);

    const addMessageSender = useCallback((message) => {
        setMessages((prev) => {
            const updatedMessages = [...prev, message];
            console.log("list messages:", updatedMessages);
            return updatedMessages;
        });
        sendMessage(message);
    }, []);

    const addMessageReceiver = useCallback((message) => {
        setMessages((prev) => {
            const updatedMessages = [...prev, message];
            console.log("list messages receiver:", updatedMessages);
            return updatedMessages;
        });
    }, []);

    const setMessage = useCallback((listMessage) => {
        setMessages(listMessage);
    }, []);

    const selectUser = useCallback((user) => {
        setActiveUser(user);
    }, []);

    const resetChatContext = useCallback(() => {
        setMessages([]);
        setActiveUser(null);
        setNotifications([]);
    }, []);

    const setNotification = useCallback((listNotification) => {
        setNotifications(listNotification);
        console.log("List notification:", listNotification);
    }, []);

    const addNotification = useCallback((notification) => {
        setNotifications((prev) => {
            const updatedNotifications = [...prev, notification];
            console.log("Notification add:", notification);
            return updatedNotifications;
        });
    }, []);

    const contextValue = useMemo(() => ({
        messages,
        activeUser,
        notifications,
        addMessageSender,
        addMessageReceiver,
        setMessage,
        selectUser,
        resetChatContext,
        setNotification,
        addNotification
    }), [messages, activeUser, notifications, addMessageSender, addMessageReceiver, setMessage, selectUser, resetChatContext, setNotification, addNotification]);

    return (
        <ChatContext.Provider value={contextValue}>
            {children}
        </ChatContext.Provider>
    );
};

export const useChat = () => useContext(ChatContext);
