import { Avatar } from '@mui/material';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useChat } from './ChatContext';
import { getUserList } from '../../Store/Auth/action';
import { api } from '../../config/api';

const UserList = () => {
    const { selectUser, setMessage } = useChat();
    const dispatch = useDispatch();
     const auth = useSelector(store => store.auth)

    const getMessage = async (senderId, recipientId) => {
        if (!senderId || !recipientId) {
            console.error("Sender or recipient ID is missing");
            return;
        }

        try {
            const { data } = await api.get(`/api/messages/${senderId}/${recipientId}`);
            console.log("list message:", data);
            setMessage(data);
        } catch (error) {
            console.error("Error fetching messages:", error);
        }
    };

    useEffect(() => {
        dispatch(getUserList());
    }, []);

    return (
        <div className="p-4">
            <div className='pt-3 font-bold text-xl text-gray-800'>
                {auth?.user?.fullName}
            </div>
            <ul className="mt-4 space-y-4">
                {console.log("list user của t đâu: ", auth.listUser)}
                {Array.isArray(auth?.listUser) && auth.listUser.length > 0 ? (
                    auth.listUser.map((user) => (
                        <li
                            key={user?.id}
                            className="flex items-center space-x-4 p-2 hover:bg-gray-100 rounded cursor-pointer transition-colors duration-200 ease-in-out"
                            onClick={() => {
                                selectUser(user);
                                console.log("activeUser: ", user)
                                getMessage(auth?.user?.id, user?.id); // 
                            }}
                        >
                            <div className="relative w-10 h-10">
                                {/* Avatar */}
                                <Avatar src={user?.image} className="w-10 h-10 rounded-full" />

                                {/* Trạng thái online */}
                                {user?.status === "ONLINE" && (
                                    <span className="absolute bottom-0 right-0 w-3 h-3 bg-green-500 border-2 border-white rounded-full"></span>
                                )}
                            </div>
                            <div>
                                <p className="font-medium">{user?.fullName}</p>
                                <p className='font-normal text-gray-500 text-xs'>{user?.status === "ONLINE" ? 'Đang hoạt động' : 'Không hoạt động'}</p>  
                            </div>
                        </li>
                    ))
                ) : (
                    <li>No users available</li> 
                )}
            </ul>
        </div>
    );
};


export default UserList;
