import { LOGOUT_USER } from "../Auth/ActionType";
import { COMMENT_POST_FAILURE, COMMENT_POST_SUCCESS, GET_ALL_POSTS_FAILURE, GET_ALL_POSTS_SUCCESS, GET_LIKE_POST_FAILURE, GET_LIKE_POST_SUCCESS, GET_POSTS_BY_ID_SUCCESS, GET_USER_POSTS_FAILURE, GET_USER_POSTS_SUCCESS, HIDDEN_POST_FAILURE, HIDDEN_POST_SUCCESS, HIDDEN_USER_FAILURE, HIDDEN_USER_SUCCESS, LIKE_COMMENT_FAILURE, LIKE_COMMENT_SUCCESS, LIKE_POST_FAILURE, LIKE_POST_SUCCESS, POST_CREATE_FAILURE, POST_CREATE_SUCCESS, REPLIES_COMMENT_FAILURE, REPLIES_COMMENT_SUCCESS } from "./ActionType";

const initialState = {
    user:null,
    post:null,
    posts:[],
    error:null,
    like:null,
    postsUser:[]
}

export const PostsReducer = (state=initialState, action) => {
    switch (action.type) {
        case POST_CREATE_FAILURE:
        case GET_ALL_POSTS_FAILURE:
        case GET_USER_POSTS_FAILURE:
        case LIKE_POST_FAILURE:
        case GET_LIKE_POST_FAILURE:
        case COMMENT_POST_FAILURE:
        case GET_ALL_POSTS_FAILURE:
        case LIKE_COMMENT_FAILURE:
        case REPLIES_COMMENT_FAILURE:
        case HIDDEN_POST_FAILURE:
        case HIDDEN_USER_FAILURE:
            return {...state, error: action.payload}
        case GET_ALL_POSTS_SUCCESS:
            return {...state,error:null, posts: [...state.posts,...action.payload]}
        case POST_CREATE_SUCCESS:
            return {...state,error:null, posts: [action.payload,...state.posts]}
        case GET_USER_POSTS_SUCCESS:
            return {...state,error:null, postsUser: action.payload}
        case LIKE_POST_SUCCESS:
        case LIKE_COMMENT_SUCCESS:
        case REPLIES_COMMENT_SUCCESS :
            return {...state,error:null, posts: state.posts.map((post) =>
                                post.id === action.payload.id ? action.payload : post)}
        case GET_POSTS_BY_ID_SUCCESS:
            return {...state,error:null, post: action.payload}
        case HIDDEN_POST_SUCCESS:
        case HIDDEN_USER_SUCCESS:
            return {...state, error:null, posts: state.posts.filter((post) => post.id !== action.payload.id) }
        case COMMENT_POST_SUCCESS:
            return {...state,error:null, posts: state.posts.map((post) =>
                post.id === action.payload.id ? action.payload : post)}
        case LOGOUT_USER:
            return initialState
        default:
            return state;
    }
}