import * as React from "react";
import Modal from "@mui/material/Modal";
import { Avatar, Box, Button, TextField } from "@mui/material";
import { useFormik } from "formik";
import { useDispatch, useSelector } from "react-redux";
import { updateUser } from "../../Store/Auth/action";
import { IconButton } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { uploadToCloudinary } from "../../Utils/uploadToCloudinary";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 600,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

export default function ProfileModal({ open, handleClose }) {
  const [selectedImage, setSelectedImage] = React.useState("");
  const [uploading, setUploading] = React.useState(false);

  const formik = useFormik({
    initialValues: {
      firstName: "",
      lastName: "",
      location: "",
      birthOfDate: "",
      bio: "",
      numberPhone: "",
      image: "",
      backgroundImage: "",
    },
    onSubmit: (values) => {
      console.log("updateuser form: ", values);
      dispatch(updateUser(values));
    },
  });

  const { auth } = useSelector((store) => store);
  const dispatch = useDispatch();

  const handleImageChange = async (event) => {
    setUploading(true);
    const { name } = event.target;
    const file = await uploadToCloudinary(event.target.files[0]);
    formik.setFieldValue(name, file);
    setSelectedImage(file);
    setUploading(false);
  };
  return (
    <div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <form onSubmit={formik.handleSubmit}>
            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-3">
                <IconButton onClick={handleClose} arial-label="delete">
                  <CloseIcon />
                </IconButton>
                <p>Edit profile</p>
              </div>
              <Button type="submit">Save</Button>
            </div>
            <div className="hideScrollBar">
              <React.Fragment>
                <div className="w-full">
                  <div className="relative">
                    <img
                      className="w-full h-[12rem] object-cover object-center"
                      src={auth?.findUser?.backgroundImage || selectedImage}
                      alt=""
                    />
                    <input
                      className="absolute top-0 left-0 w-full h-full opacity-0 cursor-pointer"
                      type="file"
                      name="backgroundImage"
                      onChange={handleImageChange}
                    />
                  </div>
                </div>
                <div className="w-full transform -translate-y-20 ml-4 h-[6rem]">
                  <div className="relative">
                    <Avatar
                      sx={{
                        width: "10rem",
                        height: "10rem",
                        border: "4px solid white",
                      }}
                      src={selectedImage || auth?.findUser?.image}
                    />
                    <input
                      className="absolute top-0 left-0 w-[10rem] h-full opacity-0 cursor-pointer"
                      type="file"
                      name="image"
                      onChange={handleImageChange}
                    />
                  </div>
                </div>
              </React.Fragment>
              <div className="space-y-3">
                <TextField
                  id="firstName"
                  label="firstName"
                  name="firstName"
                  fullWidth
                  variant="outlined"
                  value={formik.values.firstName || auth?.findUser?.firstName}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  error={
                    formik.touched.firstName && Boolean(formik.errors.firstName)
                  }
                />
                <TextField
                  id="lastName"
                  label="lastName"
                  name="lastName"
                  fullWidth
                  variant="outlined"
                  value={formik.values.lastName || auth?.findUser?.lastName}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  error={
                    formik.touched.lastName && Boolean(formik.errors.lastName)
                  }
                />
                <TextField
                  id="location"
                  label="location"
                  name="location"
                  fullWidth
                  variant="outlined"
                  value={formik.values.location || auth?.findUser?.location}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  error={
                    formik.touched.location && Boolean(formik.errors.location)
                  }
                />
                <TextField
                  id="numberPhone"
                  label="numberPhone"
                  name="numberPhone"
                  fullWidth
                  variant="outlined"
                  value={
                    formik.values.numberPhone || auth?.findUser?.numberPhone
                  }
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  error={
                    formik.touched.numberPhone &&
                    Boolean(formik.errors.numberPhone)
                  }
                />
                <TextField
                  id="bio"
                  label="bio"
                  name="bio"
                  fullWidth
                  variant="outlined"
                  value={formik.values.bio || auth?.findUser?.bio}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  error={formik.touched.bio && Boolean(formik.errors.bio)}
                />
              </div>
            </div>
          </form>
        </Box>
      </Modal>
    </div>
  );
}
