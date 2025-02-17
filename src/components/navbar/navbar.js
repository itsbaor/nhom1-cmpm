import classNames from "classnames/bind";
import styles from "./navbar.module.scss";
import netflixImage from "../../assets/images/netflixImage.png";
import logoWeb from "../../assets/images/logo-wep.png";

import { MdSearch } from "react-icons/md";
import { UseScrollY } from "../hooks";
import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
const cx = classNames.bind(styles);

function Navbar({ props }) {
  const Test = useLocation();
  const checkURL = Test.pathname === "/" || Test.pathname === "/movieMM";
  const [scrollY] = UseScrollY();
  const [keywords, setKeywords] = useState("");
  const nav = useNavigate();

  const handelInput = (e) => {
    let keyword = e.target.value;
    setKeywords(keyword);
  };
  const goHome = () => {
    nav("/home");
    setKeywords("");
  };
  useEffect(() => {
    if (checkURL) {
      nav("/");
    } else {
      keywords.length > 0
        ? nav(`/search?keywords=${keywords.trim()}`)
        : nav("/home");
    }
  }, [keywords]);

  return (
    <div className={cx("nav")} style={{ display: checkURL ? "none" : "block" }}>
      <div
        className={cx("navContainer")}
        style={
          scrollY < 50
            ? { backgroundColor: "transparent" }
            : { backgroundColor: "black" }
        }
      >
        <div className={cx("logo")} onClick={goHome}>
          <img src={logoWeb} />
        </div>
        <div className={cx("navSearch")}>
          <MdSearch className={cx("iconsearch")} />
          <input
            type="text"
            placeholder="input......."
            onChange={handelInput}
            value={keywords}
          />
        </div>
      </div>
    </div>
  );
}
export default Navbar;
