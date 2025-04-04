import React, { useEffect, useState } from 'react';
import ChatBubbleOutlineOutlinedIcon from '@mui/icons-material/ChatBubbleOutlineOutlined';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import BookmarkBorderOutlinedIcon from '@mui/icons-material/BookmarkBorderOutlined';
import FavoriteOutlinedIcon from '@mui/icons-material/FavoriteOutlined';
import { Avatar } from '@mui/material';
import { formatTimeDifference } from '../../Utils/formatTimeDifferent';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { bookmarkPost, deletePosts, hiddenPosts, hiddenUser, likePosts, removelikePosts } from '../../Store/Posts/Action';
import CommentModal from './CommentModal';
import MoreHorizOutlinedIcon from '@mui/icons-material/MoreHorizOutlined';
import UpdatePosts from './UpdatePosts';
import { toast, ToastContainer } from 'react-toastify';
import { CLEAR_POST_SUCCESS } from '../../Store/Posts/ActionType';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import SharePostModal from './SharePostModal';
import BookmarkIcon from '@mui/icons-material/Bookmark';

const PostsCard = ({ item }) => {
    //Mở modal comment
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    //Mở modal update post
    const [isUpdatePost, setIsUpdatePost] = React.useState(false);
    const handleOpenUpdatePost = () => {
        setIsUpdatePost(true);
    }
    const handleCloseUpdatePost = () => setIsUpdatePost(false);

    //Mở tác vụ cua post
    const [anchorEl, setAnchorEl] = React.useState(null);
    const openMore = Boolean(anchorEl);
    const handleClickMore = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleCloseMore = () => {
        setAnchorEl(null);
    };

    //Mở modal share post
    const [shareOpen, setShareOpen] = React.useState(false);
    const handleOpenShare = () => setShareOpen(true);
    const handleCloseShare = () => setShareOpen(false);

    const { auth, post } = useSelector(store => store)
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const handleProfile = () => {
        navigate(`/profile/${item?.user?.id}`)
    }

    const handleBlockUser = () => {
        dispatch(hiddenUser(item.id))
        handleCloseMore()
    }

    const handleHiddenPost = () => {
        dispatch(hiddenPosts(item.id))
        handleCloseMore()
    }

    const handleLikePost = () => {
        dispatch(likePosts(item?.id))
    }

    const handleRemoveLikePost = () => {
        dispatch(removelikePosts(item?.id))
    }

    const handleBookmarkPost = () => {
        dispatch(bookmarkPost(item?.id))
    }

    const handleDeletePost = () => {
        dispatch(deletePosts(item?.id))
        handleCloseMore()
        toast.success("Delete Post Success!", {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "light",
        });
    }

    const handleNotifyUpdatePostSuccess = () => {
        toast.success("Update Post success!", {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "light",
        });
        dispatch({ type: CLEAR_POST_SUCCESS })
    }

    useEffect(() => {
        if (post.successMessage) {
            handleNotifyUpdatePostSuccess()
        }
    }, [post.successMessage])

    return (
        <div className="flex w-full border-b-2 bg-white py-3 hover:bg-gray-50 transition duration-200">
            <ToastContainer />
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
                    <Button
                        id="basic-button"
                        aria-controls={openMore ? 'basic-menu' : undefined}
                        aria-haspopup="true"
                        aria-expanded={openMore ? 'true' : undefined}
                        onClick={handleClickMore}
                    >
                        <MoreHorizOutlinedIcon className='text-right' />
                    </Button>
                    {item.user.id === auth.user.id ? (
                        <Menu
                            id="basic-menu"
                            anchorEl={anchorEl}
                            open={openMore}
                            onClose={handleCloseMore}
                            MenuListProps={{
                                'aria-labelledby': 'basic-button',
                            }}
                        >
                            <MenuItem onClick={handleOpenUpdatePost}>Edit</MenuItem>
                            <MenuItem onClick={handleDeletePost}>Delete </MenuItem>
                        </Menu>
                    ) : (
                        <Menu
                            id="basic-menu"
                            anchorEl={anchorEl}
                            open={openMore}
                            onClose={handleCloseMore}
                            MenuListProps={{
                                'aria-labelledby': 'basic-button',
                            }}
                        >
                            <MenuItem onClick={handleHiddenPost}> Hidden</MenuItem>
                            <MenuItem onClick={handleBlockUser}>Block</MenuItem>
                        </Menu>
                    )
                    }


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
                        {item?.liked ? (
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
                        <div
                            className="flex space-x-2 text-gray-600 hover:text-green-500 cursor-pointer"
                            onClick={handleOpenShare}
                        >
                            <span>{item?.totalShare}</span>
                            <ShareOutlinedIcon />
                        </div>
                        
                    </div>
                    {item?.bookmarked ? (
                            <div
                                onClick={handleBookmarkPost} 
                               className="text-gray-600 hover:text-yellow-500 cursor-pointer"
                            >
                                <span>{item?.totalBookmark}</span>
                                <BookmarkIcon />
                            </div>
                        ) : (
                            <div
                                className="text-gray-600 hover:text-yellow-500 cursor-pointer"
                                onClick={handleBookmarkPost}
                            >
                                <span>{item?.totalBookmark}</span>
                                <BookmarkBorderOutlinedIcon />
                            </div>
                        )}
                </div>
            </div>
            <CommentModal
                item={item}
                open={open}
                handleClose={handleClose}
            />

            <UpdatePosts
                item={item}
                open={isUpdatePost}
                handleClose={handleCloseUpdatePost}
            />

            <SharePostModal
                item={item}
                open={shareOpen}
                handleClose={handleCloseShare}
            />
        </div>
    );
}

export default PostsCard;
