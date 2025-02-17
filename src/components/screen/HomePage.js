import React, { useEffect, useState } from "react";
import Menu from "../menu/Menu";
import MapItem from "../mapItem/MapItem";

function HomePage(props) {
  return (
    <div
      style={{
        flexDirection: "row",
        display: "flex",
        backgroundColor: "#11101d",
      }}
    >
      <Menu />
      <MapItem />
    </div>
  );
}

export default HomePage;
