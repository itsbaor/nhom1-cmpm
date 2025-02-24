import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from 'react-redux';
import {persistor, store } from './Store/store'
import { GoogleOAuthProvider } from '@react-oauth/google';
import { ChatProvider } from './Component/Messages/ChatContext';
import { PersistGate } from 'redux-persist/integration/react';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
     <PersistGate loading={null} persistor={persistor}>
      <Provider store={store}>
          <GoogleOAuthProvider>
            <ChatProvider>
              <App/>
            </ChatProvider>
          </GoogleOAuthProvider>
      </Provider>
     </PersistGate>
    </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
