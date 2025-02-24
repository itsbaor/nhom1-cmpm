import React from "react";
//Hỗ trợ quản lý form
import { useFormik } from "formik";     
//Xác thực input
import * as Yup from "yup";
//Tạo UI cho input
import { TextField } from "@mui/material";
//Dùng để gửi action đăng nhập loginUser
import { useDispatch } from "react-redux";

import { loginUser } from "../../Store/Auth/action";
//Dùng để chuyển hướng trang sau khi đăng nhập thành công
import { useNavigate } from "react-router-dom";
//Hiển thị thông báo
import { toast } from "react-toastify";
import { ToastContainer } from "react-toastify";

//Định dạng của đối tượng này, có email và password phải nhập đúng định dạng và k bỏ trống
const validateSchema = Yup.object().shape({
  email: Yup.string().email("invalid email").required("Email is required"),
  password: Yup.string().required("Password is required"),
});

//Component đăng nhập, gửi action đăng nhập khi người dùng submit form
const Signin = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const formik = useFormik({
    //Khởi tạo giá trị ban đầu của form
    initialValues: {
      email: "",
      password: "",
    },
    //Xác thực input theo schema
    validateSchema,
    //Gửi action đăng nhập cùng values
    onSubmit: (values) => {
      dispatch(loginUser(values));
      //Hiển thị thông báo 
      toast.success("You login successfull!!!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
      });
      //Điều hướng về trang chủ
      navigate("/");
      console.log(values);
    },
  });
  return (
    <div className="w-full">
      {/* Hiển thị thông báo */}
      <ToastContainer />
      <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">
        Login
      </h2>
      {/* Form đăng nhập */}
      <form onSubmit={formik.handleSubmit} className="space-y-4 text-center">
        {/* Input email */}
        <TextField
          name="email"
          fullWidth
          type="email"
          label="email"
          variant="outlined"
          //Gán giá trị
          value={formik.values.email}
          // Cập nhật giá trị khi người dùng nhập
          onChange={formik.handleChange}
          // Kiểm tra đã rời khỏi ô nhập chưa
          onBlur={formik.handleBlur}
        />

        {/* Input password */}
        <TextField
          name="password"
          fullWidth
          type="password"
          label="password"
          variant="outlined"
          value={formik.values.password}
          onChange={formik.handleChange}
          onBlur={formik.handleBlur}
        />

        <button
          type="submit"
          className="w-28 py-2 mt-4 bg-blue-600 text-white rounded-lg hover:bg-blue-700 focus:outline-none "
        >
          Login
        </button>
      </form>
    </div>
  );
};

export default Signin;
