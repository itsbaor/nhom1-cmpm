import styles from "./searchMovie.module.scss";
import classNames from "classnames/bind";
import React from "react";
import { useViewPort } from "../hooks/useViewPort";
import { useDispatch, useSelector } from "react-redux";
import { useLocation } from "react-router-dom";
import { useEffect } from "react";
import { getSearchMovie } from "../store/actions";
import { getMovieDetail } from "../store/actions";

const cx = classNames.bind(styles);
const useQuery = () => new URLSearchParams(useLocation().search);
function SearchMovie(props) {
 
  const [windowSize] = useViewPort();
  const dispatch = useDispatch();
  const { SearchMovie } = useSelector((state) => state.inforMovies);
  const keywords1 = useQuery().get("keywords");

  useEffect(() => {
    if (keywords1) dispatch(getSearchMovie(keywords1));
  }, [dispatch, keywords1]);

  const handelMovie = (movie) => {
    dispatch(getMovieDetail(movie));
  };

  return (
    <div className={cx("wrapSearch")}>
      {SearchMovie && SearchMovie.length > 0 ? (
        <div
          className={cx("wrapSearchList")}
          style={{
            gridTemplateColumns: `repeat(${
              windowSize > 1200
                ? "5"
                : windowSize > 988
                ? "4"
                : windowSize > 768
                ? "3"
                : windowSize > 600
                ? "2"
                : "1"
            }, auto)`,
          }}
        >
          {SearchMovie.map((movie, index) => {
            const imageURL = movie.backdrop_path
              ? `https://image.tmdb.org/t/p/w500/${movie.backdrop_path} `
              : `https://image.tmdb.org/t/p/original/${movie.poster_path}`;
            if (movie.backdrop_path !== null && movie.media_type !== "person") {
              return (
                <div
                  className={cx("movieItem")}
                  onClick={() => handelMovie(movie)}
                  key={index}
                >
                  <img src={imageURL} />
                  <span>{movie.title || movie.name}</span>
                </div>
              );
            }
          })}
        </div>
      ) : (
        <h1 className={cx("movieErr")}>
          Your search for "{keywords1}" did not have any matches.
        </h1>
      )}
    </div>
  );
}

export default SearchMovie;
