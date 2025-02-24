import { Avatar, Button } from '@mui/material';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import SendOutlinedIcon from '@mui/icons-material/SendOutlined';
import PostsCard from './PostsCard';
import ImageOutlinedIcon from '@mui/icons-material/ImageOutlined';
import * as Yup from 'yup'
import { useFormik } from 'formik';
import { uploadToCloudinary } from '../../Utils/uploadToCloudinary';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { createPosts, getAllPosts } from '../../Store/Posts/Action';
import { initializeWebSocket } from '../../config/websocket';
import { useChat } from '../Messages/ChatContext';
import { updateUserStatus } from '../../Store/Auth/action';
import InfiniteScroll from 'react-infinite-scroll-component';

const validateSchema = Yup.object().shape({
    content: Yup.string().required("Text is required")
})
const HomeSection = () => {
    const [uploadingImage, setUploadingImage] = useState(false)
    const [selectImage, setSelectImage] = useState("")
    const [lastCreatedAt, setLastCreatedAt] = useState(null)
    const [hasMore, setHasMore] = useState(true); 

    const post = useSelector(state => state.post);
    const auth = useSelector(state => state.auth);
    const dispatch = useDispatch()

    const handleImageChange = async (event) => {
        setUploadingImage(true)
        const imgUrl = await uploadToCloudinary(event.target.files[0])
        formik.setFieldValue("image", imgUrl) // cho url vao
        setSelectImage(imgUrl) // cho url vao
        setUploadingImage(false)
    }

    const formik = useFormik({
        initialValues:{
            content:"",
            image:"",
        },
        validateSchema,
        onSubmit: (values,action) => {
            dispatch(createPosts(values))
            action.resetForm()
            setSelectImage("")
        }
    })

    useEffect(() => {
        console.log("amount post: ",post?.posts?.length)
        if( post.posts.length === 0 ){
            dispatch(getAllPosts())
        }
    }, [auth?.jwt,dispatch]); 

    useEffect(() => {
        if (!post?.posts || post.posts.length === 0) return;

        if (post?.posts.length % 5 !== 0 && hasMore) {
            setHasMore(false);
        }
        const lastPostTime = post?.posts[post?.posts?.length - 1]?.createdAt;
        console.log("after fetch post: ", post?.posts)
        console.log("last created at: ",lastPostTime)
        if (lastCreatedAt !== lastPostTime && lastPostTime !== undefined) {
            setLastCreatedAt(lastPostTime); // Lưu timestamp bài cuối cùng
        }
    }, [post?.posts]);
    
    return (
        <div className="border-l border-r min-h-screen bg-gray-100">
            <div className="w-full py-3 border-b-8 bg-white shadow-md">
                <section className="font-bold text-xl pl-3 border-b pb-2">Home</section>
                <section className="flex pt-3 pl-5">
                    <Avatar
                        className="mt-3 border-2 border-blue-500 mr-2"
                        src={auth?.user?.image}
                    />
                    <form
                        onSubmit={formik.handleSubmit}
                        className="mr-3 w-full bg-white rounded-lg p-4 shadow-lg"
                    >
                        <input
                            type="text"
                            name="content"
                            className="w-full border-none outline-none text-xl bg-gray-100 p-3 rounded-lg focus:ring-2 focus:ring-blue-400"
                            placeholder="Write your status here?"
                            {...formik.getFieldProps("content")}
                        />
                        {formik.errors.content && formik.touched.content && (
                            <span className="text-red-500">{formik.errors.content}</span>
                        )}
                        <hr className="mt-5 w-72 bg-gray-300 h-[1px]" />
                        <label className="cursor-pointer flex items-center space-x-2 mt-4 text-blue-600 hover:text-blue-800">
                            <ImageOutlinedIcon />
                            <span>Upload an Image</span>
                            <input
                                onChange={handleImageChange}
                                type="file"
                                name="file"
                                className="hidden"
                            />
                        </label>
                        <button
                            type="submit"
                            className="float-right mt-3 mr-10 bg-blue-500 text-white px-4 py-2 rounded-lg shadow-md hover:bg-blue-600"
                        >
                            <SendOutlinedIcon fontSize="small" />
                        </button>
                    </form>
                </section>
                <div className="flex justify-center mt-3">
                    {selectImage && (
                        <img
                            width={200}
                            src={selectImage}
                            className="rounded-lg border-2 border-blue-400"
                        />
                    )}
                </div>
            </div>
            <section>
                <InfiniteScroll
                dataLength={post?.posts?.length}
                hasMore={hasMore}
                next={() => dispatch(getAllPosts(lastCreatedAt))}
                loader={<h4>Loading...</h4>}
                >
                    {post?.posts?.map((item) => (
                        <PostsCard key={item.id} item={item} />
                    ))}
                </InfiniteScroll> 
            </section>
        </div>
    );
}

export default HomeSection;
