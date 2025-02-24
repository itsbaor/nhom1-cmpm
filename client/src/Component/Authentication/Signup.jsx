//Tạo UI
import {
  Button,
  Grid2,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
//Hỗ trợ quản lý form
import { useFormik } from "formik";

import React from "react";
//Dùng để gửi action đăng nhập loginUser
import { useDispatch } from "react-redux";
//Xác thực input
import * as Yup from "yup";

import { registerUser } from "../../Store/Auth/action";
//Màu nền cho modal
import { blue } from "@mui/material/colors";
//Thông báo toast
import { toast, ToastContainer } from "react-toastify";

//Định dạng của đối tượng này, có email và password phải nhập đúng định dạng và k bỏ trống
const validateSchema = Yup.object().shape({
  email: Yup.string().email("invalid email").required("Email is required"),
  password: Yup.string().required("Password is required"),
});

//Tạo danh sách các biến để làm ngày tháng năm sinh
const currentYear = new Date().getFullYear();
const years = Array.from({ length: 100 }, (_, i) => currentYear - i);
const days = Array.from({ length: 31 }, (_, i) => i + 1);
const months = [
  { value: 1, label: "January" },
  { value: 2, label: "February" },
  { value: 3, label: "March" },
  { value: 4, label: "April" },
  { value: 5, label: "May" },
  { value: 6, label: "June" },
  { value: 7, label: "July" },
  { value: 8, label: "August" },
  { value: 9, label: "September" },
  { value: 10, label: "October" },
  { value: 11, label: "November" },
  { value: 12, label: "December" },
];

//Component đăng kí, gửi action đăng kí khi người dùng submit form
const Signup = () => {
  const dispatch = useDispatch();

  const formik = useFormik({
    //Dữ liệu ban đầu của form
    initialValues: {
      lastName: "",
      firstName: "",
      email: "",
      password: "",
      dateOfBirth: {
        day: "",
        month: "",
        year: "",
      },
      image:
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png",
    },
    //Xác thực input theo schema
    validateSchema,
    onSubmit: (values, action) => {
      const { day, month, year } = values.dateOfBirth;
      const dateOfBirth = `${year} - ${month} - ${day}`;
      values.dateOfBirth = dateOfBirth;
      dispatch(registerUser(values));
      toast.success("You signup success!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
      });
      action.resetForm();
      console.log("values:", values);
    },
  });

  //Cập nhật giá trị ngày tháng năm sinh
  const handleDateChange = (name) => (event) => {
    formik.setFieldValue("dateOfBirth", {
      ...formik.values.dateOfBirth,
      [name]: event.target.value,
    });
  };
  return (
    <div className="w-full">
      {/* Hiển thị thông báo */}
      <ToastContainer />
      
      <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">
        SignUp
      </h2>

      {/* Form đăng kí */}
      <form onSubmit={formik.handleSubmit} className="space-y-4 text-center">
        <Grid2 container spacing={2}>
          {/* Nhập Họ, tên, email, password */}
          <Grid2 item size={{ xs: 12 }}>
            <TextField
              fullWidth
              label="Last Name"
              name="lastName"
              variant="outlined"
              size="large"
              //Gán giá trị
              value={formik.values.lastName}
              //Cập nhật giá trị khi người dùng nhập
              onChange={formik.handleChange}
              //Kiểm tra đã rời khỏi ô nhập chưa
              onBlur={formik.handleBlur}
              //Kiểm tra ô nhập có lỗi không, nếu lỗi chuyển màu ô nhập sang màu lỗi
              error={formik.touched.lastName && Boolean(formik.errors.lastName)}
              //Nếu có lỗi hiển thị nội dung lỗi bên dưới ô nhập
              helperText={formik.touched.lastName && formik.errors.lastName}
            />
          </Grid2>
          <Grid2 item size={{ xs: 12 }}>
            <TextField
              fullWidth
              label="First Name"
              name="firstName"
              variant="outlined"
              size="large"
              value={formik.values.firstName}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={
                formik.touched.firstName && Boolean(formik.errors.firstName)
              }
              helperText={formik.touched.firstName && formik.errors.firstName}
            />
          </Grid2>
          <Grid2 item size={{ xs: 12 }}>
            <TextField
              fullWidth
              label="Email"
              name="email"
              variant="outlined"
              size="large"
              value={formik.values.email}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={formik.touched.email && Boolean(formik.errors.email)}
              helperText={formik.touched.email && formik.errors.email}
            />
          </Grid2>
          <Grid2 item size={{ xs: 12 }}>
            <TextField
              fullWidth
              label="Password"
              name="password"
              variant="outlined"
              size="large"
              type="password"
              value={formik.values.password}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}

              error={formik.touched.password && Boolean(formik.errors.password)}
              helperText={formik.touched.password && formik.errors.password}
            />
          </Grid2>

          {/* Chọn ngày tháng năm sinh */}
          <Grid2 item size={{ xs: 4 }}>
            <InputLabel>Date</InputLabel>
            <Select
              name="day"
              fullWidth
              // Cập nhật giá trị khi người dùng chọn
              onChange={handleDateChange("day")}
              // Kiểm tra lỗi
              onBlur={formik.handleBlur}
              value={formik.values.dateOfBirth.day}
            >
              Duyệt qua mảng days để lấy giá trị chọn
              {days.map((day) => (
                <MenuItem key={day} value={day}>
                  {day}
                </MenuItem>
              ))}
            </Select>
          </Grid2>
          <Grid2 item size={{ xs: 4 }}>
            <InputLabel>Month</InputLabel>
            <Select
              name="month"
              fullWidth
              onChange={handleDateChange("month")}
              onBlur={formik.handleBlur}
              value={formik.values.dateOfBirth.month}
            >
              {months.map((month) => (
                <MenuItem key={month.value} value={month.value}>
                  {month.label}
                </MenuItem>
              ))}
            </Select>
          </Grid2>
          <Grid2 item size={{ xs: 4 }}>
            <InputLabel>Year</InputLabel>
            <Select
              name="Year"
              fullWidth
              onChange={handleDateChange("year")}
              onBlur={formik.handleBlur}
              value={formik.values.dateOfBirth.year}
            >
              {years.map((year) => (
                <MenuItem key={year} value={year}>
                  {year}
                </MenuItem>
              ))}
            </Select>
          </Grid2>
          <Grid2 className="mt-10" item size={{ xs: 12 }}>
            <Button
              sx={{ borderRadius: "29px", py: "15px", bgcolor: blue }}
              type="submit"
              fullWidth
              variant="contained"
              size="large"
            >
              Signup
            </Button>
          </Grid2>
        </Grid2>
      </form>
    </div>
  );
};

export default Signup;
