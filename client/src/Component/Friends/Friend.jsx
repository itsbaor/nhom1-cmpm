import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Route, Routes} from 'react-router-dom';
import RequestAddFriend from './RequestAddFriend';
import SuggestFriend from './SuggestFriend';
import SendRequireAddFriend from './SendRequireAddFriend';
import { Grid2 } from '@mui/material';

const Friend = () => {
    const navigate = useNavigate()
    
    return (
        <Grid2 className="flex h-screen bg-gray-100">
            {/* Navbar Friend */}
            <div className="w-1/6 border-r bg-white shadow-lg overflow-y-auto flex flex-col space-y-4">
                <h2 className="text-xl font-bold text-gray-900 p-4 border-b ">Friends</h2>
                <div
                    className="p-4 cursor-pointer hover:bg-blue-50 hover:text-blue-600 transition-all"
                    onClick={() => navigate("reqfr")}
                >
                    📩 Request Friend

                </div>
                <div
                    className="p-4 cursor-pointer hover:bg-blue-50 hover:text-blue-600 transition-all"
                    onClick={() => navigate("sendrqfr")}
                >
                    ✉️ Send Request
                </div>
                <div
                    className="p-4 cursor-pointer hover:bg-blue-50 hover:text-blue-600 transition-all"
                    onClick={() => navigate("suggestfr")}
                >
                    🌟 Suggest Friend
                </div>
            </div>

            {/* Nội dung chính */}
            <div className="flex-1 p-6">
                <Routes>
                    <Route path="reqfr" element={<RequestAddFriend />} />
                    <Route path="sendrqfr" element={<SendRequireAddFriend />} />
                    <Route path="suggestfr" element={<SuggestFriend />} />
                </Routes>
            </div>
        </Grid2>

    
    );
}

export default Friend;
