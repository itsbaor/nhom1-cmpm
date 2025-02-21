import React, { useState } from 'react';
import ChatBubbleOutlineOutlinedIcon from '@mui/icons-material/ChatBubbleOutlineOutlined';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import BookmarkBorderOutlinedIcon from '@mui/icons-material/BookmarkBorderOutlined';
import FavoriteOutlinedIcon from '@mui/icons-material/FavoriteOutlined';
import { Avatar } from '@mui/material';
import { formatTimeDifference } from '../../Utils/formatTimeDifferent';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { hiddenPosts, hiddenUser, likePosts, removelikePosts } from '../../Store/Posts/Action';
import CommentModal from './CommentModal';
import MoreHorizOutlinedIcon from '@mui/icons-material/MoreHorizOutlined';

const PostsCard = ({item}) => {
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const auth = useSelector(store => store.auth)
    const [isMore, setIsMore] = useState(false)

    const navigate = useNavigate()
    const dispatch = useDispatch()
    const handleProfile = () => {
        navigate(`/profile/${item?.user?.id}`)
    }

    const handleBlockUser = () => {
        dispatch(hiddenUser(item.id))
    }

    const handleHiddenPost = () => {
        dispatch(hiddenPosts(item.id))
        console.log("item.id:", item.id)
    }

    const handleLikePost = () => {
        dispatch(likePosts(item?.id))
    }

    const handleRemoveLikePost = () => {
        dispatch(removelikePosts(item?.id))
    }

    return (
        <div className="flex w-full border-b-2 bg-white py-3 hover:bg-gray-50 transition duration-200">
            <Avatar
                onClick={handleProfile}
                className="mt-3 ml-3 border-2 border-green-400 shadow-sm"
                src={item?.user?.image}
            />
            <div className="flex flex-col pl-4 pr-8 w-full">
                <div className="flex justify-between">
                    <div className='flex space-x-3 items-center'>
                        <p className="font-bold text-xl ">{item?.user?.fullName}</p>
                        <p className="text-sm text-gray-400">{formatTimeDifference(item?.createdAt)}</p>
                    </div>
                    <MoreHorizOutlinedIcon className='text-right cursor-pointer' onClick={() => setIsMore(!isMore)} />
                    {isMore && (
                        <div className="absolute right-0 mt-5 w-40 bg-white shadow-lg rounded-lg p-2">
                            {item.user.id == auth.user.id ? (
                                <ul className="text-sm text-gray-700">
                                    <li className="px-4 py-2 hover:bg-gray-100 cursor-pointer">Edit</li>
                                    <li className="px-4 py-2 hover:bg-gray-100 cursor-pointer">Delete</li>
                                </ul>
                            ) : (
                                <ul className="text-sm text-gray-700">
                                    <li className="px-4 py-2 hover:bg-gray-100 cursor-pointer" 
                                        onClick={handleHiddenPost}
                                    >
                                        Hidden
                                    </li>
                                    <li className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
                                     onClick={handleBlockUser}
                                    >
                                        Block
                                    </li>
                                </ul>)
                            }

                        </div>
                    )}
                </div>
                <p className="mt-2 text-gray-800">{item?.content}</p>
                {item?.image && (
                    <img
                        src={item?.image}
                        className="w-full mt-3 rounded-lg h-60 object-cover shadow-md"
                        onClick={handleOpen}
                    />
                )}
                <div className="pt-4 flex justify-between items-center w-full">
                    <div className="flex space-x-6">
                        {item?.liked ?(
                            <div
                                onClick={handleRemoveLikePost}
                                className="text-pink-500 flex space-x-2 cursor-pointer hover:text-pink-600"
                            >
                                <span>{item?.totalLikes}</span>
                                <FavoriteOutlinedIcon />
                            </div>
                        ) : (
                            <div
                                onClick={handleLikePost}
                                className="text-gray-600 flex space-x-2 cursor-pointer hover:text-pink-500"
                            >
                                <span>{item?.totalLikes}</span>                                
                                <FavoriteBorderOutlinedIcon />
                            </div>
                            )}
                        <div
                            className="flex space-x-2 cursor-pointer text-gray-600 hover:text-blue-600"
                            onClick={handleOpen}
                        >
                            <span>{item?.totalComment}</span>
                            <ChatBubbleOutlineOutlinedIcon />
                        </div>
                        <ShareOutlinedIcon className="text-gray-600 hover:text-green-500 cursor-pointer" />
                    </div>
                    <BookmarkBorderOutlinedIcon className="text-gray-600 hover:text-yellow-500 cursor-pointer" />
                </div>
            </div>
            <CommentModal
                item={item}
                open={open}
                handleClose={handleClose}
            />
        </div>
    );
}

export default PostsCard;
