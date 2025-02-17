// actions.js
export const INCREMENT = "INCREMENT";
export const DECREMENT = "DECREMENT";
export const SELECT_PLACE = "SELECT_PLACE";

export const increment = () => ({
  type: INCREMENT,
});

export const decrement = () => ({
  type: DECREMENT,
});
export const selectPlace = (place) => ({
  type: SELECT_PLACE,
  payload: place,
});
