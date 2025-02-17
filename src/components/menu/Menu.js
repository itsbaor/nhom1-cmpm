import React, { useState } from "react";
import styles from "./Menu.module.scss";
import classNames from "classnames/bind";
import "boxicons/css/boxicons.min.css";
import logo from "../../assets/images/logo-wep.png";
import avatar from "../../assets/images/image-avtar.jpg";
import { useDispatch } from "react-redux";
// import { getListPlace } from "../store/actions";
import { selectPlace } from "../../storeContext/action";
import imageMarker from "../../assets/images/placeholder.png";

import dataCategory from "../../assets/data/dataCategory";

const cx = classNames.bind(styles);

const Menu = () => {
  const dispatch = useDispatch();
  const [isOpen, setIsOpen] = useState(true);
  const [disPlayInput, setDisPlayInput] = useState(false);
  const [dataInput, setDataInput] = useState("");
  const [listSearch, setListSearch] = useState([]);
  const NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org/search?";
  const handleChangeItem = (item) => {
    if (item) {
      const params = {
        q: item,
        format: "json",
        addressdetails: 1,
        polygon_geojson: 0,
      };
      const queryString = new URLSearchParams(params).toString();
      const requestOptions = {
        method: "GET",
        redirect: "follow",
      };
      fetch(`${NOMINATIM_BASE_URL}${queryString}`, requestOptions)
        .then((response) => response.text())
        .then((result) => {
          const response = JSON.parse(result);
          if (response.length > 0) {
            setDisPlayInput(true);
            setListSearch(response);
          }
        })
        .catch((err) => console.log("err: ", err));
    }
  };

  const handleGetLocation = (item) => {
    const dataNewPlace = {
      icon: imageMarker,
      text: "New Place",
      locations: [
        {
          name: item.name,
          lat: parseFloat(item.lat),
          long: parseFloat(item.lon),
        },
      ],
    };
    dispatch(selectPlace(dataNewPlace));
    setDisPlayInput(false);
  };
  return (
    <div className={cx("sidebar", { open: isOpen })}>
      <div className={cx("logo-details")}>
        <img src={logo} width={50} />
        <div className={cx("logo_name")}>Tour Map</div>
        <i className={`bx bx-menu`} id="btn"></i>
      </div>
      <div className={cx("line")} />
      <li>
        <i
          onClick={() => handleChangeItem(dataInput)}
          style={{
            position: "absolute",
            right: 0,
            cursor: "pointer",
            backgroundColor: "#1c9e9e",
          }}
          className="bx bx-search btn-search"
        ></i>
        <input
          type="text"
          placeholder="Search your place..."
          onChange={(event) => setDataInput(event.target.value)}
        />
        <span className={cx("tooltip")}>Search</span>
        <ul
          style={{
            display: disPlayInput ? "block" : "none",
            marginTop: 5,
            backgroundColor: "#1c9e9e",
            position: "absolute",
            zIndex: "3333",
            width: "100%",
            borderRadius: 12,
            padding: 4,
            // gap: 8,
          }}
        >
          {listSearch.length > 0 &&
            listSearch.map((item) => (
              <li
                className={cx("item-search")}
                onClick={() => handleGetLocation(item)}
              >
                {item.name}
              </li>
            ))}
        </ul>
      </li>
      <ul className={cx("nav-list")}>
        <b style={{ fontSize: 20, marginBottom: 24 }}>Ha Noi</b>
        <div className={cx("wrap-list")}>
          {dataCategory.map((item) => (
            <div
              onClick={() => dispatch(selectPlace(item))}
              className={cx("list-item")}
            >
              <img
                style={{
                  width: 45,
                }}
                src={item.icon}
              />
              <div
                style={{
                  textAlign: "center",
                }}
              >
                {item.text}
              </div>
            </div>
          ))}
        </div>

        <b style={{ fontSize: 20, marginTop: 24 }}>Calculate Distance</b>

        <li className={cx("profile")}>
          <div className={cx("profile-details")}>
            <img src={avatar} alt="profileImg" />
            <div className={cx("name_job")}>
              <div className={cx("name")}>User 1</div>
              <div className={cx("job")}>Viet Nam</div>
            </div>
          </div>
          <i className="bx bx-log-out" id="log_out"></i>
        </li>
      </ul>
    </div>
  );
};

export default Menu;
