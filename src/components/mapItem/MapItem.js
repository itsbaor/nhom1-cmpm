import React, { useEffect, useRef, useState } from "react";
import { MapContainer, Marker, Popup, TileLayer, useMap } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import { useSelector } from "react-redux";
import CustomMarker from "./CustomMarker";

const MapItem = () => {
  let popupRef = useRef();

  const [listPlace, setListPlace] = useState(null);
  const selectedPlace = useSelector((state) => state.selectedPlace);
  const [centerView, setCenterView] = useState([51.505, -0.09]);
  const ResetCenterView = ({ selectPosition }) => {
    const map = useMap();
    useEffect(() => {
      if (selectPosition) {
        if (selectPosition.locations.length > 0) {
          selectPosition.locations.map((item) => {
            map.openPopup(item.name, [item.lat, item.long]);
          });

          const dataList = selectPosition.locations;
          const lastPosition = dataList[dataList.length - 1];
          map.setView(
            L.latLng(lastPosition.lat, lastPosition.long),
            map.getZoom(),
            {
              animate: true,
            }
          );
        }
      }
    }, [selectPosition, map]);

    return null;
  };
  useEffect(() => {
    setListPlace(selectedPlace);
    if (popupRef.current !== undefined) {
      // regardless of the zoom value the popups stay open Anyways which is another Problem
      // it's suppose to only open when the zoom is >12 but that's not the Case
      console.log(1);
      popupRef.current.openPopup();
    }
  }, [selectedPlace]);

  useEffect(() => {
    if (listPlace) {
      if (listPlace.locations.length > 0) {
        const dataList = listPlace.locations;
        const lastPosition = dataList[dataList.length - 1];
        setCenterView([lastPosition.lat, lastPosition.long]);
        // map.setCenterView(centerView);
      }
    }
  }, [selectedPlace]);

  return (
    <MapContainer
      center={centerView}
      zoom={15}
      style={{ height: "100vh", width: "100%" }}
    >
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://api.maptiler.com/maps/basic/256/{z}/{x}/{y}.png?key=BmtYmS9VcBcxo2ju8MLd"
      />

      {listPlace &&
        listPlace?.locations?.map((item, index) => (
          <Marker
            isActive
            position={[item.lat, item.long]}
            icon={L.icon({
              iconUrl: listPlace.icon,
              iconSize: [60, 60],
            })}
          >
            <Popup ref={popupRef}>{item.name}</Popup>
          </Marker>
        ))}
      <ResetCenterView selectPosition={listPlace} />
    </MapContainer>
  );
};

export default MapItem;
