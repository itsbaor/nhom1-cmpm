import React from "react";
import styles from "./Login.module.scss";
import classNames from "classnames/bind";
import { FiUserMinus } from "react-icons/fi";
import { RiLockPasswordLine } from "react-icons/ri";
import { BsFacebook } from "react-icons/bs";

import { AiFillGoogleCircle, AiFillTwitterCircle } from "react-icons/ai";
import { useNavigate } from "react-router-dom";

const cx = classNames.bind(styles);

function Login(props) {
  const history = useNavigate();

  const handleRt = () => {
    // setTimeout(() => {
    //   history("/home");
    // }, 1000);
  };
  const handleSignUp = () => {
    // setTimeout(() => {
    //   history("/signup");
    // }, 1000);
  };
  return (
    <div className={cx("wrapperForm")}>
      <div className={cx("formItem")}>
        <div className={cx("formHeader")}>Login</div>

        <div className={cx("formInfor")}>
          <span className={cx("infor")}>Username</span>

          <div className={cx("input-wr")}>
            <FiUserMinus className={cx("input-icon")} />
            <input
              className={cx("input")}
              placeholder="your name........"
              type="text"
              value="user1"
            />
          </div>

          <span className={cx("infor")}>PassWord</span>

          <div className={cx("input-wr")}>
            <RiLockPasswordLine className={cx("input-icon")} />
            <input
              className={cx("input")}
              placeholder="your password ......"
              type="password"
              value="user1"
            />
          </div>

          <h6>Forgot password ?</h6>
        </div>

        <button className={cx("loginButton")} onClick={handleRt}>
          Login
        </button>
        <span className={cx("loginDiffrent")}>Or Sign Up Using</span>

        <div className={cx("contact")}>
          <BsFacebook className={cx("contact-icon-fb")} />
          <AiFillTwitterCircle className={cx("contact-icon-tw")} />
          <AiFillGoogleCircle className={cx("contact-icon-gg")} />
        </div>

        <div className={cx("footer")}>
          <span className={cx("footer-ask")}>Have not account yet?</span>
          <h4 onClick={handleSignUp}>SIGN UP</h4>
        </div>
      </div>
    </div>
  );
}

export default Login;