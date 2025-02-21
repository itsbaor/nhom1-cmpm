import { GET_ALL_POSTS_FAILURE } from "../Posts/ActionType";
import { ACCEPT_REQUEST_ADD_FRIEND_FAILURE, ACCEPT_REQUEST_ADD_FRIEND_SUCCESS, GET_ALL_LIST_FRIEND_FAILURE, GET_ALL_LIST_FRIEND_SUCCESS, GET_ALL_REQUEST_ADD_FRIEND_FAILURE, GET_ALL_REQUEST_ADD_FRIEND_SUCCESS, GET_ALL_SEND_REQUEST_ADD_FRIEND_FAILURE, GET_ALL_SEND_REQUEST_ADD_FRIEND_SUCCESS, GET_ALL_USER_LIST_FAILURE, GET_ALL_USER_LIST_SUCCESS, GET_PROFILE_USER_ID_FAILURE, GET_PROFILE_USER_ID_SUCCESS, GET_PROFILE_USER_JWT_FAILURE, GET_PROFILE_USER_JWT_SUCCESS, LOGIN_USER_FAILURE, LOGIN_USER_SUCCESS, LOGOUT_USER, LOGOUT_USER_SUCCESS, REFUSE_REQUEST_ADD_FRIEND_FAILURE, REFUSE_REQUEST_ADD_FRIEND_SUCCESS, REGISTER_USER_FAILURE, REGISTER_USER_SUCCESS, REQUEST_ADD_FRIEND_FAILURE, REQUEST_ADD_FRIEND_SUCCESS, UNFRIEND_FAILURE, UNFRIEND_SUCCESS, UPDATE_STATUS_USER_FAILURE, UPDATE_STATUS_USER_SUCCESS, UPDATE_USER_FAILURE, UPDATE_USER_SUCCESS } from "./ActionType";

const initialState = {
    user:null,
    error:null,
    jwt:null,
    listUser:[],
    requestFriend:null,
    listRequestFriend:[],
    listSendRequestFriend:[],
    listFriend:[]
}

export const authReducer = (state = initialState, action) => {
    switch (action.type) {
        case REGISTER_USER_FAILURE:
        case LOGIN_USER_FAILURE:
        case GET_PROFILE_USER_JWT_FAILURE:
        case UPDATE_USER_FAILURE:
        case GET_PROFILE_USER_ID_FAILURE:
        case GET_ALL_USER_LIST_FAILURE:
        case UPDATE_STATUS_USER_FAILURE:
        case REQUEST_ADD_FRIEND_FAILURE:
        case ACCEPT_REQUEST_ADD_FRIEND_FAILURE:
        case REFUSE_REQUEST_ADD_FRIEND_FAILURE:
        case GET_ALL_REQUEST_ADD_FRIEND_FAILURE:
        case UNFRIEND_FAILURE:
        case GET_ALL_SEND_REQUEST_ADD_FRIEND_FAILURE:
        case GET_ALL_LIST_FRIEND_FAILURE:
            return {...state, error:action.payload}
        case LOGIN_USER_SUCCESS:
            return {...state, error:null, jwt:action.payload}
        case LOGOUT_USER:
            return initialState
        case GET_PROFILE_USER_JWT_SUCCESS:
            return {...state,error:null, user:action.payload}
        case GET_PROFILE_USER_ID_SUCCESS:
            return {...state, error:null, findUser: action.payload}
        case UPDATE_USER_SUCCESS:
            return {...state, error:null, findUser: action.payload, user:action.payload}
        case GET_ALL_USER_LIST_SUCCESS:
            return {
                ...state,
                error: null,
                listUser: action.payload, // Ghi đè toàn bộ danh sách
            };
        
        case UPDATE_STATUS_USER_SUCCESS:
            return {
                ...state,
                error: null,
                listUser: state.listUser.map((user) =>
                    user.id === action.payload.id ? action.payload : user
                ), // Cập nhật trạng thái user
            };
        case REQUEST_ADD_FRIEND_SUCCESS:
            return { ...state, error:null, requestFriend: action.payload}
        case ACCEPT_REQUEST_ADD_FRIEND_SUCCESS:
            return { ...state, error:null, requestFriend: action.payload}
        case REFUSE_REQUEST_ADD_FRIEND_SUCCESS:
            return { ...state, error:null, requestFriend: action.payload}
        case GET_ALL_REQUEST_ADD_FRIEND_SUCCESS:
            return { ...state, error:null, listRequestFriend: action.payload}
        case GET_ALL_SEND_REQUEST_ADD_FRIEND_SUCCESS:
            return { ...state, error:null, listSendRequestFriend: action.payload}
        case UNFRIEND_SUCCESS:
            return { ...state, error:null, deleteFriend: action.payload}
        case GET_ALL_LIST_FRIEND_SUCCESS:
            return { ...state, error:null, listFriend: action.payload}
        default:
            return state;
    }
}